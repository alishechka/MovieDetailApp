package com.example.moviedetailapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.moviedetailapp.models.popularMovieModel.PopularMovieModel;
import com.example.moviedetailapp.models.popularMovieModel.Result;
import com.example.moviedetailapp.network.MovieClient;
import com.example.moviedetailapp.network.RetrofitClient;


import static com.example.moviedetailapp.network.Constants.API_KEY;

public class PopularMoviesActivity extends AppCompatActivity implements OnItemClick {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
//    private PopularMovieModel popularMovieModel;
    private static final String TAG = "PopularMoviesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        recyclerView = findViewById(R.id.rv_display);

        MovieClient client = RetrofitClient.getClient();
        Call<PopularMovieModel> call = client.getPopularMovieRepo(API_KEY);
        Log.i(TAG, "onCreate: "+call.toString());
        Log.i(TAG, "onCreate: "+call.request().url().toString());
        call.enqueue(new Callback<PopularMovieModel>() {
            @Override
            public void onResponse(Call<PopularMovieModel> call, Response<PopularMovieModel> response) {
                PopularMovieModel popularMovieModel = response.body();
                Log.i(TAG, "onResponse: "+response);

                movieAdapter = new MovieAdapter(popularMovieModel, PopularMoviesActivity.this);

                recyclerView.setLayoutManager(new LinearLayoutManager(PopularMoviesActivity.this));
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PopularMovieModel> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });


    }

    @Override
    public void onClick(Result result) {


    }
}
