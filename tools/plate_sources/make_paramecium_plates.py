#!/usr/bin/env python3
"""Original vector schematics for two Paramecium plates (no image model, no text on art).

    python tools/plate_sources/make_paramecium_plates.py [outdir]

Writes locomotion.png and binary_fission.png (2400 px wide, white background) with
NUMBERED markers only; English and Tamil legends belong in the app. Schematic, not to scale.
Author: generated for INVERTEBRATA by Claude (matplotlib). Rights holder to confirm (R. Ramesh).
Anterior = blunt end (right); posterior = pointed end (left); swimming direction is to the right.
"""
import sys
from pathlib import Path
import numpy as np
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse, Polygon, FancyArrowPatch, Circle

INK, CELL, CILIA, MAC, MIC, VAC = "#1d3b34", "#e8f3ee", "#2f7f6b", "#7b4fa0", "#d9822b", "#7fb7d6"


def outline(cx, cy, L, W, n=240):
    """Slipper/cigar outline: blunt anterior (+x), tapered posterior (-x)."""
    t = np.linspace(0, 2 * np.pi, n)
    x = np.cos(t)
    y = np.sin(t) * (0.62 + 0.38 * (x + 1) / 2) ** 1.0 * (1 - 0.15 * (1 - x) ** 2 / 4)
    return cx + x * L / 2, cy + y * W / 2


def badge(ax, x, y, k):
    ax.add_patch(Circle((x, y), 0.17, fc="white", ec=INK, lw=2.2, zorder=9))
    ax.text(x, y, str(k), ha="center", va="center", fontsize=17, weight="bold", color=INK, zorder=10)


def cilium(ax, x, y, nx, ny, phase, length=0.34):
    """One cilium rooted at (x,y), normal (nx,ny); bend depends on wave phase."""
    s = np.linspace(0, 1, 14)
    bend = np.sin(phase) * 0.9
    tx, ty = -ny, nx
    px = x + nx * length * s + tx * bend * length * s ** 2
    py = y + ny * length * s + ty * bend * length * s ** 2
    ax.plot(px, py, color=CILIA, lw=1.5, solid_capstyle="round", zorder=3)


def locomotion(path):
    fig, ax = plt.subplots(figsize=(15, 7), dpi=170)
    ax.set_xlim(-0.2, 15); ax.set_ylim(-0.2, 7.2); ax.set_aspect("equal"); ax.axis("off")
    cx, cy, L, W = 4.6, 4.5, 7.4, 2.5
    x, y = outline(cx, cy, L, W)
    # cilia rows with a travelling metachronal wave (phase advances with position along the cell)
    xs, ys = x, y
    for i in range(0, len(xs), 3):
        dx, dy = xs[(i + 1) % len(xs)] - xs[i - 1], ys[(i + 1) % len(xs)] - ys[i - 1]
        nrm = np.hypot(dx, dy) or 1
        nx_, ny_ = dy / nrm, -dx / nrm
        cilium(ax, xs[i], ys[i], nx_, ny_, phase=xs[i] * 1.9)
    ax.fill(x, y, fc=CELL, ec=INK, lw=3, zorder=4)
    # interior hints: macronucleus, micronucleus, two contractile vacuoles, oral groove
    ax.add_patch(Ellipse((cx + 0.2, cy + 0.1), 1.5, 0.8, fc=MAC, ec=INK, lw=2, zorder=5, alpha=.85))
    ax.add_patch(Circle((cx + 0.95, cy + 0.55), 0.14, fc=MIC, ec=INK, lw=1.5, zorder=6))
    for vx in (cx - 2.6, cx + 2.6):
        ax.add_patch(Circle((vx, cy - 0.35), 0.3, fc=VAC, ec=INK, lw=1.5, zorder=5))
    g = np.array([[cx - 1.3, cy - 0.85], [cx - 0.6, cy - 0.45], [cx + 0.5, cy - 0.25]])
    ax.plot(g[:, 0], g[:, 1], color=INK, lw=3, zorder=6)
    # wave arrow inside the ciliary fringe, same direction as swimming (posterior -> anterior)
    ax.add_patch(FancyArrowPatch((cx - 3.1, cy + 1.6), (cx + 3.1, cy + 1.6), arrowstyle="-|>", mutation_scale=28,
                                 lw=3, color=MAC, zorder=8, connectionstyle="arc3,rad=-0.08"))
    # spiral swimming path with rotation about the long axis
    t = np.linspace(0, 4 * np.pi, 300)
    px, py = 0.6 + t * 0.62, 0.9 + 0.45 * np.sin(t)
    ax.plot(px, py, color=INK, lw=2.4, ls=(0, (6, 4)), zorder=2)
    ax.add_patch(FancyArrowPatch((px[-4], py[-4]), (px[-1] + 0.35, py[-1]), arrowstyle="-|>", mutation_scale=26, lw=2.4, color=INK))
    ax.add_patch(FancyArrowPatch((8.9, 1.5), (8.9, 0.65), arrowstyle="->", mutation_scale=22, lw=2.4, color=INK,
                                 connectionstyle="arc3,rad=-1.6"))
    # inset: one cilium, effective stroke (stiff, sweeps back) and recovery stroke (curled, forward)
    ins = ax.inset_axes([0.62, 0.10, 0.37, 0.85]); ins.set_xlim(0, 4); ins.set_ylim(0, 3); ins.set_aspect("equal"); ins.axis("off")
    ins.add_patch(plt.Rectangle((0, 0), 4, 3, fc="white", ec=INK, lw=2))
    ins.plot([0.2, 3.8], [0.35, 0.35], color=INK, lw=4)
    for a in np.linspace(80, 10, 4):  # effective: straight, rotating from vertical to horizontal
        r = np.radians(a); ins.plot([1.0, 1.0 + 1.9 * np.cos(r)], [0.4, 0.4 + 1.9 * np.sin(r)], color=CILIA, lw=2.6, alpha=.35 + .65 * (80 - a) / 70)
    s = np.linspace(0, 1, 30)
    for k, amt in enumerate((0.9, 0.55, 0.25)):  # recovery: bent, travelling back to upright
        ins.plot(3.0 - amt * 1.8 * s ** 2 * (1 - .15 * k), 0.4 + 1.9 * s * (1 - .5 * amt * s) , color=MIC, lw=2.2, alpha=.4 + k * .3)
    ins.add_patch(FancyArrowPatch((1.0, 2.6), (2.6, 2.7), arrowstyle="-|>", mutation_scale=18, lw=2.4, color=CILIA))
    ins.add_patch(FancyArrowPatch((3.4, 2.7), (2.2, 2.9), arrowstyle="-|>", mutation_scale=18, lw=2.4, color=MIC,
                                  connectionstyle="arc3,rad=.3"))
    badge(ins, 0.3, 2.65, 5); badge(ins, 3.7, 2.65, 6)
    badge(ax, cx + 4.35, cy + 0.0, 1)     # anterior (blunt) end
    badge(ax, cx - 3.7, cy + 1.2, 2)     # posterior (pointed) end
    badge(ax, cx + 1.6, cy - 1.95, 3)    # somatic cilia
    badge(ax, cx - 3.5, cy + 2.1, 4)     # metachronal wave arrow
    badge(ax, 3.2, 1.7, 7)              # spiral path
    badge(ax, 8.45, 0.3, 8)              # rotation about long axis
    fig.savefig(path, facecolor="white", bbox_inches="tight", pad_inches=0.15); plt.close(fig)


def cell(ax, cx, cy, L, W):
    x, y = outline(cx, cy, L, W)
    ax.fill(x, y, fc=CELL, ec=INK, lw=3, zorder=2)


def nuc(ax, x, y, w, h, c, z=5):
    ax.add_patch(Ellipse((x, y), w, h, fc=c, ec=INK, lw=2, zorder=z, alpha=.9))


def fission(path):
    fig, axs = plt.subplots(1, 4, figsize=(16, 4.6), dpi=170)
    for a in axs:
        a.set_xlim(0, 4); a.set_ylim(0.8, 5.0); a.set_aspect("equal"); a.axis("off")
    # stage 1: interphase parent, long axis vertical drawn horizontally -> keep horizontal, anterior right
    def parent(a, mac_w=1.0, mac_h=0.7, mic_split=0.0):
        cell(a, 2, 2.6, 3.6, 1.7)
    a = axs[0]; parent(a); nuc(a, 2.0, 2.6, 0.95, 0.65, MAC); nuc(a, 2.55, 3.05, 0.2, 0.2, MIC, 6); badge(a, 0.35, 4.6, 1)
    a = axs[1]; parent(a)                                    # micronucleus in mitosis (spindle), macronucleus elongating
    nuc(a, 2.0, 2.55, 1.6, 0.6, MAC)
    a.add_patch(Ellipse((2.9, 3.1), 0.55, 0.22, fc=MIC, ec=INK, lw=2, zorder=6, alpha=.9))
    a.plot([2.65, 3.15], [3.1, 3.1], color=INK, lw=1.6, zorder=7)
    badge(a, 0.35, 4.6, 2)
    a = axs[2]                                               # constriction forming mid-cell; nuclei dividing
    x, y = outline(2, 2.6, 3.8, 1.8); y = y.copy()
    pinch = 1 - 0.55 * np.exp(-((x - 2.0) / 0.35) ** 2)
    y = 2.6 + (y - 2.6) * pinch
    a.fill(x, y, fc=CELL, ec=INK, lw=3, zorder=2)
    nuc(a, 1.45, 2.55, 0.95, 0.55, MAC); nuc(a, 2.6, 2.55, 0.95, 0.55, MAC)
    a.plot([1.95, 2.1], [2.55, 2.55], color=MAC, lw=4, zorder=4)
    nuc(a, 1.2, 3.05, 0.2, 0.2, MIC, 6); nuc(a, 2.85, 3.05, 0.2, 0.2, MIC, 6)
    badge(a, 0.35, 4.6, 3)
    a = axs[3]                                               # two daughters: proter (anterior, right) and opisthe (posterior, left)
    for cx_ in (1.05, 2.95):
        cell(a, cx_, 2.6, 1.75, 1.45); nuc(a, cx_, 2.55, 0.7, 0.5, MAC); nuc(a, cx_ + .35, 2.95, 0.17, 0.17, MIC, 6)
    badge(a, 0.35, 4.6, 4); badge(a, 2.95, 1.35, 5); badge(a, 1.05, 1.35, 6)
    fig.subplots_adjust(wspace=0.08, left=0.01, right=0.99, top=0.98, bottom=0.04)
    fig.savefig(path, facecolor="white", bbox_inches="tight", pad_inches=0.1); plt.close(fig)


if __name__ == "__main__":
    out = Path(sys.argv[1] if len(sys.argv) > 1 else "staging/drawn"); out.mkdir(parents=True, exist_ok=True)
    locomotion(out / "locomotion.png"); fission(out / "binary_fission.png")
    print("wrote", *sorted(p.name for p in out.glob("*.png")))
