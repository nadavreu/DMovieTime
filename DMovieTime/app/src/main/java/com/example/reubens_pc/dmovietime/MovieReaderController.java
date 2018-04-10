package com.example.reubens_pc.dmovietime;

import android.app.Activity;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MovieReaderController extends MovieController {
    public Activity contex;

    public List<Movie> giveMovies(){
        return allMoviesData;
    }

    public MovieReaderController(Activity activity) {
        super(activity);
        contex =activity;
    }

    public void readAllMovies(String name) {
        HttpRequest httpRequest = new HttpRequest(this);
        Locale locale = Locale.getDefault();
        String locale2=locale.toString();
        if(locale2.equals("iw_IL")) {
            httpRequest.execute("https://api.themoviedb.org/3/search/movie?api_key=fdbafdad226138d461dcb4c9b2d663f5&query=" + name + "&language=he");
        }else {
            httpRequest.execute("https://api.themoviedb.org/3/search/movie?api_key=fdbafdad226138d461dcb4c9b2d663f5&query="+name+"&page=1");
        }
    }

    public void onSuccess(String downloadedText) {

        try {
            List<Movie> tempList = new ArrayList<>();
            JSONObject jsonArray = new JSONObject(downloadedText);

            JSONArray resultArray = jsonArray.getJSONArray("results");



            Movies = new ArrayList<>();


            for (int i = 0; i < resultArray.length(); i++) {


                JSONObject jsonObject = resultArray.getJSONObject(i);
                int orderNumber = jsonObject.getInt("id");
                String name = jsonObject.getString("title");
                String desc = jsonObject.getString("overview");
                String image = jsonObject.getString("poster_path");

                Movie movie = new Movie(name,desc,image,orderNumber);
                tempList.add(i, movie);

                Movies.add(name);
            }

            allMoviesData = tempList;
            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, Movies);


            listViewMovies.setAdapter(adapter);

        }
        catch (JSONException ex) {

        }


        progressDialog.dismiss();


    }
}