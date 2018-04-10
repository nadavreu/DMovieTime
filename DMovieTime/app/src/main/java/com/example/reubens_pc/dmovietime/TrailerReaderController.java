package com.example.reubens_pc.dmovietime;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class TrailerReaderController extends TrailerController {
    String trailer="";
    public Activity context;
    public int aSwitch;

    public TrailerReaderController(Activity activity) {
        super(activity);
        context = activity;

    }
    public void getTrailer(int no, int num) {
        //aSwitch shows if its an existing movie in my list or its random that sent by me
        aSwitch = num;
        HttpRequest httpRequest = new HttpRequest(this);
        httpRequest.execute("https://api.themoviedb.org/3/movie/"+no+"/videos?api_key=fdbafdad226138d461dcb4c9b2d663f5&language=en-US");

    }
    public void onSuccess(String downloadedText) {

        try {


            JSONObject jsonObject = new JSONObject(downloadedText);
            JSONArray results= jsonObject.getJSONArray("results");

            if(results.equals("")){
                trailer="";
            }else {
                JSONObject tr=results.getJSONObject(0);
                trailer = tr.getString("key");
            }
            // sending it to the layout
            String baseYouTube = "https://www.youtube.com/watch?v=";
            String video = baseYouTube + trailer;
            Trailer trailerUrl = new Trailer(video);
            FullMovieActivity.setTrailer(trailerUrl);

        } catch (JSONException ex) {

            Toast.makeText(context, "waiting for respond", Toast.LENGTH_SHORT).show();
        }


        progressDialog.dismiss();


    }
}