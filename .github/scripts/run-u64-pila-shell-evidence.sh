#!/usr/bin/env bash
set -euo pipefail
bash .github/scripts/run-u61-unit5-bilingual.sh

for font in 100 200; do
  for lang in english tamil; do
    photo="qa-evidence/u61-unit5/captured-final/r2-${font}-unit5-pila-shell-museum-${lang}.png"
    [[ -s "$photo" ]] || {
      printf 'Missing Pila museum shell screenshot: %s\n' "$photo" >&2
      exit 1
    }
    bytes="$(wc -c < "$photo")"
    [[ "$bytes" -gt 30000 ]] || {
      printf 'Truncated Pila museum shell screenshot: %s (%s bytes)\n' "$photo" "$bytes" >&2
      exit 1
    }
    printf 'VERIFIED_PILA_SHELL_SCREENSHOT %s %s\n' "$photo" "$bytes"
  done
done
echo 'U64_PILA_SHELL_BILINGUAL_100_200_EVIDENCE_PASS'
