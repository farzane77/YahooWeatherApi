package com.example.espinas.emyahooweatherapi;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    Button btn;
    EditText edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn=(Button)findViewById(R.id.button);
        edt=(EditText)findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://api.telegram.org/bot544567540:AAHXs9eBvN-drFojmmdsbPpJjPirX1nwGSI/getUpdates";
                new MyAsincTask().execute(url);
            }
        });
    }
    public class MyAsincTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onProgressUpdate(String... values) {
            try {
                JSONObject json=new JSONObject(values[0]);
                JSONArray result=json.getJSONArray("result");
                JSONObject result1=result.getJSONObject(result.length()-1);
                JSONObject message=result1.getJSONObject("message");
                String text=message.getString("text");
                Toast.makeText(getApplicationContext(),text+"",Toast.LENGTH_LONG).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            String newData;
            try {
                URL url=new URL(strings[0]);
                try {
                    HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                    urlConnection.setConnectTimeout(7000);
                    InputStream in=new BufferedInputStream(urlConnection.getInputStream());
                    newData=convertInputStreamToString(in);
                    publishProgress(newData);
                    urlConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private String convertInputStreamToString(InputStream in) {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
        String line;
        String lineReu="";
        try {
            while((line=bufferedReader.readLine())!=null){
                lineReu+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineReu;
    }
}