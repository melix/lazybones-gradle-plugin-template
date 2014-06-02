package ${groupId}

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * This is the main plugin file. Put a description of your plugin here.
 */
class ${shortPluginClassName} implements Plugin<Project> {
    void apply(Project project) {

        project.task('${pluginName}', type:${shortPluginTaskName})
    }
}
