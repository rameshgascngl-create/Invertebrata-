# Installed-build verification — 3 November 2026

Tested commit: 341a74a07ea98d2728f0b98b7752deb32d820bb2.
[Functional run 41565905704](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41565905704).

Raw uploaded XML was read for every configuration (three methods each):
| Build | Configuration | Passed | Failed | Skipped |
|---|---|---:|---:|---:|
| Debug | Tamil 200% portrait | 3 | 0 | 0 |
| Debug | Tamil 200% landscape | 3 | 0 | 0 |
| Release | English 100% | 3 | 0 | 0 |
| Release | Tamil 100% | 3 | 0 | 0 |
| Release | Tamil 200% portrait | 3 | 0 | 0 |
| Release | Tamil 200% landscape | 2 | 1 | 0 |

The three earlier node lookups no longer failed in this run. Landscape release reached binary fission, then failed allFiveSimulatorsMoveRealDiagramPixelsAndPause:
`binary_fission / r17-nuclear-canvas: diagram capture heights differ (before=2126x432, after=2126x499) expected:<432> but was:<499>`.

This is a viewport measurement failure, not sufficient evidence to accept or reject animation correctness for the remaining nuclear cases.
The other two landscape methods passed.

## Follow-up candidate
The former settle loop accepted one unchanged 90 ms sample and could run before scrollTo had mounted/reached its target. The follow-up reaches the node first, uses the existing nuclear full-plate button (which knows unclipped layout bounds), and requires stable complete bounds for at least 540 ms across consecutive bring-into-view cycles. It fails explicitly if the viewport cannot settle.
Screenshot equality, changed-pixel count, capture dimension equality, intermediate phase and pause assertions are preserved. No resizing or common-intersection crop is used to make unequal captures pass.
A second installed-build run is required; this document does not claim that candidate has passed.


## Second installed-build run

Tested commit df05b1d53b380025f44d73f803a7b404433a174f, [run 41567444902](https://github.com/rameshgascngl-create/Invertebrata-/actions/runs/41567444902). All configurations again passed 3/3 except landscape release (2/3). Raw XML: the simulator method failed with `IllegalStateException: Required value was null` in `allFiveSimulatorsMoveRealDiagramPixelsAndPause$playbackControl`. The test directly required a transport node after moving the full nuclear plate into view; the offscreen control was no longer exported. This is a test navigation assumption, not an animation exception in app code. The next candidate reaches the real transport for every Play/Pause action, then independently restores the complete diagram before capture. It also saves both comparison images before asserting, preserving evidence if comparison fails. No pixel/dimension/phase assertion is removed. A third installed-build run is required.
