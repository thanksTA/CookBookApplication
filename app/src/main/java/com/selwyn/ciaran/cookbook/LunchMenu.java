package com.selwyn.ciaran.cookbook;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Ciaran on 6/03/2017.
 */
public class LunchMenu extends NavigationBaseActivity{

    TextView header;
    GridView gridView;
    public static Toolbar toolbar;

    String letterList[] = {"Chicken Wrap"};
    int lettersIcon[] = {R.drawable.tortillas};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_menu);
        super.onCreateDrawer();

        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        header = (TextView) findViewById(R.id.textView5);
        header.setText("Lunch");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        gridView = (GridView) findViewById(R.id.gridView);
        SubMenuAdapter adapter = new SubMenuAdapter(LunchMenu.this, lettersIcon, letterList);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                IngredientsActivity.position = position;
                String method = "getIngredients";
                BackgroundWorker backgroundWorker = new BackgroundWorker(LunchMenu.this);
                backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(position+1), "1");


                startActivity(new Intent(LunchMenu.this, IngredientsActivity.class));
            }
        });
        gridView.setAdapter(adapter);
    }
}
