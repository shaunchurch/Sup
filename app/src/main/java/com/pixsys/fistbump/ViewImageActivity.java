package com.pixsys.fistbump;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ViewImageActivity extends Activity {

    protected ParseObject mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_view_image);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        Button bumpBackButton = (Button)findViewById(R.id.buttonBumpBack);


        Uri imageUri = intent.getData();
        String messageId = intent.getStringExtra("messageId");


        setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Messages");
        query.whereEqualTo("objectId",messageId);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                mMessage = parseObjects.get(0);
                Log.d("parseObjects", mMessage.getObjectId());
            }
        });

        // load image into view
        Picasso.with(this).load(imageUri.toString()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                setProgressBarIndeterminateVisibility(false);
                Toast.makeText(getBaseContext(), "Successfully loaded image.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError() {
                setProgressBarIndeterminateVisibility(false);
                Toast.makeText(getBaseContext(), getString(R.string.error_loading_image), Toast.LENGTH_LONG).show();
            }
        });


        bumpBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add this user to bump back ids
                mMessage.addUnique(ParseConstants.KEY_BUMP_BACK_IDS, ParseUser.getCurrentUser().getObjectId());

                // save in background
                mMessage.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        // there was no error saving bump
                        if(e == null) {
                            // success
                            // sent notification to user
                            mMessage.getString("senderId");
                            // Create our Installation query
                            ParseQuery pushQuery = ParseInstallation.getQuery();
                            pushQuery.whereEqualTo("userId", mMessage.getString(ParseConstants.KEY_SENDER_ID));

                            ParsePush push = new ParsePush();
                            push.setQuery(pushQuery); // Set our Installation query
                            push.setMessage("Fist Bump back from @" + ParseUser.getCurrentUser().get(ParseConstants.KEY_USERNAME) + "!");
                            push.sendInBackground(new SendCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {
                                    if(e == null) {
                                        Toast.makeText(getBaseContext(), "Bump back sent!", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(getBaseContext(), "Failed to notify other user :(", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else
                            Toast.makeText(getBaseContext(), getString(R.string.error_loading_image), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                finish();
//            }
//        }, 10*1000);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case android.R.id.home:
                finish();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

}
