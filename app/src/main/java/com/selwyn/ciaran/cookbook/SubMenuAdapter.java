package com.selwyn.ciaran.cookbook;

        import android.content.Context;
        import android.graphics.Point;
        import android.support.v7.widget.Toolbar;
        import android.view.Display;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.RelativeLayout;
        import android.widget.TextView;

/**
 * Created by ciara_000 on 22/12/2016.
 */

public class SubMenuAdapter extends BaseAdapter {

    private int images[];
    private String descriptions[];
    private Context context;
    private LayoutInflater inflater;

    public SubMenuAdapter(Context context, int images[], String descriptions[]){
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

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y - NavigationBaseActivity.toolbar.getHeight() - getStatusBarHeight();
        View gridView = convertView;
        if(convertView == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.custom_layout2,null);
        }

        ImageView icon = (ImageView) gridView.findViewById(R.id.recipeImage);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width/3,height/2);
        icon.setLayoutParams(lp);
        TextView letter = (TextView) gridView.findViewById(R.id.letters);
        icon.setImageResource(images[position]);
        letter.setText(descriptions[position]);

        return gridView;
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
