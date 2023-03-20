package com.example.indevelUpChallenge.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.indevelUpChallenge.model.Movie;

import java.util.ArrayList;
import java.util.List;


public class MovieUtils {

    public static String getGenres(Movie result) {
        List<String> genreList = result.getGenreIds();
        List<String> stringGenres = new ArrayList<>();
        String allGenres = "";
        for (String stringGen : genreList) {
            stringGenres.add(genreConverterToString(stringGen));
        }
        for (String stringGenre : stringGenres) {
            allGenres += stringGenre + " | ";
        }
        return allGenres;
    }

    private static String genreConverterToString(String id) {
        String genre = "";
        switch (id) {
            case ("28"):
                genre = "Accion";
                break;
            case ("12"):
                genre = "Aventuras";
                break;
            case ("35"):
                genre = "Comedia";
                break;
            case ("80"):
                genre = "Crimen";
                break;
            case ("99"):
                genre = "Documental";
                break;
            case ("18"):
                genre = "Drama";
                break;
            case ("10751"):
                genre = "Familia";
                break;
            case ("14"):
                genre = "Fantasia";
                break;
            case ("36"):
                genre = "Historia";
                break;
            case ("27"):
                genre = "Terror";
                break;
            case ("10402"):
                genre = "Musical";
                break;
            case ("9648"):
                genre = "Misterio";
                break;
            case ("10749"):
                genre = "Romance";
                break;
            case ("878"):
                genre = "Ciencia Ficcion";
                break;
            case ("10770"):
                genre = "Pelicula de TV";
                break;
            case ("53"):
                genre = "Thriller";
                break;
            case ("10752"):
                genre = "Guerra";
                break;
            case ("37"):
                genre = "Western";
                break;

        }
        return genre;
    }

    public static boolean isInternetAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();


    }
}
