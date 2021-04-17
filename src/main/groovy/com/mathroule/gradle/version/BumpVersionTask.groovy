package com.mathroule.gradle.version

import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class BumpVersionTask extends AbstractBumpVersionTask {

    @Input
    def bump = project.hasProperty('bump') ? project.property('bump') : ''

    @Input
    String versionPath = project.hasProperty('versionPath') ? project.property('versionPath') : ''

    BumpVersionTask() {
        super

        description = 'Bump a version file.'
    }

    @TaskAction
    def bumpVersion() {
        switch (bump) {
            case 'major':
                bumpMajorVersionTask(versionPath)
                break
            case 'minor':
                bumpMinorVersionTask(versionPath)
                break
            case 'patch':
            default:
                bumpPatchVersionTask(versionPath)
        }
    }
}
