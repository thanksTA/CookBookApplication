package com.selwyn.ciaran.cookbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AddRecipeStep2 extends NavigationBaseActivity{

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    ImageView ivGallery, ivUpload;
    private int PICK_IMAGE_REQUEST = 1;
    private Uri filePath;

    LinearLayout linearMain;
    ArrayList<String> imageList = new ArrayList<>();
    ArrayList<Uri> imageList2 = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe_step2);
        super.onCreateDrawer();

        ivGallery = (ImageView)findViewById(R.id.ivGallery);
        ivUpload = (ImageView)findViewById(R.id.ivUpload);
        linearMain = (LinearLayout)findViewById(R.id.linearMain);

        ivGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        final MyCommand myCommand = new MyCommand(getApplicationContext());

        ivUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Uri imagePath : imageList2){
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                        final String encodedImage = ImageEncoder.encode(bitmap);

                        String url = "http://13.54.128.72/imageUploader.php";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d(")))))))", "   OOOOOOOOOOOOOOOOOOOOOOOOO");
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("image", encodedImage);
                                return super.getParams();
                            }
                        };

                       myCommand.add(stringRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                myCommand.execute();
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();

            if(ContextCompat.checkSelfPermission(AddRecipeStep2.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddRecipeStep2.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(AddRecipeStep2.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


                }
            }
            String imagePath = getPath();

            imageList.add(imagePath);
            imageList2.add(filePath);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ImageView imageViewYo = new ImageView(getApplicationContext());

                EditText editText = new EditText(getApplicationContext());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);

                layoutParams2.addRule(RelativeLayout.BELOW, layoutParams.bottomMargin);
                editText.setLayoutParams(layoutParams2);
                imageViewYo.setLayoutParams(layoutParams);

                imageViewYo.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageViewYo.setPadding(0,0,0,10);
                imageViewYo.setAdjustViewBounds(true);
                imageViewYo.setImageBitmap(bitmap);



                linearMain.addView(imageViewYo);
                linearMain.addView(editText);
                //ingredients[getStep()] = bitmap;
                //imageView.setImageBitmap(ingredients[getStep()]);
                //Toast.makeText(AddRecipeStep2.this, "Option selected :" +filePath, Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }


    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {

            return View.generateViewId();

        }
    }

    public String getPath(){
        String path;

        if (Build.VERSION.SDK_INT < 11) {
            path = RealPathUtil.getRealPathFromURI_BelowAPI11(getApplicationContext(), filePath);
        }

        // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19){
            path = RealPathUtil.getRealPathFromURI_API11to18(getApplicationContext(), filePath);
        }
        // SDK > 19 (Android 4.4)
        else
            path = RealPathUtil.getRealPathFromURI_API19(getApplicationContext(), filePath);
        return path;
    }

}
