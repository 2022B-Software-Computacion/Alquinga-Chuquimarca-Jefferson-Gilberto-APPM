package com.example.indevelUpChallenge.network;


import com.example.indevelUpChallenge.model.MovieCreditsResponse;
import com.example.indevelUpChallenge.model.MovieResponse;
import com.example.indevelUpChallenge.model.MovieVideosResponse;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.indevelUpChallenge.utils.Constants.API_KEY;
import static com.example.indevelUpChallenge.utils.Constants.BASE_URL;
import static com.example.indevelUpChallenge.utils.Constants.LANGUAGE;


public class RetrofitInstance {


    private static RetrofitInstance retrofitInstance;
    private Retrofit retrofit;


    private RetrofitInstance(String url) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    public static synchronized RetrofitInstance getInstanceMovie() {
        if (retrofitInstance == null) {
            retrofitInstance = new RetrofitInstance(BASE_URL);
        }
        return retrofitInstance;
    }



    public Single<MovieResponse> getPopularMovies() {
        return retrofit.create(MovieDBApiService.class).getPopularMovies(API_KEY, LANGUAGE);
    }

    public Single<MovieResponse> getTopRatedMovies() {
        return retrofit.create(MovieDBApiService.class).getTopRatedMovies(API_KEY, LANGUAGE);
    }

    public Single<MovieResponse> getUpcomingMovies() {
        return retrofit.create(MovieDBApiService.class).getUpcomingMovies(API_KEY, LANGUAGE);
    }

    public Single<MovieCreditsResponse> getMovieCredits(int id){
        return retrofit.create(MovieDBApiService.class).getMovieCredits(id, API_KEY);
    }

    public Single<MovieVideosResponse> getMovieVideos(int id){
        return retrofit.create(MovieDBApiService.class).getVideos(id, API_KEY, LANGUAGE);
    }

    public Single<MovieResponse> searchMovie(String query){
        return retrofit.create(MovieDBApiService.class).searchMovie(API_KEY, LANGUAGE, query);
    }



}
