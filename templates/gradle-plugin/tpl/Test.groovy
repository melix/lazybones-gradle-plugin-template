package ${groupId}

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class ${shortPluginClassName}Test {
    @Test
    void pluginIsApplied() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: '${pluginName}'


        def task = project.tasks.findByName('${pluginName}')
        assert task instanceof ${shortPluginTaskName}
    }
}