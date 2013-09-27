package com.socialapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PopularSearches extends Activity{

	ArrayList<HashMap<String, String>> mylist;
	ArrayList<String> queries;
	
	private StringBuilder inputStreamToString(InputStream is) {
	    String rLine = "";
	    StringBuilder answer = new StringBuilder();
	    BufferedReader rd = new BufferedReader(new InputStreamReader(is));

	    try {
	     while ((rLine = rd.readLine()) != null) {
	      answer.append(rLine);
	       }
	    }

	    catch (IOException e) {
	        e.printStackTrace();
	     }
	    return answer;
	   }

	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popularsearches);
        
        new Thread(new Runnable(){
        	public void run(){
        		
        		
                HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost("http://server1.mewtwo.net/~arjit/SocialApp/getqueries.php");

				queries=new ArrayList<String>();
                // Instantiate a GET HTTP method
                try {
                   
                    //
                    
                 //   ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    HttpResponse response = httpclient.execute(httppost);
                    // Parse
                                           
                    String jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                    JSONArray mArray = new JSONArray(jsonResult);
                    for (int i = 0; i < mArray.length(); i++) {
                        JSONObject object = mArray.getJSONObject(i);

                        String query = object.getString("query");
                        queries.add(query);
                        Log.d("query", query);
                        
                    }
                    
                   runOnUiThread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						 ListView lv=(ListView) findViewById(R.id.list);


		                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, queries);
		                    // updating listview
		                    lv.setAdapter(adapter);
						
					}
                	   
                   });
                   

                    
                                        //Toast.makeText(this, responseBody, Toast.LENGTH_LONG).show();

                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // Log.i(getClass().getSimpleName(), "send  task - end");


        	}
        	
        	
        }).start();

	}
}