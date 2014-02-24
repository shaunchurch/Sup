package com.pixsys.fistbump;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

public class ViewImageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_view_image);

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Uri imageUri = getIntent().getData();

        setProgressBarIndeterminateVisibility(true);

        // load image into view
        Picasso.with(this).load(imageUri.toString()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                setProgressBarIndeterminateVisibility(false);

            }

            @Override
            public void onError() {
                setProgressBarIndeterminateVisibility(false);
//                Toast.makeText(getActivity().this, getString(R.string.error_loading_image), Toast.LENGTH_LONG).show();
            }
        });


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 10*1000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
