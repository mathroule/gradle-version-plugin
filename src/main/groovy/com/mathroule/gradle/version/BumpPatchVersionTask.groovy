package com.mathroule.gradle.version

import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault(because = "Version bumping tasks modify files and should not be cached to ensure correct version updates.")
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
