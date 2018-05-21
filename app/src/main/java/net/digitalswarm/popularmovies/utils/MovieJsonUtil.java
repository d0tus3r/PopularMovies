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
    private static final String TMDB_RESULTS = "results";
    private static final String TMDB_POSTER_URL = "poster_path";
    private static final String TMDB_TITLE = "original_title";
    private static final String TMDB_ID = "id";
    private static final String TMDB_POPULARITY = "popularity";
    private static final String TMDB_RATING = "vote_average";
    private static final String TMDB_RELEASE = "release_date";
    private static final String TMDB_OVERVIEW = "overview";

    //array of movie objects
    private ArrayList<Movie> movieArrayList;

    //json objects

    //Net Utils will give a String of json data - use this data to create a json array to convert to movie objects





}
