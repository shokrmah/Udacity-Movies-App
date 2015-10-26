package com.example.shokr1.moviesapp;

import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;



public class MovieDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details_fragment);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putStringArray(MovieDetailsFragment.DETAIL_MOVIE_KEY,arguments.getStringArray(MovieDetailsFragment.DETAIL_MOVIE_KEY));
            MovieDetailsFragment fragment = new MovieDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_detail, fragment)
                    .commit();
}
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {

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





}
