package com.example.shokr1.moviesapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shokr1.moviesapp.data.MoviesContract;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.ExecutionException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    static final String DETAIL_MOVIE_KEY = "Movie_Key";


    public MovieDetailsFragment() {
    }

    ImageView star;
    ArrayAdapter videosAdapter;
    public static Movie SELECTED_MOVIE;
    ArrayList<String> links;
    ArrayList<String> review_contents;

    private String getYearFromDate(String date) {
        return date.substring(0, 4);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] movie_data;
        Bundle arguments = getArguments();
        if (arguments != null) {
            if(arguments.getStringArray(DETAIL_MOVIE_KEY) != null)
            movie_data = arguments.getStringArray(DETAIL_MOVIE_KEY);
            else
                movie_data = getActivity().getIntent().getExtras().getStringArray("movie_data");
        }
        else
            movie_data = getActivity().getIntent().getExtras().getStringArray("movie_data");

//        private long _id;
//        private String title;
//        private String release_date;
//        private String poster_path;
//        private double rate;
//        private int vote_count;
//        private String over_view;

        //View header = inflater.inflate(R.layout.detail_fragment_header, container, false);
        View header = inflater.inflate(R.layout.detail_fragment_header, null, false);


        TextView title = (TextView) header.findViewById(R.id.title);
        title.setText(movie_data[1]);


        TextView date = (TextView) header.findViewById(R.id.release_date);
        date.setText(getYearFromDate(movie_data[2]));


        ImageView poster = (ImageView) header.findViewById(R.id.poster_movie);
        String url = "http://image.tmdb.org/t/p/w500/" + movie_data[3];
        Picasso.with(getActivity()).load(url).into(poster);


        TextView rate = (TextView) header.findViewById(R.id.rate);
        rate.setText(movie_data[4] + "/10");
        //TextView view_count = (TextView) header.findViewById(R.id.view_count);
        //view_count.setText(movie_data[5]);

        TextView overview = (TextView) header.findViewById(R.id.overview);
        overview.setText(movie_data[6]);

        SELECTED_MOVIE = new Movie(Long.valueOf(movie_data[0]), movie_data[1], movie_data[2], movie_data[3],
                Double.valueOf(movie_data[4]), Integer.valueOf(movie_data[5]), movie_data[6]);

        star = (ImageView) header.findViewById(R.id.favorite);



        //String [] filter = {MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID,String.valueOf(MovieDetailsFragment.SELECTED_MOVIE.get_id())};
        String selection = MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(MovieDetailsFragment.SELECTED_MOVIE.get_id());
        Uri uri = MoviesContract.FavouriteMoviesEntry.buildFavouriteMovieWithIDUri(SELECTED_MOVIE.get_id());
        final Cursor cur = getActivity().getContentResolver().query(uri,
                null, selection, null, null);
        if (cur.getCount() > 0)
            star.setImageResource(android.R.drawable.btn_star_big_on);
        else
            star.setImageResource(android.R.drawable.btn_star_big_off);

        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                image_click_code(star);
            }
        });


        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        //I should add fragments for each list
        links = new ArrayList<String>();
        review_contents = new ArrayList<String>();

        try {
            updateLinks("videos");
            updateLinks("reviews");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        links.addAll(review_contents);
        videosAdapter = new ArrayAdapter(getActivity(), R.layout.trailers_list_item, R.id.trailer_link, links);

        ListView videosList = (ListView) view.findViewById(R.id.youtube_links_list);
        videosList.addHeaderView(header);
        videosList.setAdapter(videosAdapter);


        videosList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Movie movieSelected = (Movie) (parent.getItemAtPosition(position));
                String choosen_link = (String) (parent.getItemAtPosition(position));
                //String video_link = choosen_link.getText().toString();
                if (!choosen_link.startsWith("http"))
                    return;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(choosen_link));
                startActivity(intent);
            }
        });


        return view;
    }

    private void updateLinks(String link_type) throws ExecutionException, InterruptedException {
        String[] inputs = {String.valueOf(SELECTED_MOVIE.get_id()), link_type};
        FetchLinksReviews fetchLinksReviews = new FetchLinksReviews();


        if (link_type.equals("videos")) {
            links = fetchLinksReviews.execute(inputs).get();
            if (links.size() > 0) {
               delete_trailer_fromDB();
               insert_links_toDB();
            }
        } else {
            review_contents = fetchLinksReviews.execute(inputs).get();
            if (review_contents.size() > 0) {
                delete_revies_fromDB();
                insert_reviews_toDB();
            }
        }

    }


    private void image_click_code(ImageView star)
    {

        String selection = MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
        Uri uri_query = MoviesContract.FavouriteMoviesEntry.buildFavouriteMovieWithIDUri(SELECTED_MOVIE.get_id());
        Cursor cur = getActivity().getContentResolver().query(uri_query,
                null, selection, null, null);
        if (cur.getCount() > 0) {
            getActivity().getContentResolver().delete(uri_query,selection,null);
            star.setImageResource(android.R.drawable.btn_star_big_off);
        }
        else {
            ContentValues movieValues = new ContentValues();
            movieValues = update_favourite_columns_star(movieValues);

            getActivity().getContentResolver().insert(uri_query,movieValues);
            star.setImageResource(android.R.drawable.btn_star_big_on);
        }
    }

    private ContentValues update_favourite_columns_star(ContentValues movieValues)
    {
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_MOVIE_ID, MovieDetailsFragment.SELECTED_MOVIE.get_id());
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_TITLE, MovieDetailsFragment.SELECTED_MOVIE.getTitle());
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_RELEASE_DATE, update_date_format(MovieDetailsFragment.SELECTED_MOVIE.getRelease_date()));
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_POSTER_PATH, MovieDetailsFragment.SELECTED_MOVIE.getPoster_path());
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_RATE, MovieDetailsFragment.SELECTED_MOVIE.getRate());
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_VIEW_COUNT, MovieDetailsFragment.SELECTED_MOVIE.getView_count());
        movieValues.put(MoviesContract.FavouriteMoviesEntry.COLUMN_OVERVIEW, MovieDetailsFragment.SELECTED_MOVIE.getOver_view());
        return movieValues;
    }

    private String update_date_format(String date)
    {
        date.replace("/", "");
        return date;
    }



    private int delete_trailer_fromDB() {
        int sorting_type = Utility.update_sorting_type(getActivity());

        int deleted = 0;
        if (sorting_type == 0) {
            String selection = MoviesContract.PopularTrailerEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
            deleted = getActivity().getContentResolver().delete(MoviesContract.PopularTrailerEntry.buildPopularTrailerUri(SELECTED_MOVIE.get_id()),
                    selection, null);
        } else if (sorting_type == 1) {
            String selection = MoviesContract.TopRatedTrailerEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
            deleted = getActivity().getContentResolver().delete(MoviesContract.TopRatedTrailerEntry.buildTopRatedTrailerUri(SELECTED_MOVIE.get_id()),
                    selection, null);
        } else {
            String selection = MoviesContract.FavouriteTrailerEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
            deleted = getActivity().getContentResolver().delete(MoviesContract.FavouriteTrailerEntry.buildFavouriteTrailerUri(SELECTED_MOVIE.get_id()),
                    selection, null);
        }
        return deleted;
    }

    private int delete_revies_fromDB() {
        int sorting_type = Utility.update_sorting_type(getActivity());

        int deleted = 0;
        if (sorting_type == 0) {
            String selection = MoviesContract.PopularReviewEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
            deleted = getActivity().getContentResolver().delete(MoviesContract.PopularReviewEntry.buildPopularReviewUri(SELECTED_MOVIE.get_id()),
                    selection, null);
        } else if (sorting_type == 1) {
            String selection = MoviesContract.TopRatedReviewEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
            deleted = getActivity().getContentResolver().delete(MoviesContract.TopRatedReviewEntry.buildTopRatedReviewUri(SELECTED_MOVIE.get_id()),
                    selection, null);
        } else {
            String selection = MoviesContract.FavouriteReviewEntry.COLUMN_MOVIE_ID + " = " + String.valueOf(SELECTED_MOVIE.get_id());
            deleted = getActivity().getContentResolver().delete(MoviesContract.FavouriteReviewEntry.buildFavouriteReviewUri(SELECTED_MOVIE.get_id()),
                    selection, null);
        }
        return deleted;
    }

    private int insert_links_toDB() {
        //sorting type 0 means popular, 1 means top rated, 2 means favourite

        Vector<ContentValues> cVVector = new Vector<ContentValues>(links.size());

        int sorting_type = Utility.update_sorting_type(getActivity());

        for (int i = 0; i < links.size(); i++) {

            ContentValues links_values = new ContentValues();
            if (sorting_type == 0)
                links_values = update_popular_columns(links_values, i);
            else if (sorting_type == 1)
                links_values = update_top_columns(links_values, i);
            else
                links_values = update_favourite_columns(links_values, i);

            cVVector.add(links_values);
        }

        int inserted_items = 0;
        // add to database
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            if (sorting_type == 0)
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.PopularTrailerEntry.buildPopularTrailerUri(SELECTED_MOVIE.get_id()),
                        cvArray);
            else if (sorting_type == 1)
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.TopRatedTrailerEntry.buildTopRatedTrailerUri(SELECTED_MOVIE.get_id()),
                        cvArray);
            else
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.FavouriteTrailerEntry.buildFavouriteTrailerUri(SELECTED_MOVIE.get_id()),
                        cvArray);

        }

        return inserted_items;

    }

    private ContentValues update_popular_columns(ContentValues links_values, int i) {
        links_values.put(MoviesContract.PopularTrailerEntry.COLUMN_MOVIE_ID, SELECTED_MOVIE.get_id());
        links_values.put(MoviesContract.PopularTrailerEntry.COLUMN_TRAILER_LINK, links.get(i));
        return links_values;
    }

    private ContentValues update_top_columns(ContentValues links_values, int i) {
        links_values.put(MoviesContract.TopRatedTrailerEntry.COLUMN_MOVIE_ID, SELECTED_MOVIE.get_id());
        links_values.put(MoviesContract.TopRatedTrailerEntry.COLUMN_TRAILER_LINK, links.get(i));
        return links_values;
    }

    private ContentValues update_favourite_columns(ContentValues links_values, int i) {
        links_values.put(MoviesContract.FavouriteTrailerEntry.COLUMN_MOVIE_ID, SELECTED_MOVIE.get_id());
        links_values.put(MoviesContract.FavouriteTrailerEntry.COLUMN_TRAILER_LINK, links.get(i));
        return links_values;
    }

    private int insert_reviews_toDB() {
        //sorting type 0 means popular, 1 means top rated, 2 means favourite

        Vector<ContentValues> cVVector = new Vector<ContentValues>(review_contents.size());

        int sorting_type = Utility.update_sorting_type(getActivity());

        for (int i = 0; i < review_contents.size(); i++) {

            ContentValues reviews_values = new ContentValues();
            if (sorting_type == 0)
                reviews_values = update_popular_reviews_columns(reviews_values, i);
            else if (sorting_type == 1)
                reviews_values = update_top_reviews_columns(reviews_values, i);
            else
                reviews_values = update_favourite_reviews_columns(reviews_values, i);

            cVVector.add(reviews_values);
        }

        int inserted_items = 0;
        // add to database
        if (cVVector.size() > 0) {
            ContentValues[] cvArray = new ContentValues[cVVector.size()];
            cVVector.toArray(cvArray);

            if (sorting_type == 0)
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.PopularReviewEntry.buildPopularReviewUri(SELECTED_MOVIE.get_id()),
                        cvArray);
            else if (sorting_type == 1)
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.TopRatedReviewEntry.buildTopRatedReviewUri(SELECTED_MOVIE.get_id()),
                        cvArray);
            else
                inserted_items = getActivity().getContentResolver().bulkInsert(MoviesContract.FavouriteReviewEntry.buildFavouriteReviewUri(SELECTED_MOVIE.get_id()),
                        cvArray);

        }

        return inserted_items;

    }

    private ContentValues update_popular_reviews_columns(ContentValues reviews_values, int i) {
        reviews_values.put(MoviesContract.PopularReviewEntry.COLUMN_MOVIE_ID, SELECTED_MOVIE.get_id());
        reviews_values.put(MoviesContract.PopularReviewEntry.COLUMN_REVIEW, review_contents.get(i));
        return reviews_values;
    }

    private ContentValues update_top_reviews_columns(ContentValues reviews_values, int i) {
        reviews_values.put(MoviesContract.TopRatedReviewEntry.COLUMN_MOVIE_ID, SELECTED_MOVIE.get_id());
        reviews_values.put(MoviesContract.TopRatedReviewEntry.COLUMN_REVIEW, review_contents.get(i));
        return reviews_values;
    }

    private ContentValues update_favourite_reviews_columns(ContentValues reviews_values, int i) {
        reviews_values.put(MoviesContract.FavouriteReviewEntry.COLUMN_MOVIE_ID, SELECTED_MOVIE.get_id());
        reviews_values.put(MoviesContract.FavouriteReviewEntry.COLUMN_REVIEW, review_contents.get(i));
        return reviews_values;
    }


}
