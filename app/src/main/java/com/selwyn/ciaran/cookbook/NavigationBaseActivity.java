package com.selwyn.ciaran.cookbook;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class NavigationBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    NavigationView navigation;
    ActionBarDrawerToggle drawerToggle;
    public static Toolbar toolbar;

    protected void onCreateDrawer() {
        toolbar = (Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);



    }

    public void openMenu(View v){
        if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
            mDrawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    public void goBack(View v){
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home: {
                startActivity(new Intent(NavigationBaseActivity.this, MainActivity.class));
                break;
            }
            case R.id.nav_shopping: {
                startActivity(new Intent(NavigationBaseActivity.this, ShoppingList.class));
                break;
            }
            case R.id.nav_add: {
                startActivity(new Intent(NavigationBaseActivity.this, AddRecipeStep2.class));
                break;
            }
            case R.id.nav_remove: {
                startActivity(new Intent(NavigationBaseActivity.this, MainActivity.class));
                break;
            }
            case R.id.nav_help: {
                startActivity(new Intent(NavigationBaseActivity.this, MainActivity.class));
                break;
            }
        }
        return  true;
    }


}
