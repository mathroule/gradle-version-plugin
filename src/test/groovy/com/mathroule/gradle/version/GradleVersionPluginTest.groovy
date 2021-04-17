package com.mathroule.gradle.version

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class GradleVersionPluginTest extends AbstractVersionTest {

    private Project project

    @BeforeEach
    void setUp() {
        super.setUp()

        File file = new File('VERSION')
        file.createNewFile()
        file.text = '1.2.3'

        project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.mathroule.gradle-version-plugin'
    }

    @AfterEach
    void versionPlugin_addBumpVersionTasks_toProject() {
        assertTrue(project.tasks.bumpVersion instanceof BumpVersionTask)
        assertTrue(project.tasks.bumpPatchVersion instanceof BumpPatchVersionTask)
        assertTrue(project.tasks.bumpMinorVersion instanceof BumpMinorVersionTask)
        assertTrue(project.tasks.bumpMajorVersion instanceof BumpMajorVersionTask)
    }

    @Test
    void versionPlugin_addGetVersionCodeExtension_toProject() {
        assertEquals(1020399, project.extensions.findByName('versionPluginCode'))
    }

    @Test
    void versionPlugin_addGetVersionNameExtension_toProject() {
        assertEquals('1.2.3', project.extensions.findByName('versionPluginName'))
    }
}
