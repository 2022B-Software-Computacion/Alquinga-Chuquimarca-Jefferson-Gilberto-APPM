package com.example.indevelUpChallenge;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.indevelUpChallenge.dataSource.MovieCreditsRepository;
import com.example.indevelUpChallenge.dataSource.MovieVideosRepository;
import com.example.indevelUpChallenge.model.MovieCreditsResponse;
import com.example.indevelUpChallenge.model.MovieVideosResponse;
import com.example.indevelUpChallenge.network.RetrofitInstance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class MovieVideosRepositoryTest {


    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    public RetrofitInstance retrofitInstance;


    @InjectMocks
    public MovieVideosRepository movieVideosRepository = new MovieVideosRepository();;
    private Single<MovieVideosResponse> responseSingleTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();

    }


    @Test
    public void testSuccess() {
        MovieVideosResponse response = new MovieVideosResponse();
        responseSingleTest = Single.just(response);
        Mockito.when(retrofitInstance.getMovieVideos(0)).thenReturn(responseSingleTest);
        movieVideosRepository.refreshGetVideos(0);
        Assert.assertEquals(response, movieVideosRepository.data.getValue());
        Assert.assertEquals(false, movieVideosRepository.error.getValue());
    }

    @Test
    public void testError() {
        responseSingleTest = Single.error(Throwable::new);
        Mockito.when(retrofitInstance.getMovieVideos(0)).thenReturn(responseSingleTest);
        movieVideosRepository.refreshGetVideos(0);
        Assert.assertEquals(true, movieVideosRepository.error.getValue());
    }

    private void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run,true);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }


}
