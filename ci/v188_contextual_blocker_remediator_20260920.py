#!/usr/bin/env python3
from __future__ import annotations

import argparse
import hashlib
import json
from pathlib import Path

EXPECTED_INPUT_SHA256 = "9d4a0e5b6bd23e8fd6cd7e148100bb6017374d2cd9dea45098007eff0c899f65"
EXPECTED_INPUT_SIZE = 2389584
EXPECTED_CONTEXTUAL_ATLAS_SHA256 = "005c2e130b35454fd56b7b5b3ce6295a51adc33dea7b258c312f6f312b4597d2"
SCRIPT_ID = "v188-contextual-blocker-remap-20260920"

def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()

def sha256_file(path: Path) -> str:
    return sha256_bytes(path.read_bytes())

def require(cond: bool, msg: str) -> None:
    if not cond:
        raise SystemExit(msg)

def contextual_atlas(text: str) -> str:
    marker = '<script id="v188-contextual-atlas">'
    start = text.index(marker)
    end = text.index("</script>", start) + len("</script>")
    return text[start:end]

def make_runtime_script(mapping: dict) -> str:
    runtime = {
        "schema": mapping["schema"],
        "remaps": mapping["remaps"],
        "permittedFigureIds": mapping["permitted_figure_ids"],
        "frozenReviewIds": mapping["frozen_review_ids"],
        "frozenExistingNeutralIds": mapping["frozen_existing_neutral_ids"],
    }
    payload = json.dumps(runtime, ensure_ascii=False, separators=(",", ":"))
    return f'''<script id="{SCRIPT_ID}">
(function(){{
'use strict';
var spec={payload};
var allowed=new Set(spec.permittedFigureIds);

function normalText(value){{
  return String(value||'').replace(/\\s+/g,' ').trim();
}}

function isDefaultVisible(node){{
  return !!node&&!node.closest('details:not([open])');
}}

function headingText(section){{
  return normalText(section&&section.querySelector('h4,h3,h5')&&section.querySelector('h4,h3,h5').innerText);
}}

function migrateLessonOwnership(rule){{
  if(rule.current_lesson===rule.target_lesson)return;
  var store=window.ORG_SYSTEM_DIAGRAMS;
  if(!store)return;
  var source=String(store[rule.current_lesson]||'');
  var target=String(store[rule.target_lesson]||'');
  var tSource=document.createElement('template');
  var tTarget=document.createElement('template');
  tSource.innerHTML=source;
  tTarget.innerHTML=target;
  var selector='figure[data-v188-plate="'+CSS.escape(rule.figure_id)+'"]';
  var already=tTarget.content.querySelector(selector);
  if(already){{
    var old=tSource.content.querySelector(selector);
    if(old)old.remove();
    store[rule.current_lesson]=tSource.innerHTML;
    return;
  }}
  var fig=tSource.content.querySelector(selector);
  if(!fig)throw new Error('V188 remap source figure missing: '+rule.figure_id+' in '+rule.current_lesson);
  fig.remove();
  tTarget.content.appendChild(fig);
  store[rule.current_lesson]=tSource.innerHTML;
  store[rule.target_lesson]=tTarget.innerHTML;
}}

spec.remaps.forEach(migrateLessonOwnership);

function targetPane(rule){{
  var panes=Array.from(document.querySelectorAll('#invTheoryMount .theory-pane'));
  return panes.find(function(pane){{
    var fig=pane.querySelector('figure[data-v188-plate="'+CSS.escape(rule.figure_id)+'"]');
    return !!fig;
  }})||null;
}}

function applyPane(pane){{
  if(!pane)return;
  var figures=new Map();
  Array.from(pane.querySelectorAll('figure[data-v188-plate]')).forEach(function(fig){{
    if(allowed.has(fig.dataset.v188Plate))figures.set(fig.dataset.v188Plate,fig);
  }});
  if(!figures.size)return;

  var currentLesson=null;
  var btn=document.querySelector('button[data-open-chapter].active,button[data-open-chapter][aria-current="true"]');
  if(btn)currentLesson=btn.dataset.openChapter||null;

  var neutralRules=spec.remaps.filter(function(rule){{
    return rule.target_mode==='NEUTRAL_ORIGINAL_CONTAINER'&&figures.has(rule.figure_id);
  }});
  neutralRules.forEach(function(rule){{
    var fig=figures.get(rule.figure_id);
    var visuals=pane.querySelector('.textbook-visuals');
    if(!visuals)throw new Error('V188 remap visual container missing: '+rule.figure_id);
    if(!isDefaultVisible(visuals))throw new Error('V188 remap neutral container is not default-visible: '+rule.figure_id);
    visuals.hidden=false;
    if(fig.parentElement!==visuals)visuals.appendChild(fig);
    fig.dataset.v188ContextualRemap='neutral-original';
  }});

  var headingGroups=new Map();
  spec.remaps.filter(function(rule){{
    return rule.target_mode==='CONTEXTUAL_VISIBLE_MATCH'&&figures.has(rule.figure_id);
  }}).forEach(function(rule){{
    var key=rule.target_heading;
    if(!headingGroups.has(key))headingGroups.set(key,[]);
    headingGroups.get(key).push(rule);
  }});

  headingGroups.forEach(function(rules,targetHeading){{
    var sections=Array.from(pane.querySelectorAll('.textbook-detail .textbook-subsection')).filter(isDefaultVisible);
    var section=sections.find(function(sec){{return headingText(sec)===targetHeading;}})||null;
    if(!section)throw new Error('V188 remap destination heading missing/default-hidden: '+targetHeading);
    var anchor=section;
    rules.forEach(function(rule){{
      var fig=figures.get(rule.figure_id);
      anchor.insertAdjacentElement('afterend',fig);
      fig.dataset.v188ContextualRemap='heading:'+targetHeading;
      anchor=fig;
    }});
  }});
}}

function applyAll(){{
  spec.remaps.forEach(function(rule){{
    var pane=targetPane(rule);
    if(pane)applyPane(pane);
  }});
}}

var queued=false;
function queue(){{
  if(queued)return;
  queued=true;
  requestAnimationFrame(function(){{
    requestAnimationFrame(function(){{
      queued=false;
      applyAll();
    }});
  }});
}}
new MutationObserver(queue).observe(document.getElementById('invTheoryMount'),{{childList:true,subtree:true}});
queue();
window.__v188ContextualBlockerRemap20260920={{
  schema:spec.schema,
  figureIds:spec.permittedFigureIds.slice(),
  apply:applyAll
}};
}})();
</script>'''

def main() -> None:
    ap = argparse.ArgumentParser()
    ap.add_argument("--input", type=Path, required=True)
    ap.add_argument("--mapping", type=Path, required=True)
    ap.add_argument("--output", type=Path, required=True)
    ap.add_argument("--report", type=Path, required=True)
    args = ap.parse_args()

    raw = args.input.read_bytes()
    require(sha256_bytes(raw) == EXPECTED_INPUT_SHA256,
            f"authoritative input SHA mismatch: {sha256_bytes(raw)}")
    require(len(raw) == EXPECTED_INPUT_SIZE,
            f"authoritative input size mismatch: {len(raw)}")
    text = raw.decode("utf-8")
    atlas = contextual_atlas(text)
    require(sha256_bytes(atlas.encode("utf-8")) == EXPECTED_CONTEXTUAL_ATLAS_SHA256,
            "contextual-atlas input identity mismatch")
    require(f'id="{SCRIPT_ID}"' not in text, "remap script already present")

    mapping = json.loads(args.mapping.read_text(encoding="utf-8"))
    ids = mapping.get("permitted_figure_ids", [])
    remaps = mapping.get("remaps", [])
    require(len(ids) == 9 and len(set(ids)) == 9, "exactly nine permitted figure IDs required")
    require(len(remaps) == 9, "exactly nine remap records required")
    require({r["figure_id"] for r in remaps} == set(ids), "remap IDs do not match permitted set")
    require(all(r["target_mode"] in {"CONTEXTUAL_VISIBLE_MATCH","NEUTRAL_ORIGINAL_CONTAINER"} for r in remaps),
            "unsupported target placement mode")
    require([r["figure_id"] for r in remaps if r["current_lesson"] != r["target_lesson"]] == ["peripatus"],
            "only Peripatus lesson ownership migration is permitted")
    require({r["figure_id"]:r["target_heading"] for r in remaps if r["target_mode"]=="CONTEXTUAL_VISIBLE_MATCH"} ==
            {"asterias-oral":"External","asterias-aboral":"External"},
            "only the two Asterias External heading overrides are permitted")

    runtime_script = make_runtime_script(mapping)
    marker = "</body>"
    require(text.count(marker) == 1, "unexpected body terminator count")
    candidate = text.replace(marker, runtime_script + "\n" + marker, 1)
    # Fail closed: the frozen generic contextual-atlas code is byte-for-byte unchanged.
    require(contextual_atlas(candidate) == atlas, "generic contextual-atlas changed")
    # Removing only the authorized mapping layer must recover the exact authoritative source.
    normalized = candidate.replace(runtime_script + "\n", "", 1)
    require(normalized == text, "candidate differs outside authorized remap layer")

    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(candidate, encoding="utf-8", newline="\n")
    candidate_bytes = args.output.read_bytes()

    report = {
        "authoritative_input_sha256": EXPECTED_INPUT_SHA256,
        "authoritative_input_size": EXPECTED_INPUT_SIZE,
        "candidate_sha256": sha256_bytes(candidate_bytes),
        "candidate_size": len(candidate_bytes),
        "mapping_sha256": sha256_file(args.mapping),
        "remap_script_sha256": sha256_bytes(runtime_script.encode("utf-8")),
        "contextual_atlas_sha256_before": EXPECTED_CONTEXTUAL_ATLAS_SHA256,
        "contextual_atlas_sha256_after": sha256_bytes(contextual_atlas(candidate).encode("utf-8")),
        "contextual_atlas_unchanged": contextual_atlas(candidate) == atlas,
        "normalized_candidate_equals_authoritative_input": normalized == text,
        "remap_count": len(remaps),
        "remap_ids": ids,
        "lesson_ownership_migrations": [
            {"figure_id": r["figure_id"], "from": r["current_lesson"], "to": r["target_lesson"]}
            for r in remaps if r["current_lesson"] != r["target_lesson"]
        ],
        "heading_overrides": [
            {"figure_id": r["figure_id"], "lesson": r["target_lesson"], "heading": r["target_heading"]}
            for r in remaps if r["target_mode"] == "CONTEXTUAL_VISIBLE_MATCH"
        ],
        "neutral_overrides": [
            {"figure_id": r["figure_id"], "lesson": r["target_lesson"]}
            for r in remaps if r["target_mode"] == "NEUTRAL_ORIGINAL_CONTAINER"
        ],
    }
    args.report.parent.mkdir(parents=True, exist_ok=True)
    args.report.write_text(json.dumps(report, indent=2, ensure_ascii=False) + "\n", encoding="utf-8")
    print(json.dumps(report, indent=2, ensure_ascii=False))

if __name__ == "__main__":
    main()
