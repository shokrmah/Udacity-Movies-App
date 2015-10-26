package com.example.shokr1.moviesapp;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by shokr 1 on 10/10/2015.
 */
public class FetchMovies extends AsyncTask<Integer, Void, ArrayList<Movie>> {


//http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=40c3b1f7150e4712af7fc78a131857c1&page=2

    private String base_url = "http://api.themoviedb.org/3/discover/movie";

    static final String API_KEY_QUERY = "api_key";
    static String API_KEY = "40c3b1f7150e4712af7fc78a131857c1";

    private String sort_by_query = "sort_by";
    private String sort_by = "popularity.desc";

    //add pages:
    private String page_no_query = "page";
    private String page_no = "1";


    //private String sort_by2 = "vote_average.desc";

    ArrayList<Movie> moviesList = new ArrayList<Movie>();

    @Override
    protected ArrayList<Movie> doInBackground(Integer... params) {

        if(params == null)
        {
            sort_by = "popularity.desc";
        }
        else
        {
            if (params[0] == 0)
                sort_by = "popularity.desc";
            else
                sort_by = "vote_average.desc";
        }



        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String MovieJsonStr = null;

        try {
            Uri uri = Uri.parse(base_url).buildUpon().appendQueryParameter(API_KEY_QUERY, API_KEY).appendQueryParameter(sort_by_query, sort_by)
                    .build();

            URL url = new URL(uri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            MovieJsonStr = buffer.toString();


        } catch (IOException ex) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }
        }

        try {
            return getMovieDataFromJson(MovieJsonStr);
        } catch (JSONException e) {

        }

        return moviesList;

    }

    private ArrayList<Movie> getMovieDataFromJson(String MovieJsonStr) throws JSONException {
        //moviesList = new ArrayList<Movie>();

        JSONObject jsonMovies = new JSONObject(MovieJsonStr);
        JSONArray allMovies = jsonMovies.getJSONArray("results");

        int length;
        if (allMovies.length() > 30)
            length = 30;
        else
            length = allMovies.length();

        for (int i = 0; i < length; i++) {

            JSONObject jsonMovie = allMovies.getJSONObject(i);
            long _id = jsonMovie.getLong("id");

            String title = jsonMovie.getString("title");

            String release_date = jsonMovie.getString("release_date");
            String poster_path = jsonMovie.getString("poster_path");
            double rate = jsonMovie.getDouble("vote_average");
            int vote_count = jsonMovie.getInt("vote_count");
            String over_view = jsonMovie.getString("overview");


            Movie movie = new Movie(_id, title, release_date, poster_path, rate, vote_count, over_view);

            moviesList.add(movie);
        }

        return moviesList;

    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {

    }

}
