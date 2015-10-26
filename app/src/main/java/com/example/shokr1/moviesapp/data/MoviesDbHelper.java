package com.example.shokr1.moviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shokr 1 on 10/7/2015.
 */
public class MoviesDbHelper extends SQLiteOpenHelper {

    //Increment version each time database changes.
    private static final int DATABASE_VERSION = 1;


    static final String DATABASE_NAME = "Movies.db";

    public MoviesDbHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Movies Tables


        //Create Popular Movies table
        final String SQL_CREATE_POPULAR_TABLE = "CREATE TABLE " + MoviesContract.PopularMoviesEntry.TABLE_NAME + " (" +

                MoviesContract.PopularMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +

                MoviesContract.PopularMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                MoviesContract.PopularMoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.PopularMoviesEntry.COLUMN_RATE + " REAL NOT NULL," +

                MoviesContract.PopularMoviesEntry.COLUMN_VIEW_COUNT + " INTEGER NOT NULL, " +
                MoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +



                // To assure the application have just one movie entry
                " UNIQUE (" + MoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID + ") " +
                " ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_POPULAR_TABLE);

        //Create Top Rated Movies table
        final String SQL_CREATE_TOP_RATED_TABLE = "CREATE TABLE " + MoviesContract.TopRatedMoviesEntry.TABLE_NAME + " (" +

                MoviesContract.TopRatedMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +

                MoviesContract.TopRatedMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_RATE + " REAL NOT NULL," +

                MoviesContract.TopRatedMoviesEntry.COLUMN_VIEW_COUNT + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +



                // To assure the application have just one movie entry
                " UNIQUE (" + MoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID + ") " +
                " ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_TOP_RATED_TABLE);


        //Create Favourite Movies table
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + MoviesContract.FavouriteMoviesEntry.TABLE_NAME + " (" +

                MoviesContract.FavouriteMoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +

                MoviesContract.FavouriteMoviesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMoviesEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL, " +
                MoviesContract.FavouriteMoviesEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesContract.FavouriteMoviesEntry.COLUMN_RATE + " REAL NOT NULL," +

                MoviesContract.FavouriteMoviesEntry.COLUMN_VIEW_COUNT + " INTEGER NOT NULL, " +
                MoviesContract.FavouriteMoviesEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +



                // To assure the application have just one movie entry
                " UNIQUE (" + MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + ") " +
                " ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);

        //__________________________________________________________________________


        //Trailer tables


        //Create Popular trailer table
        final String SQL_CREATE_POPULAR_TRAILER_TABLE = "CREATE TABLE " + MoviesContract.PopularTrailerEntry.TABLE_NAME + " (" +
                MoviesContract.PopularTrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MoviesContract.PopularTrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.PopularTrailerEntry.COLUMN_TRAILER_NAME + " TEXT NULL, " +
                MoviesContract.PopularTrailerEntry.COLUMN_TRAILER_LINK + " TEXT NOT NULL, " +

        // Set up the movie ID column as a foreign key to movie table.
        " FOREIGN KEY (" + MoviesContract.PopularTrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MoviesContract.PopularMoviesEntry.TABLE_NAME + " (" + MoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID + ")); ";

        db.execSQL(SQL_CREATE_POPULAR_TRAILER_TABLE);


        //Create Top Rated trailer table
        final String SQL_CREATE_TOP_RATED_TRAILER_TABLE = "CREATE TABLE " + MoviesContract.TopRatedTrailerEntry.TABLE_NAME + " (" +
                MoviesContract.TopRatedTrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MoviesContract.TopRatedTrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedTrailerEntry.COLUMN_TRAILER_NAME + " TEXT NULL, " +
                MoviesContract.TopRatedTrailerEntry.COLUMN_TRAILER_LINK + " TEXT NOT NULL, " +

                // Set up the movie ID column as a foreign key to movie table.
                " FOREIGN KEY (" + MoviesContract.TopRatedTrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MoviesContract.TopRatedMoviesEntry.TABLE_NAME + " (" + MoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID + ")); ";

        db.execSQL(SQL_CREATE_TOP_RATED_TRAILER_TABLE);


        //Create Favourite trailer table
        final String SQL_CREATE_FAVOURITE_TRAILER_TABLE = "CREATE TABLE " + MoviesContract.FavouriteTrailerEntry.TABLE_NAME + " (" +
                MoviesContract.FavouriteTrailerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MoviesContract.FavouriteTrailerEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.FavouriteTrailerEntry.COLUMN_TRAILER_NAME + " TEXT NULL, " +
                MoviesContract.FavouriteTrailerEntry.COLUMN_TRAILER_LINK + " TEXT NOT NULL, " +

                // Set up the movie ID column as a foreign key to movie table.
                " FOREIGN KEY (" + MoviesContract.FavouriteTrailerEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MoviesContract.FavouriteMoviesEntry.TABLE_NAME + " (" + MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + ")); ";

        db.execSQL(SQL_CREATE_FAVOURITE_TRAILER_TABLE);


        //__________________________________________________________________________


        //Reviews tables

        //Create Popular review table
        final String SQL_CREATE_POPULAR_REVIEW_TABLE = "CREATE TABLE " + MoviesContract.PopularReviewEntry.TABLE_NAME + " (" +
                MoviesContract.PopularReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MoviesContract.PopularReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.PopularReviewEntry.COLUMN_REVIEW + " TEXT NOT NULL, " +

                // Set up the movie ID column as a foreign key to movie table.
                " FOREIGN KEY (" + MoviesContract.PopularReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MoviesContract.PopularMoviesEntry.TABLE_NAME + " (" + MoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID + ")); ";

        db.execSQL(SQL_CREATE_POPULAR_REVIEW_TABLE);



        //Create Top Rated review table
        final String SQL_CREATE_TOP_RATED_REVIEW_TABLE = "CREATE TABLE " + MoviesContract.TopRatedReviewEntry.TABLE_NAME + " (" +
                MoviesContract.TopRatedReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MoviesContract.TopRatedReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.TopRatedReviewEntry.COLUMN_REVIEW + " TEXT NOT NULL, " +

                // Set up the movie ID column as a foreign key to movie table.
                " FOREIGN KEY (" + MoviesContract.TopRatedReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MoviesContract.TopRatedMoviesEntry.TABLE_NAME + " (" + MoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID + ")); ";

        db.execSQL(SQL_CREATE_TOP_RATED_REVIEW_TABLE);



        //Create Favourite review table
        final String SQL_CREATE_FAVOURITE_REVIEW_TABLE = "CREATE TABLE " + MoviesContract.FavouriteReviewEntry.TABLE_NAME + " (" +
                MoviesContract.FavouriteReviewEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                MoviesContract.FavouriteReviewEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesContract.FavouriteReviewEntry.COLUMN_REVIEW + " TEXT NOT NULL, " +

                // Set up the movie ID column as a foreign key to movie table.
                " FOREIGN KEY (" + MoviesContract.FavouriteReviewEntry.COLUMN_MOVIE_ID + ") REFERENCES " +
                MoviesContract.FavouriteMoviesEntry.TABLE_NAME + " (" + MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + ")); ";

        db.execSQL(SQL_CREATE_FAVOURITE_REVIEW_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Trailers tables
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TopRatedTrailerEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.PopularTrailerEntry.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TrailerEntry.TABLE_NAME);

        //Reviews tables
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TopRatedReviewEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.PopularReviewEntry.TABLE_NAME);
        //db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.ReviewEntry.TABLE_NAME);

        //Movies tables
        //db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TopRatedMoviesEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.PopularMoviesEntry.TABLE_NAME);

        onCreate(db);
    }
}
