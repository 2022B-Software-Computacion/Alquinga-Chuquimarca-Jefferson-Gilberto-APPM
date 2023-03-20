package com.example.indevelUpChallenge.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.model.Movie;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.indevelUpChallenge.utils.Constants.IMAGE_BACK_URL;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.MovieViewHolder> {


    private List<Movie> movieList;
    private listener listener;

    public MovieRecyclerAdapter(List<Movie> movieList, listener listener) {
        this.movieList = movieList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_cell, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);
        holder.bind(movie);
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageViewPosterCell)
        ImageView imageViewPosterCell;
        @BindView(R.id.textViewTitleCell)
        TextView textViewTitleCell;
        @BindView(R.id.textViewVoteCell)
        TextView textViewVoteCell;
        @BindView(R.id.progressBarCell)
        ProgressBar progressBarCell;


        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                Movie movie = movieList.get(getAdapterPosition());
                listener.movieAdapterListener(movie);
            });

        }


        public void bind(Movie movie) {
            if (movie.getBackdropPath() !=null) {
                String imageUrl = IMAGE_BACK_URL + movie.getBackdropPath();
                Glide.with(itemView).load(imageUrl).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBarCell.setVisibility(View.GONE);
                        imageViewPosterCell.setImageResource(R.drawable.ic_cine);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBarCell.setVisibility(View.GONE);
                        return false;
                    }
                }).into(imageViewPosterCell);
            } else {
                progressBarCell.setVisibility(View.GONE);
                imageViewPosterCell.setImageResource(R.drawable.ic_cine);
            }
            textViewTitleCell.setText(movie.getTitle());
            textViewVoteCell.setText(movie.getVoteAverage());

        }


    }


    public interface listener {
        void movieAdapterListener(Movie movie);
    }
}
