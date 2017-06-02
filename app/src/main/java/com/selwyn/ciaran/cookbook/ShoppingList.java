package com.selwyn.ciaran.cookbook;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends NavigationBaseActivity {

    TextView header;
    GridView gridView;
    ImageButton deleteBtn;
    static String[] ingredientDescriptions;
    static String[] ingredientImages;
    static ArrayList<String> ingredientsList = new ArrayList<>();
    static ArrayList<String> imagesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);
        super.onCreateDrawer();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        ingredientDescriptions = ingredientsList.toArray(new String[ingredientsList.size()]);
        ingredientImages = imagesList.toArray(new String[imagesList.size()]);


        header = (TextView) findViewById(R.id.textView5);
        header.setText("Shopping List");
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        gridView = (GridView) findViewById(R.id.ShoppingListGridView);
        deleteBtn = (ImageButton) findViewById(R.id.deleteBtn);

        ShoppingListAdapter adapter = new ShoppingListAdapter(ShoppingList.this, ingredientImages, ingredientDescriptions);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                IngredientsActivity.position = position;
            }
        });
        gridView.setAdapter(adapter);
    }

    public static void addDescription(String item){
        ingredientsList.add(item);
    }

    public static void addImage(String item){
        imagesList.add(item);
    }


}
