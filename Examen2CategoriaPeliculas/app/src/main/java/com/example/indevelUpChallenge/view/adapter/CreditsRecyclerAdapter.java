package com.example.indevelUpChallenge.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.indevelUpChallenge.R;
import com.example.indevelUpChallenge.model.Cast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.indevelUpChallenge.utils.Constants.IMAGE_CAST_URL;

public class CreditsRecyclerAdapter extends RecyclerView.Adapter<CreditsRecyclerAdapter.CreditsViewHolder> {

    private List<Cast> castList;

    public CreditsRecyclerAdapter(List<Cast> castList) {
        this.castList = castList;
    }


    @NonNull
    @Override
    public CreditsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.credits_cell, parent, false);
        return new CreditsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsViewHolder holder, int position) {
        Cast cast = this.castList.get(position);
        holder.bind(cast);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }


    static class CreditsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewCreditsCell)
        ImageView imageViewCreditsCell;
        @BindView(R.id.textViewNameCreditsCell)
        TextView textViewNameCreditsCell;

        public CreditsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Cast cast) {
            textViewNameCreditsCell.setText(cast.getCastName());

            if(cast.getProfilePath()==null){
                imageViewCreditsCell.setImageResource(R.drawable.ic_cast);
            }else{
                String url = IMAGE_CAST_URL + cast.getProfilePath();
                Glide.with(itemView).load(url).into(imageViewCreditsCell);
            }
        }
    }
}
