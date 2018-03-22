package com.example.app1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                   Thread.sleep(2000);
                   Intent intent= new Intent(MyApplication.getContext(),LoginActivity.class);
                   startActivity(intent);
                   MainActivity.this.finish();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }).start();
    }

}

