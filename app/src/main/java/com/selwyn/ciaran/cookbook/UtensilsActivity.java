package com.selwyn.ciaran.cookbook;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class UtensilsActivity extends NavigationBaseActivity {

    private LinearLayout mainLayout;
    public static String[] images;
    private View cell;
    private TextView text;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utensils);
        super.onCreateDrawer();
        mainLayout = (LinearLayout) findViewById(R.id._linearLayout2);
        header = (TextView) findViewById(R.id.textView5);
        header.setText("Utensils");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        while(images == null){
            //Log.d("!!!!!!!!!!!!! ","YOLO");
        }

        for (int i = 0; i < images.length; i+=2) {

            cell = getLayoutInflater().inflate(R.layout.cell2, null);
            text = (TextView) cell.findViewById(R.id._imageName2);
            final ImageView imageView = (ImageView) cell.findViewById(R.id._image2);
            Picasso.with(getApplicationContext()).load(images[i]).into(imageView);
            for(int j = 1; j < images.length; j+=2){
                text.setText(images[i+1]);
            }
            mainLayout.addView(cell);
        }

    }

    public void change2(View v){
        //RecipeActivity.recipeName = letterList[position];
        RecipeActivity.recipeId = DrinksMenu.pos;
        startActivity(new Intent(UtensilsActivity.this, RecipeActivity.class));
        String method = "getVideo";
        String method2 = "getNoSteps";
        String method3 = "getTitle";
        String method4 = "getDescription";
        String method5 = "getTitle";

        BackgroundWorker backgroundWorker2 = new BackgroundWorker(UtensilsActivity.this);
        BackgroundWorker backgroundWorker = new BackgroundWorker(UtensilsActivity.this);
        BackgroundWorker backgroundWorker3 = new BackgroundWorker(UtensilsActivity.this);
        BackgroundWorker backgroundWorker4 = new BackgroundWorker(UtensilsActivity.this);
        BackgroundWorker backgroundWorker5 = new BackgroundWorker(UtensilsActivity.this);

        backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position + 1), "1");
        backgroundWorker2.execute(method2,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position + 1));
        backgroundWorker3.execute(method3,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position + 1));
        backgroundWorker4.execute(method4,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position + 1), "1");
        backgroundWorker5.execute(method5, String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position + 1));
    }

    public void goBack(View v){
        finish();
    }
}
