package com.selwyn.ciaran.cookbook;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Ciaran on 8/03/2017.
 */
public class SnacksMenu extends NavigationBaseActivity {
    TextView header;
    GridView gridView;
    String letterList[] = {"Tortilla Chips & Dip"};
    int lettersIcon[] = {R.drawable.guac};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_menu);
        super.onCreateDrawer();
        header = (TextView) findViewById(R.id.textView5);
        header.setText("Snacks");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gridView = (GridView) findViewById(R.id.gridView);

        SubMenuAdapter adapter = new SubMenuAdapter(SnacksMenu.this, lettersIcon, letterList);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                IngredientsActivity.position = position;
                String method = "getIngredients";
                BackgroundWorker backgroundWorker = new BackgroundWorker(SnacksMenu.this);
                backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(position+1), "1");
                startActivity(new Intent(SnacksMenu.this, IngredientsActivity.class));
            }
        });
        gridView.setAdapter(adapter);
    }

    public void goBack(View v){
        finish();
    }
}
