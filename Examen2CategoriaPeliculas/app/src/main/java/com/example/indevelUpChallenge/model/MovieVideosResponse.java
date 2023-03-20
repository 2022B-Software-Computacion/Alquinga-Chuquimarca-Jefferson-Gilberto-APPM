package com.example.indevelUpChallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideosResponse {


    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<MovieVideo> results;

    public MovieVideosResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieVideo> getResults() {
        return results;
    }

    public void setResults(List<MovieVideo> results) {
        this.results = results;
    }
}
