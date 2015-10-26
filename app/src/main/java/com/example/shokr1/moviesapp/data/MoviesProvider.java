package com.example.shokr1.moviesapp.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by shokr 1 on 10/8/2015.
 */
public class MoviesProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MoviesDbHelper mOpenHelper;

    
    static final int POPULAR_MOVIE = 100;
    static final int TOPRATED_MOVIE = 101;
    static final int FAVOURITE_MOVIE = 102;
    static final int FAVOURITE_MOVIE_WITH_ID = 103;

    static final int POPULAR_REVIEW = 200;
    static final int TOPRATED_REVIEW = 201;
    static final int FAVOURITE_REVIEW = 202;

    static final int POPULAR_TRAILER = 300;
    static final int TOPRATED_TRAILER = 301;
    static final int FAVOURITE_TRAILER = 302;

    //static final int REVIEW_WITH_MOVIE_ID = 300;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // 2) Use the addURI function to match each of the types.
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_MOVIE_POPULAR,POPULAR_MOVIE);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_MOVIE_TOP,TOPRATED_MOVIE);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_MOVIE_FAVOURITE,FAVOURITE_MOVIE);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_MOVIE_FAVOURITE +"/#",FAVOURITE_MOVIE_WITH_ID);


        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_REVIEW_POPULAR +"/#",POPULAR_REVIEW);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_REVIEW_TOP +"/#",TOPRATED_REVIEW);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_REVIEW_FAVOURITE +"/#",FAVOURITE_REVIEW);

        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_TRAILER_POPULAR +"/#",POPULAR_TRAILER);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_TRAILER_TOP +"/#",TOPRATED_TRAILER);
        matcher.addURI(MoviesContract.CONTENT_AUTHORITY,MoviesContract.PATH_TRAILER_FAVOURITE +"/#",FAVOURITE_TRAILER);

        return matcher;
    }



    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDbHelper(getContext());
        return true;
    }


    //Popular Movies
    private Cursor getPopularMovies(Uri uri, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.PopularMoviesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getPopularTrailers(Uri uri, String[] projection, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.PopularTrailerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getPopularReviews(Uri uri, String[] projection, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.PopularReviewEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    //Top Rated Moves
    private Cursor getTopRatedMovies(Uri uri, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.TopRatedMoviesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getTopRatedTrailers(Uri uri, String[] projection, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.TopRatedTrailerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getTopRatedReviews(Uri uri, String[] projection, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.TopRatedReviewEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }


    //Favourite Moves
    private Cursor getFavouriteMovies(Uri uri, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getFavouriteMoviesWithID(Uri uri, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.FavouriteMoviesEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getFavouriteTrailers(Uri uri, String[] projection, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.FavouriteTrailerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    private Cursor getFavouriteReviews(Uri uri, String[] projection, String selection,String[] selectionArgs) {
        return mOpenHelper.getReadableDatabase().query(
                MoviesContract.FavouriteReviewEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case POPULAR_MOVIE:
            {
                retCursor = getPopularMovies(uri, selection, selectionArgs);
                break;
            }
            case POPULAR_TRAILER: {
                retCursor = getPopularTrailers(uri, projection, selection, selectionArgs);
                break;
            }

            case POPULAR_REVIEW: {
                retCursor = getPopularReviews(uri, projection, selection, selectionArgs);
                break;
            }

            case TOPRATED_MOVIE:
            {
                retCursor = getTopRatedMovies(uri, selection, selectionArgs);
                break;
            }
            case TOPRATED_TRAILER: {
                retCursor = getTopRatedTrailers(uri, projection, selection, selectionArgs);
                break;
            }

            case TOPRATED_REVIEW: {
                retCursor = getTopRatedReviews(uri, projection, selection, selectionArgs);
                break;
            }

            case FAVOURITE_MOVIE:
            {
                retCursor = getFavouriteMovies(uri, selection, selectionArgs);
                break;
            }
            case FAVOURITE_MOVIE_WITH_ID:
            {
                retCursor = getFavouriteMoviesWithID(uri, selection, selectionArgs);
                break;
            }


            case FAVOURITE_TRAILER: {
                retCursor = getFavouriteTrailers(uri, projection, selection, selectionArgs);
                break;
            }

            case FAVOURITE_REVIEW: {
                retCursor = getFavouriteReviews(uri, projection, selection, selectionArgs);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case POPULAR_MOVIE:
                return MoviesContract.PopularMoviesEntry.CONTENT_TYPE;
            case POPULAR_TRAILER:
                return MoviesContract.PopularTrailerEntry.CONTENT_TYPE;
            case POPULAR_REVIEW:
                return MoviesContract.PopularReviewEntry.CONTENT_TYPE;

            case TOPRATED_MOVIE:
                return MoviesContract.TopRatedMoviesEntry.CONTENT_TYPE;
            case TOPRATED_TRAILER:
                return MoviesContract.TopRatedTrailerEntry.CONTENT_TYPE;
            case TOPRATED_REVIEW:
                return MoviesContract.TopRatedReviewEntry.CONTENT_TYPE;

            case FAVOURITE_MOVIE:
                return MoviesContract.FavouriteMoviesEntry.CONTENT_TYPE;
            case FAVOURITE_TRAILER:
                return MoviesContract.FavouriteTrailerEntry.CONTENT_TYPE;
            case FAVOURITE_REVIEW:
                return MoviesContract.FavouriteReviewEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case POPULAR_MOVIE: {
                long _id = db.insert(MoviesContract.PopularMoviesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.PopularMoviesEntry.buildPopularUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case POPULAR_TRAILER:{
                long _id = db.insert(MoviesContract.PopularTrailerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.PopularTrailerEntry.buildPopularTrailerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case POPULAR_REVIEW:{
                long _id = db.insert(MoviesContract.PopularReviewEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.PopularReviewEntry.buildPopularReviewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TOPRATED_MOVIE: {
                long _id = db.insert(MoviesContract.TopRatedMoviesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.TopRatedMoviesEntry.buildTopRatedUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TOPRATED_TRAILER:{
                long _id = db.insert(MoviesContract.TopRatedTrailerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.TopRatedTrailerEntry.buildTopRatedTrailerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case TOPRATED_REVIEW:{
                long _id = db.insert(MoviesContract.TopRatedReviewEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.TopRatedReviewEntry.buildTopRatedReviewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVOURITE_MOVIE: {
                long _id = db.insert(MoviesContract.FavouriteMoviesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.FavouriteMoviesEntry.buildFavouriteUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case FAVOURITE_MOVIE_WITH_ID: {
                long _id = db.insert(MoviesContract.FavouriteMoviesEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.FavouriteMoviesEntry.buildFavouriteUri();
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVOURITE_TRAILER:{
                long _id = db.insert(MoviesContract.FavouriteTrailerEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.FavouriteTrailerEntry.buildFavouriteTrailerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            case FAVOURITE_REVIEW:{
                long _id = db.insert(MoviesContract.FavouriteReviewEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = MoviesContract.FavouriteReviewEntry.buildFavouriteReviewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int deleted_rows;
        switch (match) {
            case POPULAR_MOVIE: {
                deleted_rows = db.delete(MoviesContract.PopularTrailerEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.PopularReviewEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.PopularMoviesEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case TOPRATED_MOVIE: {
                deleted_rows = db.delete(MoviesContract.TopRatedTrailerEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.TopRatedReviewEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.TopRatedMoviesEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case FAVOURITE_MOVIE: {
                deleted_rows = db.delete(MoviesContract.FavouriteTrailerEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.FavouriteReviewEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.FavouriteMoviesEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case FAVOURITE_MOVIE_WITH_ID: {
                deleted_rows = db.delete(MoviesContract.FavouriteTrailerEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.FavouriteReviewEntry.TABLE_NAME,selection,selectionArgs);
                deleted_rows = deleted_rows + db.delete(MoviesContract.FavouriteMoviesEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }

            case POPULAR_TRAILER: {
                deleted_rows = db.delete(MoviesContract.PopularTrailerEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case TOPRATED_TRAILER: {
                deleted_rows = db.delete(MoviesContract.TopRatedTrailerEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case FAVOURITE_TRAILER: {
                deleted_rows = db.delete(MoviesContract.FavouriteTrailerEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }


            case POPULAR_REVIEW: {
                deleted_rows = db.delete(MoviesContract.PopularReviewEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case TOPRATED_REVIEW: {
                deleted_rows = db.delete(MoviesContract.TopRatedReviewEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            case FAVOURITE_REVIEW: {
                deleted_rows = db.delete(MoviesContract.FavouriteReviewEntry.TABLE_NAME,selection,selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(selection==null || deleted_rows != 0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return deleted_rows;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int updated_rows;

        switch (match) {
            case POPULAR_MOVIE: {
                updated_rows = db.update(MoviesContract.PopularMoviesEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            }
            case POPULAR_TRAILER:{
                updated_rows = db.update(MoviesContract.PopularTrailerEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case POPULAR_REVIEW:{
                updated_rows = db.update(MoviesContract.PopularReviewEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case TOPRATED_MOVIE: {
                updated_rows = db.update(MoviesContract.TopRatedMoviesEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            }
            case TOPRATED_TRAILER:{
                updated_rows = db.update(MoviesContract.TopRatedTrailerEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case TOPRATED_REVIEW:{
                updated_rows = db.update(MoviesContract.TopRatedReviewEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case FAVOURITE_MOVIE: {
                updated_rows = db.update(MoviesContract.FavouriteMoviesEntry.TABLE_NAME,values,selection,selectionArgs);
                break;
            }
            case FAVOURITE_TRAILER:{
                updated_rows = db.update(MoviesContract.FavouriteTrailerEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }

            case FAVOURITE_REVIEW:{
                updated_rows = db.update(MoviesContract.FavouriteReviewEntry.TABLE_NAME,values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if(updated_rows != 0)
        {
            getContext().getContentResolver().notifyChange(uri,null);
        }


        return updated_rows;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;
        switch (match) {
            case POPULAR_MOVIE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.PopularMoviesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case POPULAR_TRAILER:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.PopularTrailerEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case POPULAR_REVIEW:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.PopularReviewEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;


            case TOPRATED_MOVIE:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.TopRatedMoviesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case TOPRATED_TRAILER:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.TopRatedTrailerEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;

            case TOPRATED_REVIEW:
                db.beginTransaction();
                returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.TopRatedReviewEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }


}
