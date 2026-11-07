#!/usr/bin/env python3
"""Read-only validation of the uploaded bilingual content v3 JSON."""
import json
import sys
from pathlib import Path

def validate(path: Path) -> list[str]:
    data = json.loads(path.read_text(encoding="utf-8"))
    errors = []
    if data.get("language_support") != ["en", "ta"]:
        errors.append("language_support must be ['en', 'ta']")
    phyla = data.get("phyla", [])
    questions = data.get("quiz_questions", [])
    if not isinstance(phyla, list) or not isinstance(questions, list):
        return ["phyla and quiz_questions must be arrays"]
    seen = set()
    for i, p in enumerate(phyla):
        ident = p.get("id")
        if not ident or ident in seen:
            errors.append(f"phyla[{i}]: missing/duplicate id")
        seen.add(ident)
        for key in ("name_en", "name_ta"):
            if not isinstance(p.get(key), str) or not p[key].strip():
                errors.append(f"phyla[{i}]: missing {key}")
    seen.clear()
    for i, q in enumerate(questions):
        ident = q.get("id")
        if not ident or ident in seen:
            errors.append(f"quiz_questions[{i}]: missing/duplicate id")
        seen.add(ident)
        for key in ("question_en", "question_ta", "explanation_en", "explanation_ta"):
            if not isinstance(q.get(key), str) or not q[key].strip():
                errors.append(f"quiz_questions[{i}]: missing {key}")
        en, ta = q.get("options_en"), q.get("options_ta")
        if not isinstance(en, list) or not isinstance(ta, list) or len(en) != 4 or len(ta) != 4:
            errors.append(f"quiz_questions[{i}]: exactly four aligned bilingual options required")
        elif any(not isinstance(v, str) or not v.strip() for v in en + ta):
            errors.append(f"quiz_questions[{i}]: empty option")
        answer = q.get("correct")
        if type(answer) is not int or not 0 <= answer < 4:
            errors.append(f"quiz_questions[{i}]: correct must be zero-based integer 0..3")
    return errors

if __name__ == "__main__":
    path = Path(sys.argv[1]) if len(sys.argv) > 1 else Path("invertebrata_content_final.json")
    try:
        failures = validate(path)
    except (OSError, ValueError, TypeError, KeyError) as exc:
        print(f"FAIL: {exc}", file=sys.stderr)
        sys.exit(2)
    if failures:
        print("\n".join(f"FAIL: {x}" for x in failures), file=sys.stderr)
        sys.exit(1)
    print("PASS: bilingual structure and answer-index integrity; scientific/Tamil accuracy NOT assessed")
