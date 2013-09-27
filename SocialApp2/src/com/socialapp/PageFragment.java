package com.socialapp;

 import com.androidhive.imagefromurl.ImageLoader;

import android.os.Bundle;  
import android.support.v4.app.Fragment;  
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.ImageView;
import android.widget.TextView;  
   
 public class PageFragment extends Fragment {  
        
      public static PageFragment newInstance(String service,String message,String name, String img) {
  
          PageFragment pageFragment = new PageFragment();
          Bundle bundle = new Bundle();
          bundle.putString("service", service);
          bundle.putString("message", message);
          bundle.putString("name", name);
          bundle.putString("image", img);
          pageFragment.setArguments(bundle);
          return pageFragment;
      }

      public static PageFragment newInstance(String service,String tweets) {
    	  
          PageFragment pageFragment = new PageFragment();
          Bundle bundle = new Bundle();
          bundle.putString("service", service);
          bundle.putString("tweets", tweets);
          pageFragment.setArguments(bundle);
          return pageFragment;
      }

      @Override  
      public void onCreate(Bundle savedInstanceState) {  
          super.onCreate(savedInstanceState);  
      }  
        
      @Override  
      public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {  
             
          View view = inflater.inflate(R.layout.fragment, container, false);
          
          // Loader image - will be shown before loading image
          int loader = R.drawable.loader;
          
          // Imageview to show
          ImageView stub = (ImageView)view.findViewById(R.id.loadericon);
          
          
          //If service=facebook:
          if(getArguments().getString("service").equals("facebook")){
          // Image url
          String image_url = getArguments().getString("image");
          
          // ImageLoader class instance
          ImageLoader imgLoader = new ImageLoader(view.getContext());
          
          // whenever you want to load an image from url
          // call DisplayImage function
          // url - image url to load
          // loader - loader image, will be displayed before getting image
          // image - ImageView 
          imgLoader.DisplayImage(image_url, loader, stub);
      
          TextView textView = (TextView) view.findViewById(R.id.message);  
          textView.setText(getArguments().getString("message"));
          TextView textView2 = (TextView) view.findViewById(R.id.name);  
          textView2.setText("Posted by:"+getArguments().getString("name"));
          }
          /*else
          {  //If service=twitter
      
        	  view = inflater.inflate(R.layout.singletextbox, container, false);
              
        	  
        	    // Image url
              TextView textView = (TextView) view.findViewById(R.id.textView1);  
              textView.setText(getArguments().getString("tweets"));
              textView.setMovementMethod(new ScrollingMovementMethod());

              }
              */
          return view;  
      }  
 }  
