package com.example.ahmed.appsquaretask;

/**
 * Created by ahmed on 5/1/2017.
 */

public class DataModel {

    private String repoName;
    private String description;
    private String ownerUserName;
    private String fork;
    private String repoHTMLURL;
    private String ownerHTMLURL;

    public DataModel(String repoName,String description,String ownerUserName,String fork,String repoHTMLURL
            ,String ownerHTMLURL){
        this.repoName=repoName;
        this.description=description;
        this.ownerUserName=ownerUserName;
        this.fork=fork;
        this.repoHTMLURL=repoHTMLURL;
        this.ownerHTMLURL=ownerHTMLURL;
    }

    public String getRepoHTMLURL() {
        return repoHTMLURL;
    }

    public void setRepoHTMLURL(String repoHTMLURL) {
        this.repoHTMLURL = repoHTMLURL;
    }

    public String getOwnerHTMLURL() {
        return ownerHTMLURL;
    }

    public void setOwnerHTMLURL(String ownerHTMLURL) {
        this.ownerHTMLURL = ownerHTMLURL;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public String getFork() {
        return fork;
    }

    public void setFork(String fork) {
        this.fork = fork;
    }
}
