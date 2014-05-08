package com.chaoslabgames.extensions

/**
 * Created by DES on 08.05.2014.
 */
class SyncMasterExtension {
    def File slavesReposDir;
    def List<RepoItem> repos = new ArrayList<>();

    def username;
    def password;

    def registerRepo(Closure ...configurators) {
        configurators.each { config ->
            RepoItem item = new RepoItem();
            item.with config;
            repos.add item ;
        };
    }
}
