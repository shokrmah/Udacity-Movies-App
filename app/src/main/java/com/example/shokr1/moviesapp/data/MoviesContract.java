package com.example.shokr1.moviesapp.data;

import android.provider.BaseColumns;
import android.net.Uri;
import android.content.ContentResolver;
import android.content.ContentUris;

/**
 * Created by shokr 1 on 10/7/2015.
 */
public class MoviesContract {



    // The "Content authority" is a name for the entire content provider.
    public static final String CONTENT_AUTHORITY = "com.example.shokr1.moviesapp";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE_POPULAR = "popularmovie";
    public static final String PATH_MOVIE_TOP = "topmovie";
    public static final String PATH_MOVIE_FAVOURITE = "favouritemovie";
    public static final String PATH_TRAILER_POPULAR = "populartrailer";
    public static final String PATH_TRAILER_TOP = "toptrailer";
    public static final String PATH_TRAILER_FAVOURITE = "favouritetrailer";
    public static final String PATH_REVIEW_POPULAR = "popularreview";
    public static final String PATH_REVIEW_TOP = "topreview";
    public static final String PATH_REVIEW_FAVOURITE = "favouritereview";

//    public static final class MoviesEntry implements BaseColumns {
//
//        public static final String TABLE_NAME = "movie";
//
//        public static final String COLUMN_MOVIE_ID = "movie_id";
//        public static final String COLUMN_TITLE = "title";
//        public static final String COLUMN_RELEASE_DATE = "release_date";
//        public static final String COLUMN_POSTER_PATH = "poster_path";
//        public static final String COLUMN_RATE = "rate";
//        public static final String COLUMN_VIEW_COUNT = "view_count";
//        public static final String COLUMN_OVERVIEW = "overview";
//
//
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
//
//        public static Uri buildMovieUri(long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//
//    }

    public static final class PopularMoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "popular_movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_VIEW_COUNT = "view_count";
        public static final String COLUMN_OVERVIEW = "overview";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_POPULAR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_POPULAR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_POPULAR;

        public static Uri buildPopularUri() {
            //return ContentUris.withAppendedId(CONTENT_URI, id);
            return CONTENT_URI;
        }


    }


    public static final class TopRatedMoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "top_rated_movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_VIEW_COUNT = "view_count";
        public static final String COLUMN_OVERVIEW = "overview";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_TOP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_TOP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_TOP;

        public static Uri buildTopRatedUri() {
            //return ContentUris.withAppendedId(CONTENT_URI, id);
            return CONTENT_URI;
        }


    }

    public static final class FavouriteMoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourite_movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RATE = "rate";
        public static final String COLUMN_VIEW_COUNT = "view_count";
        public static final String COLUMN_OVERVIEW = "overview";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_FAVOURITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_FAVOURITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE_FAVOURITE;

        public static Uri buildFavouriteUri() {
            //return ContentUris.withAppendedId(CONTENT_URI, id);
            return CONTENT_URI;

        }

        public static Uri buildFavouriteMovieWithIDUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
            //return CONTENT_URI;
        }

        }

//    public static final class TrailerEntry implements BaseColumns {
//        public static final String TABLE_NAME = "trailer";
//
//        public static final String COLUMN_MOVIE_ID = "movie_id";
//        public static final String COLUMN_TRAILER_NAME = "trailer_name";
//        public static final String COLUMN_TRAILER_LINK = "trailer_link";
//
//
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
//
//        public static Uri buildTrailerUri(long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//
//    }


    public static final class PopularTrailerEntry implements BaseColumns {
        public static final String TABLE_NAME = "popular_trailer";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TRAILER_NAME = "trailer_name";
        public static final String COLUMN_TRAILER_LINK = "trailer_link";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER_POPULAR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER_POPULAR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER_POPULAR;

        public static Uri buildPopularTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


    public static final class TopRatedTrailerEntry implements BaseColumns {
        public static final String TABLE_NAME = "top_rated_trailer";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TRAILER_NAME = "trailer_name";
        public static final String COLUMN_TRAILER_LINK = "trailer_link";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER_TOP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER_TOP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER_TOP;

        public static Uri buildTopRatedTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


    public static final class FavouriteTrailerEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourite_trailer";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TRAILER_NAME = "trailer_name";
        public static final String COLUMN_TRAILER_LINK = "trailer_link";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER_FAVOURITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER_FAVOURITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER_FAVOURITE;

        public static Uri buildFavouriteTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


//    public static final class ReviewEntry implements BaseColumns {
//        public static final String TABLE_NAME = "review";
//
//        public static final String COLUMN_MOVIE_ID = "movie_id";
//        public static final String COLUMN_REVIEW = "review_text";
//
//
//
//        public static final Uri CONTENT_URI =
//                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();
//
//        public static final String CONTENT_TYPE =
//                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
//        public static final String CONTENT_ITEM_TYPE =
//                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
//
//        public static Uri buildReviewUri(long id) {
//            return ContentUris.withAppendedId(CONTENT_URI, id);
//        }
//
//
//    }


    public static final class PopularReviewEntry implements BaseColumns {
        public static final String TABLE_NAME = "popular_review";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_REVIEW = "review_text";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW_POPULAR).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW_POPULAR;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW_POPULAR;

        public static Uri buildPopularReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


    public static final class TopRatedReviewEntry implements BaseColumns {
        public static final String TABLE_NAME = "top_rated_review";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_REVIEW = "review_text";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW_TOP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW_TOP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW_TOP;

        public static Uri buildTopRatedReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }


    public static final class FavouriteReviewEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourite_review";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_REVIEW = "review_text";



        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW_FAVOURITE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW_FAVOURITE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW_FAVOURITE;

        public static Uri buildFavouriteReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }


    }

}
