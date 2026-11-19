# INVERTEBRATA v1.9.1 — PhonePe Appstore submission packet

**Prepared:** 19 November 2026  
**Purpose:** Manual publisher submission; this packet is not evidence of acceptance by the app store.

## Release identity

| Field | Verified value |
| --- | --- |
| App title | INVERTEBRATA |
| Package | `com.gasczoology.invertebratelab` |
| Version name | `1.9.1` |
| Version code | `19001` |
| Platform | Pure native Android, Kotlin / Jetpack Compose |
| Minimum Android version | API 24 |
| Target API | 36 |
| Main languages | English and Tamil |
| Category | Education (publisher to confirm in dashboard) |
| Creator | R. Ramesh |
| Institutional credit | Department of Zoology, Government Arts and Science College, Nagercoil |
| Signed build source commit | `7b27c8c372b4e4afae9aea6763e466f2f7e14c4c` |
| Permanent upload certificate SHA-256 | `F8069391B6C329BFD01DBC9761EC6BA46627FDE6481DC97A8E103A2CEA26C9E2` |

**Signed artifact:** https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/44517314159/artifacts/14525629947

**Public privacy-policy URL:** https://github.com/rameshgascngl-create/Invertebrata-/blob/audit/undergraduate-coverage-register-20261114/docs/PRIVACY_POLICY.md

**Publisher support email:** **TO BE PROVIDED by the developer**. Do not invent an email address or use the GitHub issue tracker in the store's required email field.

## Store listing — English

**Title:** INVERTEBRATA

**Short description:** Offline English–Tamil Invertebrate Zoology textbook and practice.

**Full description:**

INVERTEBRATA is a bilingual digital textbook for undergraduate Invertebrate Zoology, developed for structured self-study and classroom learning.

The native Android application presents five course units and 43 book-style chapters, with clear learning objectives, scientific terminology, labelled educational illustrations and chapter-by-chapter reading. English and Tamil learning material helps students study in their preferred language.

Features include:
- Page-wise textbook reading with chapter navigation.
- Scientific figures and selected interactive learning experiences.
- Bookmarks, reading progress and chapter search.
- Practice questions and self-assessment activities.
- Offline access to the core textbook content.
- An in-app privacy policy in English and Tamil.

Created by R. Ramesh, Department of Zoology, Government Arts and Science College, Nagercoil.

This is an educational reference and study application, not a medical or clinical decision tool.

## Store listing — தமிழ்

**செயலியின் பெயர்:** INVERTEBRATA

**சுருக்க விளக்கம்:** இணையமின்றிப் பயில உதவும் ஆங்கிலம்–தமிழ் முதுகெலும்பற்ற விலங்கியல் பாடநூல்.

**முழு விளக்கம்:**

INVERTEBRATA என்பது இளநிலை விலங்கியல் மாணவர்களுக்கான இருமொழி மின்னணுப் பாடநூல் செயலியாகும்.

இச்செயலியில் முதுகெலும்பற்ற விலங்கியல் பாடத்திட்டத்தின் ஐந்து அலகுகளும், பாடநூல் வடிவில் அமைக்கப்பட்ட 43 அத்தியாயங்களும் உள்ளன. ஒவ்வொரு அத்தியாயத்திலும் கற்றல் நோக்கங்கள், அறிவியல் விளக்கங்கள், தொடர்புடைய படங்கள் மற்றும் பயிற்சி வினாக்கள் இடம்பெறுகின்றன.

முக்கிய வசதிகள்:
- அத்தியாயங்களைப் பக்கம் பக்கமாகப் படிக்கும் அமைப்பு.
- ஆங்கிலம் மற்றும் தமிழில் பாட உள்ளடக்கம்.
- அறிவியல் படங்கள் மற்றும் தேர்ந்தெடுக்கப்பட்ட ஊடாடும் கற்றல் பகுதிகள்.
- புத்தகக்குறிகள், வாசிப்பு முன்னேற்றப் பதிவு மற்றும் தேடல் வசதி.
- சுயமதிப்பீடு மற்றும் பயிற்சி வினாக்கள்.
- முக்கியமான பாடங்களை இணைய இணைப்பின்றிப் படிக்கும் வசதி.
- செயலிக்குள் இருமொழித் தனியுரிமைக் கொள்கை.

உருவாக்கியவர்: இரா. ரமேஷ், விலங்கியல் துறை, அரசு கலை மற்றும் அறிவியல் கல்லூரி, நாகர்கோவில்.

## Release notes

**Version 1.9.1:** Native English–Tamil undergraduate Zoology textbook release candidate. Retains the validated five-unit lessons, diagrams, reading and practice features. Production build packaging fixed and both APK/AAB signed with the established upload identity. No claim is made that the app-store review or device acceptance is complete.

## Store files and images

- Permanent-key signed APK: `INVERTEBRATA-v1.9.1-signed.apk`
- Permanent-key signed AAB: `INVERTEBRATA-v1.9.1-signed.aab`
- Checksum evidence: `SHA256SUMS` in the release artifact
- Eight unmodified 1080×2400 phone emulator screenshots (proposed listing selection, not synthetic images): GitHub Actions run `44507519253`, API 35 font 100% full evidence artifact `14524532855`
- App icon: comes from the Android launcher resources; export the store icon to the console's exact size/format limits if required.

PhonePe Appstore accepts signed APK and AAB packages and requires a privacy policy, support details, description, icon and screenshots. The publisher must use the current dashboard instructions.

## Outstanding manual or independent gates — do not mark PASS without evidence

- [ ] Current-commit emulator matrix: API 34 and 35, normal and 200% font; investigate API 34 / 200% full-suite failures rather than dismissing them.
- [ ] Human confirmation that the final signed APK installs, launches and retains reading progress on a physical device; verify upgrade behaviour from the existing published version and signing identity.
- [ ] Confirm the 52 integrated illustrations' rights/provenance records; owner visual acceptance is not legal permission.
- [ ] Verify the submitted privacy-policy URL opens publicly without login and matches the app's actual data practices.
- [ ] Supply the publisher-controlled support email and account verification/KYC, and select truthful target audience and declarations.
- [ ] Review actual screenshots and ensure they represent the exact functionality in the submitted APK.
- [ ] Upload signed package, listing, and privacy policy to PhonePe Appstore Developer Dashboard and record its acceptance or rejection.

**Do not expose the keystore or passwords.** Permanent signing material stays in protected GitHub Actions secrets.

Official developer documentation: https://developer.phonepeappstore.com/docs/how-to-publish-app-on-phonepeappstore
