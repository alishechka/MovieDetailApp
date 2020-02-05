package com.example.moviedetailapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedetailapp.models.popularMovieModel.PopularMovieModel;
import com.example.moviedetailapp.models.popularMovieModel.Result;
import com.example.moviedetailapp.moviedetail.MovieDetailActivity;
import com.example.moviedetailapp.network.MovieClient;
import com.example.moviedetailapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviedetailapp.network.Constants.API_KEY;
import static com.example.moviedetailapp.network.Constants.KEY_MOVIE_ID;

public class PopularMoviesActivity extends AppCompatActivity implements OnItemClick {
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private Button back, next;
    private int pageNumber = 1;

    private static final String TAG = "PopularMoviesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);

        back = findViewById(R.id.btn_back);
        next = findViewById(R.id.btn_next);
        recyclerView = findViewById(R.id.rv_display);

        callMethod(pageNumber);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pageNumber == 1) {
                    Toast.makeText(PopularMoviesActivity.this, "this is the last page", Toast.LENGTH_LONG).show();
                } else {
                    pageNumber--;
                    callMethod(pageNumber);
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNumber++;
                callMethod(pageNumber);
            }
        });

    }

    public void callMethod(int pageNumber) {
        MovieClient client = RetrofitClient.getClient();
        Call<PopularMovieModel> call = client.getPopularMovieRepo(API_KEY, pageNumber);

        Log.i(TAG, "onCreate: " + call.toString());
        Log.i(TAG, "onCreate: " + call.request().url().toString());

        call.enqueue(new Callback<PopularMovieModel>() {
            @Override
            public void onResponse(Call<PopularMovieModel> call, Response<PopularMovieModel> response) {
                PopularMovieModel popularMovieModel = response.body();
                Log.i(TAG, "onResponse: " + response);

                movieAdapter = new MovieAdapter(popularMovieModel, PopularMoviesActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(PopularMoviesActivity.this));
                recyclerView.setAdapter(movieAdapter);
            }

            @Override
            public void onFailure(Call<PopularMovieModel> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(Result result) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra(KEY_MOVIE_ID, result.getId());
        startActivity(intent);


    }
}
