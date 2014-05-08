package com.chaoslabgames.plugins

import com.chaoslabgames.extensions.RepoItem
import com.chaoslabgames.tasks.UpdateTask
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

import static junit.framework.Assert.assertEquals
import static junit.framework.Assert.assertTrue

/**
 * Created by DES on 08.05.2014.
 */
class SyncMasterPluginTest {
    @Test
    public void testInit() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'sync-master'

        assertTrue(project.tasks.updateSlaves instanceof UpdateTask)
    }

    @Test
    public void testRepoCollection() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'sync-master'

        project.syncMaster {
            slavesReposDir project.file("../slaves-repos");
            registerRepo (
                 {
                     repository = 'https://github.com/user/test1.git'
                     name = 'test1'
                     branch = 'master'
                 },
                 {
                     repository = 'https://github.com/user/test2.git'
                     name = 'test2'
                     branch = 'master2'
                 })
        }
        def RepoItem repoItem = project.syncMaster.repos[1];
        assertEquals(project.syncMaster.repos.size, 2);
        assertEquals(repoItem.repository, 'https://github.com/user/test2.git');
        assertEquals(repoItem.name, "test2");
        assertEquals(repoItem.branch, "master2");
    }
}
