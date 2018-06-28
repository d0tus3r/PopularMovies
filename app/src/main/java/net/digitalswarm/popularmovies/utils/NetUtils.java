package net.digitalswarm.popularmovies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;
import static android.content.ContentValues.TAG;

/**
 * Created by us3r on 5/17/2018.
 * Utility Class to handle URL fetching
 * inspired by sunshine / toybox NetworkUtils class
 */

public class NetUtils {
    //url strings for movie data
    private static final String TMDB_MOVIE_BASE_URL = "api.themoviedb.org";
    private static final String POPULAR_MOVIES = "popular";
    private static final String TOPRATED_MOVIES = "top_rated";

    //api key builder
    //Todo: Obfuscate key by hiding in config file not indexed by git [stretch goal]
    private static final String API_KEY = "";

    //helper method for testing for internet access
    public static boolean internetAccess(Context context) {
        //init connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //using the connectivity manager, poll active network info and save to networkInfo
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //check if networkInfo is null && connection state
        return networkInfo != null && networkInfo.isConnected();
    }


    /**
     * generate full tmdb movie url
     * @param sortPref - pulled from preferences in settings menu
     * @return URL for later JSON query / parse
     */
    public static URL genMovieUrl(String sortPref) {
        //Uri builder based on sortPref
        Uri.Builder builder = new Uri.Builder();
        URL url = null;
        //popular url: http://api.themoviedb.org/3/movie/popular?api_key=API_KEY
        if (sortPref.equals("popular")) {
            builder.scheme("http")
                    .authority(TMDB_MOVIE_BASE_URL)
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(POPULAR_MOVIES)
                    .appendQueryParameter("api_key", API_KEY);
            try {
                url = new URL(builder.build().toString());
                //temp log to troubleshoot crash
                Log.d(TAG, "genMovieUrl: " + url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        //top rated url: http://api.themoviedb.org/3/movie/top_rated?api_key=API_KEY
        } else {
            builder.scheme("http")
                    .authority(TMDB_MOVIE_BASE_URL)
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(TOPRATED_MOVIES)
                    .appendQueryParameter("api_key", API_KEY);
            try {
                url = new URL(builder.build().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        }
    }

    public static URL genTrailerUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        URL url = null;
        builder.scheme("http")
                .authority(TMDB_MOVIE_BASE_URL)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieId)
                .appendPath("videos")
                .appendQueryParameter("api_key", API_KEY);
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL genReviewUrl(String movieId) {
        Uri.Builder builder = new Uri.Builder();
        URL url = null;
        builder.scheme("http")
                .authority(TMDB_MOVIE_BASE_URL)
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieId)
                .appendPath("reviews")
                .appendQueryParameter("api_key", API_KEY);
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }



    /**
     * open connection to tmdb using constructed url from genMovieUrl
     * read data (if avail) using scanner
     * terminate connection when finished
     * @param url - built from genMovieUrl
     * @return data from tmdb -- used to create movie objects after json parsing
     * @throws IOException
     * Shout out to NetworkUtils from Toybox/Sunshine project
     */
    public static String getMovieData(URL url) throws IOException {
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        try {
            InputStream movieInput = urlCon.getInputStream();
            Scanner scanner = new Scanner(movieInput);
            scanner.useDelimiter("\\A");
            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.d(TAG, "getMovieData: Success");
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlCon.disconnect();
        }
    }

}
