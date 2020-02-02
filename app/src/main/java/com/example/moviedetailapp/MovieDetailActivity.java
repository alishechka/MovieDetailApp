package com.example.moviedetailapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.moviedetailapp.models.detailMovieModel.DetailMovieModel;
import com.example.moviedetailapp.models.popularMovieModel.PopularMovieModel;
import com.example.moviedetailapp.network.MovieClient;
import com.example.moviedetailapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviedetailapp.network.Constants.API_KEY;
import static com.example.moviedetailapp.network.Constants.KEY_MOVIE_ID;

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

        Integer movieID= getIntent().getIntExtra(KEY_MOVIE_ID,0);

        MovieClient client = RetrofitClient.getClient();
        Call<DetailMovieModel> call = client.getDetailMovieRepo(movieID,API_KEY);
        Log.i(TAG, "onCreate: " + call.request().url().toString());
        call.enqueue(new Callback<DetailMovieModel>() {
            @Override
            public void onResponse(Call<DetailMovieModel> call, Response<DetailMovieModel> response) {
                DetailMovieModel detailMovieModel=response.body();
                Log.i(TAG, "onResponse: ");
                Log.i(TAG, "onResponse: "+detailMovieModel.getOverview());
            }

            @Override
            public void onFailure(Call<DetailMovieModel> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });

    }
}
