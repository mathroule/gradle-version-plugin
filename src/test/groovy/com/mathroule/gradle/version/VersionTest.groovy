package com.mathroule.gradle.version

import org.junit.Ignore
import org.junit.Test

import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertNull

public class VersionTest extends AbstractVersionTest {

    @Test
    public void getVersionName() {
        File file = new File('VERSION')
        file.createNewFile()
        file.text = '1.2.3'
        assertEquals('1.2.3', Version.getVersionName())
    }

    @Test
    public void getVersionName_withParentFile() {
        File file = new File('../VERSION')
        file.createNewFile()
        file.text = '2.0.6'
        assertEquals('2.0.6', Version.getVersionName())
    }

    @Test
    public void getVersionName_withFile() {
        File file = new File('CUSTOM_VERSION')
        file.createNewFile()
        file.text = '3.4.5'
        assertEquals('3.4.5', Version.getVersionName(file))
    }

    @Test
    public void getVersionName_withoutFile() {
        assertNull(Version.getVersionName())
    }

    @Test
    public void getVersionCode() {
        File file = new File('VERSION')
        file.createNewFile()
        file.text = '1.2.3'
        assertEquals(1020399, Version.getVersionCode())
    }

    @Test
    public void getVersionCode_withFile() {
        File file = new File('CUSTOM_VERSION')
        file.createNewFile()
        file.text = '3.4.5'
        assertEquals(3040599, Version.getVersionCode(file))
    }

    @Test(expected = IllegalArgumentException.class)
    public void getVersionCode_withoutFile() {
        assertNull(Version.getVersionCode())
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void generateVersionCode_withNegativeMajor() {
        Version.generateVersionCode("-1.1.3")
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateVersionCode_withMinor100() {
        Version.generateVersionCode("1.100.3")
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void generateVersionCode_withNegativeMinor() {
        Version.generateVersionCode("1.-1.3")
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateVersionCode_withPatch100() {
        Version.generateVersionCode("1.2.100")
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void generateVersionCode_withNegativePatch() {
        Version.generateVersionCode("1.2.-1")
    }

    @Test
    public void generateVersionCode_withFinale() {
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
    public void generateVersionCode_withZero() {
        assertEquals(0, Version.generateVersionCode("0.0.0-SNAPSHOT"))
        assertEquals(1, Version.generateVersionCode("0.0.0-RC1"))
        assertEquals(4, Version.generateVersionCode("0.0.0-RC4"))
        assertEquals(99, Version.generateVersionCode("0.0.0"))
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateVersionCode_withInvalidCandidate() {
        Version.generateVersionCode("1.2.3-INVALID")
    }

    @Test
    public void generateVersionCode_withSnapshot() {
        assertEquals(0, Version.generateVersionCode("0.0.0-SNAPSHOT"))
        assertEquals(1020300, Version.generateVersionCode("1.2.3-SNAPSHOT"))
    }

    @Test
    public void generateVersionCode_withRC() {
        assertEquals(1, Version.generateVersionCode("0.0.0-RC1"))
        assertEquals(3, Version.generateVersionCode("0.0.0-RC3"))
        assertEquals(1020301, Version.generateVersionCode("1.2.3-RC1"))
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateVersionCode_withInvalidRC() {
        Version.generateVersionCode("0.0.0-RCinvalid")
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateVersionCode_withRC99() {
        Version.generateVersionCode("0.0.0-RC99")
    }

    @Test(expected = IllegalArgumentException.class)
    @Ignore
    public void generateVersionCode_withNegativeRC() {
        Version.generateVersionCode("1.2.3-RC-1")
    }
}
