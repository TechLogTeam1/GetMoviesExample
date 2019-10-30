package com.example.jsoupgetdata;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {

    private View mContentView;
    private TextView mTextView;


    // Delayed removal of status and navigation bar

    // Note that some of these constants are new as of API 16 (Jelly Bean)
    // and API 19 (KitKat). It is safe to use them, as they are inlined
    // at compile-time and do nothing on earlier devices.

    private View mControlsView;
    String Line,Line2;
    //String url ="https://developer.android.com";
    //String url ="https://www.google.com";
    //String url ="https://www.contra.gr";
    private Button mButton1;

    ProgressDialog progressDialog;


    //Edit AndroidManifest.xml
    //Add <uses-permission android:name="android.permission.INTERNET" />
    //after package command

    //If not runs in newer emulator (e.x. Pixel 2 API 29) simple uninstall app
    // from mobile emulator and run again

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mTextView = findViewById(R.id.textView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());

        mButton1= (Button) findViewById(R.id.button);

        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Content content=new Content();
                content.execute();

            }
        });

    }

    //@SuppressLint("InlinedApi")


    private class Content extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(FullscreenActivity.this);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mTextView.setText(Line);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Document doc= null;
            Line="";
            try {
                doc = Jsoup.connect("http://www.imdb.com/chart/top").get();
                //in programmer tools in this url -> tbody class
                Elements e=doc.getElementsByClass("titleColumn");
                Elements t=doc.getElementsByClass("imdbRating");


                float suma=0;

                for(int i=0;i<e.size();i++) suma=suma+Float.parseFloat(t.get(i).text());

                Line+="Url Title:"+doc.title()+"\n";
                Line+="Average Score:"+suma/250+"\n";

                for(int i=0;i<e.size();i++)
                 Line+=e.get(i).text()+":"+t.get(i).text()+"\n";


            } catch (IOException e) {
                e.printStackTrace();
            }

            /*try {
                Document document = Jsoup.connect(url).get();
                Line=document.title();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            return null;
        }
    }


}
