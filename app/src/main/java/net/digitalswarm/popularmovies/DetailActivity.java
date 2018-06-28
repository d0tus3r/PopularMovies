package net.digitalswarm.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import net.digitalswarm.popularmovies.adapters.MovieReviewRVAdapter;
import net.digitalswarm.popularmovies.adapters.MovieTrailerRVAdapter;
import net.digitalswarm.popularmovies.data.AppDatabase;
import net.digitalswarm.popularmovies.data.FavoriteEntry;
import net.digitalswarm.popularmovies.models.Movie;
import net.digitalswarm.popularmovies.models.Review;
import net.digitalswarm.popularmovies.models.Trailer;
import net.digitalswarm.popularmovies.utils.MovieJsonUtil;
import net.digitalswarm.popularmovies.utils.NetUtils;
import org.json.JSONException;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static net.digitalswarm.popularmovies.utils.NetUtils.internetAccess;

public class DetailActivity extends AppCompatActivity implements MovieTrailerRVAdapter.MovieTrailerRVAdapterClickListener {

    private static final String TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";
    private ArrayList<Review> reviewList;
    private MovieReviewRVAdapter reviewRVAdapter;
    private ArrayList<Trailer> trailerList;
    private MovieTrailerRVAdapter trailerRVAdapter;
    private GridLayoutManager gridLayoutTrailer;
    private GridLayoutManager gridLayoutReview;
    private TextView reviewTitleTV;
    private TextView trailerTitleTV;
    private RecyclerView trailerRV;
    private RecyclerView reviewRV;
    private Movie currentMovie;
    private ImageButton favButton;
    private FavoriteEntry favMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //pull intent from main activity
        Intent intentFromMain = getIntent();
        currentMovie = intentFromMain.getParcelableExtra("Movie");
        String currentMovieId = currentMovie.getId();
        favMovie = MainActivity.favDb.favoriteDao().selectMovieById(currentMovieId);

        //Trailer recycler view
        trailerRV = findViewById(R.id.trailer_recycler_view);
        trailerList = new ArrayList<>();
        trailerRVAdapter = new MovieTrailerRVAdapter(this, trailerList, this);
        trailerRV.setAdapter(trailerRVAdapter);
        gridLayoutTrailer = new GridLayoutManager(this, 1);
        trailerRV.setLayoutManager(gridLayoutTrailer);
        trailerTitleTV = findViewById(R.id.trailer_title_tv);

        //Review recycler view init
        reviewRV = findViewById(R.id.review_recycler_view);
        reviewList = new ArrayList<>();
        reviewRVAdapter = new MovieReviewRVAdapter(this, reviewList);
        reviewRV.setAdapter(reviewRVAdapter);
        gridLayoutReview = new GridLayoutManager(this, 1);
        reviewRV.setLayoutManager(gridLayoutReview);
        reviewTitleTV = findViewById(R.id.review_title_view);

        //display data
        displayMovieDetails(currentMovie);
        URL reviewURL = NetUtils.genReviewUrl(currentMovieId);
        if (internetAccess(this)) {
            new GetReviews().execute(reviewURL);
        } else {
            Toast.makeText(this, "Please connect to internet and try again!", Toast.LENGTH_LONG).show();
        }
        URL trailerURL = NetUtils.genTrailerUrl(currentMovieId);
        if (internetAccess(this)) {
            new GetTrailers().execute(trailerURL);
        } else {
            Toast.makeText(this, "Please connect to internet and try again!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(int position){
        //assign context and activity class for scope
        Context context = this;
        String trailerId = trailerList.get(position).gettKey();
        watchTrailer(context, trailerId);
    }
    //launches youtube app or web browser depending on device's preferences
    private static void watchTrailer(Context context, String trailerId){
        Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + trailerId));
        Intent webBrowserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailerId));
        try {
            context.startActivity(youtubeAppIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webBrowserIntent);
        }
    }

    //feed detail activity a movie and bind that data to views
    private void displayMovieDetails(Movie movie){
        ImageView moviePosterIv = findViewById(R.id.movie_poster_iv);
        TextView ogTitleTv = findViewById(R.id.og_title_tv);
        TextView releaseTv = findViewById(R.id.release_date_tv);
        TextView ratingTv = findViewById(R.id.user_rating_tv);
        TextView plotOverTv = findViewById(R.id.plot_synop_tv);
        favButton = findViewById(R.id.fav_button);
        if (favMovie != null) {
            favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
        } else {
            favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));

        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFavButtonClicked();
                if (favMovie != null) {
                    favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_off));
                } else {
                    favButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),android.R.drawable.btn_star_big_on));
                }
                finish();
            }
        });

        //clean format of date to be more human readable
        String releaseDate = movie.getReleaseDate();
        DateFormat feedDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        DateFormat resultDate = new SimpleDateFormat("MMMM dd,  yyyy", Locale.US);
        Date date = null;
        try {
            date = feedDate.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        releaseDate = resultDate.format(date);


        Picasso.with(this)
                .load((TMDB_IMAGE_URL + movie.getPosterUrl()))
                .into(moviePosterIv);
        ogTitleTv.setText(movie.getOgName());
        releaseTv.setText(releaseDate);
        ratingTv.setText(movie.getUserRating());
        plotOverTv.setText(movie.getPlotSynopsis());


    }
    public void onFavButtonClicked() {

        if (favMovie == null) {
            addFavorite();
        } else {
            delFavorite();
        }


    }

    private void addFavorite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.favDb.favoriteDao().insertFavorite(favMovie);
            }
        }).start();
    }

    private void delFavorite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MainActivity.favDb.favoriteDao().deleteFavorite(favMovie);
            }
        }).start();
    }

    private class GetReviews extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... url) {
            String reviewResults = null;
            try {
                reviewResults = NetUtils.getMovieData(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return reviewResults;
        }
        @Override
        protected void onPostExecute(String reviewResults){
            try {
                reviewList = MovieJsonUtil.getReviewResults(reviewResults);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //hide reviews if none available
            if (reviewList.size() == 0) {
                reviewRV.setVisibility(View.INVISIBLE);
                reviewTitleTV.setVisibility(View.INVISIBLE);
            }
            reviewRVAdapter.setmReviewList(reviewList);
            reviewRVAdapter.notifyDataSetChanged();
        }
    }

    private class GetTrailers extends AsyncTask<URL, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... url) {
            String trailerResults = null;
            try {
                trailerResults = NetUtils.getMovieData(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return trailerResults;
        }
        @Override
        protected void onPostExecute(String trailerResults){
            try {
                trailerList = MovieJsonUtil.getTrailerResults(trailerResults);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (trailerList.size() != 0) {
                gridLayoutTrailer.setSpanCount(trailerList.size());
            }
            //hide trailers if none available
            if (trailerList.size() == 0) {
                trailerRV.setVisibility(View.INVISIBLE);
                trailerTitleTV.setVisibility(View.INVISIBLE);
            }
            trailerRVAdapter.setmTrailerList(trailerList);
            trailerRVAdapter.notifyDataSetChanged();
        }
    }


}
