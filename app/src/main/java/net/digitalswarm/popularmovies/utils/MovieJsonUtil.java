package net.digitalswarm.popularmovies.utils;

/**
 * Created by us3r on 5/17/2018.
 * parse tmdb movie JSON and build list of movie objects for movie poster recycler view
 */

//import movie object for populating grid view
import net.digitalswarm.popularmovies.models.Movie;
//import arraylist for movie objects
import java.util.ArrayList;
//import json
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MovieJsonUtil {

    //TMDB Keys for parsing JSON
    //results json is an array of movies
    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_POSTER_URL = "poster_path";
    private static final String TMDB_TITLE = "original_title";
    private static final String TMDB_RATING = "vote_average";
    private static final String TMDB_RELEASE = "release_date";
    private static final String TMDB_OVERVIEW = "overview";

    //Net Utils will give a String of json data - use this data to create a json array to convert to movie objects
    public static ArrayList getMovieResults(String tmdbJsonResponse) throws JSONException {
        //assign tmdb response to jsonobject
        JSONObject tmdbJson = new JSONObject(tmdbJsonResponse);
        //strip results array | results is an array with each
        JSONArray movieResultsJson = tmdbJson.getJSONArray(TMDB_RESULTS);
        //array of movie objects
        ArrayList<Movie> movieArrayList = new ArrayList<>(movieResultsJson.length());
        //loop through movieResultsJson and return an array of movie objects
        for (int i = 0; i < movieResultsJson.length(); i++){
            JSONObject resultEntry = movieResultsJson.getJSONObject(i);
            movieArrayList.set(i, new Movie(
                    resultEntry.getString(TMDB_TITLE),
                    resultEntry.getString(TMDB_POSTER_URL),
                    resultEntry.getString(TMDB_RELEASE),
                    resultEntry.getString(TMDB_RATING),
                    resultEntry.getString(TMDB_OVERVIEW)));
        }
        return movieArrayList;
    }
}
