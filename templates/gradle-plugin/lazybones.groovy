def params = [:]
params['groupId'] = ask('What is the groupId for your plugin?\n')
params['pluginName'] = ask('What is the short name for your plugin?\n')
params['version'] = ask("What is the project's initial version?\n", '0.1', 'version')

['settings.gradle','build.gradle','src/test/groovy/com/acme/gradle/DummyPluginTest.groovy'].each {
    processTemplates(it, params)
}

File projectDir = targetDir instanceof File?targetDir:new File(String.valueOf(targetDir))
File descriptorFile = new File(projectDir, "src/main/resources/META-INF/gradle-plugins/${params.pluginName}.properties")
descriptorFile.parentFile.mkdirs()
descriptorFile << 'implementation-class=com.acme.gradle.DummyPlugin'

