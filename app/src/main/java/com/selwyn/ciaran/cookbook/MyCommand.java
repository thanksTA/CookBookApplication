package com.selwyn.ciaran.cookbook;

import android.app.DownloadManager;
import android.content.Context;

import com.android.volley.Request;

import java.util.ArrayList;

/**
 * Created by Ciaran on 20/02/2017.
 */
public class MyCommand<T> {

    ArrayList<Request<T>> requestList = new ArrayList<>();
    private Context context;

    public MyCommand(Context context){
        this.context = context;
    }

    public void add(Request<T> request){
        requestList.add(request);
    }

    public void remove(Request<T> request){
        requestList.remove(request);
    }

    public void execute(){
        for(Request<T> request: requestList){
            MySingleton.getInstance(context).addToRequestQueue(request);
        }
    }
}
