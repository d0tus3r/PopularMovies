package net.digitalswarm.popularmovies.utils;
/**
 * Created by us3r on 5/17/2018.
 * parse tmdb movie JSON and build list of movie objects for movie poster recycler view
 */
import android.util.Log;
import net.digitalswarm.popularmovies.models.Movie;
import net.digitalswarm.popularmovies.models.Review;
import net.digitalswarm.popularmovies.models.Trailer;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
//used for logging purposes
import static android.content.ContentValues.TAG;

public class MovieJsonUtil {
    //TMDB Keys for parsing JSON
    //results json is an array of movies
    //json pagination with
    private static final String MOVIE_RESULTS = "results";
    private static final String MOVIE_POSTER = "poster_path";
    private static final String MOVIE_TITLE = "original_title";
    private static final String MOVIE_RATING = "vote_average";
    private static final String MOVIE_RELEASE = "release_date";
    private static final String MOVIE_OVERVIEW = "overview";
    private static final String MOVIE_ID = "id";
    private static final String REVIEW_ID = "id";
    private static final String REVIEW_URL = "url";
    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String TRAILER_ID = "id";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_SITE = "site";
    private static final String TRAILER_TYPE = "type";

    //Net Utils will give a String of json data - use this data to create a json array to convert to movie objects
    public static ArrayList getMovieResults(String tmdbJsonResponse) throws JSONException {
        //assign tmdb response to jsonobject
        JSONObject tmdbJson = new JSONObject(tmdbJsonResponse);
        //strip results array | results is an array with each
        JSONArray movieResultsJson = tmdbJson.getJSONArray(MOVIE_RESULTS);
        //array of movie objects
        ArrayList<Movie> movieArrayList = new ArrayList<>(movieResultsJson.length());
        //loop through movieResultsJson and return an array of movie objects
        for (int i = 0; i < movieResultsJson.length(); i++){
            JSONObject resultEntry = movieResultsJson.getJSONObject(i);
            Log.d(TAG, "getMovieResults: " + resultEntry);
            movieArrayList.add(i, new Movie(
                    resultEntry.getString(MOVIE_TITLE),
                    resultEntry.getString(MOVIE_POSTER),
                    resultEntry.getString(MOVIE_RELEASE),
                    resultEntry.getString(MOVIE_RATING),
                    resultEntry.getString(MOVIE_OVERVIEW),
                    resultEntry.getString(MOVIE_ID)));
        }
        return movieArrayList;
    }

    public static ArrayList getReviewResults(String tmdbJsonResponse) throws JSONException {
        JSONObject tmdbJson = new JSONObject(tmdbJsonResponse);
        JSONArray reviewResultsJson = tmdbJson.getJSONArray(MOVIE_RESULTS);
        ArrayList<Review> reviewArrayList = new ArrayList<>(reviewResultsJson.length());
        for (int i = 0; i < reviewResultsJson.length(); i++) {
            JSONObject resultEntry = reviewResultsJson.getJSONObject(i);
            Log.d(TAG, "getReviewResults: " + resultEntry);
            reviewArrayList.add(i, new Review(
                    resultEntry.getString(REVIEW_ID),
                    resultEntry.getString(REVIEW_URL),
                    resultEntry.getString(REVIEW_AUTHOR),
                    resultEntry.getString(REVIEW_CONTENT)));
        }
        return reviewArrayList;
    }

    public static ArrayList getTrailerResults(String tmdbJsonResponse) throws JSONException {
        JSONObject tmdbJson = new JSONObject(tmdbJsonResponse);
        JSONArray trailerResultsJson = tmdbJson.getJSONArray(MOVIE_RESULTS);
        ArrayList<Trailer> trailerArrayList = new ArrayList<>(trailerResultsJson.length());
        for (int i = 0; i < trailerResultsJson.length(); i++) {
            JSONObject resultEntry = trailerResultsJson.getJSONObject(i);
            Log.d(TAG, "getTrailerResults: " + resultEntry);
            trailerArrayList.add(i, new Trailer(
                    resultEntry.getString(TRAILER_ID),
                    resultEntry.getString(TRAILER_KEY),
                    resultEntry.getString(TRAILER_NAME),
                    resultEntry.getString(TRAILER_SITE),
                    resultEntry.getString(TRAILER_TYPE)));
        }
        return trailerArrayList;
    }
}
