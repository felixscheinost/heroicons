# Heroicons for kotlinx.html

This is a `kotlinx.html` wrapper around [Heroicons from Tailwind](https://github.com/tailwindlabs/heroicons)

[Preview and search at Heroicons.com](https://heroicons.com)

Currently only JVM is supported but multiplatform support should be easy to add. I just didn't need it yet!

## Including in your project

### Gradle

```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation("de.felixscheinost:heroicons-kotlinx-jvm:1.0.0")
}
```

## Usage

Example: For the icon `academic-cap` in the `outline` style the function would be called: `heroiconOutlineAcademicCap()`

```kotlin 
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import de.felixscheinost.heroicons.heroiconOutlineAcademicCap

System.out.appendHTML().html {
  body{
    div {
      a("https://kotlinlang.org") {
        target = ATarget.blank
        heroiconOutlineClipboardCopy()
        +"Get your certificate!"
      }
    }
  }
}
```