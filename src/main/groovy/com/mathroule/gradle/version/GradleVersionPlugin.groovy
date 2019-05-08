package com.mathroule.gradle.version

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleVersionPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.tasks.create('bumpVersion', BumpVersionTask)
        project.tasks.create('bumpPatchVersion', BumpPatchVersionTask)
        project.tasks.create('bumpMinorVersion', BumpMinorVersionTask)
        project.tasks.create('bumpMajorVersion', BumpMajorVersionTask)
        project.extensions.add('versionPluginCode', Version.getVersionCode())
        project.extensions.add('versionPluginName', Version.getVersionName())
    }
}
