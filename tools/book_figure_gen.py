#!/usr/bin/env python3
"""Original schematic figures for the INVERTEBRATA book chapters (Units VI and VII).

Every figure is drawn from code (matplotlib), so it is original artwork prepared for this
app: not traced from, and not derived from, any published illustration. Figures carry only
numerals and letters; the key to the numbers is the bilingual caption in the chapter, so no
label is ever burned in a single language.

    python tools/book_figure_gen.py            # writes native-app/.../assets/book_figures/*.webp
    python tools/book_figure_gen.py insect-tagmata leech   # only these

Requires matplotlib and Pillow. All figures are DRAFT_UNVERIFIED schematics.
"""
import sys
from pathlib import Path

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.patches import (Circle, Ellipse, FancyArrowPatch, FancyBboxPatch, Polygon,
                                Rectangle, Wedge)
from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "native-app/app/src/main/assets/book_figures"
INK = "#1f2933"
BODY = "#f4e4c1"
BODY2 = "#e7c98f"
BLUE = "#a9cdee"
GREEN = "#bfe3c0"
PINK = "#f2b8ad"
GREY = "#d9dde2"
PURPLE = "#cdb8e6"
LW = 2.4


def canvas(w=12, h=9):
    fig, ax = plt.subplots(figsize=(w, h), dpi=100)
    ax.set_xlim(0, w * 100)
    ax.set_ylim(0, h * 100)
    ax.set_aspect("equal")
    ax.axis("off")
    fig.subplots_adjust(0, 0, 1, 1)
    return fig, ax


def num(ax, n, tx, ty, px=None, py=None, r=38):
    """Numbered marker at (tx, ty) with an optional leader line to (px, py)."""
    if px is not None:
        ax.plot([tx, px], [ty, py], color=INK, lw=1.8, zorder=8)
        ax.add_patch(Circle((px, py), 5, color=INK, zorder=9))
    r = max(r, 38)   # numerals must stay readable when the figure is shown at phone width
    ax.add_patch(Circle((tx, ty), r, fc="white", ec=INK, lw=2.6, zorder=10))
    ax.text(tx, ty - 1, str(n), ha="center", va="center", fontsize=(34 if n < 10 else 29), fontweight="bold",
            color=INK, zorder=11)


def letter(ax, s, x, y):
    ax.text(x, y, s, fontsize=44, fontweight="bold", color=INK, ha="center", va="center")


def arrow(ax, p, q, rad=0.0, lw=3.0, color=INK, style="-|>", ls="-"):
    ax.add_patch(FancyArrowPatch(p, q, connectionstyle=f"arc3,rad={rad}", arrowstyle=style,
                                 mutation_scale=26, lw=lw, color=color, linestyle=ls, zorder=7))


def ell(ax, x, y, w, h, fc=BODY, ec=INK, lw=LW, ang=0, z=3, **k):
    ax.add_patch(Ellipse((x, y), w, h, angle=ang, fc=fc, ec=ec, lw=lw, zorder=z, **k))


def rect(ax, x, y, w, h, fc=BODY, ec=INK, lw=LW, z=3, r=0):
    if r:
        ax.add_patch(FancyBboxPatch((x, y), w, h, boxstyle=f"round,pad=0,rounding_size={r}",
                                    fc=fc, ec=ec, lw=lw, zorder=z))
    else:
        ax.add_patch(Rectangle((x, y), w, h, fc=fc, ec=ec, lw=lw, zorder=z))


def poly(ax, pts, fc=BODY, ec=INK, lw=LW, z=3, closed=True):
    ax.add_patch(Polygon(pts, closed=closed, fc=fc, ec=ec, lw=lw, zorder=z))


def line(ax, pts, lw=LW, color=INK, z=4, ls="-"):
    xs, ys = zip(*pts)
    ax.plot(xs, ys, color=color, lw=lw, zorder=z, linestyle=ls, solid_capstyle="round")


def leg(ax, hip, knee, foot, lw=4):
    line(ax, [hip, knee, foot], lw=lw, z=2)


def save(fig, name):
    OUT.mkdir(parents=True, exist_ok=True)
    tmp = OUT / (name + ".png")
    fig.savefig(tmp, dpi=100, facecolor="white")
    plt.close(fig)
    im = Image.open(tmp).convert("RGB")
    im.save(OUT / (name + ".webp"), "WEBP", lossless=True, method=6)
    tmp.unlink()


# --------------------------------------------------------------------------- figures
def insect_tagmata():
    fig, ax = canvas()
    # legs first (behind the body)
    for hx, kx, fx in [(380, 330, 290), (450, 450, 420), (520, 590, 640)]:
        leg(ax, (hx, 380), (kx, 300), (fx, 190))
    # wings
    ell(ax, 470, 520, 270, 90, fc="#dbeaf7", ang=18, alpha=0.85, z=2)
    ell(ax, 560, 510, 250, 80, fc="#dbeaf7", ang=10, alpha=0.85, z=2)
    # tagmata
    ell(ax, 215, 430, 190, 150, fc=BODY2)                       # head
    rect(ax, 320, 345, 245, 160, fc=BODY, r=45)                  # thorax
    for x in (400, 482):
        line(ax, [(x, 348), (x, 502)], lw=2)
    ell(ax, 770, 425, 430, 165)                                  # abdomen
    for x in range(620, 960, 52):
        line(ax, [(x, 360), (x, 490)], lw=1.6, z=4)
    # antennae and eye
    line(ax, [(160, 495), (110, 600), (70, 660)], lw=3)
    line(ax, [(195, 505), (190, 620), (160, 690)], lw=3)
    ell(ax, 185, 450, 56, 76, fc="#34495e")
    # spiracles
    for x in (440, 690, 790):
        ax.add_patch(Circle((x, 352 if x == 440 else 350), 6, fc=INK, zorder=6))
    num(ax, 1, 215, 600, 215, 500)
    num(ax, 2, 440, 640, 440, 505)
    num(ax, 3, 790, 640, 790, 500)
    num(ax, 4, 90, 560, 110, 600)
    num(ax, 5, 110, 350, 175, 430)
    num(ax, 6, 560, 700, 520, 560)
    num(ax, 7, 700, 640, 650, 540)
    num(ax, 8, 290, 120, 300, 280)
    num(ax, 9, 900, 260, 800, 350)
    save(fig, "insect-tagmata")


def _insect_icon(ax, x, y, kind, s=1.0):
    if kind == "egg":
        ell(ax, x, y, 70 * s, 100 * s, fc="#fbf5e6")
    elif kind == "nymph1":
        ell(ax, x, y, 130 * s, 60 * s)
        for dx in (-40, 0, 40):
            line(ax, [(x + dx, y - 25 * s), (x + dx - 10, y - 55 * s)], lw=2.5)
    elif kind == "nymph2":
        ell(ax, x, y, 170 * s, 74 * s)
        ell(ax, x - 20 * s, y + 30 * s, 60 * s, 26 * s, fc=GREY, ang=20)
        for dx in (-55, -5, 45):
            line(ax, [(x + dx, y - 30 * s), (x + dx - 12, y - 66 * s)], lw=2.5)
    elif kind == "adult":
        ell(ax, x, y, 190 * s, 80 * s, fc=BODY2)
        ell(ax, x - 10, y + 50 * s, 170 * s, 50 * s, fc="#dbeaf7", ang=12, alpha=.9)
        for dx in (-60, -10, 40):
            line(ax, [(x + dx, y - 34 * s), (x + dx - 12, y - 70 * s)], lw=2.5)
    elif kind == "larva":
        for i in range(7):
            ax.add_patch(Circle((x - 90 * s + i * 30 * s, y), 24 * s, fc="#f0e6c8", ec=INK, lw=2, zorder=3))
    elif kind == "pupa":
        ell(ax, x, y, 70 * s, 140 * s, fc="#c9a46a", ang=30)
        for k in (-30, 0, 30):
            line(ax, [(x - 24, y + k - 6), (x + 24, y + k + 12)], lw=1.6)


def metamorphosis():
    fig, ax = canvas()
    letter(ax, "A", 60, 660)
    letter(ax, "B", 60, 270)
    top = [(190, "egg"), (410, "nymph1"), (650, "nymph2"), (930, "adult")]
    for i, (x, k) in enumerate(top):
        _insect_icon(ax, x, 660, k)
        num(ax, i + 1, x, 770)
        if i < 3:
            arrow(ax, (x + 105 if k != "egg" else x + 50, 660), (top[i + 1][0] - 105, 660))
    bottom = [(190, "egg"), (430, "larva"), (700, "pupa"), (940, "adult")]
    for i, (x, k) in enumerate(bottom):
        _insect_icon(ax, x, 270, k)
        num(ax, i + 1, x, 380)
        if i < 3:
            arrow(ax, (x + 105 if k not in ("egg",) else x + 50, 270), (bottom[i + 1][0] - 90, 270))
    ax.plot([30, 1170], [470, 470], color=GREY, lw=3)
    save(fig, "metamorphosis")


def parasitoid():
    fig, ax = canvas()
    cx, cy = 600, 450
    nodes = [(600, 760), (1000, 450), (600, 140), (200, 450)]
    # 1 adult wasp laying egg on a caterpillar
    x, y = nodes[0]
    for i in range(6):
        ax.add_patch(Circle((x - 130 + i * 40, y - 40), 28, fc="#b9d98f", ec=INK, lw=2, zorder=3))
    ell(ax, x + 30, y + 55, 90, 30, fc=BODY2)
    ell(ax, x + 20, y + 85, 70, 26, fc="#dbeaf7", ang=25, alpha=.9)
    line(ax, [(x + 75, y + 50), (x + 110, y + 10), (x + 118, y - 20)], lw=3)
    # 2 egg/young larva inside host
    x, y = nodes[1]
    for i in range(6):
        ax.add_patch(Circle((x - 130 + i * 40, y), 28, fc="#b9d98f", ec=INK, lw=2, zorder=3))
    ell(ax, x - 30, y, 120, 26, fc="#fff3d6", z=4)
    # 3 host emptied; parasitoid pupae
    x, y = nodes[2]
    ell(ax, x - 60, y, 190, 70, fc="#c4b49a")
    for i in range(3):
        ell(ax, x + 90 + i * 12, y + (i - 1) * 34, 46, 22, fc="#c9a46a", ang=20)
    # 4 adult emerges
    x, y = nodes[3]
    ell(ax, x, y, 90, 30, fc=BODY2)
    ell(ax, x - 8, y + 36, 80, 26, fc="#dbeaf7", ang=25, alpha=.9)
    line(ax, [(x - 40, y + 5), (x - 80, y - 30)], lw=3)
    for k, (nx, ny) in enumerate(nodes):
        ax.add_patch(Ellipse((nx, ny), 330, 190, fc="none", ec=GREY, lw=2, zorder=1))
    num(ax, 1, 600, 880 - 40)
    num(ax, 2, 1000, 600 - 0)
    num(ax, 3, 600, 262)
    num(ax, 4, 200, 560)
    arrow(ax, (760, 770), (950, 600), rad=-0.15)
    arrow(ax, (960, 320), (700, 200), rad=-0.15)
    arrow(ax, (420, 150), (230, 330), rad=-0.15)
    arrow(ax, (230, 580), (440, 760), rad=-0.15)
    save(fig, "parasitoid-cycle")


def ipm_ladder():
    fig, ax = canvas()
    steps = [("#8fbf8f", 1), ("#a8cfa0", 2), ("#c9dd9c", 3), ("#f3df8e", 4), ("#f0a99e", 5)]
    base_w, top_w = 1000, 260
    h = 140
    for i, (c, n) in enumerate(steps):
        w0 = base_w - (base_w - top_w) * i / 5
        w1 = base_w - (base_w - top_w) * (i + 1) / 5
        y0 = 60 + i * h
        pts = [(600 - w0 / 2, y0), (600 + w0 / 2, y0), (600 + w1 / 2, y0 + h), (600 - w1 / 2, y0 + h)]
        poly(ax, pts, fc=c, lw=2.6)
        num(ax, n, 600, y0 + h / 2)
    arrow(ax, (1090, 120), (1090, 760), lw=4)
    ax.text(1115, 440, "", fontsize=1)
    save(fig, "ipm-ladder")


def economic_threshold():
    fig, ax = canvas()
    ax.plot([150, 150], [90, 800], color=INK, lw=3)
    ax.plot([150, 1120], [90, 90], color=INK, lw=3)
    arrow(ax, (150, 780), (150, 840), lw=3)
    arrow(ax, (1100, 90), (1160, 90), lw=3)
    xs = np.linspace(170, 1100, 400)

    def curve(x):
        if x < 560:
            return 130 + 520 / (1 + np.exp(-(x - 470) / 55)) * 1.0
        return None
    pre = [(x, 120 + 650 / (1 + np.exp(-(x - 420) / 55))) for x in xs if x <= 540]
    cx0, cy0 = pre[-1]
    post = [(x, max(130, cy0 - 360 * (1 - np.exp(-(x - cx0) / 40))) + 0) for x in xs if cx0 <= x <= 700]
    post = [(x, y) for (x, y) in post]
    low = post[-1][1]
    again = [(x, low + 200 / (1 + np.exp(-(x - 900) / 70)) - 0) for x in xs if x >= 700]
    for seg in (pre, post, again):
        line(ax, seg, lw=5, color="#c0392b", z=5)
    ax.plot([150, 1120], [650, 650], color="#2c3e50", lw=3, linestyle=(0, (8, 6)), zorder=4)
    ax.plot([150, 1120], [520, 520], color="#d68910", lw=3, linestyle=(0, (8, 6)), zorder=4)
    ax.plot([cx0, cx0], [90, cy0], color=INK, lw=2.2, linestyle=":", zorder=4)
    arrow(ax, (cx0, 250), (cx0, 130), lw=3.5)
    num(ax, 1, 330, 230, 330, 140 + 520 / (1 + np.exp((420 - 330) / 55)) * 0 + 125)
    num(ax, 2, 1060, 700, 1060, 652)
    num(ax, 3, 1060, 470, 1060, 522)
    num(ax, 4, cx0 + 70, 260)
    num(ax, 5, 640, 330, 640, post[-1][1] if False else 180)
    num(ax, 6, 1000, 330, 1000, low + 200 / (1 + np.exp(-(1000 - 900) / 70)))
    save(fig, "economic-threshold")


def chelicerate():
    fig, ax = canvas()
    # legs (4 pairs) from prosoma
    ys = [(560, 80), (500, 20), (420, -60), (350, -120)]
    for i, (dy1, dy2) in enumerate([(120, 250), (60, 90), (-10, -110), (-70, -250)]):
        for sgn in (1, -1):
            hx = 360 + i * 38
            hy = 450 + sgn * 60
            kx = hx + 20 - i * 10
            ky = 450 + sgn * (170 + 40 * (i == 0 or i == 3))
            fx = hx + (40 if i < 2 else -60) - i * 5
            fy = 450 + sgn * (300 if i in (0, 3) else 290)
            leg(ax, (hx, hy), (kx, ky), (fx, fy), lw=5)
    ell(ax, 330, 450, 330, 250, fc=BODY2)                         # prosoma
    ell(ax, 780, 450, 470, 330)                                   # opisthosoma
    rect(ax, 495, 435, 60, 30, fc=BODY, lw=2)                     # pedicel
    # chelicerae and pedipalps
    for sgn in (1, -1):
        ell(ax, 160, 450 + sgn * 36, 70, 36, fc="#a0522d")
        line(ax, [(200, 450 + sgn * 100), (150, 450 + sgn * 150), (115, 450 + sgn * 120)], lw=6)
    for sx, sy in ((290, 520), (290, 380)):
        ax.add_patch(Circle((sx, sy), 14, fc="#34495e", zorder=6))
    num(ax, 1, 330, 120, 330, 330)
    num(ax, 2, 780, 120, 780, 290)
    num(ax, 3, 80, 330, 160, 430)
    num(ax, 4, 100, 650, 125, 570)
    num(ax, 5, 450, 800, 450, 700)
    num(ax, 6, 525, 700, 525, 468)
    save(fig, "chelicerate")


def myriapod():
    fig, ax = canvas()
    letter(ax, "A", 60, 690)
    letter(ax, "B", 60, 270)
    # head with antennae and forcipules, A (centipede): flattened, one leg pair per segment
    ell(ax, 150, 690, 120, 90, fc=BODY2)
    line(ax, [(110, 730), (80, 820), (50, 860)], lw=3)
    line(ax, [(150, 735), (150, 840), (130, 880)], lw=3)
    for sgn in (1, -1):
        line(ax, [(130, 650 if sgn == -1 else 730), (100, 600 if sgn == -1 else 790), (130, 580 if sgn == -1 else 810)], lw=6, color="#a0522d")
    for i in range(5):
        x = 260 + i * 170
        rect(ax, x, 650, 150, 80, fc=BODY, r=18)
        for sgn, y0 in ((1, 730), (-1, 650)):
            leg(ax, (x + 75, y0), (x + 105, y0 + sgn * 90), (x + 60, y0 + sgn * 150), lw=4)
    # B millipede: cylindrical diplosegments, two leg pairs per segment
    ell(ax, 150, 270, 120, 100, fc=BODY2)
    line(ax, [(115, 310), (85, 380), (60, 410)], lw=3)
    line(ax, [(150, 320), (155, 395), (140, 430)], lw=3)
    for i in range(4):
        x = 260 + i * 220
        rect(ax, x, 225, 200, 90, fc="#d8c19a", r=40)
        for k in (60, 140):
            for sgn, y0 in ((1, 315), (-1, 225)):
                line(ax, [(x + k, y0), (x + k - 8, y0 + sgn * 70)], lw=4, z=2)
    num(ax, 1, 150, 560 + 70, 150, 700)
    num(ax, 2, 70, 540, 115, 600)
    num(ax, 3, 600, 880, 600, 790)
    num(ax, 4, 480, 80, 480, 240)
    num(ax, 5, 700, 80, 700, 240)
    ax.plot([30, 1170], [470, 470], color=GREY, lw=3)
    save(fig, "myriapod-segments")


def flower():
    fig, ax = canvas()
    # receptacle & stalk
    rect(ax, 560, 60, 80, 330, fc=GREEN, r=10)
    ell(ax, 600, 400, 240, 70, fc="#9bc487")
    # sepals
    for sx in (-1, 1):
        ell(ax, 600 + sx * 125, 440, 150, 50, fc="#7fb069", ang=-sx * 25)
    # petals
    for sx in (-1, 1):
        poly(ax, [(600 + sx * 70, 440), (600 + sx * 330, 700), (600 + sx * 230, 760), (600 + sx * 100, 520)], fc=PINK)
    # carpel: ovary, ovules, style, stigma
    ell(ax, 600, 470, 130, 150, fc="#c7e3a3")
    for dy in (-30, 10, 50):
        ax.add_patch(Circle((600, 470 + dy), 14, fc="#f7e08a", ec=INK, lw=2, zorder=5))
    rect(ax, 590, 545, 20, 160, fc="#c7e3a3", lw=2)
    ell(ax, 600, 715, 90, 50, fc="#f7e08a")
    # stamens
    for sx in (-1, 1):
        line(ax, [(600 + sx * 70, 455), (600 + sx * 150, 600)], lw=5, color="#7a6a3a")
        ell(ax, 600 + sx * 160, 630, 62, 84, fc="#f4c542", ang=sx * 10)
    # pollen tube
    line(ax, [(600, 705), (590, 640), (610, 590), (600, 520)], lw=3, color="#b03a2e", ls="--")
    num(ax, 1, 140, 700, 270, 740)
    num(ax, 2, 160, 470, 440, 480)
    num(ax, 3, 1030, 700, 760, 630)
    num(ax, 4, 1000, 800, 640, 725)
    num(ax, 5, 760, 800, 612, 720)
    num(ax, 6, 1020, 470, 620, 480)
    num(ax, 7, 1020, 330, 600, 470)
    num(ax, 8, 160, 260, 560, 330)
    num(ax, 9, 760, 600, 605, 600)
    save(fig, "flower")


def vector_cycle():
    fig, ax = canvas()

    def person(x, y, sick=False):
        ax.add_patch(Circle((x, y + 85), 36, fc="#e8c39e", ec=INK, lw=2.4, zorder=3))
        rect(ax, x - 42, y - 70, 84, 130, fc="#9ec5e8" if not sick else PINK, r=24)
        if sick:
            for dx in (-14, 12):
                ax.add_patch(Circle((x + dx, y - 10 + dx), 6, fc="#b03a2e", zorder=6))

    def mosquito(x, y, loaded=False):
        ell(ax, x, y, 150, 40, fc="#8d7b68")
        ell(ax, x + 85, y + 5, 46, 36, fc="#8d7b68")
        line(ax, [(x + 105, y), (x + 170, y - 40)], lw=2.5)
        ell(ax, x - 10, y + 50, 120, 36, fc="#dbeaf7", ang=-20, alpha=.9)
        for dx in (-40, 0, 40):
            line(ax, [(x + dx, y - 18), (x + dx - 18, y - 70)], lw=2)
        if loaded:
            for dx in (-30, 0, 30):
                ax.add_patch(Circle((x + dx, y), 6, fc="#b03a2e", zorder=6))
    person(210, 640, sick=True)
    mosquito(930, 640)
    mosquito(940, 190, loaded=True)
    person(220, 190)
    arrow(ax, (330, 700), (770, 700), rad=-0.12)
    arrow(ax, (1000, 560), (1000, 330), rad=0.0)
    arrow(ax, (770, 150), (340, 150), rad=-0.12)
    arrow(ax, (230, 330), (230, 520), rad=0.0, ls="--")
    num(ax, 1, 210, 790)
    num(ax, 2, 560, 800)
    num(ax, 3, 1110, 450)
    num(ax, 4, 560, 90)
    num(ax, 5, 80, 400)
    save(fig, "vector-cycle")


def leech():
    fig, ax = canvas()
    # body outline tapering to both ends
    top = [(150 + i * 10, 450 + 130 * np.sin(np.pi * (i / 92)) ** 0.8 + 20) for i in range(93)]
    bot = [(150 + i * 10, 450 - 130 * np.sin(np.pi * (i / 92)) ** 0.8 - 20) for i in range(93)]
    poly(ax, top + bot[::-1], fc="#6b5b45", ec=INK, lw=3)
    for i in range(6, 90, 3):
        x = 150 + i * 10
        h = 130 * np.sin(np.pi * (i / 92)) ** 0.8 + 20
        line(ax, [(x, 450 - h + 6), (x, 450 + h - 6)], lw=1.2, color="#3d3226", z=4)
    # clitellum band
    x0, x1 = 430, 520
    h = 130 * np.sin(np.pi * (0.33)) ** 0.8 + 20
    rect(ax, x0, 450 - h + 4, x1 - x0, 2 * h - 8, fc="#a8896a", ec=INK, lw=2, z=5)
    # anterior sucker (small) and posterior sucker (large)
    ell(ax, 120, 450, 90, 140, fc="#8a7a62", z=4)
    ell(ax, 150, 450, 40, 60, fc="#3d3226", z=5)
    ell(ax, 1100, 450, 160, 260, fc="#8a7a62", z=4)
    ell(ax, 1100, 450, 90, 150, fc="#a8896a", z=5)
    for dy in (-30, 0, 30):
        ax.add_patch(Circle((200, 450 + dy), 8, fc=INK, zorder=6))
    num(ax, 1, 120, 640, 120, 530)
    num(ax, 2, 270, 700, 205, 480)
    num(ax, 3, 650, 760, 650, 595)
    num(ax, 4, 475, 190, 475, 335)
    num(ax, 5, 800, 190, 800, 320)
    num(ax, 6, 1100, 700, 1100, 580)
    save(fig, "leech")


def nephridia():
    fig, ax = canvas()
    letter(ax, "A", 60, 840)
    letter(ax, "B", 60, 380)
    # A protonephridium: closed tubule with a flame cell, opening outside
    rect(ax, 120, 520, 960, 270, fc="#f9f3e3", lw=2.4)               # tissue
    ell(ax, 250, 650, 120, 150, fc="#d6e8f7")
    for k in range(-3, 4):
        line(ax, [(305, 650 + k * 14), (380, 650 + k * 10)], lw=2, color="#7d6608")
    line(ax, [(380, 650), (470, 650), (520, 700), (570, 650), (620, 700), (680, 650), (850, 650), (1100, 650)], lw=7, color="#5a7da6", z=5)
    ax.plot([1090, 1090], [600, 700], color=INK, lw=0)
    ax.text(1140, 700, "", fontsize=1)
    # B metanephridium: funnel in one segment, tubule through septum into next
    rect(ax, 120, 100, 450, 250, fc="#fdf2e0", lw=2.4)
    rect(ax, 570, 100, 510, 250, fc="#fdf2e0", lw=2.4)
    line(ax, [(570, 100), (570, 350)], lw=6, z=5)                   # septum
    poly(ax, [(300, 150), (360, 150), (420, 260), (260, 260)], fc="#d6e8f7", z=4)
    for k in range(5):
        line(ax, [(285 + k * 30, 262), (285 + k * 30 - 6, 292)], lw=2, color="#7d6608")
    line(ax, [(330, 150), (330, 120), (470, 120), (570, 130), (620, 140)], lw=7, color="#5a7da6", z=5)
    t = np.linspace(0, 6 * np.pi, 120)
    line(ax, [(640 + 100 * (u / (6 * np.pi)) * 2 + 28 * np.cos(u), 195 + 40 * np.sin(u)) for u in t], lw=6, color="#5a7da6", z=5)
    line(ax, [(840, 195), (960, 150), (1050, 100)], lw=7, color="#5a7da6", z=5)
    for k in range(4):
        line(ax, [(650 + k * 50, 300), (670 + k * 50, 240)], lw=2.5, color="#b03a2e", z=6)
    ax.plot([1050, 1050], [90, 100], color=INK)
    num(ax, 1, 250, 840, 250, 725)
    num(ax, 2, 560, 760, 520, 690)
    num(ax, 3, 1100, 740, 1080, 660)
    num(ax, 4, 260, 400, 330, 255)
    num(ax, 5, 570, 400, 570, 320)
    num(ax, 6, 350, 60, 420, 160)
    num(ax, 7, 800, 400, 760, 250)
    num(ax, 8, 1130, 150, 1080, 100)
    num(ax, 9, 900, 330, 740, 270)
    ax.plot([30, 1170], [455, 455], color=GREY, lw=3)
    save(fig, "nephridia")


def _worm(ax, x0, y, n, seg=34, h=46, fc=BODY, head=None, tail=None, split=None):
    """Draw n segments in a row; return list of segment rectangles' x ranges."""
    for i in range(n):
        c = fc
        if head is not None and i in head:
            c = "#8ec6a4"
        if tail is not None and i in tail:
            c = "#f2b8ad"
        rect(ax, x0 + i * seg, y - h / 2, seg - 3, h, fc=c, lw=1.8, r=7)


def polychaete_asexual():
    fig, ax = canvas()
    # A architomy: worm, then broken pieces regenerating
    letter(ax, "A", 60, 780)
    _worm(ax, 150, 780, 24)
    arrow(ax, (600, 740), (600, 690))
    for k, (x0, n) in enumerate([(150, 7), (415, 8), (718, 8)]):
        _worm(ax, x0, 650, n)
    arrow(ax, (600, 610), (600, 560))
    for k, (x0, n) in enumerate([(150, 6), (440, 6), (730, 6)]):
        _worm(ax, x0, 520, n + 2, head=[0], tail=[n + 1])
    # B paratomy
    letter(ax, "B", 60, 380)
    _worm(ax, 150, 380, 12, head=[11 - 11])
    _worm(ax, 150 + 12 * 34, 380, 7, head=[0, 1], tail=[])
    for i in range(12, 12):
        pass
    ax.plot([150 + 12 * 34 - 2] * 2, [345, 415], color=INK, lw=3, linestyle="--")
    # C schizogamy: stolon bearing gametes with a head
    letter(ax, "C", 60, 150)
    _worm(ax, 150, 150, 14)
    _worm(ax, 150 + 14 * 34 + 20, 150, 8, head=[0], tail=[])
    for i in range(8):
        ax.add_patch(Circle((150 + 14 * 34 + 20 + i * 34 + 14, 150), 7, fc="#f7e08a", ec=INK, lw=1.6, zorder=6))
    ax.plot([150 + 14 * 34 + 8] * 2, [115, 185], color=INK, lw=3, linestyle="--")
    ax.plot([30, 1170], [270, 270], color=GREY, lw=3)
    ax.plot([30, 1170], [470, 470], color=GREY, lw=3)
    save(fig, "polychaete-asexual")


def chiton():
    fig, ax = canvas()
    letter(ax, "A", 60, 800)
    letter(ax, "B", 660, 800)
    # A: dorsal view
    ell(ax, 330, 450, 470, 620, fc="#9b8f7e", ang=90)                 # girdle
    ax.add_patch(Ellipse((330, 450), 540, 400, angle=90, fc="none", ec=INK, lw=2.4, zorder=4))
    for i in range(8):
        y = 700 - i * 60
        w = 220 - 22 * abs(i - 3.5) * 0.7
        rect(ax, 330 - w / 2, y - 34, w, 52, fc="#cdbb9a" if i % 2 == 0 else "#bfae8c", r=16, z=5)
    for k in range(40):
        a = 2 * np.pi * k / 40
        ax.add_patch(Circle((330 + 262 * np.sin(a) * 0.62, 450 + 262 * np.cos(a) * 1.0 * 0.0 + 330 * np.cos(a)), 4, fc=INK, zorder=3, alpha=.5))
    num(ax, 1, 90, 450, 330 - 120, 450)
    num(ax, 2, 560, 720, 330, 700)
    num(ax, 3, 560, 200, 330, 215)
    num(ax, 4, 120, 750, 235, 650)
    # B: ventral view
    ell(ax, 880, 450, 330, 600, fc="#9b8f7e", ang=0)
    ell(ax, 880, 430, 160, 360, fc="#e6b98a", z=4)                    # foot
    ell(ax, 880, 665, 90, 70, fc="#d9a066", z=4)                      # head region
    ax.add_patch(Circle((880, 680), 11, fc=INK, zorder=6))
    for sgn in (1, -1):
        rect(ax, 880 + sgn * 96 - 12, 270, 24, 330, fc="#b6d4e8", lw=1.8, r=10, z=5)
        for k in range(10):
            line(ax, [(880 + sgn * 96 - 12 + 4, 285 + k * 32), (880 + sgn * 96 + 12 - 4, 285 + k * 32)], lw=1.6)
    num(ax, 5, 1110, 420, 880, 420)
    num(ax, 6, 1110, 700, 880, 680)
    num(ax, 7, 640, 330, 784, 400)
    num(ax, 8, 1090, 200, 960, 330)
    save(fig, "chiton")


def bivalve_section():
    fig, ax = canvas()
    # shell valves: two curved bands meeting dorsally
    for sgn in (1, -1):
        t = np.linspace(0, 1, 60)
        cx = 600
        pts_out = [(cx + sgn * (40 + 440 * np.sin(np.pi * u * 0.62)), 790 - 700 * (1 - np.cos(np.pi * u * 0.62)) / 1.4 * 0.95) for u in t]
        line(ax, pts_out, lw=16, color="#6d4c41", z=2)
    # mantle lobes inside (thin bands)
    for sgn in (1, -1):
        t = np.linspace(0, 1, 60)
        pts = [(600 + sgn * (50 + 395 * np.sin(np.pi * u * 0.62)), 770 - 700 * (1 - np.cos(np.pi * u * 0.62)) / 1.4 * 0.95) for u in t]
        line(ax, pts, lw=7, color="#e6a57e", z=3)
    # visceral mass
    ell(ax, 600, 600, 300, 200, fc="#f2c9a0")
    # foot
    poly(ax, [(540, 505), (660, 505), (630, 260), (570, 260)], fc="#e6a57e")
    ell(ax, 600, 255, 80, 50, fc="#e6a57e")
    # gills: two on each side
    for sgn in (1, -1):
        for off, c in ((0, "#9ec5e8"), (60, "#bcd8ee")):
            rect(ax, 600 + sgn * (170 + off) - 14, 380, 28, 280, fc=c, lw=2, r=12, z=5)
    # hinge and ligament
    rect(ax, 570, 770, 60, 40, fc="#8d6e63", lw=2.2, r=10, z=6)
    num(ax, 1, 100, 520, 190, 520)
    num(ax, 2, 600, 880, 600, 810)
    num(ax, 3, 1090, 650, 940, 600)
    num(ax, 4, 380, 760, 470, 660)
    num(ax, 5, 840, 760, 780, 600)
    num(ax, 6, 380, 300, 575, 300)
    num(ax, 7, 840, 330, 745, 440)
    num(ax, 8, 340, 440, 445, 440)
    save(fig, "bivalve-section")


def crinoid():
    fig, ax = canvas(12, 11)
    # stalk of columnals
    for i in range(14):
        y = 70 + i * 28
        rect(ax, 570, y, 60, 24, fc="#d8c19a", lw=2, r=6)
    for y, sg in ((150, 1), (260, -1), (350, 1)):
        line(ax, [(600 + sg * 30, y), (600 + sg * 90, y + 40), (600 + sg * 120, y + 100)], lw=3)
    # holdfast
    poly(ax, [(540, 70), (660, 70), (720, 40), (480, 40)], fc="#b8a37a", z=3)
    # calyx
    poly(ax, [(540, 470), (660, 470), (700, 600), (500, 600)], fc=BODY2)
    ell(ax, 600, 600, 200, 60, fc="#e9dcc0")
    # arms: five branching pairs with pinnules
    angles = [-70, -35, 0, 35, 70]
    for a in angles:
        r = np.deg2rad(a)
        x0, y0 = 600 + 70 * np.sin(r), 600
        pts1 = [(x0, y0), (x0 + 120 * np.sin(r) * 1.4, y0 + 160 + 60), (x0 + 220 * np.sin(r) * 1.3, y0 + 230)]
        line(ax, pts1, lw=5, color="#7a5c3a")
        for sgn in (-1, 1):
            tx = pts1[-1][0] + sgn * 55
            line(ax, [pts1[-1], (tx, pts1[-1][1] + 70), (tx + sgn * 15, pts1[-1][1] + 150)], lw=4, color="#7a5c3a")
            for k in range(1, 6):
                px = pts1[-1][0] + sgn * (10 + 8 * k)
                py = pts1[-1][1] + 14 * k
                line(ax, [(px, py), (px + sgn * 36, py + 18)], lw=1.8, color="#7a5c3a")
    ax.add_patch(Circle((600, 615), 12, fc=INK, zorder=6))
    num(ax, 1, 130, 540, 520, 540)
    num(ax, 2, 1100, 760, 935, 830)
    num(ax, 3, 250, 780, 414, 844)
    num(ax, 4, 1060, 580, 630, 620)
    num(ax, 5, 130, 340, 575, 340)
    num(ax, 6, 1060, 230, 690, 190)
    num(ax, 7, 1060, 130, 690, 70)
    save(fig, "crinoid")


def aurelia_cycle():
    fig, ax = canvas()
    cx, cy = 600, 450
    # 1 medusa (top)
    def medusa(x, y, s=1.0):
        ax.add_patch(Wedge((x, y), 150 * s, 0, 180, fc="#d6e8f7", ec=INK, lw=2.6, zorder=3))
        for dx in (-70, -25, 25, 70):
            ax.add_patch(Circle((x + dx * s, y + 55 * s), 20 * s, fc="none", ec="#c0392b", lw=2.8, zorder=5))
        for dx in (-45, -15, 15, 45):
            line(ax, [(x + dx * s, y), (x + dx * s * 1.4, y - 100 * s)], lw=4, color="#7fa6c9")
        for dx in range(-140, 141, 28):
            line(ax, [(x + dx * s, y), (x + dx * s, y - 22 * s)], lw=1.5)
    medusa(600, 760, 1.0)
    # 2 planula (right)
    ell(ax, 1000, 640, 160, 80, fc="#f7e6b5")
    for k in range(10):
        line(ax, [(930 + k * 15, 678 if k % 2 == 0 else 602), (930 + k * 15, 690 if k % 2 == 0 else 590)], lw=1.4)
    # 3 scyphistoma (right-bottom)
    x, y = 960, 300
    poly(ax, [(x - 40, y - 100), (x + 40, y - 100), (x + 70, y + 20), (x - 70, y + 20)], fc="#e3d0a2")
    for dx in range(-60, 61, 24):
        line(ax, [(x + dx, y + 20), (x + dx * 1.2, y + 90)], lw=3, color="#7a5c3a")
    # 4 strobila (bottom)
    x, y = 600, 160
    for i in range(5):
        w = 120 + (i % 2) * 20
        ell(ax, x, y - 20 + i * 24, w, 18, fc="#e3d0a2", lw=2)
    # 5 ephyra (left-bottom)
    x, y = 230, 270
    for k in range(8):
        a = 2 * np.pi * k / 8
        poly(ax, [(x + 22 * np.cos(a - 0.2), y + 22 * np.sin(a - 0.2)), (x + 78 * np.cos(a), y + 78 * np.sin(a)), (x + 22 * np.cos(a + 0.2), y + 22 * np.sin(a + 0.2))], fc="#d6e8f7", lw=2)
    ax.add_patch(Circle((x, y), 28, fc="#d6e8f7", ec=INK, lw=2.4, zorder=4))
    arrow(ax, (700, 780), (900, 700), rad=-0.2)
    arrow(ax, (1000, 590), (980, 440), rad=0.0)
    arrow(ax, (880, 240), (700, 190), rad=0.2)
    arrow(ax, (490, 190), (300, 240), rad=0.2)
    arrow(ax, (230, 360), (480, 700), rad=-0.15)
    num(ax, 1, 400, 860)
    num(ax, 2, 1110, 700)
    num(ax, 3, 1110, 330)
    num(ax, 4, 780, 90)
    num(ax, 5, 130, 160)
    num(ax, 6, 300, 560)
    save(fig, "aurelia-cycle")


def anemone_section():
    fig, ax = canvas()
    cx, cy = 600, 450
    R = 340
    ax.add_patch(Circle((cx, cy), R, fc="#f6ead3", ec=INK, lw=4, zorder=2))
    ax.add_patch(Circle((cx, cy), R - 16, fc="none", ec=INK, lw=1.6, zorder=2))
    # pharynx (flattened) and siphonoglyphs
    ell(ax, cx, cy, 200, 70, fc="#cfe2f3", z=6)
    for sgn in (1, -1):
        ax.add_patch(Circle((cx + sgn * 100, cy), 16, fc="#7fb3d9", ec=INK, lw=2.4, zorder=7))
    # mesenteries: pairs of lines. complete reach pharynx; incomplete stop short.
    pairs_complete = [(0, 'dir'), (180, 'dir'), (55, 'c'), (125, 'c'), (235, 'c'), (305, 'c')]
    pairs_incomplete = [(25, 'i'), (90, 'i'), (155, 'i'), (205, 'i'), (270, 'i'), (335, 'i')]
    def ph(a):  # point on pharynx ellipse
        r = np.deg2rad(a)
        return cx + 100 * np.cos(r), cy + 35 * np.sin(r)
    for a, kind in pairs_complete + pairs_incomplete:
        r = np.deg2rad(a)
        for d in (-2.2, 2.2):
            rr = np.deg2rad(a + d)
            p0 = (cx + (R - 16) * np.cos(rr), cy + (R - 16) * np.sin(rr))
            if kind in ('c', 'dir'):
                p1 = ph(a + d * 2.2)
            else:
                p1 = (cx + 150 * np.cos(rr), cy + 150 * np.sin(rr))
            line(ax, [p0, p1], lw=3.6 if kind == 'dir' else 2.8, color="#a04000" if kind == 'dir' else INK, z=5)
        if kind == 'i':
            ax.add_patch(Circle((cx + 150 * np.cos(r), cy + 150 * np.sin(r)), 15, fc="#f4a460", ec=INK, lw=2, zorder=6))
    num(ax, 1, 1060, 140, 894, 280)
    num(ax, 2, 600, 780, 600, 470)
    num(ax, 3, 940, 360, 700, 450)
    num(ax, 4, 250, 800, 457, 655)
    num(ax, 5, 1110, 590, 718, 505)
    num(ax, 6, 800, 810, 600, 600)
    num(ax, 7, 140, 450, 350, 450)
    save(fig, "anemone-section")


def taenia_cycle():
    fig, ax = canvas()

    def egg(x, y, s=1.0):
        ax.add_patch(Circle((x, y), 34 * s, fc="#fff3d6", ec=INK, lw=2.4, zorder=3))
        for k in range(6):
            a = 2 * np.pi * k / 6
            ax.add_patch(Circle((x + 12 * s * np.cos(a), y + 12 * s * np.sin(a)), 3.6, fc=INK, zorder=5))
    def person(x, y):
        ax.add_patch(Circle((x, y + 85), 36, fc="#e8c39e", ec=INK, lw=2.4, zorder=3))
        rect(ax, x - 42, y - 70, 84, 130, fc="#9ec5e8", r=24)
        line(ax, [(x - 8, y - 30), (x - 8, y - 4), (x + 12, y - 4), (x + 12, y - 40)], lw=3, color="#b03a2e", z=6)
    def pig(x, y, cyst=False):
        ell(ax, x, y, 190, 100, fc="#f2b8ad")
        ell(ax, x + 105, y + 10, 70, 66, fc="#f2b8ad")
        ax.add_patch(Circle((x + 130, y + 12), 5, fc=INK, zorder=6))
        for dx in (-65, -20, 25, 65):
            rect(ax, x + dx - 9, y - 90, 18, 50, fc="#f2b8ad", lw=2, r=4, z=2)
        if cyst:
            for dx, dy in ((-50, 10), (-5, -10), (40, 15), (-25, 30)):
                ax.add_patch(Circle((x + dx, y + dy), 9, fc="#fff", ec="#b03a2e", lw=2, zorder=6))
    person(210, 640)
    pig(930, 640)
    pig(930, 200, cyst=True)
    person(230, 220)
    egg(600, 640, 1.3)
    # cysticercus enlarged
    ax.add_patch(Circle((600, 210), 46, fc="#ffffff", ec="#b03a2e", lw=3, zorder=3))
    ax.add_patch(Circle((600, 222), 11, fc="#f4c542", ec=INK, lw=2, zorder=5))
    arrow(ax, (320, 690), (520, 660), rad=-0.1)
    arrow(ax, (690, 660), (820, 680), rad=-0.1)
    arrow(ax, (930, 540), (930, 340), rad=0.0)
    arrow(ax, (830, 200), (670, 210), rad=0.0)
    arrow(ax, (520, 215), (350, 215), rad=0.0)
    arrow(ax, (230, 330), (210, 520), rad=0.0)
    arrow(ax, (560, 600), (330, 330), rad=0.2, ls="--", color="#b03a2e")
    num(ax, 1, 210, 790)
    num(ax, 2, 600, 760)
    num(ax, 3, 930, 780)
    num(ax, 4, 1100, 400)
    num(ax, 5, 740, 140)
    num(ax, 6, 360, 130)
    num(ax, 7, 130, 440)
    num(ax, 8, 420, 480)
    save(fig, "taenia-cycle")


def taenia_parts():
    fig, ax = canvas()
    # scolex (enlarged), neck, and strobilus of increasing maturity
    ax.add_patch(Circle((200, 640), 120, fc=BODY2, ec=INK, lw=3, zorder=3))
    for a in (45, 135, 225, 315):
        r = np.deg2rad(a)
        ax.add_patch(Circle((200 + 85 * np.cos(r), 640 + 85 * np.sin(r)), 34, fc="#b97a5a", ec=INK, lw=2.4, zorder=5))
    ax.add_patch(Circle((200, 640), 30, fc="#f4c542", ec=INK, lw=2.4, zorder=6))
    for k in range(14):
        a = 2 * np.pi * k / 14
        line(ax, [(200 + 30 * np.cos(a), 640 + 30 * np.sin(a)), (200 + 46 * np.cos(a), 640 + 46 * np.sin(a))], lw=2.2, z=7)
    rect(ax, 330, 618, 110, 44, fc=BODY, lw=2.4, r=10)          # neck
    # proglottids, immature to gravid
    x = 450
    items = [("imm", 35, 50), ("imm", 49, 60), ("mat", 67, 80), ("mat", 84, 100), ("mat", 102, 110), ("grav", 132, 100), ("grav", 158, 90)]
    for kind, w, h in items:
        rect(ax, x, 640 - h / 2, w, h, fc=BODY if kind != "grav" else "#f0e6c8", lw=2.2, r=4)
        if kind == "mat":
            ax.add_patch(Circle((x + w / 2, 640), 8, fc="#f4c542", ec=INK, lw=1.6, zorder=6))
            ax.add_patch(Circle((x + w * 0.2, 640 + h * 0.2), 5, fc=PINK, ec=INK, lw=1.4, zorder=6))
        if kind == "grav":
            line(ax, [(x + 8, 640), (x + w - 8, 640)], lw=3, color="#b03a2e")
            for k in range(1, 6):
                xx = x + 10 + k * (w - 20) / 6
                line(ax, [(xx, 640), (xx - 6, 640 + 36)], lw=2.2, color="#b03a2e")
                line(ax, [(xx, 640), (xx - 6, 640 - 36)], lw=2.2, color="#b03a2e")
        x += w + 6
    # zoom of gravid proglottid with uterus branches
    rect(ax, 140, 80, 520, 330, fc="#f0e6c8", lw=3, r=8)
    line(ax, [(180, 245), (620, 245)], lw=8, color="#b03a2e")
    for k in range(1, 8):
        xx = 180 + k * 55
        line(ax, [(xx, 245), (xx - 20, 380)], lw=4, color="#b03a2e")
        line(ax, [(xx, 245), (xx - 20, 110)], lw=4, color="#b03a2e")
    ax.add_patch(Circle((900, 250), 150, fc="#fff3d6", ec=INK, lw=3.4, zorder=3))
    for k in range(6):
        a = 2 * np.pi * k / 6
        ax.add_patch(Circle((900 + 60 * np.cos(a), 250 + 60 * np.sin(a)), 9, fc=INK, zorder=5))
    ax.add_patch(Circle((900, 250), 38, fc="none", ec=INK, lw=2, zorder=5))
    num(ax, 1, 200, 840, 200, 770)
    num(ax, 2, 90, 520, 130, 590)
    num(ax, 3, 330, 780, 230, 670)
    num(ax, 4, 390, 520, 390, 600)
    num(ax, 5, 560, 840, 515, 670)
    num(ax, 6, 770, 760, 660, 690)
    num(ax, 7, 1000, 760, 880, 690)
    num(ax, 8, 420, 460, 420, 410)
    num(ax, 9, 1100, 440, 960, 340)
    save(fig, "taenia-parts")


def sponge_grades():
    """Three canal-system grades in section. Water path: blue arrows; choanocyte layers: green."""
    fig, ax = canvas()
    W = "#cfe6f7"
    G = "#7fbf7f"
    def vase(cx, w, cav, top=700, bot=130):
        rect(ax, cx - w / 2, bot, w, top - bot, fc="#f1dcb0", r=30)
        rect(ax, cx - cav / 2, bot + 25, cav, top - bot - 25 + 2, fc=W, lw=2, z=4)
        rect(ax, cx - cav / 2, top - 6, cav, 20, fc="white", ec="white", lw=0, z=5)  # osculum opening
    letter(ax, "A", 70, 800); letter(ax, "B", 470, 800); letter(ax, "C", 870, 800)
    # A asconoid
    cx = 200; vase(cx, 150, 70)
    for y in (240, 340, 440, 540, 620):
        line(ax, [(cx - 75, y), (cx - 35, y)], lw=7, color=W, z=5)
        line(ax, [(cx + 35, y), (cx + 75, y)], lw=7, color=W, z=5)
        arrow(ax, (cx - 130, y), (cx - 80, y), lw=2.4, color="#2471a3")
        arrow(ax, (cx + 130, y), (cx + 80, y), lw=2.4, color="#2471a3")
    line(ax, [(cx - 35, 155), (cx - 35, 700)], lw=6, color=G, z=6)
    line(ax, [(cx + 35, 155), (cx + 35, 700)], lw=6, color=G, z=6)
    arrow(ax, (cx, 600), (cx, 770), lw=3, color="#2471a3")
    # B syconoid
    cx = 600; vase(cx, 250, 70)
    for y in (230, 330, 430, 530, 620):
        rect(ax, cx - 125 + 55, y - 18, 65, 36, fc=W, lw=2, z=5)          # radial canal (left)
        rect(ax, cx + 125 - 55 - 65, y - 18, 65, 36, fc=W, lw=2, z=5)     # radial canal (right)
        line(ax, [(cx - 70, y - 18), (cx - 70, y + 18)], lw=4, color=G, z=6)
        line(ax, [(cx + 70, y - 18), (cx + 70, y + 18)], lw=4, color=G, z=6)
    for y in (280, 380, 480, 575):                                           # incurrent canals
        rect(ax, cx - 125 + 3, y - 12, 55, 24, fc="#dbe9f4", lw=2, z=5)
        rect(ax, cx + 125 - 58, y - 12, 55, 24, fc="#dbe9f4", lw=2, z=5)
        arrow(ax, (cx - 175, y), (cx - 110, y), lw=2.4, color="#2471a3")
        arrow(ax, (cx + 175, y), (cx + 110, y), lw=2.4, color="#2471a3")
    arrow(ax, (cx, 600), (cx, 770), lw=3, color="#2471a3")
    # C leuconoid
    cx = 1000; vase(cx, 270, 36)
    import random
    random.seed(4)
    chambers = []
    for y in (200, 290, 380, 470, 560, 640):
        for dx in (-90, -40, 40, 90):
            xx = cx + dx + (8 if (y // 90) % 2 else -8) * (1 if dx < 0 else -1)
            chambers.append((xx, y))
    for xx, y in chambers:
        ax.add_patch(Circle((xx, y), 24, fc=W, ec=G, lw=5, zorder=5))
        tx = cx + (-30 if xx < cx else 30)
        line(ax, [(xx + (24 if xx < cx else -24), y), (tx, y)], lw=2, color="#5a7da6", z=5)
    for y in (200, 380, 560):
        arrow(ax, (cx - 190, y + 40), (cx - 130, y + 40), lw=2.4, color="#2471a3")
        arrow(ax, (cx + 190, y + 40), (cx + 130, y + 40), lw=2.4, color="#2471a3")
    arrow(ax, (cx, 600), (cx, 770), lw=3, color="#2471a3")
    # numerals: 1 osculum, 2 spongocoel, 3 ostium, 4 choanocyte layer, 5 radial canal,
    # 6 incurrent canal, 7 flagellated chamber, 8 excurrent canal
    num(ax, 1, 120, 840, 200, 700)
    num(ax, 2, 330, 400, 200, 400)
    num(ax, 3, 60, 340, 125, 340)
    num(ax, 4, 330, 520, 235, 520)
    num(ax, 1, 520, 840, 600, 700)
    num(ax, 2, 740, 400, 600, 400)
    num(ax, 5, 800, 620, 640, 620)
    num(ax, 6, 440, 90, 480, 280)
    num(ax, 1, 920, 840, 1000, 700)
    num(ax, 7, 1135, 300, 1050, 290)
    num(ax, 8, 800, 130, 1000, 250, r=26)
    save(fig, "sponge-grades")


def cnidarian_forms():
    """Polyp (A) and medusa (B) as the same plan, one upright, one inverted."""
    fig, ax = canvas()
    letter(ax, "A", 70, 840); letter(ax, "B", 670, 840)
    # polyp
    ell_c = "#f1dcb0"
    rect(ax, 190, 120, 200, 420, fc=ell_c, r=40)                  # body
    rect(ax, 235, 160, 110, 340, fc="#cfe6f7", lw=2, z=4)          # gastrovascular cavity
    rect(ax, 160, 70, 260, 60, fc="#b8a37a", r=14)                 # pedal disc
    ax.add_patch(Ellipse((290, 545), 110, 40, fc="#e9c999", ec=INK, lw=2.4, zorder=5))   # hypostome
    ax.add_patch(Ellipse((290, 548), 60, 20, fc="#cfe6f7", ec=INK, lw=2, zorder=6))      # mouth
    for dx in (-110, -60, 60, 110):
        line(ax, [(290 + dx * 0.55, 540), (290 + dx, 640), (290 + dx * 1.15, 740)], lw=6, color="#a0825a")
    line(ax, [(195, 160), (195, 520)], lw=2, color=INK, z=6); line(ax, [(385, 160), (385, 520)], lw=2, color=INK, z=6)
    # medusa (bell mouth downwards)
    ax.add_patch(Wedge((900, 400), 250, 0, 180, fc="#f1dcb0", ec=INK, lw=2.8, zorder=3))
    ax.add_patch(Wedge((900, 400), 205, 0, 180, fc="#cfe6f7", ec=INK, lw=2, zorder=4))
    rect(ax, 865, 220, 70, 190, fc="#e9c999", r=20, z=6)             # manubrium
    ax.add_patch(Ellipse((900, 222), 50, 18, fc="#cfe6f7", ec=INK, lw=2, zorder=7))   # mouth
    for dx in (-230, 230):
        for k in range(3):
            line(ax, [(900 + dx, 400 - k * 20), (900 + dx * 1.04, 330 - k * 40), (900 + dx * 1.02, 250 - k * 55)], lw=5, color="#a0825a")
    line(ax, [(900, 400), (900, 590)], lw=0)
    line(ax, [(690, 400), (1110, 400)], lw=5, color="#a0825a", z=8)  # velum / rim
    line(ax, [(900, 400), (800, 600)], lw=2, color="#2471a3", ls="--", z=8)  # radial canal
    line(ax, [(900, 400), (1000, 600)], lw=2, color="#2471a3", ls="--", z=8)
    num(ax, 1, 90, 560, 255, 546)
    num(ax, 2, 90, 400, 195, 400)
    num(ax, 3, 490, 400, 290, 330)
    num(ax, 4, 490, 700, 345, 700)
    num(ax, 5, 490, 100, 290, 100)
    num(ax, 2, 1130, 640, 1090, 540)
    num(ax, 6, 700, 700, 720, 560)
    num(ax, 7, 1130, 280, 935, 300)
    num(ax, 1, 700, 140, 870, 222)
    save(fig, "cnidarian-forms")


def metameres():
    """Schematic longitudinal section of a segmented worm: five segments."""
    fig, ax = canvas(12, 8)
    n, x0, sw = 5, 180, 170
    rect(ax, x0 - 60, 220, n * sw + 120, 340, fc="#f6e8cf", r=60)       # body wall
    for i in range(n):
        xs = x0 + i * sw
        rect(ax, xs + 8, 235, sw - 16, 310, fc="#fbf5e8", lw=1.2, z=3)      # coelomic compartment
        if i:
            line(ax, [(xs, 235), (xs, 545)], lw=6, z=5)                      # septum
        ax.add_patch(Circle((xs + sw / 2, 285), 15, fc="#f4c542", ec=INK, lw=2, zorder=7))   # ganglion
        line(ax, [(xs + sw / 2, 300), (xs + sw / 2, 340)], lw=2, z=6)
        line(ax, [(xs + sw / 2 - 40, 480), (xs + sw / 2 - 25, 430), (xs + sw / 2 - 5, 450), (xs + sw / 2 + 5, 500)], lw=4, color="#5a7da6", z=6)  # nephridium
        line(ax, [(xs + sw / 2 + 50, 235), (xs + sw / 2 + 50, 200)], lw=4, z=6)                                                            # chaeta
        line(ax, [(xs + sw / 2 + 60, 235), (xs + sw / 2 + 60, 205)], lw=4, z=6)
    rect(ax, x0 - 30, 390, n * sw + 60, 50, fc="#e6a57e", lw=2.4, z=6)      # gut
    line(ax, [(x0 - 30, 475), (x0 + n * sw + 30, 475)], lw=5, color="#c0392b", z=6)                 # dorsal vessel
    line(ax, [(x0 - 30, 330), (x0 + n * sw + 30, 330)], lw=6, color="#f4c542", z=6)                 # nerve cord
    ell(ax, x0 - 100, 390, 70, 120, fc="#d9a066", z=3)                                              # prostomium region
    ell(ax, x0 + n * sw + 90, 390, 60, 120, fc="#d9a066", z=3)                                      # pygidium region
    num(ax, 1, 300, 650, 330, 553)
    num(ax, 2, 480, 650, 480, 515)
    num(ax, 3, 690, 650, 690, 520)
    num(ax, 4, 880, 650, 860, 476)
    num(ax, 5, 1040, 650, 1030, 332)
    num(ax, 6, 440, 120, 435, 285, r=26)
    num(ax, 7, 700, 120, 700, 392)
    num(ax, 8, 880, 120, 835, 212)
    num(ax, 9, 130, 650, 85, 480)
    num(ax, 10, 1130, 650, 1090, 480)
    num(ax, 11, 560, 120, 583, 432)
    save(fig, "metameres")


def host_cycles():
    """(A) one-host cycle, (B) two-host cycle. Hosts are plain boxes; parasite stages are dots."""
    fig, ax = canvas()
    letter(ax, "A", 60, 800); letter(ax, "B", 60, 380)
    def host(x, y, fc, w=260, h=170):
        rect(ax, x - w / 2, y - h / 2, w, h, fc=fc, r=40)
    def parasites(x, y, n=5, c="#c0392b"):
        for k in range(n):
            ax.add_patch(Circle((x - 60 + (k % 3) * 60, y - 25 + (k // 3) * 55), 13, fc=c, ec=INK, lw=1.8, zorder=6))
    # A
    host(260, 690, "#cfe6f7"); parasites(260, 690)
    ax.add_patch(Circle((600, 690), 55, fc="#fff3d6", ec=INK, lw=5, zorder=4))
    ax.add_patch(Circle((600, 690), 22, fc="#c0392b", ec=INK, lw=1.8, zorder=6))
    host(940, 690, "#d6ecd6"); 
    arrow(ax, (400, 700), (530, 700), lw=3.4)
    arrow(ax, (670, 700), (800, 700), lw=3.4)
    num(ax, 1, 260, 820, 260, 775); num(ax, 2, 160, 580, 205, 665)
    num(ax, 3, 600, 820, 600, 748); num(ax, 4, 940, 820, 940, 775)
    # B
    host(300, 220, "#cfe6f7"); parasites(300, 220, n=3, c="#8e44ad")
    host(900, 220, "#f5d9b5"); parasites(900, 220, n=5)
    arrow(ax, (440, 270), (760, 270), rad=-0.25, lw=3.4)
    arrow(ax, (760, 170), (440, 170), rad=-0.25, lw=3.4)
    num(ax, 5, 300, 90, 300, 135); num(ax, 6, 900, 90, 900, 135)
    num(ax, 7, 600, 450, 600, 345); num(ax, 8, 600, 40, 600, 105)
    ax.plot([30, 1170], [470, 470], color=GREY, lw=3)
    save(fig, "host-cycles")


def pearl_formation():
    fig, ax = canvas()
    letter(ax, "A", 60, 800); letter(ax, "B", 660, 800)
    # A: shell layers (stacked bands), mantle epithelium, mantle body
    layers = [(690, 30, "#6d4c41"), (590, 100, "#d9d2c3"), (510, 80, "#f3dfe8")]
    for y, h, c in layers:
        rect(ax, 90, y, 460, h, fc=c, lw=2.4)
    rect(ax, 90, 480, 460, 30, fc="#e6a57e", lw=2.4)                    # epithelium
    for x in range(100, 550, 28):
        line(ax, [(x, 480), (x, 510)], lw=1.4)
    rect(ax, 90, 150, 460, 330, fc="#f6d9c4", lw=2.4)                  # mantle body
    for x in range(130, 520, 60):
        ax.add_patch(Circle((x, 300 + (30 if (x // 60) % 2 else -30)), 14, fc="#e8b28f", ec=INK, lw=1.6, zorder=5))
    num(ax, 1, 640, 790, 520, 712)
    num(ax, 2, 640, 680, 520, 640)
    num(ax, 3, 640, 580, 520, 550)
    num(ax, 4, 640, 450, 520, 495)
    num(ax, 5, 640, 280, 520, 300)
    # B: pearl in a pearl sac
    cx, cy = 910, 400
    rect(ax, 680, 120, 440, 560, fc="#f6d9c4", r=60)                    # host tissue
    ax.add_patch(Circle((cx, cy), 200, fc="#e6a57e", ec=INK, lw=2.8, zorder=3))   # sac epithelium ring
    ax.add_patch(Circle((cx, cy), 175, fc="#fbf2f6", ec=INK, lw=2, zorder=4))
    for r, c in ((160, "#efe2ea"), (125, "#e4d3de"), (90, "#d8c3d2")):
        ax.add_patch(Circle((cx, cy), r, fc=c, ec=INK, lw=1.4, zorder=5))
    ax.add_patch(Circle((cx, cy), 55, fc="#9aa0a6", ec=INK, lw=2.4, zorder=6))
    for k in range(36):
        a = 2 * np.pi * k / 36
        ax.add_patch(Circle((cx + 188 * np.cos(a), cy + 188 * np.sin(a)), 9, fc="#d98a64", ec=INK, lw=1.4, zorder=6))
    num(ax, 6, 700, 740, 880, 400, r=26)
    num(ax, 7, 1130, 740, 1010, 470)
    num(ax, 8, 1130, 120, 1070, 330)
    num(ax, 9, 700, 60, 740, 200)
    save(fig, "pearl-formation")


def body_cavities():
    """Cross-sections of an acoelomate (A), a pseudocoelomate (B) and a coelomate (C)."""
    fig, ax = canvas()
    EP, MESO, GUTW, CAV, LUM = "#e6a57e", "#f3d9a8", "#cfe6a8", "#e3f1fb", "#fff7e0"
    letter(ax, "A", 60, 840); letter(ax, "B", 460, 840); letter(ax, "C", 860, 840)
    R = 150
    # A acoelomate: epidermis ring, solid mesoderm fill, gut
    cx, cy = 200, 520
    ax.add_patch(Circle((cx, cy), R + 14, fc=EP, ec=INK, lw=2.6, zorder=2))
    ax.add_patch(Circle((cx, cy), R, fc=MESO, ec=INK, lw=2, zorder=3))
    ax.add_patch(Circle((cx, cy), 55, fc=GUTW, ec=INK, lw=2.4, zorder=4))
    ax.add_patch(Circle((cx, cy), 30, fc=LUM, ec=INK, lw=2, zorder=5))
    for a in range(0, 360, 40):
        ax.add_patch(Circle((cx + 105 * np.cos(np.deg2rad(a)), cy + 105 * np.sin(np.deg2rad(a))), 7, fc="#e6c98a", ec=INK, lw=1, zorder=4))
    # B pseudocoelomate: epidermis, muscle (mesoderm) ring, open cavity, gut
    cx = 600
    ax.add_patch(Circle((cx, cy), R + 14, fc=EP, ec=INK, lw=2.6, zorder=2))
    ax.add_patch(Circle((cx, cy), R, fc=MESO, ec=INK, lw=2, zorder=3))
    ax.add_patch(Circle((cx, cy), R - 40, fc=CAV, ec=INK, lw=2, zorder=4))
    ax.add_patch(Circle((cx, cy), 55, fc=GUTW, ec=INK, lw=2.4, zorder=5))
    ax.add_patch(Circle((cx, cy), 30, fc=LUM, ec=INK, lw=2, zorder=6))
    # C coelomate: epidermis, muscle ring, coelom with peritoneum on both surfaces, gut with muscle
    cx = 1000
    ax.add_patch(Circle((cx, cy), R + 14, fc=EP, ec=INK, lw=2.6, zorder=2))
    ax.add_patch(Circle((cx, cy), R, fc=MESO, ec=INK, lw=2, zorder=3))
    ax.add_patch(Circle((cx, cy), R - 40, fc="#8f6f50", ec=INK, lw=2, zorder=4))        # peritoneum (body wall side)
    ax.add_patch(Circle((cx, cy), R - 46, fc=CAV, ec=INK, lw=1.2, zorder=5))            # coelom
    ax.add_patch(Circle((cx, cy), 78, fc="#8f6f50", ec=INK, lw=2, zorder=6))            # peritoneum (gut side)
    ax.add_patch(Circle((cx, cy), 72, fc=MESO, ec=INK, lw=1.2, zorder=7))               # gut muscle
    ax.add_patch(Circle((cx, cy), 55, fc=GUTW, ec=INK, lw=2.4, zorder=8))
    ax.add_patch(Circle((cx, cy), 30, fc=LUM, ec=INK, lw=2, zorder=9))
    # keys: 1 epidermis, 2 mesoderm, 3 gut wall (endoderm), 4 gut lumen, 5 body cavity, 6 peritoneum
    num(ax, 1, 60, 380, 70, 460 + 0 if False else 395)
    num(ax, 2, 330, 330, 290, 470)
    num(ax, 3, 200, 330, 200, 485 - 0)
    num(ax, 4, 90, 700, 175, 550)
    num(ax, 1, 460, 380, 465, 400)
    num(ax, 5, 740, 330, 640, 470)
    num(ax, 3, 600, 330, 600, 485)
    num(ax, 2, 700, 700, 705, 575)
    num(ax, 1, 860, 380, 866, 400)
    num(ax, 5, 1140, 700, 1070, 579)
    num(ax, 6, 1140, 330, 1054, 426)
    num(ax, 3, 1000, 330, 1000, 485)
    num(ax, 2, 1140, 600, 1113, 455)
    save(fig, "body-cavities")


def nematode_bodywall():
    fig, ax = canvas(12, 9)
    cx, cy = 600, 450
    R = 330
    ax.add_patch(Circle((cx, cy), R + 22, fc="#e9e2c8", ec=INK, lw=2.6, zorder=2))          # cuticle
    ax.add_patch(Circle((cx, cy), R, fc=EP2 if False else "#e6a57e", ec=INK, lw=2.4, zorder=3))   # epidermis
    # four thickenings of the epidermis (dorsal, ventral, 2 lateral)
    for a in (0, 90, 180, 270):
        ax.add_patch(Circle((cx + (R - 8) * np.cos(np.deg2rad(a)), cy + (R - 8) * np.sin(np.deg2rad(a))), 36,
                            fc="#e6a57e", ec=INK, lw=2.2, zorder=4))
    # longitudinal muscle cells in four quadrants
    for q, a0 in enumerate((8, 98, 188, 278)):
        for k in range(7):
            a = np.deg2rad(a0 + 6 + k * 11.5)
            ax.add_patch(Circle((cx + (R - 55) * np.cos(a), cy + (R - 55) * np.sin(a)), 26,
                                fc="#d0a0c8", ec=INK, lw=1.6, zorder=5))
    ax.add_patch(Circle((cx, cy), R - 100, fc="#e3f1fb", ec=INK, lw=2, zorder=4))             # pseudocoelom
    ax.add_patch(Circle((cx, cy), 110, fc="#cfe6a8", ec=INK, lw=2.6, zorder=6))               # intestine
    ax.add_patch(Circle((cx, cy), 55, fc="#fff7e0", ec=INK, lw=2, zorder=7))
    for a in (90, 270):                                                                       # nerve cords (unlabelled)
        ax.add_patch(Circle((cx + (R - 40) * np.cos(np.deg2rad(a)), cy + (R - 40) * np.sin(np.deg2rad(a))), 10,
                            fc="#f4c542", ec=INK, lw=1.8, zorder=8))
    num(ax, 1, 1060, 800, cx + (R + 11) * np.cos(np.deg2rad(40)), cy + (R + 11) * np.sin(np.deg2rad(40)))
    num(ax, 2, 140, 800, cx + (R - 10) * np.cos(np.deg2rad(140)), cy + (R - 10) * np.sin(np.deg2rad(140)))
    num(ax, 3, 1100, 140, cx + (R - 55) * np.cos(np.deg2rad(-40)), cy + (R - 55) * np.sin(np.deg2rad(-40)))
    num(ax, 4, 140, 140, cx + 190 * np.cos(np.deg2rad(215)), cy + 190 * np.sin(np.deg2rad(215)))
    num(ax, 5, 600, 40, cx, cy - 85)
    save(fig, "nematode-bodywall")


def nematode_routes():
    """Three transmission routes of human nematodes, one per row (A Ascaris, B hookworm, C Wuchereria)."""
    fig, ax = canvas(12, 9)
    def person(x, y, dots=False):
        ax.add_patch(Circle((x, y + 62), 26, fc="#e8c39e", ec=INK, lw=2.2, zorder=3))
        rect(ax, x - 32, y - 52, 64, 100, fc="#9ec5e8", r=18)
        if dots:
            for dx, dy in ((-12, -8), (8, 6), (-2, -30)):
                ax.add_patch(Circle((x + dx, y + dy), 5, fc="#c0392b", zorder=6))
    def egg(x, y, inner=False):
        ax.add_patch(Ellipse((x, y), 70, 52, fc="#fff3d6", ec=INK, lw=3, zorder=4))
        if inner:
            t = np.linspace(0, 2 * np.pi * 1.6, 60)
            line(ax, [(x + 20 * np.cos(u) * (1 - u / 12), y + 14 * np.sin(u) * (1 - u / 12)) for u in t], lw=3, color="#7d6608", z=6)
        else:
            ax.add_patch(Circle((x, y), 11, fc="#c0392b", ec=INK, lw=1.6, zorder=6))
    def soil(x, y, w=210):
        rect(ax, x - w / 2, y - 55, w, 36, fc="#a68a64", lw=2, z=2)
    def larva(x, y, c="#7d6608", n=2):
        for k in range(n):
            t = np.linspace(0, 1, 30)
            line(ax, [(x - 40 + 80 * u, y + 12 * np.sin(8 * u + k * 2) + k * 16) for u in t], lw=4, color=c, z=6)
    def mosquito(x, y, larvae=False):
        ell(ax, x, y, 130, 36, fc="#8d7b68")
        ell(ax, x + 72, y + 4, 40, 32, fc="#8d7b68")
        line(ax, [(x + 90, y), (x + 150, y - 36)], lw=2.4)
        ell(ax, x - 8, y + 44, 100, 30, fc="#dbeaf7", ang=-20, alpha=.9)
        for dx in (-34, 0, 34):
            line(ax, [(x + dx, y - 16), (x + dx - 14, y - 60)], lw=2)
        if larvae:
            for dx in (-30, 0, 30):
                ax.add_patch(Circle((x + dx, y), 6, fc="#7d6608", zorder=6))
    xs = [150, 420, 700, 990]
    ys = [750, 450, 150]
    for y, l in zip(ys, "ABC"):
        letter(ax, l, 50, y + 30)
    # A  Ascaris: person -> egg -> embryonated egg in soil -> person swallows it
    y = ys[0]
    person(xs[0], y); egg(xs[1], y + 20); soil(xs[1], y + 20); egg(xs[2], y + 20, inner=True); soil(xs[2], y + 20); person(xs[3], y)
    # B  hookworm: person -> egg -> free larva in soil -> larva through skin of a foot
    y = ys[1]
    person(xs[0], y); egg(xs[1], y + 20); soil(xs[1], y + 20); larva(xs[2], y + 30); soil(xs[2], y + 20)
    rect(ax, xs[3] - 40, y - 30, 80, 60, fc="#e8c39e", r=14); larva(xs[3], y + 60, n=1)
    # C  Wuchereria: person (microfilariae in blood) -> mosquito feeds -> larvae develop -> bite
    y = ys[2]
    person(xs[0], y, dots=True); mosquito(xs[1] - 30, y + 10); mosquito(xs[2] - 30, y + 10, larvae=True); person(xs[3], y)
    for yy in ys:
        for a, b in ((0, 1), (1, 2), (2, 3)):
            arrow(ax, (xs[a] + 85, yy + 20), (xs[b] - 100, yy + 20), lw=3)
    for n, x in zip((1, 2, 3, 4), xs):
        num(ax, n, x, 858)
    save(fig, "nematode-routes")


def mollusc_plan():
    """Generalised mollusc, side view; head to the left, dorsal up."""
    fig, ax = canvas(12, 8)
    # foot (ventral), visceral mass, head
    ell(ax, 600, 250, 760, 130, fc="#e6a57e", z=3)                       # foot
    ell(ax, 260, 330, 180, 170, fc="#d9a066", z=4)                       # head
    ax.add_patch(Circle((235, 360), 14, fc=INK, zorder=7))                # eye
    ell(ax, 190, 310, 40, 26, fc="#8e4a3a", z=6)                          # mouth
    for k in range(2):
        line(ax, [(250 + k * 40, 405), (240 + k * 60, 470)], lw=4, color="#a0825a", z=5)    # tentacles
    ell(ax, 640, 420, 440, 250, fc="#f6d9c4", z=3)                       # visceral mass
    # mantle (thin band under the shell) and mantle cavity at the rear
    t = np.linspace(0, np.pi, 60)
    line(ax, [(640 + 255 * np.cos(u), 420 + 215 * np.sin(u)) for u in t], lw=9, color="#c97b5a", z=6)
    ell(ax, 880, 345, 130, 130, fc="#dbeaf7", z=2)                       # mantle cavity
    for k in range(5):                                                     # ctenidium lamellae
        line(ax, [(840 + k * 22, 300), (850 + k * 22, 380)], lw=6, color="#6b8fb3", z=6)
    # shell
    line(ax, [(640 + 285 * np.cos(u), 420 + 245 * np.sin(u)) for u in t], lw=18, color="#6d4c41", z=5)
    num(ax, 1, 500, 740, 560, 660)
    num(ax, 2, 900, 700, 845, 610)
    num(ax, 3, 1090, 500, 940, 400)
    num(ax, 4, 1090, 260, 880, 330)
    num(ax, 5, 560, 560, 600, 450)
    num(ax, 6, 600, 90, 600, 210)
    num(ax, 7, 120, 560, 215, 380)
    num(ax, 8, 100, 190, 185, 305)
    save(fig, "mollusc-plan")


def echino_symmetry():
    fig, ax = canvas()
    letter(ax, "A", 60, 800); letter(ax, "B", 460, 800); letter(ax, "C", 860, 800)
    def star(cx, cy, aboral):
        pts = []
        for k in range(10):
            a = np.deg2rad(90 + k * 36)
            r = 165 if k % 2 == 0 else 62
            pts.append((cx + r * np.cos(a), cy + r * np.sin(a)))
        poly(ax, pts, fc="#e6a57e", lw=2.6)
        if not aboral:
            for k in range(5):
                a = np.deg2rad(90 + k * 72)
                line(ax, [(cx, cy), (cx + 150 * np.cos(a), cy + 150 * np.sin(a))], lw=8, color="#f6d9c4", z=5)
                for j in range(1, 6):
                    r = 36 + j * 22
                    for sg in (-1, 1):
                        px = cx + r * np.cos(a) + sg * 11 * np.cos(a + np.pi / 2)
                        py = cy + r * np.sin(a) + sg * 11 * np.sin(a + np.pi / 2)
                        ax.add_patch(Circle((px, py), 6, fc="#fff3d6", ec=INK, lw=1.2, zorder=6))
            ax.add_patch(Circle((cx, cy), 22, fc="#8e4a3a", ec=INK, lw=2, zorder=7))
        else:
            ax.add_patch(Circle((cx + 26, cy + 20), 13, fc="#fff3d6", ec=INK, lw=2.4, zorder=7))
            ax.add_patch(Circle((cx, cy), 10, fc=INK, zorder=7))
    star(200, 420, True); star(600, 420, False)
    # C bilateral larva: elongated body with a looped ciliated band
    ell(ax, 1000, 420, 190, 340, fc="#e3f1fb", z=3)
    line(ax, [(930, 320), (940, 540), (1000, 590), (1060, 540), (1070, 320), (1000, 270), (930, 320)], lw=5, color="#8e4a3a", z=5)
    line(ax, [(960, 400), (1040, 400)], lw=0)
    ax.add_patch(Circle((1000, 330), 18, fc="#cfe6a8", ec=INK, lw=2, zorder=6))
    ax.plot([1000, 1000], [250, 600], color=INK, lw=1.6, linestyle=(0, (6, 5)), zorder=7)
    num(ax, 1, 200, 700, 200, 420)
    num(ax, 2, 70, 190, 112, 300)
    num(ax, 3, 330, 330, 226, 440)
    num(ax, 4, 600, 700, 600, 440)
    num(ax, 5, 760, 190, 698, 455)
    num(ax, 6, 1130, 700, 1065, 540)
    num(ax, 7, 1130, 230, 1000, 260)
    save(fig, "echino-symmetry")


def cilium_stroke():
    fig, ax = canvas(12, 8)
    GR = ["#d5dbe0", "#a9b4be", "#6f7d8a", INK]
    def surface(x, y, w):
        rect(ax, x, y, w, 60, fc="#d9c7a0", lw=2.4)
    def curve(bx, by, th0, th1, L=270, lw=6, color=INK):
        pts = []
        for u in np.linspace(0, 1, 40):
            th = np.deg2rad(th0 + (th1 - th0) * u)
            if u == 0:
                pts.append((bx, by)); continue
            x, y = pts[-1]
            pts.append((x + (L / 39) * np.cos(th), y + (L / 39) * np.sin(th)))
        line(ax, pts, lw=lw, color=color, z=5)
    letter(ax, "A", 60, 740); letter(ax, "B", 660, 740); letter(ax, "C", 60, 330)
    # A effective stroke: stiff cilium sweeps from left to right (time runs light to dark)
    surface(40, 380, 520)
    for th, c in zip((150, 120, 90, 60), GR):
        curve(300, 440, th, th, color=c)
    arrow(ax, (150, 690), (450, 690), rad=-0.45, lw=3.4)
    # B recovery stroke: cilium bends and rolls back to the left close to the surface
    surface(640, 380, 520)
    for t1, c in zip((20, 60, 110, 160), GR):
        curve(900, 440, 75 if t1 > 40 else 60, t1, L=230, color=c)
    arrow(ax, (1050, 690), (750, 690), rad=0.45, lw=3.4)
    # C metachronal wave
    surface(40, 100, 1120, )
    for k in range(13):
        bx = 80 + k * 85
        th = 90 + 38 * np.sin(2 * np.pi * k / 13)
        curve(bx, 160, th, th, L=130, lw=5)
    arrow(ax, (100, 40), (1000, 40), lw=3.4)
    num(ax, 1, 120, 520, 300, 437)
    num(ax, 2, 440, 600, 330, 520)
    num(ax, 3, 560, 750, 330, 690)
    num(ax, 4, 780, 750, 900, 690)
    num(ax, 5, 1100, 40, 1000, 40)
    save(fig, "cilium-stroke")


def coral_polyp():
    fig, ax = canvas(12, 9)
    # skeletal cup (calyx), longitudinal section: two walls and a floor, septa as ridges
    cx = 600
    poly(ax, [(cx - 280, 120), (cx + 280, 120), (cx + 280, 470), (cx + 250, 470), (cx + 250, 170), (cx - 250, 170), (cx - 250, 470), (cx - 280, 470)],
         fc="#f1efe6", lw=2.8)
    for k in range(4):
        y = 230 + k * 55
        poly(ax, [(cx - 250, y), (cx - 170, y), (cx - 170, y + 22), (cx - 250, y + 22)], fc="#f1efe6", lw=2)
        poly(ax, [(cx + 250, y), (cx + 170, y), (cx + 170, y + 22), (cx + 250, y + 22)], fc="#f1efe6", lw=2)
    # polyp tissue
    poly(ax, [(cx - 250, 170), (cx + 250, 170), (cx + 250, 470), (cx + 170, 520), (cx + 120, 560), (cx - 120, 560), (cx - 170, 520), (cx - 250, 470)], fc="#f2b8ad", lw=2.4, z=2)
    ax.add_patch(Rectangle((cx - 165, 180), 330, 330, fc="#fbe3dc", ec=INK, lw=2, zorder=3))       # gastrovascular cavity
    # pharynx and mouth
    rect(ax, cx - 55, 440, 110, 140, fc="#d98a64", lw=2.4, z=5)
    ell(ax, cx, 590, 110, 36, fc="#8e4a3a", z=6)
    # tentacles
    for dx in (-210, -105, 105, 210):
        line(ax, [(cx + dx * 0.9, 520), (cx + dx * 1.15, 640), (cx + dx * 1.3, 760)], lw=14, color="#f2b8ad", z=4)
        line(ax, [(cx + dx * 0.9, 520), (cx + dx * 1.15, 640), (cx + dx * 1.3, 760)], lw=3, color=INK, z=3)
    # zooxanthellae
    for (x, y) in ((cx - 215, 470), (cx - 230, 420), (cx + 225, 450), (cx + 205, 500), (cx - 190, 500), (cx + 190, 380)):
        ax.add_patch(Circle((x, y), 11, fc="#8a6d1f", ec=INK, lw=1.4, zorder=7))
    num(ax, 1, 330, 800, 479, 640)
    num(ax, 2, 840, 800, 640, 590)
    num(ax, 3, 1100, 600, 855, 490, r=26)
    num(ax, 4, 1100, 300, 700, 330)
    num(ax, 5, 100, 300, cx - 270, 300)
    num(ax, 6, 100, 140, cx - 210, 240)
    num(ax, 7, 100, 560, cx - 215, 470)
    save(fig, "coral-polyp")


def annelid_section():
    """Cross-section of the trunk of an annelid, dorsal side up."""
    fig, ax = canvas(12, 9)
    cx, cy, R = 600, 450, 330
    ax.add_patch(Circle((cx, cy), R + 14, fc="#e9e2c8", ec=INK, lw=2.4, zorder=2))      # cuticle
    ax.add_patch(Circle((cx, cy), R, fc="#e6a57e", ec=INK, lw=2.4, zorder=3))           # epidermis
    ax.add_patch(Circle((cx, cy), R - 28, fc="#c9a3d3", ec=INK, lw=2, zorder=4))        # circular muscle
    ax.add_patch(Circle((cx, cy), R - 60, fc="#e0b4c0", ec=INK, lw=2, zorder=5))        # longitudinal muscle
    for k in range(48):
        a = 2 * np.pi * k / 48
        ax.add_patch(Circle((cx + (R - 46) * np.cos(a), cy + (R - 46) * np.sin(a)), 3.2, fc="#7a4a8a", zorder=6))
    ax.add_patch(Circle((cx, cy), R - 100, fc="#e3f1fb", ec=INK, lw=2, zorder=6))       # coelom
    # gut
    ax.add_patch(Circle((cx, cy + 10), 105, fc="#cfe6a8", ec=INK, lw=2.6, zorder=8))
    ax.add_patch(Circle((cx, cy + 10), 60, fc="#fff7e0", ec=INK, lw=2, zorder=9))
    # vessels and nerve cord
    ax.add_patch(Circle((cx, cy + 135), 20, fc="#c0392b", ec=INK, lw=2, zorder=9))      # dorsal vessel
    ax.add_patch(Circle((cx, cy - 135), 17, fc="#e8776a", ec=INK, lw=2, zorder=9))      # ventral vessel
    ax.add_patch(Ellipse((cx, cy - 185), 64, 38, fc="#f4c542", ec=INK, lw=2, zorder=9)) # ventral nerve cord
    # nephridium: small tubule sections on one side
    for (dx, dy) in ((-150, -90), (-175, -60), (-130, -60)):
        ax.add_patch(Circle((cx + dx, cy + dy), 12, fc="#e3f1fb", ec="#5a7da6", lw=3, zorder=9))
    num(ax, 1, 1060, 800, cx + (R + 7) * np.cos(np.deg2rad(40)), cy + (R + 7) * np.sin(np.deg2rad(40)))
    num(ax, 2, 140, 800, cx + (R - 10) * np.cos(np.deg2rad(140)), cy + (R - 10) * np.sin(np.deg2rad(140)))
    num(ax, 3, 1130, 560, cx + (R - 28) * np.cos(np.deg2rad(20)), cy + (R - 28) * np.sin(np.deg2rad(20)))
    num(ax, 4, 1130, 330, cx + (R - 60) * np.cos(np.deg2rad(-22)), cy + (R - 60) * np.sin(np.deg2rad(-22)))
    num(ax, 5, 1080, 120, cx + 215 * np.cos(np.deg2rad(-45)), cy + 215 * np.sin(np.deg2rad(-45)))
    num(ax, 6, 600, 40, cx - 60, cy - 70 + 10)
    num(ax, 7, 840, 800, cx + 10, cy + 140)
    num(ax, 8, 340, 120, cx - 12, cy - 140)
    num(ax, 9, 760, 40, cx + 20, cy - 190)
    num(ax, 10, 70, 450, cx - 175, cy - 60)
    save(fig, "annelid-section")


def flame_cell():
    fig, ax = canvas(12, 8)
    # parenchyma region
    rect(ax, 60, 120, 1080, 600, fc="#f6ecd2", r=40)
    ax.plot([60, 1140], [720, 720], color=INK, lw=0)
    # flame cell
    ell(ax, 260, 420, 300, 240, fc="#f2b8ad", z=3)
    ell(ax, 275, 420, 190, 140, fc="#fbe3dc", z=4)          # cavity
    for k in range(-4, 5):
        t = np.linspace(0, 1, 20)
        line(ax, [(190 + 160 * u, 420 + k * 12 + 8 * np.sin(6 * u + k)) for u in t], lw=2.2, color="#7d6608", z=6)
    ax.add_patch(Circle((200, 330), 22, fc="#8e6aa8", ec=INK, lw=2, zorder=6))        # nucleus
    # capillary and collecting duct to the pore
    line(ax, [(410, 420), (520, 420), (560, 360), (640, 360)], lw=8, color="#5a7da6", z=5)
    line(ax, [(640, 360), (800, 360), (860, 300), (960, 300), (1060, 300), (1140, 300)], lw=14, color="#5a7da6", z=5)
    ell(ax, 700, 520, 120, 90, fc="#f2b8ad", z=3); ell(ax, 705, 520, 70, 50, fc="#fbe3dc", z=4)   # second flame cell
    for k in range(-2, 3):
        line(ax, [(670 + 70 * u, 520 + k * 12) for u in (0, 1)], lw=2, color="#7d6608", z=6)
    line(ax, [(700, 475), (700, 410), (700, 362)], lw=7, color="#5a7da6", z=5)
    ax.add_patch(Circle((1140, 300), 12, fc="white", ec=INK, lw=2.4, zorder=7))
    num(ax, 1, 260, 640, 260, 530)
    num(ax, 2, 130, 200, 235, 405)
    num(ax, 3, 120, 560, 200, 337)
    num(ax, 4, 520, 560, 520, 424)
    num(ax, 5, 960, 440, 960, 308)
    num(ax, 6, 1090, 420, 1140, 312)
    num(ax, 7, 800, 180, 780, 220)
    save(fig, "flame-cell")


def myriapod_sections():
    fig, ax = canvas(12, 7)
    letter(ax, "A", 60, 620); letter(ax, "B", 660, 620)
    # A centipede: dorsoventrally flattened, one pair of legs, lateral
    ell(ax, 300, 320, 360, 170, fc="#d8c19a", z=3)
    ell(ax, 300, 335, 150, 70, fc="#cfe6a8", z=4)                      # gut
    ell(ax, 300, 255, 70, 28, fc="#f4c542", z=4)                       # nerve cord
    for sg in (1, -1):
        line(ax, [(300 + sg * 170, 300), (300 + sg * 250, 250), (300 + sg * 290, 150)], lw=8, z=2)
    line(ax, [(150, 372), (450, 372)], lw=0)
    # B millipede: cylindrical, two pairs of legs ventral
    ax.add_patch(Circle((900, 340), 150, fc="#d8c19a", ec=INK, lw=2.6, zorder=3))
    ell(ax, 900, 365, 150, 85, fc="#cfe6a8", z=4)
    ell(ax, 900, 235, 70, 28, fc="#f4c542", z=4)
    for dx in (-85, -40, 40, 85):
        line(ax, [(900 + dx, 215), (900 + dx * 1.15, 150), (900 + dx * 1.25, 90)], lw=7, z=2)
    num(ax, 1, 300, 600, 300, 400)
    num(ax, 2, 420, 480, 330, 345)
    num(ax, 3, 110, 80, 30, 200)
    num(ax, 4, 120, 400, 275, 255)
    num(ax, 1, 900, 600, 900, 490)
    num(ax, 2, 1110, 470, 940, 380)
    num(ax, 3, 640, 90, 802, 150)
    num(ax, 4, 1110, 260, 925, 238)
    save(fig, "myriapod-sections")


def symmetry_types():
    fig, ax = canvas(12, 7)
    letter(ax, "A", 60, 620); letter(ax, "B", 460, 620); letter(ax, "C", 860, 620)
    # A asymmetry: irregular blob
    t = np.linspace(0, 2 * np.pi, 80)
    pts = [(200 + (120 + 35 * np.sin(3 * u) + 22 * np.cos(5 * u + 1)) * np.cos(u),
            320 + (100 + 30 * np.cos(2 * u + .5) + 18 * np.sin(4 * u)) * np.sin(u)) for u in t]
    poly(ax, pts, fc="#d9c7a0", lw=2.6)
    # B radial: disc with arms/tentacles evenly spaced, several planes
    cx, cy = 600, 320
    ax.add_patch(Circle((cx, cy), 95, fc="#e6a57e", ec=INK, lw=2.6, zorder=3))
    for k in range(8):
        a = np.deg2rad(k * 45)
        line(ax, [(cx + 95 * np.cos(a), cy + 95 * np.sin(a)), (cx + 175 * np.cos(a), cy + 175 * np.sin(a))], lw=9, color="#c97b5a", z=3)
    ax.add_patch(Circle((cx, cy), 20, fc="#8e4a3a", ec=INK, lw=2, zorder=5))
    for a in (0, 45, 90, 135):
        r = np.deg2rad(a)
        ax.plot([cx - 205 * np.cos(r), cx + 205 * np.cos(r)], [cy - 205 * np.sin(r), cy + 205 * np.sin(r)], color=INK, lw=1.6, linestyle=(0, (6, 5)), zorder=7)
    # C bilateral: elongated body with a head end
    ell(ax, 1000, 320, 330, 190, fc="#d9c7a0", z=3)
    ell(ax, 880, 320, 90, 110, fc="#d9a066", z=4)
    ax.add_patch(Circle((870, 345), 9, fc=INK, zorder=7)); ax.add_patch(Circle((870, 295), 9, fc=INK, zorder=7))
    ax.plot([800, 1200], [320, 320], color=INK, lw=1.6, linestyle=(0, (6, 5)), zorder=7)
    num(ax, 1, 600, 560, 600, 345)
    num(ax, 2, 740, 110, 690, 214)
    num(ax, 3, 790, 470, 870, 380)
    num(ax, 4, 1130, 470, 1160, 322)
    num(ax, 2, 1000, 500, 1000, 320)
    save(fig, "symmetry-types")


def protozoan_groups():
    fig, ax = canvas(12, 7)
    for l, x in zip("ABCD", (60, 360, 660, 960)):
        letter(ax, l, x, 640)
    # A amoeboid: body with blunt lobopodia
    for (lx, ly, w, h, ang) in ((90, 360, 130, 52, 25), (250, 400, 120, 48, -30), (110, 250, 120, 50, -25), (240, 250, 110, 46, 35)):
        ell(ax, lx, ly, w, h, fc="#cfe6f7", ang=ang, z=2)
    ell(ax, 170, 320, 190, 170, fc="#cfe6f7", z=3)
    ell(ax, 170, 320, 184, 164, fc="#cfe6f7", ec="#cfe6f7", lw=0, z=4)
    ax.add_patch(Circle((170, 320), 30, fc="#8e6aa8", ec=INK, lw=2, zorder=5))
    # B flagellate
    ell(ax, 500, 320, 110, 230, fc="#cfe6a8", ang=0)
    ax.add_patch(Circle((500, 290), 26, fc="#8e6aa8", ec=INK, lw=2, zorder=5))
    line(ax, [(500, 435)] + [(500 + 22 * np.sin(5 * u), 435 + 150 * u) for u in np.linspace(0, 1, 40)], lw=4, color="#7d6608", z=2)
    # C ciliate
    ell(ax, 800, 320, 150, 280, fc="#f6e3b6")
    for k in range(24):
        a = 2 * np.pi * k / 24
        x0, y0 = 800 + 75 * np.cos(a), 320 + 140 * np.sin(a)
        line(ax, [(x0, y0), (x0 + 28 * np.cos(a), y0 + 28 * np.sin(a))], lw=2.2, z=2)
    ax.add_patch(Circle((800, 330), 30, fc="#8e6aa8", ec=INK, lw=2, zorder=5))
    # D sporozoan cell, drawn plain
    ell(ax, 1080, 320, 90, 250, fc="#f2b8ad", ang=20)
    ax.add_patch(Circle((1076, 310), 24, fc="#8e6aa8", ec=INK, lw=2, zorder=5))
    num(ax, 1, 70, 500, 100, 385, r=26)
    num(ax, 2, 250, 130, 160, 320)
    num(ax, 3, 500, 590, 500, 540)
    num(ax, 2, 380, 130, 500, 290)
    num(ax, 4, 960, 130, 875, 400)
    save(fig, "protozoan-groups")


def blastopore_fate():
    fig, ax = canvas(12, 8)
    letter(ax, "A", 60, 720); letter(ax, "B", 60, 340)
    EC, EN, RED, BL = "#e6a57e", "#cfe6a8", "#c0392b", "#2471a3"
    def gastrula(cx, cy):
        ax.add_patch(Circle((cx, cy), 120, fc=EC, ec=INK, lw=2.6, zorder=3))
        ax.add_patch(Circle((cx, cy + 10), 78, fc=EN, ec=INK, lw=2.2, zorder=4))
        rect(ax, cx - 26, cy - 125, 52, 70, fc="white", ec="white", lw=0, z=5)
        ax.add_patch(Circle((cx, cy - 118), 14, fc=RED, ec=INK, lw=1.6, zorder=7))
    def tube(cx, cy):
        rect(ax, cx - 55, cy - 120, 110, 240, fc=EN, ec=INK, lw=2.6, r=50)
        rect(ax, cx - 30, cy - 120, 60, 240, fc="#fff7e0", ec=INK, lw=1.8, r=26, z=4)
        ax.add_patch(Circle((cx, cy - 122), 18, fc=RED, ec=INK, lw=1.8, zorder=7))
        ax.add_patch(Circle((cx, cy + 122), 18, fc=BL, ec=INK, lw=1.8, zorder=7))
    for cy in (540, 190):
        gastrula(330, cy)
        arrow(ax, (470, cy), (650, cy), lw=3.4)
        tube(820, cy)
    ax.plot([30, 1170], [370, 370], color=GREY, lw=3)
    num(ax, 1, 130, 440, 316, 422)
    num(ax, 2, 560, 640, 330, 550)
    # A protostome: red end (old blastopore) = mouth, blue end = anus
    num(ax, 3, 1030, 420, 836, 418)
    num(ax, 4, 1030, 680, 836, 662)
    # B deuterostome: red end = anus, blue end = mouth
    num(ax, 4, 1030, 70, 836, 68)
    num(ax, 3, 1030, 330, 836, 312)
    save(fig, "blastopore-fate")


def sponge_cells():
    """Section of a sponge wall: pinacoderm above, mesohyl in the middle, choanocytes lining a chamber below."""
    fig, ax = canvas(12, 8)
    # mesohyl
    rect(ax, 80, 300, 1040, 220, fc="#f1e4c0", lw=2.4)
    # pinacocytes: flat cells on top
    for k in range(6):
        x = 80 + k * 173
        ell(ax, x + 86, 545, 172, 52, fc="#d9c7a0", z=4)
        ax.add_patch(Circle((x + 86, 545), 11, fc="#8e6aa8", ec=INK, lw=1.6, zorder=6))
    # chamber (water) below
    rect(ax, 80, 40, 1040, 120, fc="#dbeaf7", lw=2.4)
    # choanocytes: tall cells with collar and flagellum into the chamber
    for k in range(7):
        x = 150 + k * 150
        ell(ax, x, 262, 80, 78, fc="#f2b8ad", z=4)
        ax.add_patch(Circle((x, 262), 12, fc="#8e6aa8", ec=INK, lw=1.6, zorder=6))
        for j in (-24, -12, 0, 12, 24):
            line(ax, [(x + j, 222), (x + j * 1.2, 160)], lw=2.2, z=5)
        line(ax, [(x, 222)] + [(x + 10 * np.sin(5 * u), 222 - 150 * u) for u in np.linspace(0, 1, 30)], lw=3, color="#7d6608", z=3)
    # archaeocyte in the mesohyl
    t = np.linspace(0, 2 * np.pi, 60)
    pts = [(420 + (60 + 18 * np.sin(4 * u)) * np.cos(u), 410 + (44 + 16 * np.cos(3 * u)) * np.sin(u)) for u in t]
    poly(ax, pts, fc="#cfe6f7", lw=2.4)
    ax.add_patch(Circle((420, 410), 22, fc="#8e6aa8", ec=INK, lw=2, zorder=6))
    num(ax, 1, 180, 700, 250, 545)
    num(ax, 2, 900, 700, 900, 440)
    num(ax, 3, 640, 640, 470, 420)
    num(ax, 4, 1100, 340, 1050, 262)
    num(ax, 5, 1130, 150, 1038, 190)
    num(ax, 6, 760, 90, 905, 132)
    save(fig, "sponge-cells")



# ---------------------------------------------------------------- stage 2 additions

def cuticle_layers():
    fig, ax = canvas()
    # epidermis (cells)
    for k in range(10):
        rect(ax, 90 + k * 100, 150, 100, 110, fc="#f2d3c9", lw=1.8)
        ax.add_patch(Circle((140 + k * 100, 205), 17, fc="#b07a9a", ec=INK, lw=1.4, zorder=5))
    # cuticle: two rigid plates with a thin flexible joint between
    thick_top, thick_bot = 640, 300
    def plate(x0, x1):
        poly(ax, [(x0, 260), (x1, 260), (x1, 510), (x0, 510)], fc="#e3c88e", z=3)
        poly(ax, [(x0, 510), (x1, 510), (x1, thick_top - 40), (x0, thick_top - 40)], fc="#c9a566", z=3)
        poly(ax, [(x0, thick_top - 40), (x1, thick_top - 40), (x1, thick_top), (x0, thick_top)],
             fc="#f6efd8", z=3)
    plate(90, 480)
    plate(720, 1090)
    # flexible joint: thin cuticle only
    poly(ax, [(480, 260), (720, 260), (720, 300), (480, 300)], fc="#e3c88e", z=3)
    poly(ax, [(480, 300), (720, 300), (720, 330), (480, 330)], fc="#f6efd8", z=3)
    line(ax, [(480, 330), (480, thick_top)], lw=2.0)
    line(ax, [(720, 330), (720, thick_top)], lw=2.0)
    num(ax, 1, 300, 800, 300, 620)      # waxy outer layer
    num(ax, 2, 900, 800, 900, 440)      # thick chitin-protein layers
    num(ax, 3, 110, 70, 190, 205)       # epidermis
    num(ax, 4, 600, 800, 600, 345)      # flexible joint
    arrow(ax, (600, 120), (600, 260), lw=2.6)
    save(fig, "cuticle-layers")


def limb_lever():
    fig, ax = canvas()
    # A: jointed limb as a lever
    letter(ax, "A", 70, 840)
    poly(ax, [(130, 380), (440, 380), (440, 500), (130, 500)], fc="#e3c88e", z=3)
    poly(ax, [(440, 380), (500, 380), (500, 500), (440, 500)], fc="#f6efd8", z=3)
    poly(ax, [(500, 380), (700, 560), (610, 640), (440, 500)], fc="#e3c88e", z=3)
    ell(ax, 330, 440, 330, 60, fc="#d98e8e", z=5)                       # muscle in the segment
    line(ax, [(330, 440), (490, 470), (560, 550)], lw=4, color=INK, z=6)  # tendon to next segment
    arrow(ax, (680, 480), (740, 640), rad=-0.5, lw=3.0)
    num(ax, 1, 200, 300, 250, 400)
    num(ax, 2, 470, 300, 470, 380)
    num(ax, 3, 330, 600, 330, 470)
    # B: muscles squeezing a fluid-filled bag
    letter(ax, "B", 750, 840)
    ell(ax, 930, 440, 340, 300, fc="#a9cdee", ec=INK, lw=3, z=3)
    ell(ax, 930, 440, 340, 300, fc="none", ec="#c47a7a", lw=14, z=4)
    for a in range(0, 360, 40):
        t = np.deg2rad(a)
        line(ax, [(930 + 170 * np.cos(t), 440 + 150 * np.sin(t)),
                  (930 + 205 * np.cos(t), 440 + 185 * np.sin(t))], lw=2)
    arrow(ax, (930, 640), (930, 560), lw=3)
    arrow(ax, (930, 240), (930, 320), lw=3)
    arrow(ax, (1150, 440), (1100, 440), lw=3)
    num(ax, 4, 1110, 640, 1050, 560)
    num(ax, 5, 930, 150, 930, 400)
    save(fig, "limb-lever")


def cephalopod_circulation():
    fig, ax = canvas()
    rect(ax, 420, 640, 360, 170, fc="#f2d3c9", r=40)                     # body tissues
    for k in range(5):
        line(ax, [(450 + k * 75, 665), (470 + k * 75, 785)], lw=1.6, color="#9a6f6f")
    for x, side in ((190, -1), (1010, 1)):                               # branchial hearts + gills
        ell(ax, x, 440, 130, 100, fc="#d98e8e")
        for dy in (-1, 0, 1):
            line(ax, [(x - 40, 260 + dy * 30), (x + 40, 260 + dy * 30)], lw=3)
        rect(ax, x - 90, 190, 180, 130, fc="#dbeaf7", r=30)
    ell(ax, 600, 190, 150, 110, fc="#d98e8e")                            # systemic heart
    arrow(ax, (330, 640), (240, 505), rad=0.1)            # veins to branchial hearts
    arrow(ax, (870, 640), (960, 505), rad=0.1)
    arrow(ax, (190, 385), (190, 325))                     # heart to gill
    arrow(ax, (1010, 385), (1010, 325))
    arrow(ax, (290, 250), (520, 215))                     # gill to systemic heart
    arrow(ax, (910, 250), (680, 215))
    arrow(ax, (600, 250), (600, 636), lw=3.4)             # arteries to body
    num(ax, 1, 760, 120, 660, 175)
    num(ax, 2, 190, 580, 190, 485)
    num(ax, 2, 1010, 580, 1010, 485)
    num(ax, 3, 80, 110, 120, 220)
    num(ax, 3, 1120, 110, 1080, 220)
    num(ax, 4, 600, 855, 600, 780)
    num(ax, 5, 250, 700, 285, 573)
    num(ax, 6, 700, 460, 612, 420)
    save(fig, "cephalopod-circulation")


def cockroach_gut():
    fig, ax = canvas(12, 8)
    HV = 5.5          # cuticle-lined (foregut, hindgut): heavy outline
    TH = 2.0          # midgut: no cuticle
    ell(ax, 130, 440, 130, 100, fc=BODY2, lw=HV)                         # mouth / pharynx
    rect(ax, 190, 425, 130, 30, fc=BODY, lw=HV, z=2)                     # oesophagus
    ell(ax, 410, 445, 190, 170, fc=BODY, lw=HV)                          # crop
    ell(ax, 570, 440, 110, 110, fc="#d6b27a", lw=HV)                     # gizzard
    for a_ in (30, 90, 150, 210, 270, 330):
        t = np.deg2rad(a_)
        line(ax, [(570 + 42 * np.cos(t), 440 + 42 * np.sin(t)), (570 + 18 * np.cos(t), 440 + 18 * np.sin(t))], lw=2.4, z=6)
    rect(ax, 620, 405, 250, 70, fc="#efd9a4", lw=TH, z=2)                # midgut
    for x in (650, 690, 730, 770, 810):                                  # hepatic caeca
        line(ax, [(x, 475), (x - 16, 565)], lw=9, color=INK, z=1)
        line(ax, [(x, 475), (x - 16, 565)], lw=5, color="#efd9a4", z=1.5)
    rect(ax, 865, 415, 90, 50, fc=BODY, lw=HV, z=2)                      # ileum
    rect(ax, 950, 400, 90, 80, fc=BODY2, lw=HV, z=2)                     # colon
    ell(ax, 1085, 440, 100, 100, fc=BODY, lw=HV)                         # rectum
    for dx, dy in ((-30, 0), (30, 20), (0, 50)):                         # salivary glands
        ell(ax, 380 + dx, 250 - dy, 90, 60, fc="#cfe3c8")
    line(ax, [(380, 250), (250, 330), (140, 392)], lw=3, z=2)
    num(ax, 1, 110, 610, 120, 485)
    num(ax, 2, 250, 610, 250, 455)
    num(ax, 3, 410, 690, 410, 528)
    num(ax, 4, 570, 610, 570, 492)
    num(ax, 5, 760, 330, 760, 405)
    num(ax, 6, 700, 650, 680, 555)
    num(ax, 7, 905, 560, 905, 462)
    num(ax, 8, 995, 330, 995, 400)
    num(ax, 9, 1100, 600, 1090, 486)
    num(ax, 10, 520, 190, 440, 235)
    num(ax, 11, 215, 190, 220, 345)
    save(fig, "cockroach-gut")


def wuchereria_cycle():
    fig, ax = canvas()
    # person with lymphatic vessel and adult worms; mosquito
    ax.add_patch(Circle((200, 690), 52, fc="#e8c39e", ec=INK, lw=2.4, zorder=3))
    rect(ax, 140, 450, 120, 190, fc="#9ec5e8", r=30)
    t = np.linspace(0, 6 * np.pi, 120)
    ax.plot(190 + 22 * np.sin(t) * 0 + 30 * np.sin(t) / 3 + 0 * t, 470 + t * 7, color="#7a5a8a", lw=3.2, zorder=6)
    ell(ax, 930, 640, 190, 50, fc="#8d7b68")
    ell(ax, 1035, 648, 56, 44, fc="#8d7b68")
    line(ax, [(1060, 640), (1140, 600)], lw=3)
    ell(ax, 905, 700, 150, 42, fc="#dbeaf7", ang=-20, alpha=.9)
    for dx in (-45, 0, 45):
        line(ax, [(930 + dx, 618), (930 + dx - 20, 555)], lw=2.4)
    # microfilaria in blood (wavy) and L3 in proboscis
    x = np.linspace(330, 560, 80)
    ax.plot(x, 520 + 16 * np.sin((x - 330) / 14), color="#7a5a8a", lw=3.2, zorder=6)
    # mosquito gut -> thoracic muscle -> proboscis (inside the mosquito)
    rect(ax, 690, 190, 470, 180, fc="#efe6d8", r=40)
    for k, lab in enumerate((3, 4)):
        pass
    ax.add_patch(Circle((780, 280), 26, fc="#cfe3c8", ec=INK, lw=2.2, zorder=5))
    ax.add_patch(Circle((920, 280), 26, fc="#cfe3c8", ec=INK, lw=2.2, zorder=5))
    ax.add_patch(Circle((1060, 280), 26, fc="#cfe3c8", ec=INK, lw=2.2, zorder=5))
    arrow(ax, (810, 280), (888, 280))
    arrow(ax, (950, 280), (1028, 280))
    # human lymph
    rect(ax, 190, 190, 330, 180, fc="#efe6d8", r=40)
    ax.add_patch(Circle((290, 280), 26, fc="#d4bde6", ec=INK, lw=2.2, zorder=5))
    ax.add_patch(Circle((420, 280), 26, fc="#d4bde6", ec=INK, lw=2.2, zorder=5))
    arrow(ax, (322, 280), (388, 280))
    # main loop arrows
    arrow(ax, (280, 560), (800, 640), rad=-0.18, lw=3.4)         # blood meal: man to mosquito
    arrow(ax, (1000, 540), (1000, 380), lw=3.4)                  # into the mosquito
    arrow(ax, (690, 230), (540, 230), lw=3.4)                    # bite: back to man
    num(ax, 1, 150, 330, 190, 480)
    num(ax, 2, 420, 700, 450, 590)
    num(ax, 3, 900, 90, 900, 190)
    num(ax, 4, 620, 90, 620, 230)
    num(ax, 5, 330, 90, 330, 190)
    save(fig, "wuchereria-cycle")


def fluke_larvae():
    fig, ax = canvas()
    rect(ax, 380, 360, 450, 400, fc="#efe6d8", r=60, lw=3.6)               # snail host
    ell(ax, 150, 560, 150, 80, fc=BODY)                                   # miracidium
    for a in range(0, 360, 36):
        t = np.deg2rad(a)
        line(ax, [(150 + 75 * np.cos(t), 560 + 40 * np.sin(t)), (150 + 92 * np.cos(t), 560 + 54 * np.sin(t))], lw=2)
    ell(ax, 480, 620, 120, 100, fc="#cfe3c8")                              # sporocyst
    ell(ax, 650, 650, 150, 60, fc="#cfe3c8")                              # redia
    ell(ax, 690, 480, 150, 60, fc="#cfe3c8")                              # second redia
    ell(ax, 520, 440, 100, 55, fc=BODY2)                                  # cercaria body
    line(ax, [(470, 440), (410, 420), (380, 440)], lw=4)
    ell(ax, 960, 520, 100, 100, fc=PURPLE)                                # metacercaria cyst
    ell(ax, 960, 520, 60, 60, fc=BODY, z=4)
    ell(ax, 1020, 190, 220, 120, fc="#9ec5e8")                            # final host (schematic)
    arrow(ax, (240, 560), (385, 590))
    arrow(ax, (535, 620), (570, 645))
    arrow(ax, (600, 600), (560, 480), rad=0.2)
    arrow(ax, (580, 440), (905, 500), rad=-0.2)
    arrow(ax, (980, 450), (1010, 250))
    ax.add_patch(FancyArrowPatch((620, 700), (740, 700), connectionstyle="arc3,rad=-0.9", arrowstyle="-|>",
                                 mutation_scale=22, lw=2.6, color=INK, zorder=7))
    num(ax, 1, 150, 700, 150, 600)
    num(ax, 2, 440, 800, 475, 665)
    num(ax, 3, 790, 790, 700, 690)
    num(ax, 4, 560, 300, 530, 415)
    num(ax, 5, 960, 700, 960, 575)
    num(ax, 6, 1060, 80, 1020, 135)
    num(ax, 7, 250, 300, 380, 400)
    save(fig, "fluke-larvae")


def sponge_sexual_cycle():
    fig, ax = canvas()
    cx, cy, rx, ry = 600, 450, 430, 330
    angs = [90, 90 - 51.4, 90 - 102.8, 90 - 154.2, 90 - 205.6, 90 - 257, 90 - 308.4]
    pts = [(cx + rx * np.cos(np.deg2rad(a)), cy + ry * np.sin(np.deg2rad(a))) for a in angs]
    def vase(x, y, s=1.0, gam=False):
        poly(ax, [(x - 50 * s, y - 60 * s), (x + 50 * s, y - 60 * s), (x + 62 * s, y + 40 * s),
                  (x + 36 * s, y + 70 * s), (x - 36 * s, y + 70 * s), (x - 62 * s, y + 40 * s)], fc=BLUE)
        if gam:
            for dx, dy in ((-18, 0), (14, 10), (0, -26)):
                ax.add_patch(Circle((x + dx * s, y + dy * s), 7 * s, fc=PINK, ec=INK, lw=1.4, zorder=6))
    def larva(x, y):
        ell(ax, x, y, 100, 80, fc=BODY)
        for a in range(-80, 100, 20):
            t = np.deg2rad(a)
            line(ax, [(x + 50 * np.cos(t), y + 40 * np.sin(t)), (x + 68 * np.cos(t), y + 54 * np.sin(t))], lw=2)
    vase(*pts[0], 1.0, gam=True)                                         # 1 adult with gametes
    for dx in (-30, 0, 30):                                              # 2 sperm
        ax.add_patch(Circle((pts[1][0] + dx, pts[1][1] + (dx // 3)), 9, fc=INK, zorder=6))
        line(ax, [(pts[1][0] + dx - 6, pts[1][1] + dx // 3 - 4), (pts[1][0] + dx - 34, pts[1][1] + dx // 3 - 20)], lw=2)
    ell(ax, pts[2][0], pts[2][1], 90, 90, fc=PINK)                       # 3 egg + sperm
    ax.add_patch(Circle((pts[2][0] - 56, pts[2][1] + 26), 10, fc=INK, zorder=6))
    for dx, dy in ((-18, 0), (18, 0), (0, 22), (0, -22)):                # 4 zygote cleaving inside parent
        ax.add_patch(Circle((pts[3][0] + dx, pts[3][1] + dy), 22, fc=PINK, ec=INK, lw=2, zorder=5))
    larva(*pts[4])                                                       # 5 larva swimming
    ell(ax, pts[5][0], pts[5][1] - 20, 130, 40, fc=BODY2)                # 6 settled
    line(ax, [(pts[5][0] - 90, pts[5][1] - 42), (pts[5][0] + 90, pts[5][1] - 42)], lw=4)
    vase(pts[6][0], pts[6][1], 0.7)                                      # 7 young sponge
    for i in range(7):
        a, b = pts[i], pts[(i + 1) % 7]
        arrow(ax, (a[0] + (b[0] - a[0]) * .30, a[1] + (b[1] - a[1]) * .30),
              (a[0] + (b[0] - a[0]) * .70, a[1] + (b[1] - a[1]) * .70), rad=-0.15, lw=3.2)
    for i, (x, y) in enumerate(pts):
        num(ax, i + 1, x + (60 if x > cx else -60), y + (60 if y > cy else -60))
    save(fig, "sponge-sexual-cycle")


def food_vacuole():
    fig, ax = canvas()
    ell(ax, 620, 450, 1000, 560, fc="#f6efd8", lw=3.2)                   # elongate cell (schematic)
    poly(ax, [(150, 330), (260, 300), (360, 305), (340, 350), (230, 380)], fc="white", z=4)   # oral groove
    line(ax, [(360, 305), (470, 290)], lw=8, color=INK, z=5)             # cytopharynx
    line(ax, [(360, 305), (470, 290)], lw=4, color="#f6efd8", z=6)
    path = [(500, 290, 36, "#e8e2d0"), (690, 240, 46, "#f3b9ad"), (880, 270, 46, "#a9cdee"),
            (980, 380, 30, "#c9d2d8")]
    for x, y, r, c in path:
        ax.add_patch(Circle((x, y), r, fc=c, ec=INK, lw=2.4, zorder=6))
    for i in range(len(path) - 1):
        a_, b_ = path[i], path[i + 1]
        arrow(ax, (a_[0] + 55, a_[1] - 4), (b_[0] - 55, b_[1] + 4), rad=-0.12, lw=2.8)
    arrow(ax, (1000, 420), (1092, 420), lw=2.8)
    poly(ax, [(1096, 400), (1128, 400), (1128, 440), (1096, 440)], fc="white", ec="white", z=5)
    ax.add_patch(Circle((1160, 420), 12, fc="#c9d2d8", ec=INK, lw=2, zorder=6))
    for x, y in ((640, 400), (800, 420)):
        ax.add_patch(Circle((x, y), 26, fc="#d4bde6", ec=INK, lw=1.8, zorder=5))
    line(ax, [(650, 374), (680, 290)], lw=2.0, ls="--", z=5)
    line(ax, [(790, 394), (850, 312)], lw=2.0, ls="--", z=5)
    num(ax, 1, 200, 160, 230, 335)
    num(ax, 2, 480, 120, 500, 254)
    num(ax, 3, 690, 120, 690, 194)
    num(ax, 4, 890, 120, 880, 224)
    num(ax, 5, 1010, 560, 985, 410)
    num(ax, 6, 1110, 560, 1100, 432)
    num(ax, 7, 720, 620, 650, 420)
    save(fig, "food-vacuole")


# Figures kept after visual review. Others drawn during development (chelicerate, myriapod,
# flower, economic threshold, nephridia, chiton, bivalve, IPM ladder) were rejected as
# anatomically unclear or unsupported by the chapter text and are not shipped.
ALL = {"insect-tagmata": insect_tagmata, "metamorphosis": metamorphosis,
       "parasitoid-cycle": parasitoid, "vector-cycle": vector_cycle, "leech": leech,
       "polychaete-asexual": polychaete_asexual, "crinoid": crinoid,
       "aurelia-cycle": aurelia_cycle, "anemone-section": anemone_section,
       "taenia-cycle": taenia_cycle, "taenia-parts": taenia_parts,
       "sponge-grades": sponge_grades, "cnidarian-forms": cnidarian_forms, "metameres": metameres,
       "host-cycles": host_cycles, "pearl-formation": pearl_formation,
       "body-cavities": body_cavities, "nematode-bodywall": nematode_bodywall,
       "nematode-routes": nematode_routes,
       "mollusc-plan": mollusc_plan, "echino-symmetry": echino_symmetry,
       "cilium-stroke": cilium_stroke, "coral-polyp": coral_polyp,
       "annelid-section": annelid_section, "flame-cell": flame_cell, "myriapod-sections": myriapod_sections,
       "symmetry-types": symmetry_types, "protozoan-groups": protozoan_groups,
       "blastopore-fate": blastopore_fate, "sponge-cells": sponge_cells,
       "cuticle-layers": cuticle_layers,
       "limb-lever": limb_lever, "cephalopod-circulation": cephalopod_circulation,
       "cockroach-gut": cockroach_gut, "wuchereria-cycle": wuchereria_cycle,
       "fluke-larvae": fluke_larvae, "sponge-sexual-cycle": sponge_sexual_cycle,
       "food-vacuole": food_vacuole}

if __name__ == "__main__":
    wanted = sys.argv[1:] or list(ALL)
    for name in wanted:
        ALL[name]()
        print("wrote", name)
