# INVERTEBRATA — Store submission dossier (native Kotlin/Compose)

## Build and identity
- App: INVERTEBRATA; `com.gasczoology.invertebratelab`
- Version currently frozen for QA: `1.9.0`, `versionCode=19000`; increment only when actually submitting an update to an existing listing.
- `compileSdk=36`, `targetSdk=36`, `minSdk=24`; no WebView; `INTERNET` permission absent; `allowBackup=false`.
- Offline English/Tamil privacy policy appears in Home → Privacy policy and developer contact and is also bundled as two UTF-8 APK assets.
- Public privacy policy preview for this QA branch: https://github.com/rameshgascngl-create/Invertebrata-/blob/feat/native-textbook-reader-qa/PRIVACY_POLICY.md
- Before production submission, publish the same policy at a permanent HTTPS webpage and enter it into Play Console / PhonePe Appstore. Do not use the page for another app.

## Developer / contact
- Developer: Rajamoni Ramesh
- Privacy and grievance contact: rameshgascngl@gmail.com (previously published by this developer for Ornamental Fish Science)
- Affiliation: Department of Zoology, Government Arts and Science College, Nagercoil. Institutional publisher/endorsement requires independent confirmation.

## English store listing
**Title:** INVERTEBRATA — Zoology Textbook

**Short description:** Offline bilingual Zoology lessons, anatomical PNGs and interactive learning.

**Long description:**
INVERTEBRATA is a native Android Zoology learning resource for students and teachers. Explore five core invertebrate Zoology units and two supplementary units through chapter-based reading, anatomical illustrations, practical study topics and interactive Paramecium teaching simulations. Switch between English and Tamil, keep track of your reading position, bookmark lessons and practise validated five-mark questions. The app is designed to work offline, without sign-in, ads or an Internet connection. Image credits and source notes accompany applicable teaching figures.

Features include a page-based textbook reader, seven catalogue units (five core units with 43 chapters, plus two supplementary units with 20 chapters), 86 core five-mark practice questions, native illustrations and guided simulator activities. Certain artwork and Tamil terminology remain subject to expert editorial acceptance; do not market these as independently approved.

## Tamil store listing
**Title:** INVERTEBRATA — விலங்கியல் பாடநூல்

**Short description:** இணையமின்றி ஆங்கிலம், தமிழில் விலங்கியல் பாடங்கள், படங்கள், கற்றல் பயிற்சிகள்.

**Long description:**
INVERTEBRATA என்பது மாணவர்களுக்கும் ஆசிரியர்களுக்கும் உருவாக்கப்பட்ட இயல்நிலை Android விலங்கியல் கற்றல் செயலியாகும். முதுகெலும்பற்ற விலங்குகள் பற்றிய ஐந்து அடிப்படை அலகுகளும் இரண்டு கூடுதல் அலகுகளும், அத்தியாயவாரியான பாடவிளக்கங்கள், உடற்கூறுப் படங்கள் மற்றும் பாரமீசியம் தொடர்பான ஊடாடும் கற்றல் செயல்பாடுகள் இதில் இடம்பெற்றுள்ளன. ஆங்கிலம் அல்லது தமிழைத் தேர்ந்தெடுத்துப் படிக்கலாம். வாசித்த இடம், புத்தகக்குறிகள் மற்றும் ஐந்து மதிப்பெண் வினாப் பயிற்சிகளைச் சாதனத்தில் சேமித்துத் தொடர்ந்து பயன்படுத்தலாம்.

இச்செயலி இணைய இணைப்பு, பயனர் கணக்கு மற்றும் விளம்பரங்கள் இல்லாமல் செயல்படும். பொருத்தமான கல்விப் படங்களுக்கான படைப்பாளர் மற்றும் மூலத் தகவல்கள் செயலியினுள் வழங்கப்பட்டுள்ளன.

## Data safety / privacy questionnaire — verify against final packaged manifest and dependencies
- User sign-in: No.
- Ads: No.
- App-requested Internet permission: No.
- Developer-side user-data collection or transmission: No feature or SDK identified in the current project.
- Local-only data: language preference, bookmarks, reading position, learning state.
- Automatic backup: Disabled.
- Text-to-speech: Optional device-engine processing; provider may separately use online processing.
- Account deletion: Not applicable because there is no account.
- Local-data deletion: Android Settings → Apps → INVERTEBRATA → Storage → Clear storage, or uninstall.
Do not submit declarations until the exact release artifact and merged manifest have been inspected.

## Observed release-gate evidence (11 November 2026)
- GitHub commit `18503c49` compiled and the optimised QA APK passed package/signature checks; its signing key is a temporary test key, **not** the store release key.
- GitHub Actions `42933062105`: established UI Paramecium language-switch assertion and Tamil 200% landscape simulator assertion still failed in debug; another debug matrix encountered a Maven Central HTTP 429 dependency-download error. The 200% Tamil landscape **release** instrumentation suite passed.
- Latest figure intake `qa-intake/five-unit-academic-reviews.json` records **zero** independent completed reviewer decisions. Do not convert file/static QA into scientific, Tamil or rights approval.
- A published Play/PhonePe listing must use a legitimate production signing certificate, retain the historical update-signing identity if applicable, and have a public privacy-policy URL and actual release-device screenshots.
- The two supplementary units are already exposed in the app's catalogue. Store descriptions must not imply the catalogue stops at Unit V.

## Submission blockers / acceptance checklist
- [x] Native Kotlin/Compose app (not an HTML wrapper)
- [x] targetSdk 36 and backup disabled
- [x] Bilingual policy bundled and linked from Home in code
- [x] Verified developer privacy email in policy
- [ ] Passing end-to-end release/emulator tests, including Tamil 200% landscape
- [ ] Scientific and Tamil figure review, source/rights acceptance (formal gate currently fails)
- [ ] Physical Android device install and pedagogical acceptance
- [ ] Permanent public HTTPS privacy policy page verified accessible without login
- [ ] Consistent production signing key; signed AAB for Google Play; release-signed APK/AAB for Indus
- [ ] 512×512 PNG listing icon, no watermarks or unintended whitespace; match launcher
- [ ] At least two actual APK screenshots (up to eight for PhonePe); capture release build
- [ ] Current Play Data safety, app access, ads, target audience, content rating and app category declarations
- [ ] Owner confirmation of store publisher identity, support email and age group
- [ ] Increment versionCode if replacing a published package; retain prior signing certificate

**Do not publish the QA/debug/test-key APK as production.** Android signing certificates must be retained for app updates. The current independent academic hold is NOT a missing-file error and must not be bypassed.

Policy guidance: https://support.google.com/googleplay/android-developer/answer/9859455
PhonePe checklist: https://developer.indusappstore.com/docs/how-to-publish-app-on-phonepeappstore
