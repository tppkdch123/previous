package com.example.app1;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app1.activities.tools;

import Http.MyHttpConnection;

public class registerActivity extends Activity {
  private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools.golive(this,R.layout.activity_register,"注册");
        ((Button)findViewById(R.id.button8)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerActivity.this.finish();
            }
        });
        editText3=(EditText)findViewById(R.id.editText3);
        editText4=(EditText)findViewById(R.id.editText4);
        editText5=(EditText)findViewById(R.id.editText5);
        button3=(Button)findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   new registerTask().execute(editText3.getText().toString(),editText4.getText().toString(),editText5.getText().toString());
            }
        });
    }
    class registerTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
           return MyHttpConnection.getRegisterResult(params[0],params[1],params[2]);
        }
        @Override
        protected void onPostExecute(String result){
            switch(result){
                case "success":
                    Toast.makeText(registerActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    break;
                case "usernameExists":
                    Toast.makeText(registerActivity.this,"用户名已经存在",Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(registerActivity.this,result,Toast.LENGTH_SHORT).show();
            }
        }
    }
}
