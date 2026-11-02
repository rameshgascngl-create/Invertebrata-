# INVERTEBRATA release shrinking rules.
# Gson (ValidatedA5Parser, PlateCredits) deserialises by reflection: keep generic
# signatures/annotations and every app class so field names survive R8.
-keepattributes Signature,*Annotation*,InnerClasses,EnclosingMethod
-keep class com.gasczoology.invertebratelab.** { *; }
-keep class * extends com.google.gson.reflect.TypeToken { *; }
-dontwarn sun.misc.Unsafe

# AndroidJUnitRunner calls this shared API before discovering any tests.
# R8 must retain its name and members across the app and instrumentation APKs.
-keep class androidx.tracing.Trace { *; }

# The instrumentation APK shares the target's Kotlin/coroutine runtime.
# Preserve that binary API when the two APKs are processed independently by R8.
-keep class kotlin.** { *; }
-keep class kotlinx.coroutines.** { *; }
