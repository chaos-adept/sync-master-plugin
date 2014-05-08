package com.chaoslabgames.plugins

import com.chaoslabgames.extensions.SyncMasterExtension
import com.chaoslabgames.tasks.UpdateTask
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by DES on 08.05.2014.
 */
class SyncMasterPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.with {

            def SyncMasterExtension extension = extensions.create("syncMaster", SyncMasterExtension);

            extension.username = System.properties['GIT_USER_NAME'];
            extension.password = System.properties['GIT_USER_PASSWORD'];

            tasks.create("updateSlaves", UpdateTask)
        }
    }
}
