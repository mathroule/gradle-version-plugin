package com.mathroule.gradle.version

import org.gradle.api.tasks.TaskAction

class BumpMinorVersionTask extends AbstractBumpVersionTask {

    BumpMinorVersionTask() {
        super

        description = 'Minor bump a version file.'
    }

    @TaskAction
    def bumpMinorVersion() {
        bumpMinorVersionTask()
    }
}
