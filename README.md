**Note:** Jackson 2.12+ publishes Gradle Module Metadata and this plugin is no longer needed to align on a 2.12+ version of Jackson.

A Gradle plugin that adds additional metadata to 2.6.7+ releases of the [Jackson](https://github.com/FasterXML/jackson) libraries.
By applying this plugin, different Jackson components automatically align on their versions as described in [this blog post](https://blog.gradle.org/alignment-with-gradle-module-metadata).

Apply the plugin in your Gradle project:

```
plugins {
  id("de.jjohannes.missing-metadata-jackson") version "0.1"
}
```

This plugin requires no configuration.
