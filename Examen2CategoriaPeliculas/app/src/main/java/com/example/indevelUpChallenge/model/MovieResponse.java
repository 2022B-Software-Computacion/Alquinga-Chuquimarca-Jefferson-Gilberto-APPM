package com.example.indevelUpChallenge.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class MovieResponse implements Serializable {


    @SerializedName("results")
    private List<Movie> movieResult;

    public MovieResponse() {
    }

    public MovieResponse(List<Movie> movieResult) {
        this.movieResult = movieResult;
    }

    public List<Movie> getMovieResult() {
        return movieResult;
    }

    public void setMovieResult(List<Movie> peliculasResult) {
        this.movieResult = peliculasResult;
    }


    public void setType(String type) {
        for (Movie movie : movieResult){
            movie.setType(type);
        }
    }
}