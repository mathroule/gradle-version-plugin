package com.mathroule.gradle.version

import org.gradle.api.DefaultTask

abstract class AbstractBumpVersionTask extends DefaultTask {

    AbstractBumpVersionTask() {
        super

        group = 'tools'
    }

    static String DEFAULT_FILENAME = 'VERSION'

    static bumpPatchVersionTask(String versionPath) {
        def versionFile = getVersionFile(versionPath, '0.0.1')

        if (versionFile == null)
            return

        def (int major, int minor, int patch) = extractVersion(versionFile)

        if ((patch >= 99 && minor >= 99) || minor >= 99) {
            major++
            minor = 0
            patch = 0
        } else if (patch >= 99) {
            minor++
            patch = 0
        } else {
            patch++
        }

        def newVersion = getVersion(major, minor, patch)
        saveVersion(versionFile, newVersion)
    }

    static bumpMinorVersionTask(String versionPath) {
        def versionFile = getVersionFile(versionPath, '0.1.0')

        if (versionFile == null)
            return

        def (int major, int minor, int patch) = extractVersion(versionFile)

        if (minor >= 99) {
            major++
            minor = 0
            patch = 0
        } else {
            minor++
            patch = 0
        }

        def newVersion = getVersion(major, minor, patch)
        saveVersion(versionFile, newVersion)
    }

    static bumpMajorVersionTask(String versionPath) {
        def versionFile = getVersionFile(versionPath, '1.0.0')

        if (versionFile == null)
            return

        def (int major, int minor, int patch) = extractVersion(versionFile)

        major++
        minor = 0
        patch = 0

        def newVersion = getVersion(major, minor, patch)
        saveVersion(versionFile, newVersion)
    }

    static getVersionFile(String versionPath, String initVersion) {
        def versionFile = new File(versionPath != null && !versionPath.isEmpty() ? versionPath : DEFAULT_FILENAME)
        if (!versionFile.exists()) {
            versionFile.createNewFile()
            versionFile.text = initVersion
            println("Version init with " + versionFile.text)
            return null
        }

        return versionFile
    }

    static extractVersion(File versionFile) {
        def version = versionFile.text
        Version.checkVersion(version)

        def versionTokens = version.tokenize('.').toArray()

        def major = versionTokens[0] as int
        Version.checkMajorVersion(major)

        def minor = versionTokens[1] as int
        Version.checkVersion(minor)

        def patch = versionTokens[2] as int
        Version.checkVersion(patch)

        [major, minor, patch]
    }

    static saveVersion(File versionFile, String version) {
        def oldVersion = versionFile.text
        versionFile.text = version
        println("Version bumped from " + oldVersion + " to " + version)
    }

    static getVersion(int major, int minor, int patch) {
        return major + '.' + minor + '.' + patch
    }
}
