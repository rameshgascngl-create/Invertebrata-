#!/usr/bin/env python3
"""Six independent, reproducible graphite-style **editorial candidates**.

Original digitally authored anatomy; NOT photographs/scans of pencil plates and
NOT academic acceptance. Scientific staging: CDC DPDx Fascioliasis / Ascariasis.
Native Kotlin keeps bilingual organ labels and true coordinate-based hotspots
OUTSIDE this art. All images share 960x1280 intrinsic coordinates.
"""
from PIL import Image, ImageDraw, ImageFilter
from pathlib import Path
from math import sin, cos, pi, atan2, sqrt
from random import Random
import hashlib

W,H=960,1280
ROOT=Path("native-app/app/src/main/assets/type_studies")
R=Random(730129)
class Plate:
    def __init__(self):
        self.im=Image.new("RGB",(W,H),(251,250,246))
        self.d=ImageDraw.Draw(self.im)
        # Graphite paper grain; reproducible and gentle, not AI image hallucinations.
        for _ in range(12000):
            x,y=R.randrange(W),R.randrange(H)
            v=R.randrange(227,250)
            self.d.point((x,y),fill=(v,v,v))
        self.d.rectangle((24,24,W-25,H-25),outline=(211,209,202),width=2)
    def p(self,x,y):return (int(x*W),int(y*H))
    def points(self,coords):return [self.p(x,y) for x,y in coords]
    def line(self,coords,width=3,shade=64):
        self.d.line(self.points(coords),fill=(shade,shade,shade),width=width,joint="curve")
    def curve(self,controls,width=3,shade=65):
        # Catmull-Rom curve; anatomical geometry drawn explicitly at source.
        if len(controls)<3:return self.line(controls,width,shade)
        samples=[]
        seq=[controls[0]]+controls+[controls[-1]]
        for i in range(1,len(seq)-2):
            a,b,c,e=seq[i-1:i+3]
            for j in range(22):
                t=j/22
                def interp(k):
                    p0,p1,p2,p3=a[k],b[k],c[k],e[k]
                    return .5*((2*p1)+(-p0+p2)*t+(2*p0-5*p1+4*p2-p3)*t*t+
                                (-p0+3*p1-3*p2+p3)*t*t*t)
                samples.append((interp(0),interp(1)))
        samples.append(controls[-1])
        self.line(samples,width,shade)
    def oval(self,x,y,rx,ry,width=3,shade=65,fill=None):
        bbox=(self.p(x-rx,y-ry),self.p(x+rx,y+ry))
        self.d.ellipse(bbox,outline=(shade,)*3,width=width,
                       fill=None if fill is None else (fill,)*3)
    def dot(self,x,y,r=.003,shade=76):
        self.oval(x,y,r,r,1,shade,shade)
    def spines(self,center,rx,ry,count):
        for i in range(count):
            a=2*pi*i/count
            x,y=center
            xa=x+rx*cos(a); ya=y+ry*sin(a)
            xb=x+(rx+.012)*cos(a); yb=y+(ry+.008)*sin(a)
            self.line([(xa,ya),(xb,yb)],1,138)
    def arrow(self,a,b,shade=70):
        self.line([a,b],4,shade)
        ang=atan2(b[1]-a[1],b[0]-a[0])
        q=lambda k: (b[0]-.018*cos(ang+k),b[1]-.013*sin(ang+k))
        self.line([q(.55),b,q(-.55)],4,shade)
    def save(self,organism,plate):
        dest=ROOT/organism/(plate+".png")
        dest.parent.mkdir(parents=True,exist_ok=True)
        self.im.save(dest,optimize=True)
        print(f"UNIT3_PLATE={dest} DIMENSIONS={W}x{H} SHA256={hashlib.sha256(dest.read_bytes()).hexdigest()}")
        return dest

def fluke_base(p,veins=True):
    # Adult Fasciola cone, pronounced shoulders and broad tapering leaf.
    left=[(.50,.066),(.467,.086),(.458,.116),(.442,.153),(.352,.174),
          (.281,.230),(.246,.319),(.252,.461),(.302,.635),(.372,.803),(.50,.949)]
    right=[(1-x,y) for x,y in left[-2::-1]]
    outline=left+right
    p.curve(outline,5,50)
    p.curve(outline,1,141)
    if veins:
        for side in (-1,1):
            for j in range(13):
                y=.28+j*.042
                x=.5+side*.052
                ex=.5+side*(.14+.075*sin(j*.3))
                p.curve([(x,y),(x+side*.095,y-.013),(ex,y+.024)],1,179)
    for i in range(300):
        y=R.uniform(.22,.87); x=R.uniform(.31,.69)
        # avoid spurious anatomy details; very light stipple/tegument grain
        p.dot(x,y,.0008,214)
    p.oval(.5,.105,.022,.025,4,61,239)   # oral sucker
    p.oval(.5,.282,.049,.041,4,60,239)   # ventral sucker
    p.oval(.5,.105,.007,.007,2,59)
    p.oval(.5,.234,.006,.007,2,68)       # midventral genital pore
    for j in range(15):
        a=2*pi*j/15
        p.line([(.5+.028*cos(a),.282+.032*sin(a)),
                (.5+.039*cos(a),.282+.035*sin(a))],1,126)

def intestine(p):
    p.curve([(.5,.125),(.5,.153),(.5,.180),(.46,.2)],5,78)
    p.curve([(.5,.180),(.53,.2),(.56,.25)],5,78)
    for side in (-1,1):
        x=.5+side*.11
        p.curve([(.5,.184),(.5+side*.063,.21),(x,.28),
                 (x+side*.011,.44),(x+side*.02,.69),(x+side*.01,.83)],6,99)
        for j in range(11):
            y=.29+j*.045
            p.curve([(x+side*.004,y),(x+side*.040,y+.008),
                    (x+side*.065,y+.018)],2,115)

def branched_gonad(p,x,y,scale=.075):
    p.curve([(x-.006,y-.077),(x+.014,y-.028),(x,y+.077)],4,75)
    for side in (-1,1):
        for j in range(5):
            yy=y-.051+j*.027
            bend=side*(scale*.53 + .013*(j%2))
            end=side*(scale+.006*(j%3))
            p.curve([(x,yy),(x+bend,yy-.018),(x+end,yy-.03)],3,98)
            p.curve([(x+bend*.9,yy-.015),(x+end*.78,yy+.005),
                    (x+end*.98,yy+.013)],2,116)

def external_fluke():
    p=Plate();fluke_base(p,True)
    # External plate intentionally does NOT invent an adult eyespot or anus.
    for _ in range(500):
        y=R.uniform(.24,.88); x=R.uniform(.29,.71)
        if abs(x-.5)<.03:continue
        p.dot(x,y,.0013,191)
    return p.save("fasciola","external")

def internal_fluke():
    p=Plate();fluke_base(p,False);intestine(p)
    # Lateral vitelline follicles. They are NOT intestinal caeca.
    for s in [-1,1]:
        for j in range(35):
            y=.32+j*.0138
            x=.5+s*(.195+.013*sin(j*1.7))
            p.oval(x,y,.010,.006,1,133,218)
    # Coiled anterior uterus; transverse serpentine loops are outside the gut.
    u=[(.5+.09*sin(i*.47),.329+i*.0041) for i in range(49)]
    p.curve(u,5,82)
    # One branching ovary, *anterior* to anterior testis.
    branched_gonad(p,.425,.55,.055)
    # TWO successive, deeply branched testes, not laterally paired.
    branched_gonad(p,.51,.686,.104)
    branched_gonad(p,.505,.819,.093)
    return p.save("fasciola","digestive-reproductive")

def egg(p,x,y,ry=.055,embryo=False):
    p.oval(x,y,ry*.64,ry,4,78,239)
    p.line([(x-ry*.35,y-ry*.96),(x+ry*.35,y-ry*.96)],3,80)
    if embryo:p.curve([(x-.014,y+.006),(x-.012,y-.015),(x+.014,y-.014),
                      (x+.019,y+.014),(x-.002,y+.020)],3,103)
    else:
        for j in range(11):
            a=j*2*pi/11
            p.dot(x+.024*cos(a),y+.027*sin(a),.003,124)

def snail(p,x,y):
    p.oval(x,y,.057,.046,5,67,232)
    p.oval(x+.004,y,.025,.027,3,107)
    p.curve([(x+.008,y+.002),(x+.019,y-.007),(x+.027,y+.004),
             (x+.012,y+.017)],3,98)
    p.curve([(x-.056,y+.035),(x-.07,y+.064),(x+.085,y+.067),
             (x+.106,y+.055)],5,77)
    for dx in [.070,.091]:
        p.line([(x+dx,y+.052),(x+dx+.015,y+.009)],2,87)

def cercaria(p,x,y):
    p.oval(x,y-.018,.019,.03,3,83,229)
    p.curve([(x,y+.012),(x-.018,y+.059),(x+.019,y+.072),(x-.022,y+.117)],3,71)
    p.curve([(x-.013,y-.022),(x,y-.033),(x+.015,y-.016)],2,110)

def mirac(p,x,y):
    p.oval(x,y,.023,.043,4,78,233)
    for k in range(14):
        a=2*pi*k/14
        p.line([(x+.024*cos(a),y+.045*sin(a)),
                (x+.035*cos(a),y+.063*sin(a))],1,136)

def vegetation(p,x,y):
    p.line([(x-.085,y+.048),(x+.086,y+.048)],2,116)
    for dx,dy in [(-.05,-.075),(-.012,-.086),(.041,-.077),(.078,-.055)]:
        p.curve([(x+dx*.5,y+.05),(x+dx*.73,y-.015),(x+dx,y+dy)],3,102)
    p.oval(x+.03,y-.02,.018,.019,3,73,238)

def ruminant(p,x,y):
    # Small grazing mammal icon; schematic, not identification of a species.
    p.oval(x,y,.067,.045,4,78,237)
    for dx in [-.048,-.011,.041,.062]:
        p.line([(x+dx,y+.031),(x+dx-.005,y+.09)],4,80)
    p.oval(x+.090,y-.024,.025,.026,4,77,241)
    p.line([(x+.094,y-.042),(x+.085,y-.081)],3,75)
    p.line([(x+.104,y-.043),(x+.123,y-.076)],3,75)
    p.line([(x-.065,y-.01),(x-.084,y-.041)],3,87)

def cycle_frame(p,pts):
    # clockwise stage arrows, all stage captions stay in native Compose.
    for i in range(len(pts)):
        a,b=pts[i],pts[(i+1)%len(pts)]
        center=(.5,.5)
        da=(a[0]-center[0],a[1]-center[1])
        db=(b[0]-center[0],b[1]-center[1])
        p.arrow((a[0]-da[0]*.23,a[1]-da[1]*.19),
                (b[0]-db[0]*.23,b[1]-db[1]*.19))

def fasciola_cycle():
    p=Plate()
    # Clockwise: egg -> miracidium -> snail -> cercaria -> metacercaria -> host.
    pts=[(.5,.155),(.79,.32),(.78,.69),(.50,.86),(.20,.69),(.20,.32)]
    for x,y in pts:p.oval(x,y,.100,.099,2,214)
    egg(p,*pts[0],embryo=False);mirac(p,*pts[1]);snail(p,*pts[2])
    cercaria(p,*pts[3]);vegetation(p,*pts[4]);ruminant(p,*pts[5])
    cycle_frame(p,pts)
    return p.save("fasciola","life-cycle")

def worm(p,path,thick=.038):
    # Tube has smooth longitudinal walls and a distinct lumen; no random viscera.
    for a,b in zip(path[:-1],path[1:]):
        ang=atan2(b[1]-a[1],b[0]-a[0]); dx=thick*cos(ang);dy=thick*sin(ang)
        for side in [-1,1]:
            p.line([(a[0]+side*dx,a[1]-side*dy),
                    (b[0]+side*dx,b[1]-side*dy)],5,67)
    p.curve(path,2,155)

def ascaris_external():
    p=Plate()
    # Female longer with straight subterminal tail; male smaller, curved tail.
    f=[(.30,.12),(.305,.24),(.312,.40),(.31,.57),(.315,.77),(.32,.92)]
    m=[(.67,.18),(.664,.32),(.66,.48),(.663,.63),(.67,.77),
       (.69,.84),(.744,.855),(.771,.824),(.744,.792)]
    worm(p,f,.027);worm(p,m,.022)
    # Terminal mouth ring and THREE distinct lips in inset style.
    for x,y in [(f[0][0],f[0][1]),(m[0][0],m[0][1])]:
        p.oval(x,y,.027,.012,3,74,238)
        for a in [0,2*pi/3,4*pi/3]:
            p.dot(x+.012*cos(a),y+.008*sin(a),.005,70)
    p.dot(.310,.386,.007,61) # female vulva at anterior/middle third
    p.dot(.32,.905,.006,69) # female subterminal anus
    p.dot(.744,.817,.007,62) # male subterminal cloaca
    p.line([(.746,.818),(.772,.803)],2,86) # copulatory spicule
    return p.save("ascaris","male-female-external")

def ascaris_female():
    p=Plate()
    path=[(.48,.09),(.48,.23),(.48,.41),(.48,.65),(.48,.91)]
    worm(p,path,.115)
    p.curve([(.49,.13),(.49,.25),(.49,.44),(.49,.68),(.49,.884)],10,93) # intestine
    # Coiled, paired, tubular ovaries/oviducts and uteri. NOT grape-like gonads.
    for side in [-1,1]:
        x=.48+side*.064
        coil=[(x+.024*sin(i*.54)*side,.30+i*.0102) for i in range(39)]
        p.curve(coil,3,78) # narrow ovary -> oviduct
        p.curve([(x,.69),(x+side*.01,.76),(x,.82)],10,130) # wide uterus
        for i in range(14):
            y=.735+i*.0055
            p.dot(x+side*.003,y,.0018,90)
        p.curve([(x,.82),(.48+side*.017,.37),(.48,.365)],3,94)
    p.dot(.48,.367,.009,62) # vulva, anterior 1/3
    p.dot(.48,.895,.006,75) # subterminal anus
    return p.save("ascaris","female-internal")

def lungs(p,x,y):
    for side in [-1,1]:
        x0=x+side*.035
        p.curve([(x0,y-.045),(x0+side*.027,y-.049),
                 (x0+side*.049,y+.029),(x0,y+.058),(x,y+.019)],3,81)
        for i in range(4):
            p.line([(x0,y-.01+i*.014),(x0+side*.027,y+i*.012)],2,150)
    p.line([(x,y-.09),(x,y+.018)],5,85)
    p.line([(x,y+.017),(x-.033,y+.037)],4,88)
    p.line([(x,y+.017),(x+.033,y+.037)],4,88)

def larvae(p,x,y):
    p.curve([(x-.045,y-.04),(x-.018,y-.008),(x+.012,y-.04),
             (x+.045,y-.018),(x+.012,y+.04),(x-.033,y+.017)],6,82)

def adult_pair(p,x,y):
    p.curve([(x-.027,y-.069),(x-.040,y-.013),(x-.030,y+.066)],6,69)
    p.curve([(x+.030,y-.065),(x+.035,y-.006),(x+.051,y+.045),
             (x+.074,y+.063),(x+.09,y+.04)],5,69)

def ascaris_cycle():
    p=Plate()
    # 6 stages: unembryonated eggs -> soil embryonation -> ingestion ->
    # intestinal hatch -> pulmonary transit -> adult intestinal worms.
    pts=[(.50,.15),(.79,.30),(.79,.68),(.50,.86),(.20,.68),(.20,.30)]
    for x,y in pts:p.oval(x,y,.102,.100,2,215)
    egg(p,*pts[0],embryo=False);egg(p,*pts[1],embryo=True)
    # Oral ingestion is iconified by a small head and egg at the mouth.
    p.oval(.79,.68,.049,.060,3,97);p.line([(.762,.700),(.820,.700)],3,100)
    egg(p,.81,.677,.014,True)
    larvae(p,*pts[3]);lungs(p,*pts[4]);adult_pair(p,*pts[5])
    cycle_frame(p,pts)
    return p.save("ascaris","life-cycle")

def main():
    from collections import Counter
    sources=[external_fluke(),internal_fluke(),fasciola_cycle(),
             ascaris_external(),ascaris_female(),ascaris_cycle()]
    assert len(sources)==6 and len(set(sources))==6
    for src in sources:
        with Image.open(src) as img:
            assert img.size==(960,1280) and img.format=="PNG" and img.getbbox()
    print("SIX_UNIQUE_TYPE_STUDY_RASTERS=PASS; SCIENTIFIC_REVIEW=PENDING")
if __name__=="__main__":main()
