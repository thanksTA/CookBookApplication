package com.selwyn.ciaran.cookbook;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.selwyn.ciaran.cookbook.MainActivity.toolbar;

    public class IngredientsActivity extends NavigationBaseActivity {

        private LinearLayout mainLayout;
        public static String[] images;
        private View cell;
        private TextView text;
        TextView header;
        ImageButton addSome;
        public static int position;
        ShoppingList s = new ShoppingList();


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ingredients);
            super.onCreateDrawer();
            header = (TextView) findViewById(R.id.textView5);
            header.setText("Ingredients");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mainLayout = (LinearLayout) findViewById(R.id._linearLayout);
            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);
            while(images == null){
                //Log.d("!!!!!!!!!!!!! ","YOLO");
            }
            for(int i = 0; i<images.length; i++){
                //Log.d("!!!!!!!!!!!!! ",images[i] + "");
            }
            for (int i = 0; i < images.length; i+=2) {

                cell = getLayoutInflater().inflate(R.layout.cell, null);
                text = (TextView) cell.findViewById(R.id._imageName);
                final ImageButton addSome = (ImageButton) cell.findViewById(R.id.addIngredient);
                final ImageView imageView = (ImageView) cell.findViewById(R.id._image);
                Picasso.with(getApplicationContext()).load(images[i]).into(imageView);
                for(int j = 1; j < images.length; j+=2){
                    text.setText(images[i+1]);
                }
                final int finalI = i;
                addSome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShoppingList.addDescription(images[finalI]);
                        ShoppingList.addImage(images[finalI+1]);
                        Log.d("pleeeeeeeease ",images[finalI] + "");
                        Log.d("pleeeeeeeease ",images[finalI + 1] + "");
                        Toast.makeText(IngredientsActivity.this,
                                (CharSequence) imageView.getTag(), Toast.LENGTH_SHORT).show();
                    }
                });
                imageView.setTag("Image " + (i/2));
                mainLayout.addView(cell);


            }

        }

        public void change(View v){
            String method = "getUtensils";
            BackgroundWorker backgroundWorker = new BackgroundWorker(IngredientsActivity.this);
            backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(position + 1));
            startActivity(new Intent(IngredientsActivity.this, UtensilsActivity.class));
        }

    }
