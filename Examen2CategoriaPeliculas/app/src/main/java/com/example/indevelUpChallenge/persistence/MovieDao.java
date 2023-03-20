package com.example.indevelUpChallenge.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import com.example.indevelUpChallenge.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMoviesList(List<Movie> moviesList);

    @Query("DELETE FROM movie_table WHERE type='popular'")
    void deletePopMovies();

    @Query("DELETE FROM movie_table WHERE type='top_rated'")
    void deleteTopMovies();

    @Query("DELETE FROM movie_table WHERE type='upcoming'")
    void deleteUpcomingMovies();

    @Query("SELECT * FROM movie_table WHERE type='popular'")
    List<Movie> getPopMovies();

    @Query("SELECT * FROM movie_table WHERE type='top_rated'")
    List<Movie> getTopMovies();

    @Query("SELECT * FROM movie_table WHERE type='upcoming'")
    List<Movie> getUpcomingMovies();

    @Query("SELECT * FROM movie_table WHERE type='popular' AND title LIKE :search")
    List<Movie> searchPopMovie(String search);

    @Query("SELECT * FROM movie_table WHERE type='top_rated' AND title LIKE :search")
    List<Movie> searchTopMovie(String search);

    @Query("SELECT * FROM movie_table WHERE type='upcoming' AND title LIKE :search")
    List<Movie> searchUpcomingMovie(String search);


}
