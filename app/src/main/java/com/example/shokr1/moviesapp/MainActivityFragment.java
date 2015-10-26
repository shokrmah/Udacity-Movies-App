package com.example.shokr1.moviesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.shokr1.moviesapp.data.MoviesContract;
import java.util.ArrayList;

import java.util.Vector;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public static final int MOVIE_LOADER = 0;
    MovieCursorAdapter movieAdapter;
    Cursor mcur;
    ArrayList<Movie> moviesList;
    int sorting_type = 0;

    public interface Callback {
        public void onItemSelected(String[] movie_data);
    }


    public static final String[] MOVIE_COLUMNS = {
            MoviesContract.PopularMoviesEntry.TABLE_NAME + "." + MoviesContract.PopularMoviesEntry._ID,
            MoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID,
            MoviesContract.PopularMoviesEntry.COLUMN_TITLE,
            MoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE,
            MoviesContract.PopularMoviesEntry.COLUMN_POSTER_PATH,
            MoviesContract.PopularMoviesEntry.COLUMN_RATE,
            MoviesContract.PopularMoviesEntry.COLUMN_VIEW_COUNT,
            MoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_RELEASE_DATE = 3;
    public static final int COL_POSTER_PATH = 4;
    public static final int COL_RATE = 5;
    public static final int COL_VIEW_COUNT = 6;
    public static final int COL_OVERVIEW = 7;


    public MainActivityFragment() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_refresh)
        {
            update_net();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void update_sorting_type() {
        sorting_type = Utility.update_sorting_type(getActivity());


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movies_list_fragment, container, false);

        update_sorting_type();
        update();



        GridView postersGrid = (GridView) rootView.findViewById(R.id.gridview_forecast);

        postersGrid.setAdapter(movieAdapter);


        //updateMovieList();

        postersGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //Movie movieSelected = (Movie) (parent.getItemAtPosition(position));
                Cursor cursor = movieAdapter.getCursor();

                if (cursor != null && cursor.moveToPosition(position)) {
                    String[] movie_data = {String.valueOf(cursor.getLong(COL_MOVIE_ID)), cursor.getString(COL_TITLE),
                            String.valueOf(cursor.getLong(COL_RELEASE_DATE)), cursor.getString(COL_POSTER_PATH),
                            String.valueOf(cursor.getDouble(COL_RATE)), String.valueOf(cursor.getInt(COL_VIEW_COUNT)),
                            cursor.getString(COL_OVERVIEW)};
//
//                    Intent intent = new Intent(getActivity(), MovieDetails.class);
//                    intent.putExtra("movie_data", movie_data);
//                    startActivity(intent);

                    //Uri moviesUri;

//                    return new CursorLoader(getActivity(),moviesUri,
//                            null, null, null, null);

                    ((Callback) getActivity()).onItemSelected(movie_data);
                }
            }
        });

        return rootView;


    }

    @Override
    public void onResume() {
        super.onResume();

        update_sorting_type();
        update2();

        if(movieAdapter == null)
        {
            update_net();
        }
        else
        {
            if (movieAdapter.getCursor().getCount() == 0)
            {
                update_net();
            }
        }


    }

    private void update_net()
    {
        update_sorting_type();
        updateMovieList();
        if (moviesList.size() > 0)
            delete_fromDB();

        if (insert_toDB() > 0)
            update2();
        else
        if(sorting_type == 0 || sorting_type == 1)
            Toast.makeText(getActivity(), "Please connect to the Internet.", Toast.LENGTH_LONG).show();
        else if(favouriteIsEmpty())
            Toast.makeText(getActivity(), "Please add movies to favourite.", Toast.LENGTH_LONG).show();



    }
    private boolean favouriteIsEmpty()
    {
        Uri moviesUri = MoviesContract.FavouriteMoviesEntry.buildFavouriteUri();

        Cursor favourite_cur = getActivity().getContentResolver().query(moviesUri,
                null, null, null, null);
        if(favourite_cur.getCount() > 0)
            return false;
        else
            return true;
    }
    private void update() {
        Uri moviesUri;
        if (sorting_type == 0)
            moviesUri = MoviesContract.PopularMoviesEntry.buildPopularUri();
        else if (sorting_type == 1)
            moviesUri = MoviesContract.TopRatedMoviesEntry.buildTopRatedUri();
        else
            moviesUri = MoviesContract.FavouriteMoviesEntry.buildFavouriteUri();

        mcur = getActivity().getContentResolver().query(moviesUri,
                null, null, null, null);


            movieAdapter = new MovieCursorAdapter(getActivity(), mcur, 0);

       update_net();
    }

    private void update2() {
        Uri moviesUri;
        if (sorting_type == 0)
            moviesUri = MoviesContract.PopularMoviesEntry.buildPopularUri();
        else if (sorting_type == 1)
            moviesUri = MoviesContract.TopRatedMoviesEntry.buildTopRatedUri();
        else
            moviesUri = MoviesContract.FavouriteMoviesEntry.buildFavouriteUri();

        mcur = getActivity().getContentResolver().query(moviesUri,
                null, null, null, null);
        movieAdapter.changeCursor(mcur);
        movieAdapter.notifyDataSetChanged();

    }
    private void updateMovieList() {
        if (moviesList != null)
        {
            moviesList.clear();
        }
        moviesList = new ArrayList<Movie>();
        FetchMovies fetchMovies = new FetchMovies();

        fetchMovies.execute(sorting_type);
        try {
            moviesList = fetchMovies.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    private int insert_toDB() {
        //sorting type 0 means popular, 1 means top rated, 2 means favourite

        Vector<ContentValues> cVVector = new Vector<ContentValues>(moviesList.size());


        for (int i = 0; i < moviesList.size(); i++) {

            ContentValues movieValues = new ContentValues();

            if (sorting_type == 0)
                movieValues = update_popular_columns(movieValues, i);
            else if (sorting_type == 1)
                movieValues = update_top_columns(movieValues, i);

            cVVector.add(movieValues);
        }

        int inserted_items = 0;
        // add to database
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            if (sorting_type == 0)
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.PopularMoviesEntry.CONTENT_URI, cvArray);
            else if (sorting_type == 1)
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.TopRatedMoviesEntry.CONTENT_URI, cvArray);

        }

        return inserted_items;
    }

    private int delete_fromDB() {
        int deleted = 0;
        if (sorting_type == 0)
            deleted = getActivity().getContentResolver().delete(MoviesContract.PopularMoviesEntry.CONTENT_URI, null, null);
        else if (sorting_type == 1)
            deleted = getActivity().getContentResolver().delete(MoviesContract.TopRatedMoviesEntry.CONTENT_URI, null, null);
        return deleted;
    }

    private ContentValues update_popular_columns(ContentValues movieValues, int i) {
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_MOVIE_ID, moviesList.get(i).get_id());
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_TITLE, moviesList.get(i).getTitle());
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_RELEASE_DATE, update_date_format(moviesList.get(i).getRelease_date()));
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_POSTER_PATH, moviesList.get(i).getPoster_path());
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_RATE, moviesList.get(i).getRate());
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_VIEW_COUNT, moviesList.get(i).getView_count());
        movieValues.put(MoviesContract.PopularMoviesEntry.COLUMN_OVERVIEW, moviesList.get(i).getOver_view());
        return movieValues;
    }

    private ContentValues update_top_columns(ContentValues movieValues, int i) {
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_MOVIE_ID, moviesList.get(i).get_id());
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_TITLE, moviesList.get(i).getTitle());
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_RELEASE_DATE, update_date_format(moviesList.get(i).getRelease_date()));
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_POSTER_PATH, moviesList.get(i).getPoster_path());
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_RATE, moviesList.get(i).getRate());
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_VIEW_COUNT, moviesList.get(i).getView_count());
        movieValues.put(MoviesContract.TopRatedMoviesEntry.COLUMN_OVERVIEW, moviesList.get(i).getOver_view());
        return movieValues;
    }

    private String update_date_format(String date) {
        date.replace("/", "");
        return date;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        Uri moviesUri;
        if (sorting_type == 0)
            moviesUri = MoviesContract.PopularMoviesEntry.buildPopularUri();
        else if (sorting_type == 1)
            moviesUri = MoviesContract.TopRatedMoviesEntry.buildTopRatedUri();
        else
            moviesUri = MoviesContract.FavouriteMoviesEntry.buildFavouriteUri();

        return new CursorLoader(getActivity(), moviesUri,
                null, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }
}
