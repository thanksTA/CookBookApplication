package com.selwyn.ciaran.cookbook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ciaran on 30/12/2016.
 */
    public class BackgroundWorker extends AsyncTask<String, Void, String[]> {

    String JSON_STRING;
    Context context;
    String result = null;
    RecipeActivity r = new RecipeActivity();
    public static String noSteps;
    public static String videos;
    public static String[] videos2;
    ProgressDialog loading;

        BackgroundWorker(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected String[] doInBackground(String... params) {
            String requestVideoSteps = "http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/getSteps.php";
            String requestVideoURL2 = "http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/url.php";
            String requestDescription = "http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/requestDescription.php";
            String requestTitle = "http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/requestTitle.php";
            String requestIngredientsURL = "http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/getIngredients.php";
            String requestUtensilsURL = "http://ec2-13-54-128-72.ap-southeast-2.compute.amazonaws.com/getUtensils.php";
            String method = params[0];

            if (method.equals("getNoSteps")) {
                String meal_id = params[1];
                String recipe_id = params[2];

                try {
                    URL url = new URL(requestVideoSteps);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                    while((JSON_STRING = bufferedReader.readLine()) != null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    result = stringBuilder.toString();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("videos");
                    //videos = new String[parentArray.length()];
                    for(int i = 0; i<parentArray.length(); i++){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        noSteps = finalObject.getString("url");

                        //String urls = finalObject.getString("url");
                    }
                    //controller.setList(videos);
                    for(int i = 0; i < parentArray.length(); i++){
                        //Log.d("Member name: ",videos[i]);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //uril = response;
                    RecipeActivity.noSteps = Integer.valueOf(noSteps);
                    return videos2;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            //--------------------------------------------------------------------------------------

            if(method.equals("getVideo")){
                String meal_id = params[1];
                String recipe_id = params[2];
                String stepNo = params[3];

                try {
                    URL url = new URL(requestVideoURL2);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("meal_id", "UTF-8") + "=" + URLEncoder.encode(meal_id, "UTF-8") + "&" +
                            URLEncoder.encode("recipe_id", "UTF-8") + "=" + URLEncoder.encode(recipe_id, "UTF-8") + "&" +
                            URLEncoder.encode("stepNo", "UTF-8") + "=" + URLEncoder.encode(stepNo, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                    StringBuilder stringBuilder = new StringBuilder();
                    while((JSON_STRING = bufferedReader.readLine()) != null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    result = stringBuilder.toString();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("videos");
                    //videos = new String[parentArray.length()];
                    for(int i = 0; i<parentArray.length(); i++){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        videos = finalObject.getString("url");

                        //String urls = finalObject.getString("url");
                    }
                    //controller.setList(videos);
                    for(int i = 0; i < parentArray.length(); i++){
                        Log.d("Member name: ",videos);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //uril = response;

                    return videos2;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //--------------------------------------------------------------------------------------

            if(method.equals("getUtensils")){
                String meal_id = params[1];
                String recipe_id = params[2];

                try {
                    URL url = new URL(requestUtensilsURL);
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
                    while((JSON_STRING = bufferedReader.readLine()) != null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    result = stringBuilder.toString();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("videos");
                    UtensilsActivity.images = new String[parentArray.length()];
                    for(int i = 0; i<parentArray.length(); i++){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        UtensilsActivity.images[i] = finalObject.getString("url");
                    }


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return videos2;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //--------------------------------------------------------------------------------------

            if(method.equals("getIngredients")){
                String meal_id = params[1];
                String recipe_id = params[2];

                try {
                    URL url = new URL(requestIngredientsURL);
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
                    while((JSON_STRING = bufferedReader.readLine()) != null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    result = stringBuilder.toString();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("videos");
                    IngredientsActivity.images = new String[parentArray.length()];
                    for(int i = 0; i<parentArray.length(); i++){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        IngredientsActivity.images[i] = finalObject.getString("url");
                    }


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return videos2;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //--------------------------------------------------------------------------------------

            if(method.equals("getDescription")){
                String meal_id = params[1];
                String recipe_id = params[2];
                String stepNo = params[3];

                try {
                    URL url = new URL(requestDescription);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setReadTimeout(15000);
                    httpURLConnection.setConnectTimeout(15000);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("meal_id", "UTF-8") + "=" + URLEncoder.encode(meal_id, "UTF-8") + "&" +
                            URLEncoder.encode("recipe_id", "UTF-8") + "=" + URLEncoder.encode(recipe_id, "UTF-8") + "&" +
                            URLEncoder.encode("stepNo", "UTF-8") + "=" + URLEncoder.encode(stepNo, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                    StringBuilder stringBuilder = new StringBuilder();
                    while((JSON_STRING = bufferedReader.readLine()) != null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    result = stringBuilder.toString();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("videos");
                    for(int i = 0; i<parentArray.length(); i++){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        RecipeActivity.text = finalObject.getString("url");
                    }


                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return videos2;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //--------------------------------------------------------------------------------------

            if(method.equals("getTitle")){
                String meal_id = params[1];
                String recipe_id = params[2];

                try {
                    URL url = new URL(requestTitle);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
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
                    while((JSON_STRING = bufferedReader.readLine()) != null){
                        stringBuilder.append(JSON_STRING+"\n");
                    }
                    result = stringBuilder.toString();
                    JSONObject parentObject = new JSONObject(result);
                    JSONArray parentArray = parentObject.getJSONArray("videos");
                    //videos = new String[parentArray.length()];
                    for(int i = 0; i<parentArray.length(); i++){
                        JSONObject finalObject = parentArray.getJSONObject(i);
                        RecipeActivity.headerText = finalObject.getString("url");

                        //String urls = finalObject.getString("url");
                    }
                    //controller.setList(videos);
                    for(int i = 0; i < parentArray.length(); i++){
                        Log.d("Member name: ",videos);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    //uril = response;

                    return videos2;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            loading = ProgressDialog.show(BackgroundWorker.this.context, "Loading...", null,true,true);
            //controller.setList();

        }

        @Override
        public void onPostExecute(String[] result) {
            loading.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }


