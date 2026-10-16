# INVERTEBRATA Unit III — screenshot QA, provenance and bilingual anatomical acceptance
**Audit date:** 2026-10-16. **Release status: HOLD.**

## 1. Independently inspected U3.6 CI evidence

- Exact previously successful HEAD: `b8390efe28d9935889a9fbb8bd72556d72466db9`; run `38900194186`; status.txt exactly `result=PASS; exit=0`.
- Examined the **actual** original screenshot PNGs, 1080×2400px:
  - `unit3-100-fasciola-pencil-intake-provisional.png`
  - `unit3-200-fasciola-pencil-intake-provisional.png`
  - `unit3-100-ascaris-pencil-intake-pending.png`
  - `unit3-200-ascaris-pencil-intake-pending.png`
- Parsed Android XML from downloaded 96MiB workflow ZIP: font 100%: Unit III **8/8**, Unit II **4/4**; font 200%: Unit III **8/8**, Unit II **4/4**. **0 failures, 0 errors, 0 skipped**, exact original screenshot files present.
- Functional assertions PASS (native offline source-intake, original APK asset byte preservation, rejected malformed candidate, Tamil empty state, no student hotspot).
- **VISUAL FAIL AT 200% FONT:** Source-intake explanatory red text consumes excessive viewport. The Fasciola source image becomes a tiny scaled preview; Ascaris's empty-state screen is dominated by oversized explanatory prose. No accepted atlas should be signed off on that layout.
- **U3.6a corrective HEAD:** use a short, non-ellipsized academic HOLD notice occupying at most two lines; expose its COMPLETE original limitations in a native, scrollable TalkBack-readable alert dialog. Preserve the image-first layout, all checks and student locks.
- Require new exact-commit Android screenshot captures at 100/200%:
  `fasciola-pencil-intake-provisional`, `ascaris-pencil-intake-pending`,
  `fasciola-pencil-intake-full-disclosure`, `ascaris-pencil-intake-full-disclosure`.
  **Do not declare the visual defect closed without inspecting these new actual PNGs.**

## 2. Archived illustration source triage — NOT original graphite scans

These are independently catalogued **historical PRINTED scientific figures**, not a certified artist's original pencil drawing, even when an image visually resembles graphite. They are preserved as documentary references/rights research only, **never installed into the six approved atlas master slots**.

| Reference | Archive bibliographic origin | Original file metadata from Commons | Commons' rights claim | Verdict |
|---|---|---|---|---|
| Fasciola historic print | *Encyclopaedia Britannica*, 9th edition, vol. 23, 1888, Fig. 1, *Trematoda* | 1286×1708 PNG; SHA-1 `b315b17ad886044e69b82f37685f7f5020faeb67` | Public domain (published 1888) | **Printed engraving/line drawing, NOT authenticated graphite pencil drawing.** Subfigures A–E differ; cannot assume all are an adult whole-organ morphology plate. |
| Ascaris historic print 1 | Delorieux for Johann Gottfried Bremser, *XIX tabulae ...*, 1831 | 1500×632 JPEG; SHA-1 `e16d00aa7b0c06b95bac57411d03683ede055733` | Public-domain scanned printed work | **Print scan, NOT pencil drawing.** Specific sex/structures require manual checking. |
| Ascaris historic print 2 | Delorieux for Johann Gottfried Bremser, 1831 | 1500×534 JPEG; SHA-1 `c11b930ee1a9bbb36e6840c577f2ae3667014225` | Public-domain scanned printed work | **Print scan, NOT pencil drawing.** Do not infer exact adult sex or internal reproductive anatomy. |

Canonical source pages:
- https://commons.wikimedia.org/wiki/File:EB_9th_Volume23_Trematota_Fig_1.png
- https://commons.wikimedia.org/wiki/File:Ascaris_lumbricoides1.jpg
- https://commons.wikimedia.org/wiki/File:Ascaris_lumbricoides2.jpg

The archive's reported SHA-1 values are **catalogue metadata**, not local checksums of downloaded original bytes. Original-file download was not reliably available from this runtime, so **do not say the original scan bytes have been acquired and checked**. Retain original attribution and public-domain statement; verify local legal/reuse status before any alternative product use. User library has earlier generated **pencil-style illustrative infographics**; visual style or an AI rendering does **not** authenticate that a physical hand-drawn graphite original was scanned.

## 3. Six-slot morphology and image-authentication gate

One *physically drawn or individually verified artist-original* pencil plate is required for each of these **separate** source identities before replacement of any student raster:

| Locked Unit III plate | English biological review — required | Tamil editorial targets | Gate |
|---|---|---|---|
| Fasciola external | Distinct leaf-shaped adult, anterior cephalic cone and shoulders, **oral and ventral suckers in correct relative positions**, posterior tip, spinose tegument; **do not label adult eyespots**. | முன்கூம்பு, வாயுறிஞ்சி, வயிற்றுறிஞ்சி, தோள்பகுதி, முட்களுள்ள புற உறை | NO ORIGINAL PENCIL SCAN |
| Fasciola digestive-reproductive | Paired branched intestinal caeca distinct from reproductive ducts; **one ovary**, branched tandem testes, uterus and lateral vitelline fields. Reject fused anatomical paths. | கிளைத்த குடற்பைகள், சூலகம், கிளைத்த விந்தகங்கள், கருப்பை, மஞ்சள் கருச் சுரப்பிகள் | NO ORIGINAL PENCIL SCAN |
| Fasciola life-cycle | Eggs passed unembryonated; freshwater miracidium, appropriate lymnaeid snail with sporocyst–redia–cercaria, metacercaria on aquatic vegetation, adult in biliary duct. No human-to-human shortcut. | முட்டை, மிராசிடியம், நத்தை, ஸ்போரோசிஸ்ட், ரீடியா, செர்கேரியா, மெட்டாசெர்கேரியா, பித்தநாளம் | NO ORIGINAL PENCIL SCAN |
| Ascaris male-female external | Larger straight-tailed female and smaller ventrally curved male; anterior **three lips**, female vulva, anus versus male cloaca correctly differentiated. | மூன்று உதடுகள், பெண் இனப்பெருக்கத் துளை/வல்வா, மலத்துளை, ஆண் பின்முனைக் குளோவாகா | NO ORIGINAL PENCIL SCAN |
| Ascaris female-internal | Straight intestine distinguished from paired coiled ovaries, oviducts, paired uteri, vagina and vulva; no artificial fused duct network. | குடல், சூலகங்கள், சூலகக் குழாய்கள், கருப்பைகள், யோனி, வல்வா | NO ORIGINAL PENCIL SCAN |
| Ascaris life-cycle | Embryonated egg becomes infectious in environment; ingestion, larval migration through circulation to lungs, ascent and re-swallowing, adult in small intestine; **no snail**. | கருவளர்ச்சியடைந்த முட்டை, உட்கொள்ளுதல், நுரையீரல் இடம்பெயர்ச்சி, மீண்டும் விழுங்குதல், சிறுகுடல் | NO ORIGINAL PENCIL SCAN |

Authoritative scientific comparators:
- CDC DPDx Fascioliasis: https://www.cdc.gov/dpdx/fascioliasis/
- CDC DPDx Ascariasis: https://www.cdc.gov/dpdx/ascariasis/

## 4. Human review and student promotion requirements

**Image source chain:** the creator's identity, original pencil drawing, physical or unaltered photograph/scan, capture date, rights/consent/licence, unretouched source PNG/JPEG, raw source SHA-256, dimensions, species, specimen/sex/life stage and specific plate slot. For composites, label each subfigure and original source. Do not substitute a printed line engraving, unlabeled photo, AI drawing or guessed rendering and call it a 'pencil scan'.

**Independent morphology review:** Two genuinely separate qualified zoologists must review every organ, endpoint, anatomical labels, life stage and species against both the scan and independent scientific references. They must individually identify reviewer name, date, results (pass/correct/reject), reasoning, signed approval and rights assessment; agreement in pixel coordinates is insufficient.

**Tamil editorial:** An independent Tamil science-language reviewer must inspect terminology, grammar, font fallback at system 200%, line wrapping and meaning. For each organ-specific audio script, check installed Tamil voice audio with a real phone; an enabled TTS button is not proof of audible Tamil.

**Pixel invalidation:** Any accepted source-image replacement changes the SHA-256; **all old candidate anatomical coordinates and leader-line endpoints become invalid**. Recalibrate and re-sign for the new original pixels only.

**Physical Android tests:** latest QA APK in airplane mode, import original image through Android Files, rotate 90°, background/process kill, reload same SHA, export SAF source manifest and see it locally, compare on phone/tablet, 200% Tamil, TalkBack, permissions, compression/EXIF failure and back gesture. Confirm debug-review tools are *absent* in production signed APK.

### Current decision
- Android engineering validation: **U3.6 base PASS; U3.6a screenshot correction pending its own exact CI and visual inspection.**
- **Authentic hand-pencil masters: 0/6 independently supplied and authenticated.**
- Independent zoological reviewer signatures: **0/6 complete**, Tamil editorial signoff **0/6**, original scan copyright permissions **0/6**.
- All six current student atlas illustrations remain unchanged. No anatomical hotspot released. No Google Play/Indus production release authorised.

**Missing user input to finish art approval:** please supply each original hand-pencil scan/photo and source-author/rights evidence, or arrange physical drawings from a suitably qualified human illustrator for authenticated scanning. This requirement cannot be fulfilled by generating a convincing pencil-style rendering and describing it as a physical scan.
