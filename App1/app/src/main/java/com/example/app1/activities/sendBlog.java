package com.example.app1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app1.MyApplication;
import com.example.app1.R;
import com.example.app1.blogsActivity;

import java.util.HashMap;
import java.util.Map;

import Http.MyHttpConnection;

public class sendBlog extends Activity {
   private EditText E1;
    private EditText E2;
    private int plateid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_blog);
        plateid=getIntent().getIntExtra("plateid",0);
        E1=(EditText)findViewById(R.id.editText6);
        E2=(EditText)findViewById(R.id.editText7);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.send_blog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_main_edit:
                new sendTask().execute(E1.getText().toString(),E2.getText().toString());
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    class sendTask extends AsyncTask<String,Void,Boolean>{


        @Override
        protected Boolean doInBackground(String... params) {
            Map<String,String> M=new HashMap<String,String>();
            M.put("title",params[0]);
            M.put("describes",params[1]);
            M.put("blogtype","default");
            M.put("plateid",new Integer(plateid).toString());
            return MyHttpConnection.insertBlogs(M);
        }

        @Override
        protected void onPostExecute(Boolean result){
          if(result)
              Toast.makeText(MyApplication.getContext(),"发布完成",Toast.LENGTH_SHORT).show();
            else Toast.makeText(MyApplication.getContext(),"发布失败",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(MyApplication.getContext(),blogsActivity.class);
            intent.putExtra("plateid",plateid);
            startActivity(intent);
            sendBlog.this.finish();
        }
    }
}
