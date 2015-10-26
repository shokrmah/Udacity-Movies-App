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
public class FetchLinksReviews extends AsyncTask<String[],Void,ArrayList<String>> {

    private String base_url = "http://api.themoviedb.org/3/movie/";
    //videos
    //http://api.themoviedb.org/3/movie/135397/videos?api_key=40c3b1f7150e4712af7fc78a131857c1
    //link
    //https://www.youtube.com/watch?v=RFinNxS5KN4
//reviews
    //http://api.themoviedb.org/3/movie/135397/reviews?api_key=40c3b1f7150e4712af7fc78a131857c1


    ArrayList<String> data = new ArrayList<String>();
    //ArrayList<String> review_contents;

    @Override
    protected ArrayList<String> doInBackground(String[]... params) {
        if (params == null)
        {
            return null;
        }


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String MovieJsonStr = null;

        try {
            Uri uri = Uri.parse(base_url).buildUpon()
                    .appendPath(params[0][0])
                    .appendPath(params[0][1])
                    .appendQueryParameter(FetchMovies.API_KEY_QUERY, FetchMovies.API_KEY)
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
            if (params[0][1].equals("videos"))
                return getVideosLinks(MovieJsonStr);
            else
            {
                return getReviewsLinks(MovieJsonStr);
            }
        } catch (JSONException e) {

        }

//        if (params[0][1].equals("videos"))
//            return data;
//        else
//            return data;
        return data;
    }

    private ArrayList<String> getVideosLinks(String MovieJsonStr) throws JSONException
    {

        JSONObject jsonMovies = new JSONObject(MovieJsonStr);
        JSONArray allMovies = jsonMovies.getJSONArray("results");

        //links = new String[allMovies.length()];

        for (int i = 0; i < allMovies.length(); i++) {

            JSONObject jsonMovie = allMovies.getJSONObject(i);
            String youtube_key = jsonMovie.getString("key");

            String youtube_link = "https://www.youtube.com/watch?v=" + youtube_key;
            data.add(youtube_link);
        }

        return data;
    }

    private ArrayList<String> getReviewsLinks(String MovieJsonStr) throws JSONException
    {

        JSONObject jsonMovies = new JSONObject(MovieJsonStr);
        JSONArray allMovies = jsonMovies.getJSONArray("results");

        //String [] review_contents = new String[allMovies.length()];

        for (int i = 0; i < allMovies.length(); i++) {

            JSONObject jsonMovie = allMovies.getJSONObject(i);
            //String review_link = jsonMovie.getString("url");
            String review_content = jsonMovie.getString("content");

            data.add(review_content);
        }

        return data;
    }


}
