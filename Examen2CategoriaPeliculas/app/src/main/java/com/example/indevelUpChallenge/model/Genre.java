package com.example.indevelUpChallenge.model;


import com.google.gson.annotations.SerializedName;

public class Genre {

    @SerializedName("name")
    private String genreName;

    public Genre() {
    }

    public Genre(String genreName) {
        this.genreName = genreName;
    }



    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

}

