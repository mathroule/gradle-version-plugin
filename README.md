# Gradle Version Plugin

[![](https://jitpack.io/v/mathroule/gradle-version-plugin.svg)](https://jitpack.io/#mathroule/gradle-version-plugin)

A Gradle plugin to manage [semantic versioning](https://semver.org) `minor.major.patch` and generate corresponding Java version or Android version name and version code.

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
gradle bumpVersion      # Shortcut for bumpPatchVersion
gradle bumpPatchVersion # Example bump version 1.1.2 to 1.1.3
gradle bumpMinorVersion # Example bump version 1.1.2 to 1.2.0
gradle bumpMajorVersion # Example bump version 1.1.2 to 2.0.0
```

If no VERSION file is presents, the file is created and init using version 0.0.1

Android version code generation
-------------------------------
The Android version code is built from the version name like this:
```
12345699
| | | \- 2 fourth digit => release candidate version
| | \- 2 third digit => patch version
| \- 2 second digit => minor version
\- 2 first digit => major version
```

Example:
| Version name   | Version code |
|----------------|--------------|
| 1.0.0          | 1000099      |
| 1.2.3          | 1020399      |
| 1.2.3-SNAPSHOT | 1020300      |
| 1.2.3-RC1      | 1020301      |
| 1.2.3-RC4      | 1020304      |
| 99.2.3         | 99020399     |
| 100.2.3        | 100020399    |

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
        versionCode versionPluginCode
        versionName versionPluginName
    }
}
```
