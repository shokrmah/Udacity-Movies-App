package com.example.shokr1.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by shokr 1 on 9/23/2015.
 */
public class MovieListAdapter extends ArrayAdapter {

    Context context;
    private List<Movie> myList;

    MovieListAdapter(Context context, int res)
    {
        super(context,res);
        this.context = context;
        this.myList = new ArrayList<>();

    }
    static class DataHandler {
        ImageView poster;
    }

    @Override
    public void add(Object object) {
        super.add(object);
        myList.add((Movie) object);
            notifyDataSetChanged();
    }

    @Override
    public void addAll(Object[] items) {
        for (Object item : items)
        {
            myList.add((Movie) item);
        }
        notifyDataSetChanged();

    }

    @Override
    public void clear() {
        myList.clear();
        //super.clear();
    }

    @Override
    public int getCount() {
        return this.myList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.myList.get(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DataHandler handler;

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.movies_list_item,parent,false);
            handler = new DataHandler();
            handler.poster = (ImageView) row.findViewById(R.id.poster);
            row.setTag(handler);
        }
        else
        {
            handler = (DataHandler) row.getTag();
        }


       Movie movie = (Movie)this.getItem(position);

        String url = "http://image.tmdb.org/t/p/w500/" + movie.getPoster_path();

        Picasso.with(context).load(url).into(handler.poster);

        return row;


    }
}
