package com.khalnayak.imageprocessing;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.khalnayak.imageprocessing.Setting.About;
import com.khalnayak.imageprocessing.Setting.Setting;

public class Animation extends AppCompatActivity {

    LinearLayout animationLayout;
    SharedPreferences sp;

    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_REQUEST = 1888;
    Bitmap photo;
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animation_activity);
        imageView = (ImageView) findViewById(R.id.imgView);
        animationLayout = (LinearLayout) findViewById(R.id.animationlinear);

        sp = getSharedPreferences("BG_Color", Context.MODE_PRIVATE);
        animationLayout.setBackgroundColor(sp.getInt("BG_Color_Key", Color.WHITE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1,1,1,"Help");
        menu.add(1,2,2,"Setting");
        menu.add(1,3,3,"About Navigator");
        menu.add(1,4,4,"Rate Navigator");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Toast helpmsg = Toast.makeText(getBaseContext(),"Help is Under Construction", Toast.LENGTH_LONG);
                helpmsg.setGravity(Gravity.BOTTOM,0,0);
                helpmsg.show();
                break;
            case 2:
                Intent setting = new Intent(getBaseContext(),Setting.class);
                startActivity(setting);
                break;
            case 3:
                Intent about = new Intent(getBaseContext(), About.class);
                startActivity(about);
                break;
            case 4:
                Toast rate = Toast.makeText(getBaseContext(),"Rating App is Under Construction", Toast.LENGTH_LONG);
                rate.setGravity(Gravity.BOTTOM,0,0);
                rate.show();
        }
        return super.onOptionsItemSelected(item);
    }
    public void getImage(View view){
        switch (view.getId()){
            case R.id.btnGallery:
                gallery();
                break;
            case R.id.btnCamera:
                camera();
        }
    }

    private void camera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    private void gallery() {
        Intent gallery = new Intent(
                Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        gallery.putExtra("crop", "true");
        gallery.putExtra("scale", true);
        gallery.putExtra("outputX", 256);
        gallery.putExtra("outputY", 256);
        gallery.putExtra("aspectX", 1);
        gallery.putExtra("aspectY", 1);
        gallery.putExtra("return-data", true);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                photo = extras.getParcelable("data");
            }
            imageView.setImageBitmap(photo);

        }
        else if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK){
            photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }


    public void doEffect(View view){
        Bitmap effect;
        switch (view.getId()){
            case R.id.btnGrayScale:
                new MyAnimation().execute();
                break;

            case R.id.btnSketch:
                effect = AddingEffect.doSketch(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnSepia:
                effect = AddingEffect.doSepia(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnBlur:
                effect = AddingEffect.doBlur(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnFlea:
                effect = AddingEffect.doFlea(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnInverted:
                effect = AddingEffect.doInverted(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnTint:
                effect = AddingEffect.dotint(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnSaturation:
                effect = AddingEffect.doSaturation(photo);
                imageView.setImageBitmap(effect);
                break;

            case R.id.btnReset:
                imageView.setImageBitmap(photo);
                break;
        }
    }

    public void doAnimation(View view){
        ObjectAnimator rotateAnimation;
        AnimatorSet animatorSet;
        TranslateAnimation animation;
        ObjectAnimator alphaAnimation;
        ObjectAnimator shakeAnimation;

        switch (view.getId()){
            case R.id.clockwise:
                rotateAnimation = ObjectAnimator.ofFloat(imageView, "rotation", 0,360);
                rotateAnimation.setDuration(2000);
                animatorSet = new AnimatorSet();
                animatorSet.play(rotateAnimation);
                animatorSet.start();
                break;

            case R.id.antiClockwise:
                rotateAnimation = ObjectAnimator.ofFloat(imageView, "rotation", 360,0);
                rotateAnimation.setDuration(2000);
                animatorSet = new AnimatorSet();
                animatorSet.play(rotateAnimation);
                animatorSet.start();
                break;

            case R.id.leftToright:
                animation = new TranslateAnimation(0.0f, 400.0f, 0.0f, 0.0f);
                animation.setDuration(2000);
                imageView.startAnimation(animation);
                break;

            case R.id.rightToleft:
                animation = new TranslateAnimation(400.0f,0.0f, 0.0f, 0.0f);
                animation.setDuration(2000);
                imageView.startAnimation(animation);
                break;

            case R.id.moveUp:
                animation = new TranslateAnimation(0.0f, 0.0f,400.0f, 0.0f);
                animation.setDuration(2000);
                imageView.startAnimation(animation);
                break;

            case R.id.moveDown:
                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f,400.0f);
                animation.setDuration(2000);
                imageView.startAnimation(animation);
                break;

            case R.id.fadeIn:
                alphaAnimation = ObjectAnimator.ofFloat(imageView, "alpha", 0,1);
                alphaAnimation.setDuration(2000);
                animatorSet = new AnimatorSet();
                animatorSet.play(alphaAnimation);
                animatorSet.start();
                break;

            case R.id.fadeOut:
                alphaAnimation = ObjectAnimator.ofFloat(imageView, "alpha",1,0);
                alphaAnimation.setDuration(2000);
                animatorSet = new AnimatorSet();
                animatorSet.play(alphaAnimation);
                animatorSet.start();
                break;

            case R.id.shake:
                shakeAnimation = ObjectAnimator.ofFloat(imageView, "translationX", 0,50,0,50,0,50,0,50,0,50,0);
                shakeAnimation.setDuration(3000);
                animatorSet = new AnimatorSet();
                animatorSet.play(shakeAnimation);
                animatorSet.start();
                break;
        }
    }

    class MyAnimation extends AsyncTask{
        Bitmap effect;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {
            effect = AddingEffect.doGreyscale(photo);
            return null;
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            imageView.setImageBitmap(effect);
        }


    }

}
