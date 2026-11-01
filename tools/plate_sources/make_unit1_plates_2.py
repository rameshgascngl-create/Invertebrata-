#!/usr/bin/env python3
"""Original vector schematics for the other four missing Unit I plates (no image model, no text on art).

    python tools/plate_sources/make_unit1_plates_2.py [outdir]

Writes nutrition.png, conjugation.png, trypanosoma.png, leishmania.png. Numbered markers only;
legends (EN/TA) belong in the app. Schematic, not to scale. Anterior = right in every panel.
Conjugation uses ring = haploid, filled = diploid; counts follow ParameciumLearningEngine.kt (7 stages).
Author: generated for INVERTEBRATA by Claude (matplotlib). Rights holder to confirm (R. Ramesh).
"""
import sys
from pathlib import Path
import numpy as np
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse, Circle, FancyArrowPatch
sys.path.insert(0, str(Path(__file__).parent))
from make_paramecium_plates import INK, CELL, CILIA, MAC, MIC, VAC, outline, badge, nuc

RBC, ANL = "#e6a5a5", "#b99ad6"
KIN = "#222222"


def arrow(ax, a, b, c=INK, rad=0.0, lw=2.4, ms=22):
    ax.add_patch(FancyArrowPatch(a, b, arrowstyle="-|>", mutation_scale=ms, lw=lw, color=c,
                                 connectionstyle=f"arc3,rad={rad}", zorder=9))


def setup(w, h, dpi=170):
    fig, ax = plt.subplots(figsize=(w, h), dpi=dpi)
    ax.set_xlim(0, w); ax.set_ylim(0, h); ax.set_aspect("equal"); ax.axis("off")
    return fig, ax


def save(fig, path):
    fig.savefig(path, facecolor="white", bbox_inches="tight", pad_inches=0.12); plt.close(fig)


# ---------- 2. nutrition ----------
def nutrition(path):
    fig, ax = setup(13, 7.5)
    cx, cy, L, W = 6.2, 3.7, 10.0, 4.6
    x, y = outline(cx, cy, L, W)
    ax.fill(x, y, fc=CELL, ec=INK, lw=3.2, zorder=2)
    # oral groove (ventral, anterior half) -> vestibule -> cytostome -> cytopharynx -> forming food vacuole
    ax.plot([8.7, 7.2], [2.45, 2.6], color=INK, lw=9, solid_capstyle="round", zorder=4, alpha=.25)   # groove (1)
    ax.plot([8.7, 7.2], [2.45, 2.6], color=INK, lw=2.4, zorder=5)
    ax.add_patch(Ellipse((6.95, 2.8), 0.7, 0.5, fc="white", ec=INK, lw=2.2, zorder=5))               # vestibule (2)
    ax.add_patch(Circle((6.55, 3.05), 0.16, fc=INK, ec=INK, zorder=6))                               # cytostome (3)
    ax.plot([6.55, 6.25, 6.05], [3.05, 3.3, 3.55], color=INK, lw=7, solid_capstyle="round", zorder=5, alpha=.35)  # cytopharynx (4)
    ax.add_patch(Circle((5.95, 3.85), 0.34, fc="#f6d89a", ec=INK, lw=2, zorder=6))                    # forming food vacuole (5)
    nuc(ax, 7.7, 4.6, 1.5, 0.75, MAC); nuc(ax, 8.6, 5.0, 0.2, 0.2, MIC, 6)
    # cyclosis: food vacuoles circulate and shrink as digestion proceeds (6, 7)
    for k, t in enumerate(np.linspace(0, 2 * np.pi, 7, endpoint=False)):
        if k == 0: continue
        px, py = 4.8 + 1.05 * np.cos(t + 0.3), 3.65 + 0.9 * np.sin(t + 0.3)
        ax.add_patch(Circle((px, py), max(0.34 - 0.04 * k, 0.14), fc="#f1c97a" if k < 3 else "#d9ab5a", ec=INK, lw=1.8, zorder=6))
    arrow(ax, (5.2, 4.75), (3.9, 4.55), MAC, rad=0.3); arrow(ax, (4.0, 2.65), (5.3, 2.65), MAC, rad=0.3)
    # cytoproct: posterior ventral; contractile vacuole + its pore: dorsal, separate structures (8, 9, 10)
    ax.add_patch(Ellipse((2.0, 3.0), 0.5, 0.2, angle=-20, fc="white", ec=INK, lw=2.2, zorder=6))
    ax.add_patch(Circle((2.3, 3.2), 0.12, fc="#d9ab5a", ec=INK, lw=1.5, zorder=7))
    arrow(ax, (1.9, 2.9), (1.45, 2.45), INK, rad=0.0)
    ax.add_patch(Circle((2.7, 4.0), 0.3, fc=VAC, ec=INK, lw=2, zorder=6))
    for a in np.linspace(0, 2 * np.pi, 7)[:-1]:
        ax.plot([2.7 + .3 * np.cos(a), 2.7 + .6 * np.cos(a)], [4.0 + .3 * np.sin(a), 4.0 + .6 * np.sin(a)],
                color=VAC, lw=2.2, zorder=5)
    ax.plot([2.7, 2.7], [4.3, 4.72], color=INK, lw=2.6, zorder=6)
    for k, pos in zip(range(1, 11), [(8.1, 3.05), (7.0, 2.1), (7.1, 3.55), (5.6, 3.05), (6.0, 4.5),
                                      (4.8, 4.9), (4.2, 2.4), (1.0, 2.2), (3.4, 4.0), (3.15, 5.1)]):
        badge(ax, *pos, k)
    save(fig, path)


# ---------- 4. conjugation (7 stages; ring = haploid, filled = diploid) ----------
def dots(ax, pts, haploid, color, r=0.11):
    for (x, y) in pts:
        ax.add_patch(Circle((x, y), r, fc="white" if haploid else color, ec=color if haploid else INK,
                            lw=2.6 if haploid else 1.4, zorder=6))


def conjugation(path):
    fig, axs = plt.subplots(2, 4, figsize=(17, 8), dpi=150)
    for a in axs.flat:
        a.set_xlim(0, 4); a.set_ylim(0.6, 4.4); a.set_aspect("equal"); a.axis("off")
    axs[1, 3].axis("off")
    def pair(a, joined, old_mac=True, faded=False):
        gap = 0.0 if joined else 0.25
        for cy_, flip in ((2.95 + gap, 1), (1.95 - gap, -1)):
            xx, yy = outline(2, cy_, 3.0, 0.95)
            if flip == -1: yy = 2 * cy_ - yy
            a.fill(xx, yy, fc=CELL, ec=INK, lw=2.6, zorder=2)
        if joined:
            a.plot([1.2, 2.8], [2.45, 2.45], color=INK, lw=7, alpha=.25, zorder=3)   # oral-surface contact
    def mac(a, y, fade):
        a.add_patch(Ellipse((2.0, y), 0.9, 0.38, fc=MAC, ec=INK, lw=1.6, zorder=5, alpha=.25 if fade else .9, ls="--" if fade else "-"))
    # 1 pair
    a = axs[0, 0]; pair(a, True); mac(a, 3.0, False); mac(a, 1.9, False)
    dots(a, [(2.6, 3.0)], False, MIC); dots(a, [(2.6, 1.9)], False, MIC); badge(a, 0.3, 4.15, 1)
    # 2 meiosis -> 4 haploid per cell
    a = axs[0, 1]; pair(a, True); mac(a, 3.0, True); mac(a, 1.9, True)
    dots(a, [(2.2, 3.0), (2.5, 3.0), (2.8, 3.0), (3.1, 3.0)], True, MIC)
    dots(a, [(2.2, 1.9), (2.5, 1.9), (2.8, 1.9), (3.1, 1.9)], True, MIC); badge(a, 0.3, 4.15, 2)
    # 3 exchange and fusion -> 1 diploid synkaryon per cell
    a = axs[0, 2]; pair(a, True); mac(a, 3.0, True); mac(a, 1.9, True)
    dots(a, [(2.9, 3.1)], False, MIC, .15); dots(a, [(2.9, 1.8)], False, MIC, .15)
    dots(a, [(2.45, 2.65)], True, MIC); dots(a, [(1.95, 2.25)], True, MIC)
    arrow(a, (2.0, 2.62), (2.0, 2.3), INK, ms=14, lw=2); arrow(a, (2.5, 2.3), (2.5, 2.62), INK, ms=14, lw=2)
    badge(a, 0.3, 4.15, 3)
    # 4 first postzygotic division, still paired: 2 diploid per cell
    a = axs[0, 3]; pair(a, True); mac(a, 3.0, True); mac(a, 1.9, True)
    dots(a, [(2.6, 3.0), (3.0, 3.0)], False, MIC); dots(a, [(2.6, 1.9), (3.0, 1.9)], False, MIC); badge(a, 0.3, 4.15, 4)
    # 5 exconjugants separate: 2 diploid each
    a = axs[1, 0]; pair(a, False); mac(a, 3.2, True); mac(a, 1.7, True)
    dots(a, [(2.6, 3.2), (3.0, 3.2)], False, MIC); dots(a, [(2.6, 1.7), (3.0, 1.7)], False, MIC); badge(a, 0.3, 4.15, 5)
    # 6 further mitoses: 8 diploid per exconjugant = 4 posterior (anlagen) + 4 anterior (germline candidates)
    a = axs[1, 1]; pair(a, False)
    for yy, off in ((3.2, 0.28), (1.7, -0.28)):
        dots(a, [(0.9 + i * 0.28, yy) for i in range(4)], False, ANL, .12)           # posterior: macronuclear anlagen
        dots(a, [(2.5 + i * 0.28, yy) for i in range(4)], False, MIC, .12)           # anterior: presumptive germline
    badge(a, 0.3, 4.15, 6)
    # 7 later vegetative fissions are separate events: still two cells, no multiplication drawn
    a = axs[1, 2]; pair(a, False)
    for yy in (3.2, 1.7):
        nuc(a, 1.4, yy, 0.8, 0.34, MAC); dots(a, [(2.8, yy)], False, MIC, .12)
    badge(a, 0.3, 4.15, 7)
    fig.subplots_adjust(wspace=0.05, hspace=0.05, left=0.01, right=0.99, top=0.99, bottom=0.01)
    fig.savefig(path, facecolor="white", bbox_inches="tight", pad_inches=0.1); plt.close(fig)


# ---------- 5/6. flagellates ----------
def body(ax, x0, y0, length, width, bend, kin=None, nuc_pos=0.5, ends="point", z=3):
    """Slender cell along a gentle S-curve, anterior at +x. Returns centreline fn and half-width fn."""
    s = np.linspace(0, 1, 160)
    cl = lambda u: y0 + bend * np.sin(u * 2 * np.pi * 1.2)
    hw = lambda u: width / 2 * np.sin(np.pi * np.clip(u, 0, 1)) ** 0.55 + 0.02
    X = x0 + length * s; Y = cl(s); H = hw(s)
    ax.fill(np.r_[X, X[::-1]], np.r_[Y + H, (Y - H)[::-1]], fc=CELL, ec=INK, lw=2.6, zorder=z)
    return (lambda u: x0 + length * u), cl, hw


def trypanosoma(path):
    fig, ax = setup(14, 6.2)
    rng = np.random.default_rng(3)
    for (bx, by) in [(1.2, 1.2), (3.0, 4.9), (5.6, 1.0), (7.9, 5.0), (10.2, 1.3), (12.4, 4.6), (12.7, 1.5), (0.9, 4.9), (7.0, 2.2)]:
        ax.add_patch(Circle((bx, by), 0.62, fc=RBC, ec="#b57070", lw=1.8, zorder=1))
        ax.add_patch(Ellipse((bx, by), 0.5, 0.28, fc="#f6dada", ec="none", zorder=1))
    X, cl, hw = body(ax, 1.6, 3.1, 8.8, 0.9, 0.35, z=3)
    u = np.linspace(0.12, 1.0, 120)
    top = cl(u) + hw(u)
    rip = 0.16 * np.sin(u * 60) * (1 - (u - .55) ** 2)
    edge = top + 0.2 + rip                                              # undulating membrane outer margin (carries the flagellum)
    ax.fill(np.r_[X(u), X(u)[::-1]], np.r_[edge, top[::-1]], fc="#f0e7c9", ec=INK, lw=1.2, zorder=2, alpha=.9)
    ax.plot(X(u), edge, color=INK, lw=2.6, zorder=4)                    # flagellum running forward along the edge
    ax.plot([X(1.0), X(1.0) + 1.8], [edge[-1], edge[-1] + 0.15], color=INK, lw=2.6, zorder=4)   # free flagellum (anterior)
    ax.add_patch(Circle((X(0.12), cl(0.12) + 0.08), 0.11, fc="white", ec=INK, lw=2, zorder=6))     # flagellar pocket (posterior)
    ax.add_patch(Ellipse((X(0.2), cl(0.2)), 0.42, 0.14, fc=KIN, ec=KIN, zorder=6))               # kinetoplast (posterior to nucleus)
    ax.add_patch(Ellipse((X(0.5), cl(0.5)), 1.4, 0.55, fc=MAC, ec=INK, lw=2, zorder=6))          # nucleus (middle)
    # inset: epimastigote (kinetoplast anterior to nucleus, shorter membrane) for contrast
    ins = ax.inset_axes([0.60, 0.70, 0.38, 0.28]); ins.set_xlim(0, 5); ins.set_ylim(0, 1.9); ins.set_aspect("equal"); ins.axis("off")
    ins.add_patch(plt.Rectangle((0, 0), 5, 1.9, fc="white", ec=INK, lw=1.8))
    sx = np.linspace(0.3, 4.2, 100); sw = 0.3 * np.abs(np.sin(np.pi * np.clip((sx - .3) / 3.9, 0, 1))) ** .55 + .02
    ins.fill(np.r_[sx, sx[::-1]], np.r_[0.8 + sw, (0.8 - sw)[::-1]], fc=CELL, ec=INK, lw=2)
    ins.add_patch(Ellipse((1.6, 0.8), 0.6, 0.26, fc=MAC, ec=INK, lw=1.6, zorder=5))
    ins.add_patch(Ellipse((2.55, 0.8), 0.22, 0.08, fc=KIN, ec=KIN, zorder=5))
    ins.plot(sx[40:], 0.8 + sw[40:] + 0.08 + 0.05 * np.sin(sx[40:] * 14), color=INK, lw=2)
    ins.plot([4.2, 4.9], [0.88, 0.95], color=INK, lw=2)
    badge(ins, 0.35, 1.5, 7)
    for k, pos in zip(range(1, 8), [(X(0.04), cl(0.04) - 0.75), (X(0.2), cl(0.2) - 0.8), (X(0.5), cl(0.5) - 1.0), (X(0.5), cl(0.5) + 1.65),
                                     (11.6, 3.55), (3.0, 4.9), (0, 0)]):
        if k < 7: badge(ax, *pos, k)
    save(fig, path)


def leishmania(path):
    fig, ax = setup(14, 6.2)
    # left: macrophage with amastigotes
    ax.add_patch(Ellipse((3.3, 3.1), 5.4, 4.6, fc="#fbf0e4", ec=INK, lw=3, zorder=1))
    nuc(ax, 2.9, 3.6, 1.7, 1.3, MAC, 2)
    for (ax_, ay_, ang) in [(4.7, 2.0, 20), (3.2, 1.7, -30), (4.8, 3.6, 60), (1.6, 2.2, 0), (2.0, 4.6, 40), (4.0, 4.4, -15)]:
        ax.add_patch(Ellipse((ax_, ay_), 0.8, 0.55, angle=ang, fc=CELL, ec=INK, lw=2, zorder=4))
        ax.add_patch(Circle((ax_ + .05, ay_ + .05), 0.11, fc=MAC, ec=INK, lw=1, zorder=5))
        ax.add_patch(Ellipse((ax_ - .17, ay_ - .1), 0.2, 0.07, angle=ang, fc=KIN, ec=KIN, zorder=5))
    # right: promastigote (kinetoplast IN FRONT of nucleus; one free anterior flagellum; no undulating membrane)
    X, cl, hw = body(ax, 7.0, 3.0, 4.2, 1.0, 0.18, z=3)
    ax.add_patch(Ellipse((X(0.35), cl(0.35)), 0.95, 0.5, fc=MAC, ec=INK, lw=2, zorder=6))
    ax.add_patch(Ellipse((X(0.68), cl(0.68)), 0.3, 0.1, fc=KIN, ec=KIN, zorder=6))
    xs = np.linspace(X(1.0), X(1.0) + 1.8, 60)
    ax.plot(xs, cl(1.0) + 0.09 * np.sin((xs - xs[0]) * 3), color=INK, lw=2.8, zorder=4)
    for k, pos in zip(range(1, 7), [(3.3, 5.65), (1.5, 3.5), (4.6, 1.2), (X(0.35), cl(0.35) - 1.0), (X(0.68), cl(0.68) + 1.05),
                                     (X(1.0) + 1.3, cl(1.0) + 0.8)]):
        badge(ax, *pos, k)
    save(fig, path)


if __name__ == "__main__":
    out = Path(sys.argv[1] if len(sys.argv) > 1 else "staging/drawn"); out.mkdir(parents=True, exist_ok=True)
    nutrition(out / "nutrition.png"); conjugation(out / "conjugation.png")
    trypanosoma(out / "trypanosoma.png"); leishmania(out / "leishmania.png")
    print("wrote", *sorted(p.name for p in out.glob("*.png")))
