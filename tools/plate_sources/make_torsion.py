#!/usr/bin/env python3
"""Original schematic: gastropod torsion (generalised, not Pila-specific).
Dorsal view, head up. Blue = structures on the animal's ORIGINAL LEFT, red = ORIGINAL RIGHT.
Render:  python tools/plate_sources/make_torsion.py out.png
"""
import sys
import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
from matplotlib.patches import Ellipse, Circle, FancyArrowPatch

BLUE, RED = "#1f5fa8", "#c0392b"
SKIN, VISC, CAV = "#ece3cf", "#d2b48c", "#d6ebf5"
EDGE = "#2b2b2b"
FS = 13


def body(ax, cavity_center):
    ax.add_patch(Ellipse((5, 6.4), 4.4, 7.4, fc=SKIN, ec=EDGE, lw=1.8, zorder=1))          # foot
    ax.add_patch(Ellipse((5, 10.9), 2.8, 2.0, fc=SKIN, ec=EDGE, lw=1.8, zorder=2))         # head
    for sx in (-1, 1):                                                                       # tentacles + eyes
        ax.plot([5 + sx * 0.8, 5 + sx * 1.6], [11.7, 12.9], color=EDGE, lw=2.2, zorder=2)
        ax.add_patch(Circle((5 + sx * 0.8, 11.2), 0.14, fc=EDGE, zorder=3))
    ax.add_patch(Ellipse((5, 3.5), 5.2, 4.8, fc=VISC, ec=EDGE, lw=1.8, zorder=3, alpha=0.95))  # visceral mass + shell
    ax.add_patch(Ellipse(cavity_center, 3.8, 2.2, fc=CAV, ec=EDGE, lw=1.4, ls="--", zorder=4))  # mantle cavity


def gill(ax, x, y, color):
    ax.add_patch(Ellipse((x, y), 0.75, 1.5, angle=0, fc=color, ec=EDGE, lw=1, alpha=0.85, zorder=6))


def nerve(ax, pts, color):
    xs, ys = zip(*pts)
    ax.plot(xs, ys, color=color, lw=3.2, zorder=7, solid_capstyle="round")


def label(ax, text, xy, xytext, ha="left"):
    ax.annotate(text, xy=xy, xytext=xytext, fontsize=FS, ha=ha, va="center", zorder=9,
                arrowprops=dict(arrowstyle="-", color="#444", lw=1.2, shrinkA=2, shrinkB=2))


fig = plt.figure(figsize=(10, 6.9), dpi=200, facecolor="white")
axA = fig.add_axes([0.00, 0.11, 0.50, 0.77]); axB = fig.add_axes([0.50, 0.11, 0.50, 0.77])
for ax in (axA, axB):
    ax.set_xlim(-3.2, 14.0); ax.set_ylim(-0.3, 13.4); ax.set_aspect("equal"); ax.axis("off")

# ---- A: before torsion ----
body(axA, (5, 1.9))
gill(axA, 3.9, 2.2, BLUE); gill(axA, 6.1, 2.2, RED)
axA.add_patch(Circle((5, 1.3), 0.18, fc="#222", zorder=8))
for x, c in ((4.2, BLUE), (5.8, RED)):
    axA.add_patch(Circle((x, 7.6), 0.2, fc=c, ec=EDGE, zorder=8))
nerve(axA, [(4.2, 7.6), (3.5, 6.0), (4.3, 4.3), (5, 3.9)], BLUE)
nerve(axA, [(5.8, 7.6), (6.5, 6.0), (5.7, 4.3), (5, 3.9)], RED)
axA.add_patch(Circle((5, 3.9), 0.25, fc="#222", zorder=8))
label(axA, "Head", (4.2, 11.2), (-2.9, 11.2))
label(axA, "Foot", (3.2, 7.4), (-2.9, 8.8))
label(axA, "Visceral mass\nand shell", (2.6, 4.2), (-2.9, 4.6))
label(axA, "Mantle cavity\n(posterior)", (3.3, 1.5), (-2.9, 0.4))
label(axA, "Anus", (5.1, 1.3), (7.9, 0.2))
label(axA, "Visceral ganglion", (5.2, 3.9), (8.0, 4.6))
label(axA, "Nerve loop\nuncrossed", (6.5, 6.0), (8.3, 7.2))
axA.text(5, 13.25, "A  Before torsion", fontsize=16, fontweight="bold", ha="center")

# ---- B: after 180 degree torsion ----
body(axB, (5, 8.9))
gill(axB, 6.1, 8.7, BLUE); gill(axB, 3.9, 8.7, RED)   # left gill now on the right, and vice versa
axB.add_patch(Circle((5, 9.75), 0.18, fc="#222", zorder=8))
for x, c in ((4.2, BLUE), (5.8, RED)):
    axB.add_patch(Circle((x, 7.2), 0.2, fc=c, ec=EDGE, zorder=8))
nerve(axB, [(4.2, 7.2), (4.7, 6.2), (5.4, 5.0), (5, 3.9)], BLUE)
nerve(axB, [(5.8, 7.2), (5.3, 6.2), (4.6, 5.0), (5, 3.9)], RED)
axB.add_patch(Circle((5, 3.9), 0.25, fc="#222", zorder=8))
label(axB, "Head", (4.2, 11.4), (-2.9, 12.0))
label(axB, "Mantle cavity\n(now anterior)", (3.3, 9.1), (-2.9, 9.6))
label(axB, "Anus", (5.1, 9.8), (8.0, 11.0))
label(axB, "Originally LEFT\nctenidium", (6.2, 8.4), (8.2, 8.9))
label(axB, "Originally RIGHT\nctenidium", (3.8, 8.4), (-2.9, 7.2))
label(axB, "Nerve loop crossed\n(figure of 8)", (5, 5.8), (8.1, 6.0))
label(axB, "Visceral ganglion", (5.2, 3.9), (8.0, 3.3))
label(axB, "Visceral mass\nand shell", (2.6, 3.6), (-2.9, 2.6))
axB.text(5, 13.25, "B  After torsion", fontsize=16, fontweight="bold", ha="center")

fig.add_artist(FancyArrowPatch((0.44, 0.80), (0.56, 0.80), transform=fig.transFigure,
                               arrowstyle="simple,head_width=20,head_length=20,tail_width=8",
                               mutation_scale=1, fc="#888", ec="#555", zorder=10))
fig.text(0.5, 0.855, "180° torsion", ha="center", fontsize=14, fontweight="bold", color="#444")
fig.text(0.5, 0.035,
         "Torsion rotates the visceral mass and mantle cavity 180° anticlockwise (viewed from above); head and foot do not turn.\n"
         "Blue = structures originally on the left; red = originally on the right. Diagrammatic, not to scale; generalised gastropod.",
         ha="center", va="center", fontsize=10.5, color="#333")
fig.suptitle("Gastropod torsion", fontsize=20, fontweight="bold", y=0.985)
fig.savefig(sys.argv[1], dpi=200, facecolor="white")
