package com.acme.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class DummyPluginTest {
    @Test
    void pluginIsApplied() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: '${pluginName}'


        def task = project.tasks.findByName('dummy')
        assert task instanceof DummyTask
    }
}