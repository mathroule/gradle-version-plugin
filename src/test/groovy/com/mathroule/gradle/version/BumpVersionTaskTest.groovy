package com.mathroule.gradle.version

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.AssertTrue.assertTrue
import static org.junit.jupiter.api.AssertEquals.assertEquals
import static org.junit.jupiter.api.AssertFalse.assertFalse

class BumpVersionTaskTest extends AbstractVersionTest {

    private static String DEFAULT_FILENAME = 'VERSION'

    @Test
    public void canAddTasksToProject() {
        Project project = ProjectBuilder.builder().build()

        def bumpVersionTask = project.task('bumpVersion', type: BumpVersionTask)
        assertTrue(bumpVersionTask instanceof BumpVersionTask)

        def bumpPatchVersionTask = project.task('bumpPatchVersion', type: BumpPatchVersionTask)
        assertTrue(bumpPatchVersionTask instanceof BumpPatchVersionTask)

        def bumpMinorVersionTask = project.task('bumpMinorVersionTask', type: BumpMinorVersionTask)
        assertTrue(bumpMinorVersionTask instanceof BumpMinorVersionTask)

        def bumpMajorVersion = project.task('bumpMajorVersion', type: BumpMajorVersionTask)
        assertTrue(bumpMajorVersion instanceof BumpMajorVersionTask)
    }

    @Test
    public void bumpVersion() {
        checkWithFile(TASKS[0], '0.0.0', '0.0.1')
        checkWithFile(TASKS[0], '0.0.1', '0.0.2')
        checkWithFile(TASKS[1], '0.0.0', '0.0.1')
        checkWithFile(TASKS[1], '0.0.1', '0.0.2')
        checkWithFile(TASKS[2], '0.0.0', '0.1.0')
        checkWithFile(TASKS[2], '0.1.0', '0.2.0')
        checkWithFile(TASKS[3], '0.0.0', '1.0.0')
        checkWithFile(TASKS[3], '1.0.0', '2.0.0')
    }

    @Test
    public void bumpVersion_withInvalidVersion() {
        checkWithInvalidVersionFile('invalid')
        checkWithInvalidVersionFile('a.b.c')
        checkWithInvalidVersionFile('1.b.c')
        checkWithInvalidVersionFile('a.a.1')
        checkWithInvalidVersionFile('2.a')
    }

    @Test
    public void bumpVersion_withInvalidMajorVersion() {
        checkWithInvalidVersionFile('a.1.1')
    }

    @Test
    public void bumpVersion_withInvalidMinorVersion() {
        checkWithInvalidVersionFile('1.a.1')
    }

    @Test
    public void bumpVersion_withInvalidPatchVersion() {
        checkWithInvalidVersionFile('1.1.a')
    }

    @Test
    public void bumpVersion_withNegativeMajor() {
        checkWithInvalidVersionFile('-1.2.3')
    }

    @Test
    public void bumpVersion_withNegativeMinor() {
        checkWithInvalidVersionFile('1.-2.3')
    }

    @Test
    public void bumpVersion_withNegativePatch() {
        checkWithInvalidVersionFile('1.2.-3')
    }

    @Test
    public void bumpVersion_withMinor99AndPatch99() {
        checkWithFile(TASKS[0], '0.99.99', '1.0.0')
        checkWithFile(TASKS[1], '0.99.99', '1.0.0')
        checkWithFile(TASKS[2], '0.99.99', '1.0.0')
        checkWithFile(TASKS[3], '0.99.99', '1.0.0')
    }

    @Test
    public void bumpVersion_withMinor99() {
        checkWithFile(TASKS[0], '0.99.0', '1.0.0')
        checkWithFile(TASKS[1], '0.99.0', '1.0.0')
        checkWithFile(TASKS[2], '0.99.0', '1.0.0')
        checkWithFile(TASKS[3], '0.99.0', '1.0.0')
    }

    @Test
    public void bumpVersion_withPatch99() {
        checkWithFile(TASKS[0], '0.0.99', '0.1.0')
        checkWithFile(TASKS[1], '0.0.99', '0.1.0')
        checkWithFile(TASKS[2], '0.0.99', '0.1.0')
        checkWithFile(TASKS[3], '0.0.99', '1.0.0')
    }

    @Test
    public void bumpVersion_withoutFile() {
        checkWithoutFile(TASKS[0], '0.0.1')
        checkWithoutFile(TASKS[1], '0.0.1')
        checkWithoutFile(TASKS[2], '0.1.0')
        checkWithoutFile(TASKS[3], '1.0.0')
    }

    @Test
    public void bumpVersion_withProperty() {
        checkWithFileAndBump(TASKS[0], '', '0.1.2', '0.1.3')
        checkWithFileAndBump(TASKS[0], 'invalid', '0.1.2', '0.1.3')
        checkWithFileAndBump(TASKS[0], 'patch', '0.1.2', '0.1.3')
        checkWithFileAndBump(TASKS[0], 'minor', '0.1.2', '0.2.0')
        checkWithFileAndBump(TASKS[0], 'major', '0.1.2', '1.0.0')
    }

    private static TASKS = [['bumpVersion', BumpVersionTask],
                            ['bumpPatchVersion', BumpPatchVersionTask],
                            ['bumpMinorVersion', BumpMinorVersionTask],
                            ['bumpMajorVersion', BumpMajorVersionTask]]

    private static void checkWithInvalidVersionFile(String initVersion) {
        for (List taskInfo : TASKS) {
            def taskName = taskInfo[0]
            def taskType = taskInfo[1]
            println("test task: " + taskName + " with invalid version: " + initVersion)
            Project project = ProjectBuilder.builder().build()
            def task = project.task(taskName, type: taskType)
            File file = new File(DEFAULT_FILENAME)
            file.createNewFile()
            file.text = initVersion
            try {
                task."$taskName"()
                fail("IllegalArgumentException should be thrown")
            } catch (IllegalArgumentException ignored) {
            }
            assertEquals(initVersion, new File(DEFAULT_FILENAME).text)
            new File(DEFAULT_FILENAME).delete()
        }
    }

    private static void checkWithFile(List taskInfo, String initVersion, String expectedVersion) {
        def taskName = taskInfo[0]
        def taskType = taskInfo[1]
        println("test task: " + taskName + " with version: " + initVersion + " => " + expectedVersion)
        Project project = ProjectBuilder.builder().build()
        def task = project.task(taskName, type: taskType)
        File file = new File(DEFAULT_FILENAME)
        file.createNewFile()
        file.text = initVersion
        task."$taskName"()
        assertEquals(expectedVersion, new File(DEFAULT_FILENAME).text)
        new File(DEFAULT_FILENAME).delete()
    }

    private static void checkWithFileAndBump(List taskInfo, String bump, String initVersion, String expectedVersion) {
        def taskName = taskInfo[0]
        def taskType = taskInfo[1]
        println("test task: " + taskName + " with bump: " + bump + " and version: " + initVersion + " => " + expectedVersion)
        Project project = ProjectBuilder.builder().build()
        def task = project.task(taskName, type: taskType)
        File file = new File(DEFAULT_FILENAME)
        file.createNewFile()
        file.text = initVersion
        task.setProperty("bump", bump)
        task."$taskName"()
        assertEquals(expectedVersion, new File(DEFAULT_FILENAME).text)
        new File(DEFAULT_FILENAME).delete()
    }

    private static void checkWithoutFile(List taskInfo, String expectedVersion) {
        def taskName = taskInfo[0]
        def taskType = taskInfo[1]
        println("test task: " + taskName + " init with " + expectedVersion)
        Project project = ProjectBuilder.builder().build()
        def task = project.task(taskName, type: taskType)
        assertFalse(new File(DEFAULT_FILENAME).exists())
        task."$taskName"()
        assertTrue(new File(DEFAULT_FILENAME).exists())
        assertEquals(expectedVersion, new File(DEFAULT_FILENAME).text)
        new File(DEFAULT_FILENAME).delete()
    }
}
