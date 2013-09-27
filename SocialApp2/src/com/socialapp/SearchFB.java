package com.socialapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.restfb.FacebookClient.AccessToken;
import com.restfb.*;
import com.restfb.types.Post;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class SearchFB extends FragmentActivity {  
	  
	public Connection<Post> publicSearch;
	StringBuilder tweetResultBuilder;
    private static final int NUMBER_OF_PAGES = 10;  
 ProgressDialog progressDialog;
    String accessToken;
    private ViewPager mViewPager;  
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;  
 
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.pagermenu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        
        switch (item.getItemId())
        {
        case R.id.twitteroption:
        	// Single menu item is selected do something
        	// Ex: launching new activity/screen or show alert message
            Intent i=new Intent(getApplicationContext(),TwitterResults.class);
            i.putExtra("tweets", tweetResultBuilder.toString());
            startActivity(i);
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    
    void resultsReady(){
    	
    	mViewPager = (ViewPager) findViewById(R.id.viewpager);  
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());  
        mViewPager.setAdapter(mMyFragmentPagerAdapter);  


    	
    }
    
    public void onCreate(Bundle savedInstanceState) {  
 
         super.onCreate(savedInstanceState);  
         setContentView(R.layout.pager);  
     

         
         
         
    	 SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
    	   accessToken= sharedPreferences.getString("fbtoken", "");

    	   progressDialog = new ProgressDialog(this);
    	   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); 

    	   progressDialog.setMessage("Our Ninjas are on your job, Please Wait..."); 
    	   
    	   progressDialog.show(); 

    	   Intent i=getIntent();
    	   String query=i.getStringExtra("query");
    	   
    	   new doSearch().execute(query);
    	   
    	   
    	   
    	   
         
    }  
 
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {  

    	
         public MyFragmentPagerAdapter(FragmentManager fm) {  
              super(fm);  
         }  
 
         @Override  
         public Fragment getItem(int index) {  
 
        	 if(publicSearch.getData().get(index).getMessage()!=null){
        		 return PageFragment.newInstance("facebook",publicSearch.getData().get(index).getMessage(),publicSearch.getData().get(index).getFrom().getName(),publicSearch.getData().get(index).getPicture()); 
        	 }
              
        	 else
        		 return PageFragment.newInstance("facebook",publicSearch.getData().get(index).getDescription(),publicSearch.getData().get(index).getFrom().getName(),publicSearch.getData().get(index).getPicture());
         }  
 
         @Override  
         public int getCount() {  
 
              return NUMBER_OF_PAGES;  
         }  
    }
    
    

	private class doSearch extends AsyncTask<String, Void, Void>{

		 
			protected Void doInBackground(String... params) {
				
				// TODO Auto-generated method stub
				String query=params[0];

				String encodedSearch = null;
				try {
					encodedSearch = URLEncoder.encode(query, "UTF-8");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//append encoded user search term to search URL
				String searchURL = "http://search.twitter.com/search.json?q="+encodedSearch;
		

				
				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new HttpPost("http://server1.mewtwo.net/~arjit/SocialApp/addquery.php");
				try {                   

				    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);

					nameValuePairs.add(new BasicNameValuePair("query", query ));
				    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				    httpclient.execute(httppost);
				    Log.d("php", "addquery");

				} catch (ClientProtocolException e) {
				    Toast.makeText(getApplicationContext(), "Client protokol exception ", Toast.LENGTH_LONG).show();
				} catch (IOException e) {
				    Toast.makeText(getApplicationContext(), "IO exception "+e.getMessage(), Toast.LENGTH_LONG).show();
				}

//Facebook fetching:
			        		FacebookClient facebookClient = new DefaultFacebookClient(accessToken);

			        		
			        		publicSearch =
			        				  facebookClient.fetchConnection("search", Post.class,
			        				    Parameter.with("q", query), Parameter.with("type", "post"));
			        		
			        		
			        		
			        		//Twitter fetching:
			        		Log.d("twitter","Now fetching Twitter");
			    			//start building result which will be json string
			    			StringBuilder tweetFeedBuilder = new StringBuilder();
			    			//should only be one URL, receives array
			    			
			    				HttpClient tweetClient = new DefaultHttpClient();
			    				try {
			    					//pass search URL string to fetch
			    					HttpGet tweetGet = new HttpGet(searchURL);
			    					//execute request
			    					HttpResponse tweetResponse = tweetClient.execute(tweetGet);
			    					//check status, only proceed if ok
			    					StatusLine searchStatus = tweetResponse.getStatusLine();
			    					if (searchStatus.getStatusCode() == 200) {
			    						//get the response
			    						HttpEntity tweetEntity = tweetResponse.getEntity();
			    						InputStream tweetContent = tweetEntity.getContent();
			    						//process the results
			    						InputStreamReader tweetInput = new InputStreamReader(tweetContent);
			    						BufferedReader tweetReader = new BufferedReader(tweetInput);
			    						String lineIn;
			    						while ((lineIn = tweetReader.readLine()) != null) {
			    							tweetFeedBuilder.append(lineIn);
			    						}
			    					}
			    					
			    				}
			    				catch(Exception e){ 
			    					//tweetDisplay.setText("Whoops - something went wrong!");
			    					e.printStackTrace(); 
			    				}
			    			
			    				tweetResultBuilder = new StringBuilder();
			    				try {
			    					//get JSONObject from result
			    					JSONObject resultObject = new JSONObject(tweetFeedBuilder.toString());
			    					//get JSONArray contained within the JSONObject retrieved - "results"
			    					JSONArray tweetArray = resultObject.getJSONArray("results");
			    					//loop through each item in the tweet array
			    					for (int t=0; t<tweetArray.length(); t++) {
			    						//each item is a JSONObject
			    						JSONObject tweetObject = tweetArray.getJSONObject(t);
			    						//get the username and text content for each tweet
			    						tweetResultBuilder.append(tweetObject.getString("from_user")+": ");
			    						tweetResultBuilder.append(tweetObject.get("text")+"\n\n");
			    					}
			    				}
			    				catch (Exception e) {
			    					//tweetDisplay.setText("Whoops - something went wrong!");
			    					e.printStackTrace();
			    				}
			    				//check result exists
			    			//	if(tweetResultBuilder.length()>0)
			    			
			    				
			    					//tweetDisplay.setText("Sorry - no tweets found for your search!");
			    			
			        		
			        		
			        		progressDialog.dismiss();
			        		


			        		runOnUiThread(new Runnable() {
			        		    public void run() {

			        		    	resultsReady();
			        		    }
			        		});
				return null;
			}
	    	
	    	
			protected void onPostExecute(Void result) {
				
				
			}
	    	
	    }
    	 
    }


    
	

    

