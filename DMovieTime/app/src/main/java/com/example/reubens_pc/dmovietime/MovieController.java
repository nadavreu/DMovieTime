package com.example.reubens_pc.dmovietime;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;



public abstract class MovieController  implements HttpRequest.Callbacks {

    protected static ArrayList<String> Movies;
    protected Activity activity;
    protected ProgressDialog progressDialog;
    protected ListView listViewMovies;
    public List<Movie> allMoviesData;


    public MovieController(Activity activity) {
        this.activity = activity;
        listViewMovies = (ListView)activity.findViewById(R.id.listView);
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle("Downloading...");
        progressDialog.setMessage("Please Wait...");
    }
    public void onAboutToStart() {
        progressDialog.show();
    }
    public void onError(String errorMessage) {
        progressDialog.dismiss();
    }
}
