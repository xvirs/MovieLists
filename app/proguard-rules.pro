# Mantener todas las clases y métodos de tu aplicación
-keep class com.example.** { *; }

# Mantener todas las clases y métodos de las bibliotecas que utilizas
-keep class androidx.** { *; }
-keep class kotlinx.** { *; }
-keep class io.ktor.** { *; }
-keep class io.insert-koin.** { *; }
-keep class com.google.firebase.** { *; }
-keep class coil.** { *; }

# No advertir sobre clases faltantes
-dontwarn org.slf4j.impl.StaticLoggerBinder
-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
-dontwarn org.slf4j.impl.StaticMDCBinder

# Mantener clases y métodos específicos
-keep class com.example.MyClass { *; }
-keep interface com.example.MyInterface { *; }

# Mantener anotaciones
-keepattributes *Annotation*

# Mantener clases y métodos utilizados por bibliotecas
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Mantener clases y métodos utilizados por Koin
-keep class org.koin.** { *; }
-keep class io.insert-koin.** { *; }

# Mantener clases y métodos utilizados por Room
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# Mantener clases y métodos utilizados por Ktor
-keep class io.ktor.** { *; }
-keep class io.ktor.client.** { *; }
-keep class io.ktor.client.engine.** { *; }
-keep class io.ktor.client.features.** { *; }
-keep class io.ktor.client.request.** { *; }
-keep class io.ktor.client.response.** { *; }
-keep class io.ktor.client.statement.** { *; }
-keep class io.ktor.client.utils.** { *; }
-keep class io.ktor.http.** { *; }
-keep class io.ktor.util.** { *; }

# Mantener clases y métodos utilizados por Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
