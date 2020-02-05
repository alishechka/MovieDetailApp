package com.example.moviedetailapp.moviedetail;

import com.example.moviedetailapp.models.detailMovieModel.DetailMovieModel;
import com.example.moviedetailapp.network.MovieClient;
import com.example.moviedetailapp.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.moviedetailapp.network.Constants.API_KEY;

public class MovieDetailPresenter implements DetailPresenterInterface {
    private DetailVewInterface view;

    public MovieDetailPresenter(DetailVewInterface view) {
        this.view = view;
    }

    @Override
    public void downloadData(Integer movieId) {
        MovieClient client = RetrofitClient.getClient();
        Call<DetailMovieModel> call = client.getDetailMovieRepo(movieId, API_KEY);
        call.enqueue(new Callback<DetailMovieModel>() {
            @Override
            public void onResponse(Call<DetailMovieModel> call, Response<DetailMovieModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    DetailMovieModel detailMovieModel = response.body();
                    view.showData(detailMovieModel);

                    view.hideProgressBar();
                } else {
                    view.hideProgressBar();
                    view.showError(response.message());
                }

            }

            @Override
            public void onFailure(Call<DetailMovieModel> call, Throwable t) {
                view.hideProgressBar();
                view.showError(t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyCalled() {
        view = null;
    }
}
