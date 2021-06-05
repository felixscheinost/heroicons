plugins {
  id("org.jetbrains.kotlin.jvm") version "1.4.31"
}

repositories {
  mavenCentral()
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
  api("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.3")
  implementation("org.jsoup:jsoup:1.13.1")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
  kotlinOptions {
    // Compatibility Kotlin Optional and JSR-305 Java @Nonnull annotation
    freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.io.path.ExperimentalPathApi")
    jvmTarget = "1.8"
  }
}
