package com.mathroule.gradle.version

import org.gradle.api.Plugin
import org.gradle.api.Project

class GradleVersionPlugin implements Plugin<Project> {

    void apply(Project project) {
        project.tasks.register('bumpVersion', BumpVersionTask)
        project.tasks.register('bumpPatchVersion', BumpPatchVersionTask)
        project.tasks.register('bumpMinorVersion', BumpMinorVersionTask)
        project.tasks.register('bumpMajorVersion', BumpMajorVersionTask)
        project.extensions.add('versionPluginCode', Version.getVersionCode())
        project.extensions.add('versionPluginName', Version.getVersionName())
    }
}
