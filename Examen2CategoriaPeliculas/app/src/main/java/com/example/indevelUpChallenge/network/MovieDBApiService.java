package com.example.indevelUpChallenge.network;

import com.example.indevelUpChallenge.model.MovieCreditsResponse;
import com.example.indevelUpChallenge.model.MovieResponse;
import com.example.indevelUpChallenge.model.MovieVideosResponse;

import io.reactivex.Single;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBApiService {

    @GET("3/movie/popular")
    Single<MovieResponse> getPopularMovies(@Query("api_key") String api_key,
                                           @Query("language") String language);


    @GET("3/movie/top_rated")
    Single<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key,
                                            @Query("language") String language);

    @GET("3/movie/upcoming")
    Single<MovieResponse> getUpcomingMovies(@Query("api_key") String api_key,
                                            @Query("language") String language);


    @GET("3/movie/{movie_id}/credits")
    Single<MovieCreditsResponse> getMovieCredits(@Path("movie_id") int movie_id,
                                                 @Query("api_key") String api_key);


    @GET("3/movie/{movie_id}/videos")
    Single<MovieVideosResponse> getVideos(@Path("movie_id") int movie_id,
                                          @Query("api_key") String api_key,
                                          @Query("language") String language);

    @GET("3/search/movie")
    Single<MovieResponse> searchMovie(@Query("api_key") String api_key,
                                      @Query("language") String language,
                                      @Query("query") String query);


}