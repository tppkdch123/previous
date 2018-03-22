package com.example.app1.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app1.MyApplication;
import com.example.app1.R;

import Http.MyHttpConnection;

public class responseActivity extends Activity {
private EditText responseContent;
    private int blogid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools.golive4(this,R.layout.activity_response,"回复");
        ((ImageButton)findViewById(R.id.imageButton5)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responseActivity.this.finish();
            }
        });
        ((ImageButton)findViewById(R.id.imageButton6)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new sendResponse().execute(String.valueOf(blogid),responseContent.getText().toString());
            }
        });
        blogid=getIntent().getIntExtra("blogid",-1);
        responseContent=(EditText)findViewById(R.id.editText8);
    }

    class sendResponse extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return MyHttpConnection.getStringContent("insertResponse.do?blogid="+params[0]+"&content="+params[1]);
        }
        @Override
        protected void onPostExecute(String result){
  if(result!=null&&result.equals("responseSuccess")){
      Intent intent =new Intent(MyApplication.getContext(),openBlog.class);
      intent.putExtra("blogid",blogid);
      startActivity(intent);
      finish();
  }
  else Toast.makeText(responseActivity.this,"回复大失败",Toast.LENGTH_SHORT).show();
        }
    }
}
