package com.socialapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class TwitterResults extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.twitter);
        
        
        //Animate cloud:
        ImageView img=(ImageView)findViewById(R.id.twittercloud);
        Animation rotation=AnimationUtils.loadAnimation(this, R.anim.translate);
        
        rotation.setRepeatCount(Animation.INFINITE);
        img.startAnimation(rotation);
        
        //Display Tweets:
        TextView tv=(TextView)findViewById(R.id.tweetbox);
        String tweets=getIntent().getExtras().getString("tweets");
        tv.setText(tweets);
        tv.setMovementMethod(new ScrollingMovementMethod());


    }
    
}
