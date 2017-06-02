package com.selwyn.ciaran.cookbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ciaran on 8/03/2017.
 */
public class ShoppingListAdapter extends BaseAdapter {
    private String images[];
    private String descriptions[];
    private Context context;
    private LayoutInflater inflater;

    public ShoppingListAdapter(Context context, String images[], String descriptions[]){
        this.context = context;
        this.images = images;
        this.descriptions = descriptions;

    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return images[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y - NavigationBaseActivity.toolbar.getHeight() - getStatusBarHeight();
        View gridView = convertView;
        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_layout3,null);
        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.ingredientImage);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width/3,height/2);
        icon.setLayoutParams(lp);
        TextView letter = (TextView) gridView.findViewById(R.id.ingredientDescription);

        try {
            URL url = new URL(descriptions[position]);
            Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            icon.setImageBitmap(image);
        } catch(IOException e) {
            System.out.println(e);
        }

        letter.setText(images[position]);

        return gridView;
    }

    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {
        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection)new URL(url) .openConnection();
        connection.setRequestProperty("User-agent","Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return x;
    }

    public int getStatusBarHeight() {   //method returns status bars height
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
