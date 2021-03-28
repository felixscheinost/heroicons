plugins {
  id("org.jetbrains.kotlin.jvm") version "1.4.20"
}

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  api("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
  implementation("org.jsoup:jsoup:1.13.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    // Compatibility Kotlin Optional and JSR-305 Java @Nonnull annotation
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "1.8"
    freeCompilerArgs += "-Xopt-in=kotlin.io.path.ExperimentalPathApi"
    // https://blog.jetbrains.com/kotlin/2021/02/the-jvm-backend-is-in-beta-let-s-make-it-stable-together/
    useIR = true
  }
}
