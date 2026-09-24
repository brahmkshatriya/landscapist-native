import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.Sync
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

/**
 * Repairs custom `skiaMain` metadata compilation when Compose Native substitutes native-only
 * Compose modules. Alpha05 already repairs common Compose Runtime metadata; custom shared source
 * sets also need those repaired KLIBs and the official ui-skiko non-Android metadata KLIB.
 */
internal fun Project.configureComposeNativeSkiaMetadataRepair(libs: VersionCatalog) {
  val uiSkikoMetadata = configurations.create("landscapistUiSkikoMetadataRepair") {
    isCanBeConsumed = false
    isCanBeResolved = true
    isVisible = false
    isTransitive = false
  }
  dependencies {
    add(
      uiSkikoMetadata.name,
      "org.jetbrains.compose.ui:ui-skiko:${libs.findVersion("jetbrains-compose").get()}@jar",
    )
  }

  val repairedUiSkiko =
    layout.buildDirectory.dir("kotlinComposeNativeMetadataLibraries/skiaMain/ui-skiko")
  val repairedUiSkikoKlib = repairedUiSkiko.map { it.dir("nonAndroidMain") }
  val repairUiSkiko = tasks.register<Sync>("repairComposeNativeSkiaUiMetadata") {
    inputs.files(uiSkikoMetadata)
    from({ zipTree(uiSkikoMetadata.singleFile) }) {
      include("nonAndroidMain/**")
      includeEmptyDirs = false
    }
    into(repairedUiSkiko)
  }

  tasks.configureEach {
    when (name) {
      "compileSkiaMainKotlinMetadata" -> {
        dependsOn("repairComposeNativeCommonMetadata", repairUiSkiko)
        metadataLibraries()?.from(
          layout.buildDirectory.dir("kotlinComposeNativeMetadataLibraries/commonMain").map { directory ->
            directory.asFileTree.matching { include("*.klib") }
          },
          repairedUiSkikoKlib,
        )
      }
    }
  }
}

@Suppress("UNCHECKED_CAST")
private fun org.gradle.api.Task.metadataLibraries(): ConfigurableFileCollection? =
  javaClass.methods
    .singleOrNull { it.name == "getLibraries" && it.parameterCount == 0 }
    ?.invoke(this) as? ConfigurableFileCollection
