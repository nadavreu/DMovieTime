package com.example.reubens_pc.dmovietime;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.List;



public class EditChanges extends Dialog implements
        android.view.View.OnClickListener {
//custom class for long press on movie to decide if you want to edit or delete the movie
    public Activity c;
    public Button delete, edit;
    public String NAME;
    public int ID;
    MovieDB db;
    List<Movie> names;

    public EditChanges(Activity a,String name,int id) {
        super(a);
        NAME=name;
        ID=id;
        this.c = a;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.edit_dialog);
        delete = (Button) findViewById(R.id.btn_delete);
        edit = (Button) findViewById(R.id.btn_edit);
        delete.setOnClickListener(this);
        edit.setOnClickListener(this);
        db= new MovieDB(c);
        names=db.getAllMovieList();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_delete:
                db.deleteMovie(ID);
                c.recreate();
                break;
            case R.id.btn_edit:

                for(int i=0;i<names.size();i++){
                    if(NAME.equals(names.get(i).getSubject())){
                        String title =names.get(i).getSubject();
                        String des =names.get(i).getBody();
                        String url =names.get(i).getUrl();
                        int id = names.get(i).get_id();
                        int No = names.get(i).getOrderNumber();
                        Intent editActivity = new Intent(c,EditActivity.class);
                        editActivity.putExtra("No",No);
                        editActivity.putExtra("name",title);
                        editActivity.putExtra("des",des);
                        editActivity.putExtra("url",url);
                        editActivity.putExtra("id",id);
                        c.startActivityForResult(editActivity,1);
                        break;
                    }
                }


            default:
                break;
        }
        dismiss();
    }
}
