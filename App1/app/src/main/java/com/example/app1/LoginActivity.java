package com.example.app1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Http.MyHttpConnection;

public class LoginActivity extends AppCompatActivity {
    public EditText editText;
    public EditText editText2;
    public Button button;
    public Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        editText=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        button=(Button)findViewById(R.id.button);
        button2=(Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(MyHttpConnection.isOnline())
                new LoginTask().execute(editText.getText().toString(),editText2.getText().toString());
            else Toast.makeText(LoginActivity.this,"请开启网络",Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,registerActivity.class);
                startActivity(intent);
            }
        });
    }
    class LoginTask extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... params) {
            return MyHttpConnection.getLoginResult(params[0],params[1]);
        }
        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            if(result!=null){
               if(result.equals("success")) {
                   Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(LoginActivity.this, paltesActivity.class);
                   startActivity(intent);
                   finish();
               }
                else  Toast.makeText(LoginActivity.this,result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}


