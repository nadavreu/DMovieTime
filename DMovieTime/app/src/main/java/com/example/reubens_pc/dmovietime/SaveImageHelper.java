package com.example.reubens_pc.dmovietime;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.ref.WeakReference;



public class SaveImageHelper implements Target {
    //this class is helper class to save the movie poster , i added picasso and spotDialog to download from the net with the url i am getting from the api
    private Context context;

    private WeakReference<AlertDialog> alertDialogWeakReference;
    private WeakReference<ContentResolver> contentResolverWeakReference;
    private String name;
    private String desc;

    public SaveImageHelper(Context context,AlertDialog alertDialog, ContentResolver contentResolver, String name, String desc) {
        this.context=context;
        this.alertDialogWeakReference = new WeakReference<AlertDialog>(alertDialog);
        this.contentResolverWeakReference = new WeakReference<ContentResolver>(contentResolver);
        this.name = name;
        this.desc = desc;
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ContentResolver r=contentResolverWeakReference.get();
        AlertDialog dialog=alertDialogWeakReference.get();
        if(r!=null)
            MediaStore.Images.Media.insertImage(r,bitmap,name,desc);
        dialog.dismiss();

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
