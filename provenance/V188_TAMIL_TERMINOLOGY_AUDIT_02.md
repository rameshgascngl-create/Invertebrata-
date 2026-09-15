# INVERTEBRATA v1.8.8 — Tamil terminology audit 02

Scope: source-audit closure. Primary terminology authority is the accepted bilingual lesson payload and its existing `V183_VISUAL_TA` / `TA_TEXTBOOK_REPLACEMENTS` localisation architecture. No external terminology is imported and no anatomy is changed.

## Localisation architecture verified
The accepted payload already localises SVG `<text>` inside Tamil theory panes through the project terminology map. This provides evidence-backed Tamil or established transliteration for a large part of the figure vocabulary rather than requiring duplicate bilingual text inside every SVG.

### VERIFIED / VERIFIED TRANSLITERATION examples from the accepted project vocabulary
- Paramecium: `Cilia → குறுஇழைகள்`; `Contractile vacuole → சுருங்கும் நுண்குமிழ்`; oral groove/cytostome/nuclear terms are present in the accepted map.
- Sycon: `Osculum → ஆஸ்குலம்`; `Spongocoel → ஸ்பாஞ்சோகோயல்`; `Incurrent canal → உட்பாயும் கால்வாய்`; `Radial canal → ஆரைக் கால்வாய்`; canal-system vocabulary is already established.
- Obelia: `Hydrorhiza → ஹைட்ரோரைக்ஸா`; `Hydrocaulus → ஹைட்ரோகாலஸ்`; `Hydranth/gastrozooid → ஹைட்ராந்த் / உணவுப் பாலிப்`; `Gonangium/gonozooid → கோனாங்கியம் / இனப்பெருக்க பாலிப்`; `Medusa → மெடூசா`; manubrium/ring-canal/gonad terms are mapped.
- Nereis: `Prostomium → புரோஸ்டோமியம்`; `Peristomium → பெரிஸ்டோமியம்`; `Notopodium → நோட்டோபோடியம்`; `Neuropodium → நியூரோபோடியம்`; dorsal/ventral cirrus and `Acicula → அசிகுலா` are established. The accepted lesson also explicitly uses `Metanephridial funnel → மெட்டாநெஃப்ரிடியல் புனல்` and `nephrostome → நெஃப்ரோஸ்டோம்`.
- Penaeus: established project transliterations/terms exist for rostrum, antennule, antenna, maxillipeds, pereiopods, pleopods, uropods, cephalothorax, gills and hepatopancreas.
- Pila: `Ctenidium (gill) → க்டெனிடியம் (செவுள்)`; `Pulmonary sac → நுரையீரற்பை`; `Osphradium → ஆஸ்ப்ரேடியம்`; accepted lesson uses nuchal-lobe transliteration and the same dual-respiration vocabulary.
- Asterias: `Madreporite → மேட்ரிப்போரைட்`; `Ampulla → ஆம்புலா`; `Tube foot → குழற்கால்`; accepted lesson consistently uses transliteration for the specialised WVS terms.
- Ascaris: accepted Tamil practical/lesson material explicitly supplies female reproductive labels (paired ovaries/oviducts/uteri, vagina, vulva) and male labels (single testis, vas deferens, seminal vesicle, ejaculatory duct, cloaca, spicules).

These established terms may be treated as **VERIFIED** or **VERIFIED TRANSLITERATION** when the English label is an exact project-map term or an unambiguous grammatical variant handled by the existing replacement rules.

## Terms that remain terminology-review blockers
The following are not closed merely by finding a biologically plausible translation:

1. **Fasciola — ootype / Mehlis’ gland**: English accepted lesson contains these terms, but a definitive Tamil visual-label convention is not established strongly enough in the accepted terminology map. `TERMINOLOGY REVIEW PENDING`.
2. **Pila — Organ of Bojanus**: the accepted lesson supports the single kidney but does not establish a project-standard Tamil visual label for the eponym. `TERMINOLOGY REVIEW PENDING`; scientifically clear English/transliteration may be used later only after explicit terminology acceptance.
3. **Nereis — Heteronereis**: the accepted lesson teaches epitoky but does not expose a stable project Tamil mapping for the Heteronereis name. `TERMINOLOGY REVIEW PENDING` for that display term; `epitoke/epitoky` may remain established transliteration where already used.
4. **Penaeus — petasma / thelycum**: these are required by the accepted lesson, but a stable Tamil visual-label mapping was not established in the current project dictionary inspection. `TERMINOLOGY REVIEW PENDING`.
5. **Earthworm — detailed nephridial subtype/discharge vocabulary and complete internal reproductive terms**: previously audited as partial/pending. No new translations are invented here.

## Batch-3 consequence
The Batch-3 production factory supplies Tamil captions but the 20 Batch-3 SVG bodies contain no visible `<text>` labels/callouts or visible legend text. Tamil label localisation therefore cannot be declared complete for those instructional plates; this is primarily a figure-completeness/label defect, not a translation problem.

## Corrections made in this operation
**None.** No unequivocal wrong Tamil term requiring text-only replacement was established. Existing accepted terms are retained; unresolved terms remain explicitly pending rather than guessed.

## Gate result
Tamil source terminology is **PARTIALLY VERIFIED / NOT CLOSED**. Most core organism/system vocabulary is already evidence-backed by the accepted lesson/localisation map, but the unresolved specialised terms above remain SOURCE-RESOLVABLE PENDING. This independently prevents `SOURCE AUDIT CONDITIONALLY CLOSED` at this stage.