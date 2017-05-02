package com.example.ahmed.appsquaretask;

/**
 * Created by ahmed on 5/1/2017.
 */

public class DataModel {

    private String repoName;
    private String description;
    private String ownerUserName;

    public DataModel(String repoName,String description,String ownerUserName){
        this.repoName=repoName;
        this.description=description;
        this.ownerUserName=ownerUserName;
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
}
