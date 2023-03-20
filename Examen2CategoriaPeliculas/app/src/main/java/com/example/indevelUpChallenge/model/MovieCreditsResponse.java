package com.example.indevelUpChallenge.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieCreditsResponse {

    @SerializedName("id")
    private Integer idCredits;
    @SerializedName("cast")
    private List<Cast> cast;
    @SerializedName("crew")
    private List<Crew> crew;

    public MovieCreditsResponse() {
    }

    public MovieCreditsResponse(Integer idCredits, List<Cast> cast, List<Crew> crew) {
        this.idCredits = idCredits;
        this.cast = cast;
        this.crew = crew;
    }

    public Integer getIdCredits() {
        return idCredits;
    }

    public void setIdCredits(Integer idCredits) {
        this.idCredits = idCredits;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }
}
