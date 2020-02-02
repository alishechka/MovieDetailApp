package com.example.moviedetailapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moviedetailapp.models.detailMovieModel.DetailMovieModel;
import com.example.moviedetailapp.network.MovieClient;
import com.example.moviedetailapp.network.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviedetailapp.network.Constants.API_KEY;
import static com.example.moviedetailapp.network.Constants.KEY_MOVIE_ID;
import static com.example.moviedetailapp.network.Constants.POSTER_PATH;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView poster;
    private TextView title, overview;
    private RatingBar rating;
    private static final String TAG = "MovieDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        poster = findViewById(R.id.iv_poster_detail);
        title = findViewById(R.id.tv_title_detail);
        overview = findViewById(R.id.tv_overview_detail);
        rating = findViewById(R.id.rb_rating_detail);

        Integer movieID = getIntent().getIntExtra(KEY_MOVIE_ID, 0);

        MovieClient client = RetrofitClient.getClient();
        Call<DetailMovieModel> call = client.getDetailMovieRepo(movieID, API_KEY);
        call.enqueue(new Callback<DetailMovieModel>() {
            @Override
            public void onResponse(Call<DetailMovieModel> call, Response<DetailMovieModel> response) {
                DetailMovieModel detailMovieModel = response.body();

                title.setText(detailMovieModel.getTitle());
                overview.setText(detailMovieModel.getOverview());
                float voteAverage = detailMovieModel.getVoteAverage() / 2;
                rating.setRating(voteAverage);
                Picasso.get().load(POSTER_PATH + detailMovieModel
                        .getPosterPath()).into(poster);

            }

            @Override
            public void onFailure(Call<DetailMovieModel> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });

    }
}
