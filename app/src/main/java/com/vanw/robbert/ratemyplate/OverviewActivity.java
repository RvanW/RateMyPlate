package com.vanw.robbert.ratemyplate;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;


public class OverviewActivity extends AppCompatActivity {

    GridView gridView;
    ArrayList<Plate> gridArray = new ArrayList<Plate>();
    CustomGridViewAdapter customGridAdapter;

    public static Context contextOfApplication;
    String sortValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // get context for later
        contextOfApplication = getApplicationContext();

        // get two bitmaps and scale them down
        Bitmap burgerBitmap = scaleDownBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.burger),100,getApplicationContext());
        Bitmap pizzaBitmap = scaleDownBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.pizza), 100, getApplicationContext());


        // lets add some dummy data
        gridArray.add(new Plate(burgerBitmap,"Burger with chicken"));
        gridArray.add(new Plate(pizzaBitmap,"Pizza margherita"));
        gridArray.add(new Plate(burgerBitmap,"Burger with bacon"));
        gridArray.add(new Plate(pizzaBitmap,"Pizza quatro stagioni"));
        gridArray.add(new Plate(burgerBitmap,"Hamburger"));
        gridArray.add(new Plate(pizzaBitmap,"Pizza ham"));
        gridArray.add(new Plate(burgerBitmap,"Cheeseburger"));
        gridArray.add(new Plate(pizzaBitmap,"Another pizza"));
        gridArray.add(new Plate(burgerBitmap,"Burger with ham"));
        gridArray.add(new Plate(pizzaBitmap, "Pizza with cheese"));
        gridArray.add(new Plate(burgerBitmap, "Burger with chickenbacon"));
        gridArray.add(new Plate(pizzaBitmap, "Pizza with bacon"));

        updateGrid();

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position,
                                    long arg3) {
                Toast.makeText(getApplicationContext(), gridArray.get(position).getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DisplayPlate.class);
                intent.putExtra("data", gridArray.get(position));
                startActivity(intent);


            }
        });
        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @TargetApi(Build.VERSION_CODES.KITKAT)
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (Objects.equals(parent.getItemAtPosition(pos).toString(), "Title")) {
                    Collections.sort(gridArray, Plate.COMPARE_BY_TITLE);
                    sortValue = "Title";
                }
                else if (Objects.equals(parent.getItemAtPosition(pos).toString(), "Rating")) {
                    Collections.sort(gridArray, Plate.COMPARE_BY_RATING);
                    sortValue = "Rating";
                }
                updateGrid();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing?
            }
        });
    }

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    public void updateGrid() {
        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onRestart() {
        super.onRestart();
        if (Objects.equals(sortValue, "Title"))
            Collections.sort(gridArray, Plate.COMPARE_BY_TITLE);
        else if (Objects.equals(sortValue, "Rating"))
            Collections.sort(gridArray, Plate.COMPARE_BY_RATING);
        updateGrid();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_overview, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public static Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

        final float densityMultiplier = context.getResources().getDisplayMetrics().density;

        int h= (int) (newHeight*densityMultiplier);
        int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

        photo=Bitmap.createScaledBitmap(photo, w, h, true);

        return photo;
    }

    public void inPogress(View view) {
        Toast.makeText(getApplicationContext(), "This is not working yet", Toast.LENGTH_LONG).show();
    }
}
