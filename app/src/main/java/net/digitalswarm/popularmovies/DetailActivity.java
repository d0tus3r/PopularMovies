package net.digitalswarm.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.digitalswarm.popularmovies.models.Movie;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private static final String TMDB_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //pull intent from main activity
        Intent intentFromMain = getIntent();
        Movie currentMovie = intentFromMain.getParcelableExtra("Movie");
        displayMovieDetails(currentMovie);
    }



    //feed detail activity a movie and bind that data to views
    private void displayMovieDetails(Movie movie){
        ImageView moviePosterIv = findViewById(R.id.movie_poster_iv);
        TextView ogTitleTv = findViewById(R.id.og_title_tv);
        TextView releaseTv = findViewById(R.id.release_date_tv);
        TextView ratingTv = findViewById(R.id.user_rating_tv);
        TextView plotOverTv = findViewById(R.id.plot_synop_tv);

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


}
