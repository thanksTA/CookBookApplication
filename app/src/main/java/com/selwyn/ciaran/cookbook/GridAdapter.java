package com.selwyn.ciaran.cookbook;

        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.Point;
        import android.util.Log;
        import android.view.Display;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.WindowManager;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

/**
 * Created by Ciaran on 7/12/2016.
 */
public class GridAdapter extends BaseAdapter {

    private int icons[];
    private String letters[];
    private String colours[];
    private Context context;
    private LayoutInflater inflater;


    public GridAdapter(Context context, int icons[], String letters[], String colours[]){
        this.context = context;
        this.icons = icons;
        this.letters = letters;
        this.colours = colours;
    }


    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public Object getItem(int position) {
        return icons[position];
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
            gridView = inflater.inflate(R.layout.custom_layout,null);
        }

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(width/3,height/2);
        gridView.setLayoutParams(lp);

        gridView.setBackgroundColor(Color.parseColor(colours[position]));

        ImageView icon = (ImageView) gridView.findViewById(R.id.icons);

        TextView letter = (TextView) gridView.findViewById(R.id.letters);

        icon.setImageResource(icons[position]);
        letter.setText(letters[position]);
        Log.d("!!!!!!!!!!!!! ",width + "");

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
