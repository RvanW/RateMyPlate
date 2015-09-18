package com.vanw.robbert.ratemyplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayPlate extends AppCompatActivity {
    RatingBar ratingBar;
    Context context = this;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_plate);

        // get the plate
        final Plate plate_instance = (Plate) getIntent().getSerializableExtra("data");

        // set the image
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(plate_instance.getImage());

        // set the title
        TextView titleText = (TextView) findViewById(R.id.TitleText);
        titleText.setText(plate_instance.getTitle());

        ratingBar = (RatingBar) findViewById(R.id.ratingBar2);
        // get shared prefs
        pref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        float rating = pref.getFloat(plate_instance.getId()+"", 0);
        ratingBar.setRating(rating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener(){

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                // set shared prefs
                SharedPreferences.Editor editor = pref.edit();
                editor.putFloat(plate_instance.getId()+"", rating);
                editor.apply();
            }

        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_plate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go burger
                Intent intent = new Intent(this, OverviewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void inPogress(View view) {
        Toast.makeText(getApplicationContext(), "This is not working yet", Toast.LENGTH_LONG).show();
    }
    public void back(View view) {
        finish();
    }
}
