package com.example.indevelUpChallenge.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.indevelUpChallenge.dataSource.MovieCreditsRepository;
import com.example.indevelUpChallenge.model.MovieCreditsResponse;

public class MovieCreditsViewModel extends AndroidViewModel {

    public MutableLiveData<MovieCreditsResponse> dataCredits;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> error;
    private MovieCreditsRepository movieCreditsRepository;

    public MovieCreditsViewModel(@NonNull Application application) {
        super(application);
        movieCreditsRepository = new MovieCreditsRepository();
    }

    public void getMovieCredits(int id){
        movieCreditsRepository.refreshGetMovieCredits(id);
        dataCredits = movieCreditsRepository.getDataCredits();
        loading = movieCreditsRepository.getLoading();
        error = movieCreditsRepository.getError();
    }
}
