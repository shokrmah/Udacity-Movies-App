package com.example.shokr1.moviesapp;

/**
 * Created by shokr 1 on 9/23/2015.
 */
public class Movie {


    private long _id;
    private String title;
    private String release_date;
    private String poster_path;
    private double rate;
    private int view_count;
    private String over_view;

//    private String[] videos_paths;
//    private String[] reviews_paths;



    public Movie(long _id, String title, String release_date, String poster_path, double rate, int view_count,
                 String over_view)
    {
        set_id(_id);
        setTitle(title);
        setRelease_date(release_date);
        setPoster_path(poster_path);
        setRate(rate);
        setView_count(view_count);
        setOver_view(over_view);
    }



    public String getOver_view() {
        return over_view;
    }

    public void setOver_view(String over_view) {
        this.over_view = over_view;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getView_count() {
        return view_count;
    }

    public void setView_count(int view_count) {
        this.view_count = view_count;
    }


}
