package com.acme.gradle

import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.TaskAction

/**
 * This is a dummy task added by the plugin. Put some information about your
 * task here.
 */
class DummyTask extends AbstractTask {

    @TaskAction
    void exec() {
    }

}
