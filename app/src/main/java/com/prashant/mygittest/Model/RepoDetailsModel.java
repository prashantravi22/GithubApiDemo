package com.prashant.mygittest.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by ravi on 24-11-2017.
 */

public class RepoDetailsModel implements Parcelable {

    private String name=null;
    private String projectlink=null;
    private String description;
    private String imageurl;
    ArrayList<RepoDetailsModel> repoDetailsModels;


    public  RepoDetailsModel(){

    }

    public  RepoDetailsModel(String projectlink,String imageurl){

        this.projectlink=projectlink;

        this.imageurl=imageurl;

    }

  public  RepoDetailsModel(String name,String projectlink,String description,String imageurl  ){
        this.name=name;
        this.projectlink=projectlink;
        this.description=description;
        this.imageurl=imageurl;
       // this.repoDetailsModels=repoDetailsModels;

    }

    public ArrayList<RepoDetailsModel> getRepoDetailsModels() {
        return repoDetailsModels;
    }

    public void setRepoDetailsModels(ArrayList<RepoDetailsModel> repoDetailsModels) {
        this.repoDetailsModels = repoDetailsModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectlink() {
        return projectlink;
    }

    public void setProjectlink(String projectlink) {
        this.projectlink = projectlink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(getDescription());
        dest.writeString(getImageurl());
        dest.writeString(getProjectlink());
        dest.writeString(getName());


    }

    public RepoDetailsModel(Parcel in) {

        imageurl = in.readString();
        description = in.readString();
        projectlink = in.readString();
        name = in.readString();


    }
    public static final Parcelable.Creator<RepoDetailsModel> CREATOR = new Parcelable.Creator<RepoDetailsModel>() {
        public RepoDetailsModel createFromParcel(Parcel in) {
            return new RepoDetailsModel(in);
        }

        public RepoDetailsModel[] newArray(int size) {
            return new RepoDetailsModel[size];
        }
    };

}
