import de.felixscheinost.heroicons.generate.generate

plugins {
  kotlin("jvm") version "1.5.10"
  id("maven-publish")
}

group = "de.felixscheinost"
version = (groovy.json.JsonSlurper().parse(file("package.json")) as Map<*, *>)["version"]!!

repositories {
  mavenCentral()
  jcenter()
}

dependencies {
  implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
  implementation(kotlin("stdlib-jdk8"))
  api("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
  testImplementation("org.jetbrains.kotlin:kotlin-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

val generate by tasks.creating(DefaultTask::class) {
  val inputDir = file("optimized")
  val outputDir = file("src/main/kotlin/de/felixscheinost/heroicons")
  inputs.dir(inputDir)
  outputs.dir(outputDir)
  doLast {
    generate(logger, inputDir.toPath(), outputDir.toPath())
  }
}

tasks.compileKotlin.get().dependsOn(generate)

val sourcesJar by tasks.creating(Jar::class) {
  archiveClassifier.set("sources")
  from(sourceSets.getByName("main").allSource)
}

