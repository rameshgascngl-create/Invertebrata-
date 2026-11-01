#!/usr/bin/env python3
"""Two process plates that replace duplicate-byte concept images (no image model, no text on art).

    python tools/plate_sources/make_unit1_plates_3.py [outdir]

Writes oral_process.png (feeding current into the oral groove) and osmoregulation_cycle.png
(four phases of the contractile-vacuole cycle). Numbered markers only; legends belong in the app.
Schematic, not to scale. Author: generated for INVERTEBRATA by Claude (matplotlib).
"""
import sys
from pathlib import Path
import numpy as np
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse, Circle
sys.path.insert(0, str(Path(__file__).parent))
from make_paramecium_plates import INK, CELL, CILIA, MAC, MIC, VAC, outline, badge, nuc
from make_unit1_plates_2 import arrow, setup, save

FOOD = "#c9a24a"


def oral_process(path):
    fig, ax = setup(13, 7.5)
    cx, cy, L, W = 6.4, 3.6, 10.0, 4.6
    x, y = outline(cx, cy, L, W)
    ax.fill(x, y, fc=CELL, ec=INK, lw=3.2, zorder=2)
    # oral groove: ventral (lower) edge, running obliquely from the anterior toward the middle of the cell
    gx = np.linspace(9.2, 6.9, 40); gy = 2.35 + 0.35 * (9.2 - gx) / 2.3 + 0.05 * np.sin(gx * 5)
    ax.plot(gx, gy, color=INK, lw=14, solid_capstyle="round", alpha=.18, zorder=3)
    for gxi, gyi in zip(gx[::3], gy[::3]):                         # ciliary rows lining the groove
        ax.plot([gxi, gxi - 0.12], [gyi, gyi - 0.38], color=CILIA, lw=1.8, zorder=4)
    ax.add_patch(Ellipse((6.7, 3.05), 0.8, 0.55, fc="white", ec=INK, lw=2.2, zorder=5))             # vestibule
    ax.add_patch(Circle((6.25, 3.3), 0.17, fc=INK, ec=INK, zorder=6))                               # cytostome
    ax.plot([6.25, 5.85, 5.55], [3.3, 3.55, 3.85], color=INK, lw=8, solid_capstyle="round", alpha=.35, zorder=5)  # cytopharynx
    ax.add_patch(Circle((5.35, 4.1), 0.38, fc="#f6d89a", ec=INK, lw=2, zorder=6))                   # food vacuole forming
    nuc(ax, 4.0, 4.55, 1.5, 0.7, MAC); nuc(ax, 4.9, 4.9, 0.18, 0.18, MIC, 6)
    # external feeding current: particles carried on ciliary currents that converge on the groove
    rng = np.random.default_rng(7)
    for (px, py) in [(10.3, 0.7), (9.6, 0.5), (8.4, 0.45), (7.3, 0.6), (11.0, 1.4), (11.6, 2.3), (6.1, 0.9), (10.0, 1.2)]:
        ax.add_patch(Circle((px, py), 0.11, fc=FOOD, ec=INK, lw=1.2, zorder=6))
    for a, b, r in [((11.2, 1.2), (9.6, 2.05), 0.25), ((9.9, 0.8), (8.7, 2.2), 0.2), ((7.9, 0.75), (7.6, 2.3), -0.2), ((6.3, 1.1), (7.0, 2.45), -0.25)]:
        arrow(ax, a, b, MAC, rad=r)
    arrow(ax, (7.9, 2.9), (6.9, 3.1), INK, ms=16, lw=2)
    for k, pos in zip(range(1, 7), [(11.4, 3.0), (8.5, 3.1), (7.1, 3.8), (6.0, 2.6), (5.0, 3.15), (5.35, 4.85)]):
        badge(ax, *pos, k)
    save(fig, path)


def osmo_panel(a, vac_r, canal, pore_open, water_in=False, water_out=False):
    a.set_xlim(0, 4); a.set_ylim(0, 4.2); a.set_aspect("equal"); a.axis("off")
    # a section of cell: membrane arc at top, cytoplasm below
    t = np.linspace(0.12 * np.pi, 0.88 * np.pi, 80)
    mx, my = 2 + 2.25 * np.cos(t), 0.2 + 3.75 * np.sin(t) ** 0.7
    a.fill(np.r_[mx, mx[-1], mx[0]], np.r_[my, 0.0, 0.0], fc=CELL, ec="none")
    a.plot(mx, my, color=INK, lw=3.2, zorder=3)
    cxv, cyv = 2.0, 2.0
    for ang in np.linspace(0, 2 * np.pi, 7)[:-1]:                          # radiating collecting canals
        L = 1.0
        a.plot([cxv + vac_r * np.cos(ang), cxv + (vac_r + L) * np.cos(ang)],
               [cyv + vac_r * np.sin(ang), cyv + (vac_r + L) * np.sin(ang)], color=VAC, lw=3 + 7 * canal, solid_capstyle="round", zorder=4)
        a.add_patch(Circle((cxv + (vac_r + L) * np.cos(ang), cyv + (vac_r + L) * np.sin(ang)), 0.10 + 0.17 * canal, fc=VAC, ec=INK, lw=1, zorder=4))
    a.add_patch(Circle((cxv, cyv), vac_r, fc=VAC, ec=INK, lw=2.4, zorder=5))
    a.plot([cxv, cxv], [cyv + vac_r, 3.55 if not pore_open else 3.98], color=INK, lw=3 if not pore_open else 6, zorder=5)   # pore channel to surface
    if water_in:
        for xx in (0.8, 1.6, 2.4, 3.2):
            arrow(a, (xx, 4.15), (xx, 3.65), INK, ms=14, lw=2)
    if water_out:
        arrow(a, (cxv, 3.85), (cxv, 4.2), MAC, ms=18, lw=3)


def osmoregulation_cycle(path):
    fig, axs = plt.subplots(1, 4, figsize=(16, 4.8), dpi=170)
    osmo_panel(axs[0], 0.18, 1.0, False, water_in=True)               # 1 diastole begins: canals full of water, vacuole small
    osmo_panel(axs[1], 0.45, 0.45, False, water_in=True)              # 2 canals empty into the vacuole; vacuole enlarging
    osmo_panel(axs[2], 0.75, 0.05, True, water_out=True)              # 3 vacuole full; pore opens, contents expelled (systole)
    osmo_panel(axs[3], 0.18, 0.85, False, water_in=True)              # 4 vacuole collapsed; canals refilling
    for a, k in zip(axs, range(1, 5)):
        badge(a, 0.3, 3.9, k)
    badge(axs[0], 3.55, 2.3, 5); badge(axs[1], 2.0, 1.15, 6)
    fig.subplots_adjust(wspace=0.04, left=0.01, right=0.99, top=0.99, bottom=0.01)
    fig.savefig(path, facecolor="white", bbox_inches="tight", pad_inches=0.1); plt.close(fig)


def nuclear_anatomy(path):
    """Macronucleus and micronucleus: position in the cell and enlarged detail (not to scale)."""
    fig, ax = setup(14, 6.4)
    cx, cy, L, W = 3.7, 3.2, 6.6, 3.1
    x, y = outline(cx, cy, L, W)
    ax.fill(x, y, fc=CELL, ec=INK, lw=3.2, zorder=2)
    nuc(ax, 3.6, 3.15, 1.6, 0.85, MAC, 4)                                         # macronucleus
    ax.add_patch(Circle((4.5, 3.75), 0.17, fc=MIC, ec=INK, lw=1.8, zorder=6))     # micronucleus beside it
    ax.add_patch(Ellipse((2.0, 2.9), 0.55, 0.45, fc=VAC, ec=INK, lw=1.6, zorder=3))
    ax.add_patch(Ellipse((5.4, 2.7), 0.55, 0.45, fc=VAC, ec=INK, lw=1.6, zorder=3))
    # enlarged macronucleus: large, polyploid, many small nucleoli
    arrow(ax, (4.4, 3.35), (8.6, 4.6), INK, rad=-0.15, lw=1.8, ms=16)
    ax.add_patch(Ellipse((10.0, 4.8), 3.4, 1.9, fc=MAC, ec=INK, lw=2.6, alpha=.9, zorder=4))
    rng = np.random.default_rng(11)
    for _ in range(46):
        a = rng.uniform(0, 2 * np.pi); r = np.sqrt(rng.uniform(0, 1))
        ax.add_patch(Circle((10.0 + 1.45 * r * np.cos(a), 4.8 + 0.78 * r * np.sin(a)), 0.07, fc="#e9dff3", ec="none", zorder=5))
    # enlarged micronucleus: small, diploid, compact chromosomes
    arrow(ax, (4.6, 3.85), (8.7, 1.9), INK, rad=0.2, lw=1.8, ms=16)
    ax.add_patch(Circle((10.0, 1.7), 0.95, fc="#f6dcb8", ec=INK, lw=2.6, zorder=4))
    for ang in np.linspace(0, 2 * np.pi, 7)[:-1]:
        ax.plot([10.0 + .15 * np.cos(ang), 10.0 + .62 * np.cos(ang + .5)], [1.7 + .15 * np.sin(ang), 1.7 + .62 * np.sin(ang + .5)],
                color=MIC, lw=4.5, solid_capstyle="round", zorder=5)
    for k, pos in zip(range(1, 7), [(3.1, 3.95), (4.5, 4.35), (8.1, 5.4), (8.4, 1.0), (11.95, 5.0), (11.3, 1.7)]):
        badge(ax, *pos, k)
    save(fig, path)


if __name__ == "__main__":
    out = Path(sys.argv[1] if len(sys.argv) > 1 else "staging/drawn3"); out.mkdir(parents=True, exist_ok=True)
    oral_process(out / "oral_process.png"); osmoregulation_cycle(out / "osmoregulation_cycle.png")
    nuclear_anatomy(out / "nuclear_anatomy.png")
    print("wrote", *sorted(p.name for p in out.glob("*.png")))
