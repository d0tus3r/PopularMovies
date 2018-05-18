package net.digitalswarm.popularmovies.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by us3r on 5/17/2018.
 * Utility Class to handle URL fetching
 * inspired by sunshine / toybox NetworkUtils class
 */

public class NetUtils {
    //url strings for movie data
    public static final String TMDB_MOVIE_BASE_URL = "api.themoviedb.org/3/movie";
    public static final String POPULAR_MOVIES = "popular";
    public static final String TOPRATED_MOVIES = "top_rated";
    //url strings for movie image
    public static final String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p";
    public static final String TMDB_IMAGE_SIZE = "w185/";
    //api key builder
    //Todo: Obfuscate key by hiding in config file not indexed by git [stretch goal]
    public static final String API_KEY = "";


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
        if (sortPref == "popular") {
            builder.scheme("http")
                    .authority(TMDB_MOVIE_BASE_URL)
                    .appendPath(POPULAR_MOVIES)
                    .appendQueryParameter("api_key", API_KEY);

            try {
                url = new URL(builder.build().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return url;
        //top rated url: http://api.themoviedb.org/3/movie/top_rated?api_key=API_KEY
        } else {
            builder.scheme("http")
                    .authority(TMDB_MOVIE_BASE_URL)
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

    /**
     * open connection to tmdb using constructed url from genMovieUrl
     * read data (if avail) using scanner
     * terminate connection when finished
     * @param url - built from genMovieUrl
     * @return data from tmdb -- used to create movie objects after json parsing
     * @throws IOException
     * Shout out to NetworkUtils from Toybox/Sunshine project
     */
    public static String getMoveData(URL url) throws IOException {
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        try {
            InputStream movieInput = urlCon.getInputStream();
            Scanner scanner = new Scanner(movieInput);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlCon.disconnect();
        }
    }

}
