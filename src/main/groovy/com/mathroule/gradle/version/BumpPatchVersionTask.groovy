package com.mathroule.gradle.version

import org.gradle.api.tasks.TaskAction

class BumpPatchVersionTask extends AbstractBumpVersionTask {

    BumpPatchVersionTask() {
        super

        description = 'Patch bump a version file.'
    }

    @TaskAction
    def bumpPatchVersion() {
        bumpPatchVersionTask()
    }
}
