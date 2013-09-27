package com.socialapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Logo extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
        
        ImageView logo=(ImageView)findViewById(R.id.logo);
        //Animating the Social Ninja logo:
        Animation translate=AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate);
        logo.setAnimation(translate);
        
        //Animating the Ninja star:
        ImageView img=(ImageView)findViewById(R.id.ninjastar);
        Animation rotation=AnimationUtils.loadAnimation(this, R.anim.rotation);
        
        rotation.setRepeatCount(Animation.INFINITE);
        img.startAnimation(rotation);

        new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				final Intent mainIntent=new Intent(getApplicationContext(),MainActivity.class);
				Logo.this.startActivity(mainIntent);
				Logo.this.finish();
			}
		}, 5000);
        
        
	}
}