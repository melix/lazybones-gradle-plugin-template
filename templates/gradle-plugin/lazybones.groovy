File projectDir = targetDir instanceof File?targetDir:new File(String.valueOf(targetDir))

def params = [:]
params['groupId'] = ask('What is the groupId for your plugin?\n')
params['pluginName'] = ask('What is the short name for your plugin?\n')
params['version'] = ask("What is the project's initial version?\n", '0.1', 'version')

['settings.gradle','build.gradle','src/test/groovy/com/acme/gradle/DummyPluginTest.groovy'].each {
    processTemplates(it, params)
}

String capitalized = params.pluginName.capitalize()
String shortPluginClassName = "${capitalized}Plugin"
String shortPluginTaskName = "${capitalized}Task"
String fqPluginClassName = "${params.groupId}.${shortPluginClassName}"

def createTemplateFile = { String path, String contents ->
    File f = new File(projectDir, path)
    f.parentFile.mkdirs()
    f << contents
}

createTemplateFile("src/main/resources/META-INF/gradle-plugins/${params.pluginName}.properties", "implementation-class=${fqPluginClassName}")

createTemplateFile("src/main/groovy/${params.groupId.replaceAll('\\.','/')}/${shortPluginClassName}.groovy", """\
package ${params.groupId}

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * This is the main plugin file. Put a description of your plugin here.
 */
class ${shortPluginClassName} implements Plugin<Project> {
    void apply(Project project) {

        project.task('${params.pluginName}', type:${shortPluginTaskName})
    }
}
""")

createTemplateFile("src/main/groovy/${params.groupId.replaceAll('\\.','/')}/${shortPluginTaskName}.groovy", """\
package ${params.groupId}

import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

/**
 * This is a dummy task added by the plugin. Put some information about your
 * task here.
 */
class ${shortPluginTaskName} extends AbstractTask {

    @TaskAction
    void exec() {
    }

}
""")

createTemplateFile("src/test/groovy/${params.groupId.replaceAll('\\.','/')}/${shortPluginClassName}Test.groovy", """\
package ${params.groupId}

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class ${shortPluginClassName}Test {
    @Test
    void pluginIsApplied() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: '${params.pluginName}'


        def task = project.tasks.findByName('${params.pluginName}')
        assert task instanceof ${shortPluginTaskName}
    }
}
""")

