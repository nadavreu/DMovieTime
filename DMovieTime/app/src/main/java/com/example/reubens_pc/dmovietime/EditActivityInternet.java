package com.example.reubens_pc.dmovietime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class EditActivityInternet extends AppCompatActivity {

    public ListView listViewMovies;
    public List<Movie> tempMovies;
    public MovieReaderController MovieReaderController;
    public Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_internet);
        getSupportActionBar().hide();

        mContext = App.getContext();

        listViewMovies = (ListView) findViewById(R.id.listView);
        MovieReaderController = new MovieReaderController(this);

        listViewMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                send(i);
            }
        });
    }

    public void send(int i){
        String tempName = listViewMovies.getAdapter().getItem(i).toString();
        String baseImageUrl = "http://image.tmdb.org/t/p/w185";//the string we are getting from the api is only part of the url
        String baseYouTube = "https://www.youtube.com/watch?v=";

        tempMovies = MovieReaderController.giveMovies();

        for(int j = 0; j< tempMovies.size(); j++){
            if(tempName.equals(tempMovies.get(j).getSubject())){
                if(i==j){
                    String description = tempMovies.get(j).getBody();
                    String imageUrl = baseImageUrl + tempMovies.get(j).getUrl();
                    int No = tempMovies.get(j).getOrderNumber();

                    Intent editActivity = new Intent(this,EditActivity.class);
                    editActivity.putExtra("No",No);
                    editActivity.putExtra("title",tempName);
                    editActivity.putExtra("des",description);
                    editActivity.putExtra("url",imageUrl);
                    editActivity.putExtra("id",1);
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).startActivityForResult(editActivity, 1);
                        finish();
                    }
                }
            }
        }
    }

    public void showMovies(View v){
        EditText nameText = (EditText) findViewById(R.id.name);
        String name = nameText.getText().toString();
        MovieReaderController.readAllMovies(name);
    }

    public void cancel(View v){
        finish();
    }
}