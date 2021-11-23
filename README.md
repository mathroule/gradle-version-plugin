# Gradle Version Plugin

[![](https://jitpack.io/v/mathroule/gradle-version-plugin.svg)](https://jitpack.io/#mathroule/gradle-version-plugin)

A Gradle plugin to manage semantic versioning `minor.major.patch` and generate corresponding Java version or Android version name and version code.

Task usage
----------
```groovy
buildscript {
    repositories {
        maven { url 'https://jitpack.io' }
    }

    dependencies {
        classpath 'com.mathroule:gradle-plugin:1.0.3'
    }
}

apply plugin: 'com.mathroule.gradle-plugin'
```

After applying the plugin you'll find `bumpVersion` tasks in your project.

To bump version, use
```
gradle bumpVersion
gradle bumpPatchVersion
gradle bumpMinorVersion
gradle bumpMajorVersion
```

If no VERSION file is presents, the file is created and init using version 0.0.1

Java usage
----------
```groovy
version versionPluginName
```

Android usage
-------------
```groovy
android {
    defaultConfig {
        VersionHelper versionPluginCode
        versionName versionPluginName
    }
}
```
