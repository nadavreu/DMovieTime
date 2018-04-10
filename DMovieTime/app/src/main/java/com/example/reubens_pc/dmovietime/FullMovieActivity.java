package com.example.reubens_pc.dmovietime;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Locale;
import java.util.UUID;

import dmax.dialog.SpotsDialog;

public class FullMovieActivity extends AppCompatActivity{
    // the activity to show all the details about a movie we have on our list

    public FullMovieReaderController fullMovieReaderController;
    public TrailerReaderController trailerReaderController;
    static LinearLayout l;
    static LinearLayout movieLinear;
    static TextView head;
    static TextView description;
    static ImageView image;
    static TextView vote;
    static RatingBar rate;
    static TextView date;
    static TextView budget;
    static TextView runtime;
    static WebView trailer;
    static Activity activity;
    static Context context;
    static String trUrl;
    static Button trailBut;
    static String movieName;
    static String movieScore;
    int aSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_movie);

        movieLinear=(LinearLayout) findViewById(R.id.fullMovie);
        trailBut=(Button)findViewById(R.id.trailbut);
        fullMovieReaderController = new FullMovieReaderController(this);
        trailerReaderController = new TrailerReaderController(this);
        Intent i = getIntent();
        int No =i.getIntExtra("No",0);
        aSwitch =i.getIntExtra("switch",0);
        fullMovieReaderController.getFullMovie(No,aSwitch);
        trailerReaderController.getTrailer(No,aSwitch);
        l = (LinearLayout) findViewById(R.id.l);
        head = (TextView) findViewById(R.id.title);
        description = (TextView) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.imageView);
        vote = (TextView) findViewById(R.id.vote_average);
        rate = (RatingBar) findViewById(R.id.MyRating);
        date = (TextView) findViewById(R.id.release_date);
        budget = (TextView) findViewById(R.id.budget);
        runtime = (TextView) findViewById(R.id.runtime);
        trailer = (WebView) findViewById(R.id.trailer);
        activity = this;
        context = this;
        trailer.setBackground(context.getResources().getDrawable(R.drawable.film_roll));



    }
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.share,menu);

        return true;

    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.share:
               share();
                return true;
        }


        return false;


    }

    @Override
    protected void onPause() {
        super.onPause();
        if(aSwitch==1) {
            finish();
        }
    }
    //---------this method are used in another class and checks if there is empty places in the details
    public static void setAllDetails(FullMovie movie){


        head.setText(movie.getSubject());
        description.setText(movie.getBody());

        if (movie.getUrl().equals("")) {
            image.setBackgroundResource(R.drawable.film_roll);
            image.getBackground().setAlpha(150);
        } else {
            new LoadImage(activity,l,context, image, movie.getUrl()).execute();
        }

        String voteText;
        if(0==movie.getVote_average()){
            rate.setVisibility(View.GONE);
            vote.setVisibility(View.VISIBLE);
            voteText = "there are no information on this detail";
        }else {
            voteText = movie.getVote_average()+"";
            if(7<=movie.getVote_average()){
                movieLinear.setBackgroundResource(R.drawable.good_stars);
            }else{
                movieLinear.setBackgroundResource(R.drawable.falling_stars);
            }
        }

        vote.setText("Score: "+voteText);
        rate.setNumStars(5);
        rate.setMax(5);
        float rating = (float) 0.5*movie.getVote_average();
        rate.setStepSize((float)0.05);
        rate.setRating(rating);
        Locale locale = Locale.getDefault();
        String locale2=locale.toString();
        if(locale2.equals("iw_IL")) {
            date.setText("תאריך הפצה: " + movie.getRelease_date());
        }else {
            date.setText("Release Date: " + movie.getRelease_date());
        }
        String money;
        if(0==movie.getBudget()){
            if(locale2.equals("iw_IL")) {
                money = "לא נימצא תקציב לסרט זה :(";
            }else {
                money = "there are no information on this detail";
            }
        }else {
            money = movie.getBudget() + "";
        }
        if(locale2.equals("iw_IL")) {
            budget.setText("תקציב: " + money);
        }else {
            budget.setText("Budget: " + money);
        }
        if(0!=movie.getRuntime()) {
            int hours = movie.getRuntime() / 60;
            int minutes = movie.getRuntime() % 60;
            if(locale2.equals("iw_IL")) {
                runtime.setText("אורך הסרט: " + hours + " שעות ו" + minutes + " דקות");
            }else {
                runtime.setText("Movie length: " + hours + " hours and " + minutes + " minutes");
            }
        }else{
            if(locale2.equals("iw_IL")) {
                runtime.setText("אורך הסרט: לא נימצא אורך על סרט זה :(");
            }else {
                runtime.setText("Movie length: there are no information on this detail");
            }
        }

        movieName = movie.getSubject().toString();
        movieScore = movie.getVote_average()+"";
    }
    public  static void setTrailer(final Trailer trailerUrl){

        trUrl=trailerUrl.getTrailer();
        if(trUrl.equals("")){
            trailBut.setVisibility(View.GONE);
        }




        //trailer.setVideoURI(Uri.parse(trailerUrl.getTrailer()));

        //trailer.setText(trailerUrl.getTrailer());
    }
    public void webViewClick(View v){
        trailer.loadUrl(trUrl);
    }
    public void share(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "You need to see this movie "+movieName+"its amazing!!!");
        sendIntent.setType("text/plain");
        startActivity(sendIntent.createChooser(sendIntent, getResources().getText(R.string.app_name)));
    }
}
