package com.mathroule.gradle.version

class Version {

    static def getVersionCode() {
        return generateVersionCode(getVersionName())
    }

    static def getVersionCode(File file) {
        return generateVersionCode(getVersionName(file))
    }

    static def getVersionName() {
        def versionFile = new File('VERSION')
        if (versionFile.exists()) {
            return versionFile.text
        }

        versionFile = new File('../VERSION')
        if (versionFile.exists()) {
            return versionFile.text
        }
    }

    static def getVersionName(File file) {
        if (file.exists()) {
            return file.text
        }
    }

    static def generateVersionCode(version) {
        if (!version) {
            throw new IllegalArgumentException("Version should not be empty")
        }

        // TODO check with regex checkVersionPattern version

        def candidate = 99
        def (major, minor, patch) = version.toLowerCase().replaceAll('-', '').tokenize('.')
        if (patch.endsWith("snapshot")) {
            candidate = 0
            patch = patch.replaceAll("[^0-9]", "")
        } else {
            def rc
            (patch, rc) = patch.tokenize("rc")
            if (rc) {
                checkRelease rc as int
                candidate = rc
            }
        }

        (major, minor, patch, candidate) = [major, minor, patch, candidate].collect {
            it.toInteger()
        }

        // println "major: " + major + " minor: " + minor + " patch: " + patch + " candidate: " + candidate

        checkMajorVersion major
        checkVersion minor
        checkVersion patch

        return build(major, minor, patch, candidate);
    }

    private static def checkVersionPattern(version) {
        if (version =~ /[0-9]+\.[0.9]+\.[0.9]/) {
            throw new IllegalArgumentException()
        }
    }

    static def checkVersion(version) {
        if (false) {
            throw new IllegalArgumentException()
        }
    }

    static def checkMajorVersion(int version) {
        if (version < 0) {
            throw new IllegalArgumentException("Major version should be greater than 0")
        }
    }

    static def checkVersion(int version) {
        if (version < 0 || 99 < version) {
            throw new IllegalArgumentException("Version should be greater than 0 and lower than 100")
        }
    }

    private static def checkRelease(int patch) {
        if (patch < 0 || 99 <= patch) {
            throw new IllegalArgumentException("Release should be greater than 0 and lower than 99")
        }
    }

    private static def build(int major, int minor, int patch, int candidate) {
        (major * 1000000) + (minor * 10000) + (patch * 100) + candidate
    }
}
