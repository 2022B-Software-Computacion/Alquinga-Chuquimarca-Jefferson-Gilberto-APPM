package com.example.indevelUpChallenge;

import android.app.Application;
import android.test.mock.MockContext;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.indevelUpChallenge.dataSource.MovieResponseRepository;
import com.example.indevelUpChallenge.persistence.MovieDao;
import com.example.indevelUpChallenge.persistence.MovieDatabase;
import com.example.indevelUpChallenge.model.MovieResponse;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieResponseRepositoryTest {


    private MovieDao movieDao;
    private Single<MovieResponse> responseSingleTest;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();


    @InjectMocks
    public MovieDatabase movieDatabase;

    @Mock
    MockContext contextMock = mock(MockContext.class);

    @Mock
    Application application = mock(Application.class);

    @Mock
    public RetrofitInstance retrofitInstance;


    @InjectMocks
    public MovieResponseRepository movieResponseRepository = new MovieResponseRepository(application);


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();
        movieDao = mock(MovieDao.class);

        when(contextMock.getApplicationContext()).thenReturn(contextMock);
    }


    @Test
    public void testSuccess() {
        movieDatabase = MovieDatabase.getInstance(contextMock);
        MovieResponse response = new MovieResponse();
        responseSingleTest = Single.just(response);
        Mockito.when(retrofitInstance.getPopularMovies()).thenReturn(responseSingleTest);
        movieResponseRepository.refreshGetPopMovieResult();
        Assert.assertEquals(response, movieResponseRepository.data.getValue());
        Assert.assertEquals(false, movieResponseRepository.error.getValue());
    }

    @Test
    public void testError() {
        responseSingleTest = Single.error(Throwable::new);
        Mockito.when(retrofitInstance.getPopularMovies()).thenReturn(responseSingleTest);
        movieResponseRepository.refreshGetPopMovieResult();
        Assert.assertEquals(true, movieResponseRepository.error.getValue());
    }

    private void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run, true);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }


}
