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

        versionFile = new File('../../VERSION')
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

        checkVersionPattern version

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
            it as int
        }

        checkMajorVersion major as int
        checkMinorVersion minor as int
        checkPatchVersion patch as int

        build(major, minor, patch, candidate)
    }

    static def checkVersionPattern(version) {
        if (!(version =~ /^(\d+\.)?(\d+\.)?(\*|\d+)(-+\w+\d*)?$/)) {
            throw new IllegalArgumentException("Version '$version' does not match pattern")
        }
    }

    static def checkMajorVersion(int version) {
        if (version < 0) {
            throw new IllegalArgumentException("Major version '$version' should be greater than 0")
        }
    }

    static def checkMinorVersion(int version) {
        if (version < 0 || 99 < version) {
            throw new IllegalArgumentException("Minor version '$version' should be greater than 0 and lower than 100")
        }
    }

    static def checkPatchVersion(int version) {
        if (version < 0 || 99 < version) {
            throw new IllegalArgumentException("Patch version '$version' should be greater than 0 and lower than 100")
        }
    }

    private static def checkRelease(int patch) {
        if (patch < 0 || 99 <= patch) {
            throw new IllegalArgumentException("Release '$patch' should be greater than 0 and lower than 99")
        }
    }

    private static def build(int major, int minor, int patch, int candidate) {
        (major * 1000000) + (minor * 10000) + (patch * 100) + candidate
    }
}
