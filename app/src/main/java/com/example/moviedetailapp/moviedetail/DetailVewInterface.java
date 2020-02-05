package com.example.moviedetailapp.moviedetail;

import com.example.moviedetailapp.models.detailMovieModel.DetailMovieModel;

public interface DetailVewInterface {
    void showData(DetailMovieModel model);
    void showProgressBar();
    void hideProgressBar();
    void showError(String message);
    void hideError();

}
