package com.mathroule.gradle.version

import org.gradle.api.tasks.TaskAction

class BumpMajorVersionTask extends AbstractBumpVersionTask {

    BumpMajorVersionTask() {
        super

        description = 'Major bump a version file.'
    }

    @TaskAction
    def bumpMajorVersion() {
        bumpMajorVersionTask()
    }
}
