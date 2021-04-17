package com.mathroule.gradle.version

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

class AbstractVersionTest {

    private String version

    @BeforeEach
    void setUp() {

        File file = new File('VERSION')
        if (file.exists()) {
            version = file.text
        }
        file.delete()

        new File('../VERSION').delete()
    }

    @AfterEach
    void tearDown() {
        File file = new File('VERSION')
        file.createNewFile()
        file.text = version

        new File('../VERSION').delete()
        new File('CUSTOM_VERSION').delete()
    }
}
