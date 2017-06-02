package com.selwyn.ciaran.cookbook;

        import android.content.Intent;
        import android.content.pm.ActivityInfo;
        import android.os.Bundle;
        import android.support.design.widget.NavigationView;
        import android.support.v7.widget.Toolbar;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.GridView;
        import android.widget.ImageButton;
        import android.widget.TextView;
        import android.widget.Toast;

public class MainActivity extends NavigationBaseActivity{
    GridView gridView;
    private ImageButton backBtn;
    public static int menuId;
    String letterList[] = {"Breakfast", "Lunch", "Dinner", "Drinks", "Snacks", "Dessert"};
    String[] gridColour = {"#525061", "#5A475B", "#6C5570", "#E04150", "#EE494F", "#F58D77"};
    TextView header;

    int lettersIcon[] = {R.drawable.breakfast_icon64, R.drawable.lunch_icon64, R.drawable.dinner_icon64, R.drawable.drinks_icon64, R.drawable.snacks_icon64, R.drawable.desserts_icon64};

    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreateDrawer();
        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        backBtn = (ImageButton) findViewById(R.id.goBack);
        backBtn.setVisibility(View.GONE);
        header = (TextView) findViewById(R.id.textView5);
        header.setText("My Cook Book");
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        gridView = (GridView) findViewById(R.id.gridView);

        GridAdapter adapter = new GridAdapter(MainActivity.this, lettersIcon, letterList, gridColour);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                IngredientsActivity.images = null;
                UtensilsActivity.images = null;
                //Toast.makeText(MainActivity.this, "Option selected :" +letterList[position] + position, Toast.LENGTH_SHORT).show();
                if(position == 0) {
                    startActivity(new Intent(MainActivity.this, BreakfastMenu.class));
                    menuId = position + 1;
                }
                if(position == 1) {
                    startActivity(new Intent(MainActivity.this, LunchMenu.class));
                    menuId = position + 1;
                }
                if(position == 2) {
                    startActivity(new Intent(MainActivity.this, DinnerMenu.class));
                    menuId = position + 1;
                }
                if(position == 3){
                    menuId = position + 1;
                    startActivity(new Intent(MainActivity.this, DrinksMenu.class));
                }
                if(position == 4) {
                    startActivity(new Intent(MainActivity.this, SnacksMenu.class));
                    menuId = position + 1;
                }

            }
        });


    }

}
