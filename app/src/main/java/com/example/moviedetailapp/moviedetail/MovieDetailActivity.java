package com.example.moviedetailapp.moviedetail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.moviedetailapp.R;
import com.example.moviedetailapp.models.detailMovieModel.DetailMovieModel;
import com.squareup.picasso.Picasso;

import static com.example.moviedetailapp.network.Constants.KEY_MOVIE_ID;
import static com.example.moviedetailapp.network.Constants.POSTER_PATH;

public class MovieDetailActivity extends AppCompatActivity implements DetailVewInterface {
    private ImageView poster;
    private TextView title, overview;
    private RatingBar rating;
    private static final String TAG = "MovieDetailActivity";
    private DetailPresenterInterface presenter;
    private ProgressBar bpLoading;
    private ConstraintLayout detailContainer;
    private LinearLayout errorContainer;
    private TextView errorMessage;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        bpLoading = findViewById(R.id.pb_loading);
        detailContainer = findViewById(R.id.detail_container);
        errorContainer = findViewById(R.id.error_container);
        errorMessage = findViewById(R.id.tv_message_error);
        btnRetry = findViewById(R.id.btn_retry);

        poster = findViewById(R.id.iv_poster_detail);
        title = findViewById(R.id.tv_title_detail);
        overview = findViewById(R.id.tv_overview_detail);
        rating = findViewById(R.id.rb_rating_detail);
        presenter = new MovieDetailPresenter(this);

        Integer movieID = getIntent().getIntExtra(KEY_MOVIE_ID, 0);
        presenter.downloadData(movieID);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError();
                showProgressBar();
                presenter.downloadData(movieID);
            }
        });

    }

    @Override
    public void showData(DetailMovieModel model) {
        title.setText(model.getTitle());
        overview.setText(model.getOverview());
        float voteAverage = model.getVoteAverage() / 2;
        rating.setRating(voteAverage);
        Picasso.get().load(POSTER_PATH + model
                .getPosterPath()).into(poster);
        detailContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showProgressBar() {
        bpLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        bpLoading.setVisibility(View.GONE);

    }

    @Override
    public void showError(String message) {
        errorMessage.setText(message);
        errorContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideError() {
        errorContainer.setVisibility(View.GONE);

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyCalled();
        super.onDestroy();
    }
}
