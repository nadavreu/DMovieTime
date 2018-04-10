package com.example.reubens_pc.dmovietime;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    MovieDB db;
    LinearLayout layout;
    List<Movie> names;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private static final int PERMISSION_REQUEST_CODE=1000;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request permission to phone media
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },PERMISSION_REQUEST_CODE);

        //declaring toggle for the side bar, navigation bar
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawerLayout);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView mNavigationView=(NavigationView)findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        App.setContext(this); //i use this for later use to declare startActivityForResult from another Activity.
        db = new MovieDB(this);//start my data base
        layout = (LinearLayout) findViewById(R.id.linearLayoutMain);//the outer layout- so i could use it in addPicture();
        loadMovie();
    }
    public void loadMovie(){
        names =db.getAllMovieList();//get list of movies from the Movie data base
        int i =0;
        if (names.size() == i) {
            TextView empty = (TextView) findViewById(R.id.empty);
            empty.setVisibility(View.VISIBLE);
        } else {
            while(i<names.size()) {
                String s = names.get(i).getSubject();
                String u = names.get(i).getUrl();
                String d = names.get(i).getBody();
                int id =names.get(i).get_id();
                int No = names.get(i).getOrderNumber();
                int watched = names.get(i).getWatched();
                makeMovie(s,d,u,id,No,watched);

                i++;
            }
        }
    }
    public void makeMovie(String name,String description , String url,int id,int orderNumber,int watched) {

        ImageView image = new ImageView(this);
        TextView title = new TextView(this);
        TextView des = new TextView(this);
        TextView hint = new TextView(this);
        TextView watchNumberTextView = new TextView(this);
        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout innerLayout = new LinearLayout(this);
        LinearLayout movieImage = new LinearLayout(this);
        final Button goPageButton = new Button(this);
        Button watchButton = new Button(this);
        //giving the element params
        linearLayoutImageInsideResize(movieImage);
        buttonResize(goPageButton);
        linearLayoutInsideResize(innerLayout);
        textDesResize(des);
        textViewWatchNumberResize(hint);
        hint.setText(getString(R.string.watch));
        linearLayoutResize(linearLayout);
        imageViewResize(image);
        textViewResize(title);
        watchButton.setTag(id);
        setAddWatch(watchButton,watched,watchNumberTextView);
        addPicture(image, url);

        image.setTag(name);
        goPageButton.setTag(orderNumber);

        goPageButton.setText(R.string.moviepage);
        goPageButton.setTextSize(13);
        goPageButton.setBackground(getDrawable(R.drawable.buttons));
        goPageButton.setTextColor(getColor(R.color.white));
        title.setText(name);
        des.setText(description);
// calling the dialog EditChanges
        final EditChanges cdd = new EditChanges(MainActivity.this, name, id);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEdit(view);
            }
        });
        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                cdd.show();
                return false;
            }
        });

        goPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int orderNumber = (int)goPageButton.getTag();
                goToFullMovieActivity(orderNumber);
            }
        });

        innerLayout.addView(title);
        innerLayout.addView(des);

        if(orderNumber!=0) {
            innerLayout.addView(goPageButton);
        }
        movieImage.addView(image);

        movieImage.addView(watchNumberTextView);
        movieImage.addView(watchButton);
        movieImage.addView(hint);

        linearLayout.addView(movieImage);
        linearLayout.addView(innerLayout);

        layout.addView(linearLayout);
    }
    public void buttonResize(Button sv){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(positionRules);
        positionRules.setMargins(10, 10, 10, 10);
    }
    public void linearLayoutImageInsideResize(LinearLayout layoutImage){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutImage.setLayoutParams(positionRules);
        layoutImage.setGravity(Gravity.CENTER);
        layoutImage.setOrientation(LinearLayout.VERTICAL);
    }
    public void linearLayoutInsideResize(LinearLayout innerLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        innerLayout.setLayoutParams(positionRules);
        innerLayout.getLayoutParams().height = 1050;
        positionRules.setMargins(25,0, 0, 5);
        innerLayout.setOrientation(LinearLayout.VERTICAL);
    }
    public void textDesResize(TextView des){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        des.setLayoutParams(positionRules);
        des.setTextColor(Color.WHITE);
        des.setTextSize(13);
        positionRules.setMargins(5,5, 5, 0);
        des.getLayoutParams().height = 425;
    }
    public void linearLayoutResize(LinearLayout linearLayout){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(positionRules);
        positionRules.setMargins(25, 25, 25, 25);
        linearLayout.setLayoutParams(positionRules);
        linearLayout.getLayoutParams().height = 1050;
        linearLayout.setBackgroundResource(R.drawable.layoutstyle);
    }
    public void textViewResize(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextColor(Color.WHITE);
        b.setTextSize(25);
        positionRules.setMargins(15,0, 15, 15);
    }
    public void textViewWatchNumberResize(TextView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        b.setTextSize(15);
        b.setTextColor(Color.WHITE);

    }
    public void watchButtonResize(Button sv){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(positionRules);
        sv.getLayoutParams().height = 80;
        sv.getLayoutParams().width = 80;
        sv.setBackground(getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
    }
    public void imageViewResize(ImageView b){
        LinearLayout.LayoutParams positionRules = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        b.setLayoutParams(positionRules);
        positionRules.setMargins(15, 15, 25, 0);
        b.getLayoutParams().height = 710;
        b.getLayoutParams().width = 400;
    }
    public void setAddWatch(final Button b ,int watched,final TextView watchNumberTextView){

        textViewWatchNumberResize(watchNumberTextView);
        watchButtonResize(b);
        if(watched!=0){
            b.setBackground(getDrawable(R.drawable.ic_check_box_black_24dp));
        }
        String watchString = watched+"";
        watchNumberTextView.setText(watchString);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = Integer.parseInt(view.getTag().toString());
                addWatch(b,watchNumberTextView,id);

            }
        });

        b.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int id = Integer.parseInt(view.getTag().toString());
                resetWatch(b,watchNumberTextView,id);
                return false;
            }
        });

    }
    public void addWatch(Button b,TextView watchNumberTextView,int id){
        int watched=0;
        for(int p=0;p<names.size();p++){
            if(id==names.get(p).get_id()){
                names.get(p).setWatched(names.get(p).getWatched()+1);
                watched = names.get(p).getWatched();
                db.updateWatch(id);
            }
        }
        if(watched>0){
            b.setBackground(getDrawable(R.drawable.ic_check_box_black_24dp));
        }
        String watch = watched+"";
        watchNumberTextView.setText(watch);
    }
    public void resetWatch(Button b,TextView watchNumberTextView,int id){
        int watched=0;
        for(int p=0;p<names.size();p++){
            if(id==names.get(p).get_id()){
                names.get(p).setWatched(0);
                watched = names.get(p).getWatched();
                db.resetWatch(id);
            }
        }
        b.setBackground(getDrawable(R.drawable.ic_check_box_outline_blank_black_24dp));
        String watch = watched+"";
        watchNumberTextView.setText(watch);
    }
    public void addPicture(ImageView b,String u) {
        if (u.equals("")) {
            b.setBackgroundResource(R.drawable.film_roll);
            b.getBackground().setAlpha(150);
        } else {
            new LoadImage(this, layout,this, b, u).execute();
        }
    }
    public void goToFullMovieActivity(int No){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Intent FullMovieActivity = new Intent(this,FullMovieActivity.class);
            FullMovieActivity.putExtra("No",No);
            FullMovieActivity.putExtra("switch",0);
            startActivity(FullMovieActivity);
        }else{
            Toast.makeText(this,"there is no internet connection",Toast.LENGTH_SHORT).show();
        }



    }
    public void goToEdit(View v){
        String movieTitle = v.getTag().toString();
//sending all the movie details to the editActivity
        for(int i=0;i<names.size();i++){
            if(movieTitle.equals(names.get(i).getSubject())){
                String title =names.get(i).getSubject();
                String des =names.get(i).getBody();
                String url =names.get(i).getUrl();
                int id = names.get(i).get_id();

                Intent editActivity = new Intent(this,EditActivity.class);
                editActivity.putExtra("name",title);
                editActivity.putExtra("des",des);
                editActivity.putExtra("url",url);
                editActivity.putExtra("id",id);
                this.startActivityForResult(editActivity,1);

            }
        }
    }
    public void deleteall(){
        db.clear();
        restart();
    }
    public void restart(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
        this.finishAffinity();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.menu_bar,menu);




        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int itemId = item.getItemId();

        switch (itemId) {

            case R.id.exitApp:
                finish();
                System.exit(0);
                return true;

        }

        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// getting the extras
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                int No=data.getIntExtra("No",0);
                String name=data.getStringExtra("name");
                String des=data.getStringExtra("des");
                String url1=data.getStringExtra("url");

                if(des.equals("")) {
                    if ( url1.equals("")) {
                        Movie Movie = new Movie(name,"","",No);
                        Movie.setWatched(0);
                        db.addMovie(Movie);
                    } else {
                        Movie Movie = new Movie(name, "", url1,No);
                        Movie.setWatched(0);
                        db.addMovie(Movie);
                    }
                }else if(url1.equals("")){
                    Movie Movie = new Movie(name,des,"",No);
                    Movie.setWatched(0);
                    db.addMovie(Movie);
                }else{
                    Movie Movie = new Movie(name,des,url1,No);
                    Movie.setWatched(0);
                    db.addMovie(Movie);
                }


                this.recreate();



            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        NavigationView myNavi= (NavigationView) findViewById(R.id.navigation_view);
        Menu myMenu=myNavi.getMenu();
        int itemId = item.getItemId();
        if(itemId==R.id.addMovie) {
            boolean b=!myMenu.findItem(R.id.addManually).isVisible();
            myMenu.findItem(R.id.addManually).setVisible(b);
            myMenu.findItem(R.id.addFromInternet).setVisible(b);
        }
        if(itemId==R.id.addManually) {
            Intent add = new Intent(this,EditActivity.class);
            add.putExtra("id",-1);//the id i sent is to get recognized at the next activity that its adding movie that i want
            startActivityForResult(add,1);
        }
        if(itemId==R.id.addFromInternet) {
            Intent net = new Intent(this,EditActivityInternet.class);
            startActivity(net);
        }

        if(itemId==R.id.removeMovie) {
            boolean b=myMenu.findItem(R.id.addManually).isVisible();
            if(b) {
                myMenu.findItem(R.id.addManually).setVisible(!b);
                myMenu.findItem(R.id.addFromInternet).setVisible(!b);
            }
            deleteDialog();
        }
        return false;
    }
    public void deleteDialog(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Delete all movies")
                .setMessage("Are you sure you want to delete all the movies?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteall();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}
