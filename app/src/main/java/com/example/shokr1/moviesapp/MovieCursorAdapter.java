package com.example.shokr1.moviesapp;

import android.content.Context;
import android.database.Cursor;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.shokr1.moviesapp.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by shokr 1 on 10/10/2015.
 */
public class MovieCursorAdapter extends CursorAdapter {

    Context context;
    Cursor cur;
    public MovieCursorAdapter(Context context, Cursor c, int flags) {

        super(context, c, flags);
        this.context = context;
        this.cur = c;
    }


    static class DataHandler {
        ImageView poster;

        DataHandler(View view)
        {
            poster = (ImageView) view.findViewById(R.id.poster);
        }

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(R.layout.movies_list_item, parent, false);
        DataHandler handler = new DataHandler(view);
        view.setTag(handler);

        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        DataHandler handler = (DataHandler) view.getTag();

        String path = cursor.getString(MainActivityFragment.COL_POSTER_PATH);
        String url = "http://image.tmdb.org/t/p/"+ MainActivity.BEST_WIDTH + "/" + path;

        Picasso.with(context).load(url).into(handler.poster);
    }



}
