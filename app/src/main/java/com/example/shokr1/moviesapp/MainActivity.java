package com.example.shokr1.moviesapp;

import android.content.Intent;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    boolean mTwoPane = false;
    int sorting = 0;
    private static final String DETAILFRAGMENT_TAG = "DTAG";

    public static String BEST_WIDTH = "w92";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sorting = Utility.update_sorting_type(this);
        setContentView(R.layout.activity_main);

        image_width();

        if (findViewById(R.id.fragment_detail) != null) {
            mTwoPane = true;
            if (savedInstanceState != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_detail, new MovieDetailsFragment(),DETAILFRAGMENT_TAG)
                        .commit();
            }

        } else {
            mTwoPane = false;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));

            return true;
        }
        else  if (id == R.id.action_share) {

            String Link = getLink();
            if (Link == null) {
                Toast.makeText(this, "Sorry, There is no links to share", Toast.LENGTH_LONG).show();
                return true;
            }

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = Link;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share trailer to");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private String getLink() {
        //Get youtube links ListView
        ListView movie_links = (ListView) findViewById(R.id.youtube_links_list);
        //if it's not null get first video link to share
        //header is the first element in listview so must share position 1
        if (movie_links != null) {
            if (movie_links.getCount() > 1) {
                String link = (String) movie_links.getItemAtPosition(1);
                if (!link.startsWith("http"))
                    return null;
                else
                    return link;
            }
        }

        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    @Override
    public void onItemSelected(String[] movie_data) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putStringArray(MovieDetailsFragment.DETAIL_MOVIE_KEY, movie_data);

            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail, fragment,DETAILFRAGMENT_TAG)
                    .commit();
        } else {

            Intent intent = new Intent(this, MovieDetails.class)
                    .putExtra("movie_data", movie_data);
            startActivity(intent);
        }
    }

    private int getHalfWidth() {
        String best_match = "";
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        return width / 2;
    }

    private void image_width() {
        int width = getHalfWidth();
        if (width >= 780)
            BEST_WIDTH = "w780";
        else if (width >= 500)
            BEST_WIDTH = "w500";
        else if (width >= 342)
            BEST_WIDTH = "w342";
        else if (width >= 185)
            BEST_WIDTH = "w185";
        else if (width >= 154)
            BEST_WIDTH = "w154";


    }
}
