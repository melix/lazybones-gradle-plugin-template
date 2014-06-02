File projectDir = targetDir instanceof File?targetDir:new File(String.valueOf(targetDir))

def params = [:]
params['groupId'] = ask('What is the groupId for your plugin?\n')
params['pluginName'] = ask('What is the short name for your plugin?\n').toLowerCase()
params['version'] = ask("What is the project's initial version?\n", '0.1', 'version')

['settings.gradle','build.gradle'].each {
    processTemplates(it, params)
}

def capitalized = params.pluginName.capitalize()
params.shortPluginClassName = "${capitalized}Plugin"
params.shortPluginTaskName = "${capitalized}Task"
params.fqPluginClassName = "${params.groupId}.${params.shortPluginClassName}"

def createTemplateFile = { String templateName, String path ->
    String template = "tpl/$templateName"
    processTemplates(template, params)
    File moved = new File(projectDir, path)
    moved.parentFile.mkdirs()
    new File(projectDir, template).renameTo(moved)
}

createTemplateFile('plugin-descriptor.properties',"src/main/resources/META-INF/gradle-plugins/${params.pluginName}.properties")

createTemplateFile('Plugin.groovy', "src/main/groovy/${params.groupId.replaceAll('\\.','/')}/${params.shortPluginClassName}.groovy")
createTemplateFile('Task.groovy', "src/main/groovy/${params.groupId.replaceAll('\\.','/')}/${params.shortPluginTaskName}.groovy")
createTemplateFile('Test.groovy', "src/test/groovy/${params.groupId.replaceAll('\\.','/')}/${params.shortPluginClassName}Test.groovy")

new File(projectDir, 'tpl').deleteDir()