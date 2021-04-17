package com.mathroule.gradle.version

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNull
import static org.junit.jupiter.api.Assertions.assertThrows

class VersionTest extends AbstractVersionTest {

    @Test
    void getVersionName() {
        File file = new File('VERSION')
        file.createNewFile()
        file.text = '1.2.3'
        assertEquals('1.2.3', Version.getVersionName())
    }

    @Test
    void getVersionName_withParentFile() {
        File file = new File('../VERSION')
        file.createNewFile()
        file.text = '2.0.6'
        assertEquals('2.0.6', Version.getVersionName())
    }

    @Test
    void getVersionName_withFile() {
        File file = new File('CUSTOM_VERSION')
        file.createNewFile()
        file.text = '3.4.5'
        assertEquals('3.4.5', Version.getVersionName(file))
    }

    @Test
    void getVersionName_withoutFile() {
        assertNull(Version.getVersionName())
    }

    @Test
    void getVersionCode() {
        File file = new File('VERSION')
        file.createNewFile()
        file.text = '1.2.3'
        assertEquals(1020399, Version.getVersionCode())
    }

    @Test
    void getVersionCode_withFile() {
        File file = new File('CUSTOM_VERSION')
        file.createNewFile()
        file.text = '3.4.5'
        assertEquals(3040599, Version.getVersionCode(file))
    }

    @Test
    void getVersionCode_withoutFile() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.getVersionCode()
        })

        assertEquals("Version should not be empty", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withNegativeMajor() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("-1.1.3")
        })

        assertEquals("Version '-1.1.3' does not match pattern", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withMinor100() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("1.100.3")
        })

        assertEquals("Minor version '100' should be greater than 0 and lower than 100", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withNegativeMinor() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("1.-1.3")
        })

        assertEquals("Version '1.-1.3' does not match pattern", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withPatch100() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("1.2.100")
        })

        assertEquals("Patch version '100' should be greater than 0 and lower than 100", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withNegativePatch() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("1.2.-1")
        })

        assertEquals("Version '1.2.-1' does not match pattern", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withFinale() {
        assertEquals(99, Version.generateVersionCode("0.0.0"))
        assertEquals(1000099, Version.generateVersionCode("1.0.0"))
        assertEquals(1009999, Version.generateVersionCode("1.0.99"))
        assertEquals(1020099, Version.generateVersionCode("1.2.0"))
        assertEquals(1990099, Version.generateVersionCode("1.99.0"))
        assertEquals(1020399, Version.generateVersionCode("1.2.3"))
        assertEquals(99020399, Version.generateVersionCode("99.2.3"))
        assertEquals(100020399, Version.generateVersionCode("100.2.3"))
    }

    @Test
    void generateVersionCode_withZero() {
        assertEquals(0, Version.generateVersionCode("0.0.0-SNAPSHOT"))
        assertEquals(1, Version.generateVersionCode("0.0.0-RC1"))
        assertEquals(4, Version.generateVersionCode("0.0.0-RC4"))
        assertEquals(99, Version.generateVersionCode("0.0.0"))
    }

    @Test
    void generateVersionCode_withSnapshot() {
        assertEquals(0, Version.generateVersionCode("0.0.0-SNAPSHOT"))
        assertEquals(1020300, Version.generateVersionCode("1.2.3-SNAPSHOT"))
    }

    @Test
    void generateVersionCode_withRC() {
        assertEquals(1, Version.generateVersionCode("0.0.0-RC1"))
        assertEquals(3, Version.generateVersionCode("0.0.0-RC3"))
        assertEquals(1020301, Version.generateVersionCode("1.2.3-RC1"))
    }

    @Test
    void generateVersionCode_withRC99() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("0.0.0-RC99")
        })

        assertEquals("Release '99' should be greater than 0 and lower than 99", throwable.getMessage())
    }

    @Test
    void generateVersionCode_withNegativeRC() {
        Throwable throwable = assertThrows(IllegalArgumentException.class, () -> {
            Version.generateVersionCode("1.2.3-RC-1")
        })

        assertEquals("Version '1.2.3-RC-1' does not match pattern", throwable.getMessage())
    }
}
