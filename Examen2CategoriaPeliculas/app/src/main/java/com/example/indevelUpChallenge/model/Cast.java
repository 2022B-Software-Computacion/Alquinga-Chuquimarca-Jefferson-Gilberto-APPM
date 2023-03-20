package com.example.indevelUpChallenge.model;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;

import com.google.gson.annotations.SerializedName;

public class Cast {

    @SerializedName("name")
    private String castName;
    @ColumnInfo(name="profile_path")
    @SerializedName("profile_path")
    private String profilePath;

    public Cast() {
    }

    public Cast(String castName, String profilePath) {
        this.castName = castName;
        this.profilePath = profilePath;
    }




    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }
}
