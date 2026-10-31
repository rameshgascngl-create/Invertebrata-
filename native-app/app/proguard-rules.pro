# INVERTEBRATA release shrinking rules.
# Gson (ValidatedA5Parser, PlateCredits) deserialises by reflection: keep generic
# signatures/annotations and every app class so field names survive R8.
-keepattributes Signature,*Annotation*,InnerClasses,EnclosingMethod
-keep class com.gasczoology.invertebratelab.** { *; }
-keep class * extends com.google.gson.reflect.TypeToken { *; }
-dontwarn sun.misc.Unsafe
