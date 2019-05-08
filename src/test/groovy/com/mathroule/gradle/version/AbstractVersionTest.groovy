package com.mathroule.gradle.version

import org.junit.After
import org.junit.Before

public class AbstractVersionTest {

    private String version

    @Before
    public void setUp() {

        File file = new File('VERSION')
        if (file.exists()) {
            version = file.text
        }
        file.delete()

        new File('../VERSION').delete()
    }

    @After
    public void tearDown() {
        File file = new File('VERSION')
        file.createNewFile()
        file.text = version

        new File('../VERSION').delete()
        new File('CUSTOM_VERSION').delete()
    }
}
