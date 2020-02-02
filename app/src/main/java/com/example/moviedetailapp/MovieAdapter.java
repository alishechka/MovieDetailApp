package com.example.moviedetailapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedetailapp.models.popularMovieModel.PopularMovieModel;
import com.example.moviedetailapp.models.popularMovieModel.Result;
import com.squareup.picasso.Picasso;

import java.util.List;
import static com.example.moviedetailapp.network.Constants.POSTER_PATH;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.myViewHolder> {
    private PopularMovieModel popularMovieModels;
    private OnItemClick onItemClick;

    public MovieAdapter(PopularMovieModel popularMovieModels, OnItemClick onItemClick) {
        this.popularMovieModels = popularMovieModels;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.activity_movie_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        List<Result> resultList = popularMovieModels.getResults();
        Result result = null;
        if (position < resultList.size()) {
            result = popularMovieModels.getResults().get(position);

            float voteAverage = result.getVoteAverage().floatValue() / 2;

            holder.ratingItem.setRating(voteAverage);
            holder.titleItem.setText(result.getTitle());
            Picasso.get().load(POSTER_PATH + result.getPosterPath()).into(holder.posterItem);
            holder.bind(result);
        }
    }

    @Override
    public int getItemCount() {
        return popularMovieModels.getResults().size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterItem;
        private TextView titleItem;
        private RatingBar ratingItem;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            posterItem = itemView.findViewById(R.id.iv_poster_item);
            titleItem = itemView.findViewById(R.id.tv_title_item);
            ratingItem = itemView.findViewById(R.id.rb_rating_item);
        }


        public void bind(final Result result) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.onClick(result);
                }
            });
        }
    }

}
