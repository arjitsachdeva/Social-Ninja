package com.socialapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SearchHistory extends Activity{
	SQLiteDatabase mydb;
	private static String DBNAME = "arjit_queries.db";
	private static String TABLE = "queries";

	ArrayList<String>queriesList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popularsearches); //just because it has only a listview that we need

	queriesList=new ArrayList<String>();
	
	
	mydb = openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE,null);
    Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  TABLE, null);
    //Integer cindex = allrows.getColumnIndex("QUERY");
    if(allrows.moveToFirst()){
        do{
    String query = allrows.getString(0);
    Log.d("query:",query);
    queriesList.add(query);
    //queriesList.add(allrows.getString(cindex));
        }
        while(allrows.moveToNext());
    }
    mydb.close();
    
    ListView lv=(ListView) findViewById(R.id.list);


    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, queriesList);
    // updating listview
    lv.setAdapter(adapter);
    
    
    }
}