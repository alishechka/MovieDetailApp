package com.example.moviedetailapp.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import com.example.moviedetailapp.models.detailMovieModel.DetailMovieModel;
import com.example.moviedetailapp.models.popularMovieModel.PopularMovieModel;

public interface MovieClient {

    @GET("movie/popular?")
    Call<PopularMovieModel> getPopularMovieRepo(@Query("api_key") String key);

    @GET("movie/{movie_id}")
    Call<DetailMovieModel> getDetailMovieRepo(@Path("movie_id") Integer movieId, @Query("api_key") String key);
}
