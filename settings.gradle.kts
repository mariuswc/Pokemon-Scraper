pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    // Some plugins (including parts of Spring tooling) may add repositories at the project level.
    // Use PREFER_SETTINGS so this build still works, while keeping mavenCentral as the primary source.
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        mavenCentral()
    }
}

rootProject.name = "web-scraper"
