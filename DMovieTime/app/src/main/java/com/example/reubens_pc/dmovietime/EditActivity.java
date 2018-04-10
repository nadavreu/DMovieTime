package com.example.reubens_pc.dmovietime;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.UUID;

import dmax.dialog.SpotsDialog;



public class EditActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE=1000;

    EditText title;
    EditText description;
    EditText url1;
    LinearLayout l;
    ImageView imageView;
    int ID;
    int check;
    MovieDB db;
    int No=0;
    Button btnDownload;
    String defURL="http://bmovienation.com/wp-content/uploads/2012/02/movie-reel.jpg";
    String urlDownload;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().hide();
        imageView = (ImageView) findViewById(R.id.imageView);
        title = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        url1 = (EditText) findViewById(R.id.url);
        l = (LinearLayout) findViewById(R.id.outside);
        db = new MovieDB(this);
        btnDownload=(Button)findViewById(R.id.download);
        Intent A = getIntent();
        Bundle b = A.getExtras();

            if (b != null) {

                String NAME = (String) b.get("name");
                String d = (String) b.get("des");
                String u = (String) b.get("url");
                urlDownload=u;
                int id = (int) b.get("id");
                ID = id;

                if (null != b.get("No")) {
                    No = (int) b.get("No");
                }

                if (NAME == null) {
                    check = 0;
                    NAME = (String) b.get("title");
                } else {
                    check = -1;
                }


                title.setText(NAME);
                description.setText(d);
                url1.setText(u);

            }
        btnDownload=(Button)findViewById(R.id.download);
            //pushing the download button making sure grant permission and than download image to gallery
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (urlDownload.length() < 15) {
                    try {
                        if (ActivityCompat.checkSelfPermission(EditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(EditActivity.this, "you should grant permission", Toast.LENGTH_SHORT).show();
                            requestPermissions(new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, PERMISSION_REQUEST_CODE);
                            return;
                        } else {
                            AlertDialog dialog = new SpotsDialog(EditActivity.this);
                            dialog.show();
                            dialog.setMessage("Downloading...");

                            String fileName = UUID.randomUUID().toString() + ".jpg";
                            Picasso.with(getBaseContext())
                                    .load(defURL)
                                    .into(new SaveImageHelper(getBaseContext(),
                                            dialog,
                                            getApplicationContext().getContentResolver(),
                                            fileName,
                                            "Image Description"));

                        }
                    } catch (Exception e) {
                        Toast.makeText(EditActivity.this, "ERROR check URL exists", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    try {
                        if (ActivityCompat.checkSelfPermission(EditActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(EditActivity.this, "you should grant permission", Toast.LENGTH_SHORT).show();
                            requestPermissions(new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                            }, PERMISSION_REQUEST_CODE);
                            return;
                        } else {
                            AlertDialog dialog = new SpotsDialog(EditActivity.this);
                            dialog.show();
                            dialog.setMessage("Downloading...");

                            String fileName = UUID.randomUUID().toString() + ".jpg";
                            Picasso.with(getBaseContext())
                                    .load(urlDownload)
                                    .into(new SaveImageHelper(getBaseContext(),
                                            dialog,
                                            getApplicationContext().getContentResolver(),
                                            fileName,
                                            "Image Description"));

                        }
                    } catch (Exception e) {
                        Toast.makeText(EditActivity.this, "ERROR check URL exists", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });


    }

    public void ok(View v){
        MovieDB db = new MovieDB(this);
        if(check==-1){
            db.deleteMovie(ID);
        }
        Intent returnIntent = new Intent();
        String name = title.getText().toString();
        List<Movie> m =db.getAllMovieList();
        boolean nameExist = true;
        for(int i=0;i<m.size();i++){
            if(name.equals(m.get(i).getSubject())) {
                nameExist = false;
            }
        }
        if(nameExist) {
            String des = description.getText().toString();
            String url = url1.getText().toString();
            if (name.equals("")) {
                Toast.makeText(this, getResources().getText(R.string.toast), Toast.LENGTH_SHORT).show();
            } else {
                returnIntent.putExtra("No", No);
                returnIntent.putExtra("name", name);
                returnIntent.putExtra("des", des);
                returnIntent.putExtra("url", url);
                setResult(Activity.RESULT_OK, returnIntent);
                No=0;
                finish();
            }
        }else{
            Toast.makeText(this,"the movie title is already existed",Toast.LENGTH_SHORT).show();
        }


    }
    public void cancel(View v){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
    public void show(View v){

        String url = url1.getText().toString();
        new LoadImage(this,l,this, imageView, url).execute();
    }
}
