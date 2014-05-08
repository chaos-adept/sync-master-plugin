package com.chaoslabgames.tasks

import com.chaoslabgames.extensions.SyncMasterExtension
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by DES on 08.05.2014.
 */
class UpdateTask extends DefaultTask {

    def SyncMasterExtension syncMasterExt;

    UpdateTask() {

    }

    @TaskAction
    def action() {
        syncMasterExt = project.extensions.syncMaster;

        if (!syncMasterExt.username) {
            throw new RuntimeException('define git user name - syncMaster.username');
        }
        if (!syncMasterExt.password) {
            throw new RuntimeException('define git user name - syncMaster.password');
        }

        UsernamePasswordCredentialsProvider user =
                new UsernamePasswordCredentialsProvider(syncMasterExt.username, syncMasterExt.password);

        logger.lifecycle("repos ${syncMasterExt.repos}");

        syncMasterExt.repos.each { repoItemInfo ->
            def repoDir = new File(repoItemInfo.name, syncMasterExt.slavesReposDir);
            logger.lifecycle("repoDir $repoDir exists ${repoDir.exists()}");
            if (!repoDir.exists()) {
                logger.lifecycle("clone from ${repoItemInfo.repository}");
                CloneCommand clone = Git.cloneRepository();
                clone.setBare(false);
                clone.setBranch(repoItemInfo.branch)
                clone.setDirectory(repoDir).setURI(repoItemInfo.repository);

                clone.setCredentialsProvider(user);
                clone.call();
            } else {
                logger.lifecycle("pull");
                Git.open(repoDir)
                        .pull()
                        .setCredentialsProvider(user)
                        .call()
                }
        }

    }
}
