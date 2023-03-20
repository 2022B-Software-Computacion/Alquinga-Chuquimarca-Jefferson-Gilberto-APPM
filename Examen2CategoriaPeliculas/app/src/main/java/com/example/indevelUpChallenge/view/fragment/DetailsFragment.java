package com.example.indevelUpChallenge.view.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.model.Cast;
import com.example.indevelUpChallenge.model.Crew;
import com.example.indevelUpChallenge.model.Movie;
import com.example.indevelUpChallenge.utils.MovieUtils;
import com.example.indevelUpChallenge.view.adapter.CreditsRecyclerAdapter;
import com.example.indevelUpChallenge.viewModel.MovieCreditsViewModel;
import com.example.indevelUpChallenge.viewModel.MovieVideosViewModel;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.indevelUpChallenge.utils.Constants.GOOGLE_KEY;
import static com.example.indevelUpChallenge.utils.Constants.IMAGE_URL_BACK;
import static com.example.indevelUpChallenge.utils.Constants.IMAGE_URL_POSTER;
import static com.example.indevelUpChallenge.utils.Constants.KEY_ID;


public class DetailsFragment extends Fragment {

    private static final String TAG = "Details Fragment: ";

    private YouTubePlayerSupportFragmentX youTubePlayerSupportFragmentX;
    private MovieVideosViewModel movieVideosViewModel;

    private Movie movie;
    private MovieCreditsViewModel movieCreditsViewModel;

    @BindView(R.id.imageViewBackDetails)
    ImageView imageViewBackDetails;
    @BindView(R.id.imageViewPosterDetails)
    ImageView imageViewPosterDetails;
    @BindView(R.id.textViewMovieTitleDetails)
    TextView textViewMovieTitleDetails;
    @BindView(R.id.textViewYearDetails)
    TextView textViewYearDetails;
    @BindView(R.id.textViewOverviewDetails)
    TextView textViewOverviewDetails;
    @BindView(R.id.textViewVoteDetails)
    TextView textViewVoteDetails;
    @BindView(R.id.recyclerCastDetails)
    RecyclerView recyclerCastDetails;
    @BindView(R.id.textViewGenresDetails)
    TextView textViewGenresDetails;
    @BindView(R.id.textViewDirectorTitle)
    TextView textViewDirectorTitle;
    @BindView(R.id.textViewDirector)
    TextView textViewDirector;
    @BindView(R.id.youtubeFragmentContainerDetails)
    ConstraintLayout youtubeFragmentContainerDetails;
    @BindView(R.id.textViewCastTitleDetails)
    TextView textViewCastTitleDetails;
    @BindView(R.id.textViewTrailerTitleDetails)
    TextView textViewTrailerTitleDetails;
    @BindView(R.id.progressBarDetails)
    ProgressBar progressBarDetails;




    public DetailsFragment() {
        // Required empty public constructor
    }


    public static DetailsFragment newInstance(Movie movie) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_ID, movie);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(KEY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);


        youTubePlayerSupportFragmentX = (YouTubePlayerSupportFragmentX) getChildFragmentManager().findFragmentById(R.id.youtubeFragmentDetails);
        movieVideosViewModel = new ViewModelProvider(this).get(MovieVideosViewModel.class);
        movieCreditsViewModel = new ViewModelProvider(this).get(MovieCreditsViewModel.class);

        getVideoTrailer(movie.getId());
        bindDetails(movie, container.getContext());


        return view;
    }


    private void bindDetails(Movie movie, Context context) {

        String url = IMAGE_URL_BACK + movie.getBackdropPath();
        Glide.with(context).load(url).into(imageViewBackDetails);
        textViewGenresDetails.setText(MovieUtils.getGenres(movie));
        url = IMAGE_URL_POSTER + movie.getPosterPath();
        Glide.with(context).load(url).into(imageViewPosterDetails);
        textViewMovieTitleDetails.setText(movie.getTitle());
        String year = "(" + movie.getReleaseDate().substring(0, 4) + ")";
        textViewYearDetails.setText(year);
        textViewVoteDetails.setText(movie.getVoteAverage());
        textViewOverviewDetails.setText(movie.getOverview());
        getMovieCredits();

    }

    private void getMovieCredits() {
        movieCreditsViewModel.getMovieCredits(movie.getId());
        getMovieCreditsObserver();
    }

    private void getMovieCreditsObserver() {
        movieCreditsViewModel.dataCredits.observe(getViewLifecycleOwner(), movieCreditsResult -> {
            String director = "";
            for (Crew crew : movieCreditsResult.getCrew()) {
                if (crew.getJob().equals("Director")) director = crew.getJobName();
            }
            textViewDirector.setText(director);
            List<Cast> castList = movieCreditsResult.getCast();
            CreditsRecyclerAdapter creditsRecyclerAdapter = new CreditsRecyclerAdapter(castList);
            setCreditsRecycler(creditsRecyclerAdapter);
        });

        movieCreditsViewModel.error.observe(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                Log.e(TAG, "Error de conexion");
                textViewCastTitleDetails.setVisibility(View.GONE);
                textViewDirectorTitle.setVisibility(View.GONE);
                progressBarDetails.setVisibility(View.GONE);
                textViewTrailerTitleDetails.setVisibility(View.GONE);
            }
        });
    }

    private void setCreditsRecycler(CreditsRecyclerAdapter creditsRecyclerAdapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerCastDetails.setLayoutManager(layoutManager);
        recyclerCastDetails.setHasFixedSize(true);
        recyclerCastDetails.setItemViewCacheSize(10);
        recyclerCastDetails.setAdapter(creditsRecyclerAdapter);
    }



    private void getVideoTrailer(Integer id) {
        movieVideosViewModel.getVideos(id);
        getVideoObserver();
    }

    private void getVideoObserver() {
        movieVideosViewModel.data.observe(getViewLifecycleOwner(), movieVideosResponse -> {
            String path = movieVideosResponse.getResults().get(0).getKey();
            initYoutube(path);
        });
    }

    private void initYoutube(String path) {
        progressBarDetails.setVisibility(View.VISIBLE);
        youTubePlayerSupportFragmentX.initialize(GOOGLE_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    textViewTrailerTitleDetails.setVisibility(View.VISIBLE);
                    youtubeFragmentContainerDetails.setVisibility(View.VISIBLE);
                    youTubePlayer.cueVideo(path);
                    progressBarDetails.setVisibility(View.GONE);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(Objects.requireNonNull(getActivity()), 1).show();
                } else {
                    Log.e(TAG, "Error al inicializar Youtube: " + youTubeInitializationResult.toString());
                }
                youtubeFragmentContainerDetails.setVisibility(View.GONE);
                progressBarDetails.setVisibility(View.GONE);
            }
        });
    }


}