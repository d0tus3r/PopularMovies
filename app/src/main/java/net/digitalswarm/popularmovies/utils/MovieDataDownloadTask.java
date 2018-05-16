package net.digitalswarm.popularmovies.utils;

import android.os.AsyncTask;

import org.json.JSONObject;

/**
 * Created by us3r on 5/9/2018.
 * uses asynctask to download movie data from tmdb api in background
 */

public class MovieDataDownloadTask extends AsyncTask<String, Integer, Void> {

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(Void result) {
        if (null != feedList) {
            updateList();
        }
    }

    @Override
    protected Void doInBackground(String... params) {
        String url = params[0];

        //get json string from url
        JSONObject json = getJSONFromUrl(url);

        //parse data
        parseJson(json);
        return null;
    }


    public JSONObject getJSONFromUrl

}
