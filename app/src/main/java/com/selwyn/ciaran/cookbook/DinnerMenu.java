package com.selwyn.ciaran.cookbook;
        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.content.res.Configuration;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.design.widget.NavigationView;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.GridView;
        import android.widget.TextView;

public class DinnerMenu extends NavigationBaseActivity  {

    TextView header;
    GridView gridView;
    String letterList[] = {"Chicken Stir-Fry"};
    int lettersIcon[] = {R.drawable.stirfry};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drinks_menu);
        super.onCreateDrawer();
        header = (TextView) findViewById(R.id.textView5);
        header.setText("Dinner");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gridView = (GridView) findViewById(R.id.gridView);

        SubMenuAdapter adapter = new SubMenuAdapter(DinnerMenu.this, lettersIcon, letterList);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                IngredientsActivity.position = position;
                String method = "getIngredients";
                BackgroundWorker backgroundWorker = new BackgroundWorker(DinnerMenu.this);
                backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(position+1), "1");
                startActivity(new Intent(DinnerMenu.this, IngredientsActivity.class));
            }
        });
        gridView.setAdapter(adapter);
    }

    public void goBack(View v){
        finish();
    }







}
