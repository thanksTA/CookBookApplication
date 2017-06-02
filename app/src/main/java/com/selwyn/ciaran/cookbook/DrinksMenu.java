package com.selwyn.ciaran.cookbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

    public class DrinksMenu extends NavigationBaseActivity {

        String JSON_STRING;
        String result = null;
        public static Toolbar toolbar;
        public static int pos;
        GridView gridView;
        String letterList[] = {"Chocolate Shake"};
        int lettersIcon[] = {R.drawable.chokkie};
        public static String images[];
        public static String noIngredients;
        TextView header;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_drinks_menu);
            super.onCreateDrawer();
            toolbar = (Toolbar) findViewById(R.id.nav_action);
            setSupportActionBar(toolbar);
            header = (TextView) findViewById(R.id.textView5);
            header.setText("Drinks");
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);
            gridView = (GridView) findViewById(R.id.gridView);
            SubMenuAdapter adapter = new SubMenuAdapter(DrinksMenu.this, lettersIcon, letterList);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                    pos = position;
                    getImages("http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/getIngredients.php",
                    String.valueOf(MainActivity.menuId), String.valueOf(position+1));
                    IngredientsActivity.position = position;
                    startActivity(new Intent(DrinksMenu.this, IngredientsActivity.class));
                }
            });
            gridView.setAdapter(adapter);
        }

        private void getImages(String url, String mealId, String recipeId){

            class GetImages extends AsyncTask<String, Void, String[]> {



                public String[] images2;
                ProgressDialog loading;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    loading = ProgressDialog.show(DrinksMenu.this, "Uploading...", null,true,true);

                }

                @Override
                protected void onPostExecute(String[] s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    //setImages(s);
                }

                @Override
                protected String[] doInBackground(String... params) {
                    String uri = params[0];
                    String meal_id = params[1];
                    String recipe_id = params[2];


                    try {
                        URL url = new URL(uri);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setReadTimeout(15000);
                        httpURLConnection.setConnectTimeout(15000);
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String data = URLEncoder.encode("meal_id", "UTF-8") + "=" + URLEncoder.encode(meal_id, "UTF-8") + "&" +
                                URLEncoder.encode("recipe_id", "UTF-8") + "=" + URLEncoder.encode(recipe_id, "UTF-8");
                        bufferedWriter.write(data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                        StringBuilder stringBuilder = new StringBuilder();
                        while ((JSON_STRING = bufferedReader.readLine()) != null) {
                            stringBuilder.append(JSON_STRING + "\n");
                        }
                        result = stringBuilder.toString();
                        JSONObject parentObject = new JSONObject(result);
                        JSONArray parentArray = parentObject.getJSONArray("videos");
                        IngredientsActivity.images = new String[parentArray.length()];
                        for (int i = 0; i < parentArray.length(); i++) {
                            JSONObject finalObject = parentArray.getJSONObject(i);
                            IngredientsActivity.images[i] = finalObject.getString("url");
                        }

                        JSONArray parentArray2 = parentObject.getJSONArray("images");
                        for (int i = 0; i < parentArray.length(); i++) {
                            JSONObject finalObject = parentArray2.getJSONObject(i);
                            noIngredients = finalObject.getString("url2");
                        }

                        Log.d("!!!!!!!!!!!!! ",noIngredients + "YOLO");


                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();
                        return images;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                return null;
                }
            }
            GetImages gi = new GetImages();
            gi.execute(url, mealId, recipeId);
        }
    }
