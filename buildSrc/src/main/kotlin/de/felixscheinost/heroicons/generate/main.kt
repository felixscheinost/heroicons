package de.felixscheinost.heroicons.generate

import org.gradle.api.logging.Logger
import org.jsoup.Jsoup
import java.io.Writer
import java.nio.file.*
import kotlin.io.path.*

val lowercaseLetterFollowingDash = Regex("-(\\w)")

fun Writer.line(string: String) {
  write(string)
  write("\n")
}

fun generate(logger: Logger, inputDir: Path, outputDir: Path) {
  if (!inputDir.exists()) {
    throw Exception("Input directory '$inputDir' doesn't exist")
  }

  outputDir.toFile().deleteRecursively()
  outputDir.toFile().mkdirs()

  listOf("outline", "solid").forEach { variant: String ->

    logger.info("Start processing variant: $variant")

    val variantDir = inputDir.resolve(variant)
    if (!variantDir.exists()) {
      throw Exception("Variant directory '$variantDir' doesn't exist")
    }

    val variantOutFile = outputDir.resolve("$variant.kt")
    logger.info("Writing into $variantOutFile")
    variantOutFile.outputStream(StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING).writer(Charsets.UTF_8).use { writer ->

      writer.line("package de.felixscheinost.heroicons")
      writer.line("")
      writer.line("import kotlinx.html.*")
      writer.line("")

      variantDir.listDirectoryEntries("*.svg").forEach { icon: Path ->

        logger.info("Found icon: $icon")

        val camelCaseFileName = icon.name.dropLast(4).replace(lowercaseLetterFollowingDash) {
          it.groupValues[1].capitalize()
        }
        val functionName = "heroicon${variant.capitalize()}${camelCaseFileName.capitalize()}"
        logger.info("Will be named $functionName")

        val parsedSvg = Jsoup.parse(icon.toFile(), null).selectFirst("svg")

        writer.line("@HtmlTagMarker")
        writer.line("fun FlowOrPhrasingContent.${functionName}(classes: String? = null): Unit = svg(classes = classes) {")
        parsedSvg.attributes().forEach {
          if (it.key != "xmlns") {
            writer.line("  attributes[\"${it.key}\"] = \"${it.value}\"")
          }
        }
        writer.line("  unsafe {")
        parsedSvg.children().forEach {
          writer.line("    +\"\"\"${it.outerHtml()}\"\"\"")
        }
        writer.line("  }")
        writer.line("}")
        writer.line("")
      }
    }
  }
}