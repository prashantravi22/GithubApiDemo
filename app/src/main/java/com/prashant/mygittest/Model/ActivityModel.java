package com.prashant.mygittest.Model;

/**
 * Created by ravi on 21-11-2017.
 */

public class ActivityModel {


    private String user;
    private String fullname;
    private String watcher;
    private String commit;
    private String image;
    private String repo;
    private String stargazers_count;
    private String fork_count;
    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(String stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getFork_count() {
        return fork_count;
    }

    public void setFork_count(String fork_count) {
        this.fork_count = fork_count;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getWatcher() {
        return watcher;
    }

    public void setWatcher(String watcher) {
        this.watcher = watcher;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
