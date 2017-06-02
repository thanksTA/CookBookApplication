package com.selwyn.ciaran.cookbook;

    import android.content.pm.ActivityInfo;
    import android.net.Uri;
    import android.support.design.widget.NavigationView;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.View;
    import android.widget.Button;
    import android.widget.ImageButton;
    import android.widget.MediaController;
    import android.widget.TextView;
    import android.widget.Toast;
    import android.widget.VideoView;
    import static com.selwyn.ciaran.cookbook.MainActivity.toolbar;

    public class RecipeActivity extends NavigationBaseActivity {
        ImageButton play;
        ImageButton back;
        ImageButton next;
        VideoView videoView;
        MediaController mediaController;
        String videos;
        static int recipeId;
        private int step = 1;
        public static int noSteps;
        TextView description;
        TextView stepCount;
        public static String text;
        public static String headerText;
        TextView header;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_recipe);
            super.onCreateDrawer();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            back = (ImageButton) findViewById(R.id.backBtn);
            next = (ImageButton) findViewById(R.id.nextBtn);
            play = (ImageButton) findViewById(R.id.buttonPlay);
            header = (TextView) findViewById(R.id.textView5);
            header.setText(headerText);
            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(this);
            description = (TextView) findViewById(R.id.videoDescription);
            stepCount = (TextView) findViewById(R.id.stepCount);
            stepCount.setText("  Step No. " + step + "  ");
            back.setVisibility(View.GONE);


            videoView = (VideoView) findViewById(R.id.videoView);
            mediaController = new MediaController(this);
            toolbar = (Toolbar) findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);
        }

        public void videoPlay(View view){
            play.setVisibility(View.GONE);
            videos = BackgroundWorker.videos;
            //Toast.makeText(RecipeActivity.this, videos, Toast.LENGTH_SHORT).show();
            Log.d("******* ",noSteps + "");

            description.setText(text);
                Uri uri = Uri.parse(videos);
                videoView.setVideoURI(uri);
                videoView.setMediaController(mediaController);
                mediaController.setAnchorView(videoView);
                videoView.start();


        }

        public void videoPlayNext(View view){
            play.setVisibility(View.VISIBLE);
            step++;
            if(step > 1){
                back.setVisibility(View.VISIBLE);
            }
            if(step >= noSteps){
                next.setVisibility(View.GONE);
            }

            stepCount.setText("  Step No. " + step + "  ");
            videoView.setMediaController(mediaController);
            videoView.stopPlayback();
            String method = "getVideo";
            String method2 = "getDescription";
            BackgroundWorker backgroundWorker = new BackgroundWorker(RecipeActivity.this);
            backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position+1),String.valueOf(step));

            BackgroundWorker backgroundWorker2 = new BackgroundWorker(RecipeActivity.this);
            backgroundWorker2.execute(method2,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position+1),String.valueOf(step));

        }

        public void videoPlayPrevious(View view){


            play.setVisibility(View.VISIBLE);
            step--;
            if(step <= 1){
                back.setVisibility(View.GONE);
            }
            if(step < noSteps){
                next.setVisibility(View.VISIBLE);
            }
            stepCount.setText("  Step No. " + step + "  ");
            videoView.setMediaController(mediaController);
            videoView.stopPlayback();
            String method = "getVideo";
            String method2 = "getDescription";

            BackgroundWorker backgroundWorker = new BackgroundWorker(RecipeActivity.this);
            backgroundWorker.execute(method,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position+1),String.valueOf(step));
            BackgroundWorker backgroundWorker2 = new BackgroundWorker(RecipeActivity.this);
            backgroundWorker2.execute(method2,String.valueOf(MainActivity.menuId),String.valueOf(IngredientsActivity.position+1),String.valueOf(step));
        }


    }
