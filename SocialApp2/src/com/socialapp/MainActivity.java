package com.socialapp;

import com.restfb.FacebookClient.AccessToken;
import com.restfb.*;
import com.restfb.types.Post;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {

SQLiteDatabase mydb;
private static String DBNAME = "arjit_queries.db";
private static String TABLE = "queries";

	AccessToken accessToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        EditText e=(EditText) findViewById(R.id.searchbar);
        e.setBackgroundColor(Color.WHITE);

        /*
        //Just to rotate the image:
        ImageView img=(ImageView)findViewById(R.id.imageView1);
        Animation rotation=AnimationUtils.loadAnimation(this, R.anim.rotation);
        
        rotation.setRepeatCount(Animation.INFINITE);
        img.startAnimation(rotation);
        */
        //Create SQLLite Database:
        /*
        try{
        	mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
        	mydb.execSQL("CREATE TABLE IF  NOT EXISTS "+ TABLE +" (QUERY TEXT);");
        	mydb.close();
        	}catch(Exception e){
        	Toast.makeText(getApplicationContext(), "Error in creating table", Toast.LENGTH_LONG).show();
        	}
        */
        
        //This is search magnifier button
        ImageView img2=(ImageView)findViewById(R.id.imageView2);
        
        img2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				EditText e=(EditText)findViewById(R.id.searchbar);
				
				//Put the query inside SQLite here:
				mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
				
				mydb.execSQL("INSERT INTO " + TABLE + "(QUERY) VALUES('"+e.getText().toString()+"')");
				
				Intent i=new Intent(getApplicationContext(),SearchFB.class);
				i.putExtra("query", e.getText().toString());
				startActivity(i);
				
			}
		});
        
        //Access token is obtained here:
        new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
		        accessToken =
		        		  new DefaultFacebookClient().obtainAppAccessToken("143020499163285", "3347a18f9c6205ed1b614a73a5b2b6c2");

		        		System.out.println("My application access token: " + accessToken);


		        		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		                SharedPreferences.Editor editor = sharedPreferences.edit();
		                editor.putString("fbtoken", accessToken.getAccessToken());
		                editor.commit();
        		

		        		
			}
        	
        	
        }).start(); 

/*        
        
        Spinner s=(Spinner)findViewById(R.id.spinner1);
        
        s.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int pos,long id) {
				// TODO Auto-generated method stub
				
				if(pos==1){ //refers to Popular Searches as per strings.xml
					Intent i=new Intent(getApplicationContext(),PopularSearches.class);
					startActivity(i);
				}
				
				else
					if(pos==2){ //refers to Options as per strings.xml
						Intent i=new Intent(getApplicationContext(),Options.class);
						startActivity(i);
					}
					else
						if(pos==3) //refers to About
						{
							Toast.makeText(getApplicationContext(), "Social Ninja created by Arjit Dhruv Jasdev ", Toast.LENGTH_LONG );
						}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

*/        
    }


    /* Initiating Menu XML file (menu.xml) */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        setMenuBackground();

        return true;
    }
    
    protected void setMenuBackground(){
        
    Log.d("back", "Enterting setMenuBackGround");
    getLayoutInflater().setFactory( new Factory() {

		@Override
		public View onCreateView(String name, Context context,
				AttributeSet attrs) {
			
			if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
                
                try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView( name, null, attrs );
                        /* 
                         * The background gets refreshed each time a new item is added the options menu. 
                         * So each time Android applies the default background we need to set our own 
                         * background. This is done using a thread giving the background change as runnable
                         * object
                         */
                        new Handler().post( new Runnable() {
                                public void run () {
                                        view.setBackgroundResource( R.drawable.background);
                                }
                        } );
                        return view;
                }
                catch ( InflateException e ) {}
                catch ( ClassNotFoundException e ) {}
        }

			// TODO Auto-generated method stub
			return null;

			// TODO Auto-generated method stub
		}
            
            


			
    });
    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        case R.id.popularsearches:
        	// Single menu item is selected do something
        	// Ex: launching new activity/screen or show alert message
            Intent i=new Intent(getApplicationContext(),PopularSearches.class);
            startActivity(i);
            return true;
            
        case R.id.searchhistory:
        	i=new Intent(getApplicationContext(),SearchHistory.class);
        	startActivity(i);
        	return true;
        case R.id.about:
        		Toast.makeText(getApplicationContext(), "A rough prototype by Arjit Sachdeva", Toast.LENGTH_LONG).show();
            return true;
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    
}
