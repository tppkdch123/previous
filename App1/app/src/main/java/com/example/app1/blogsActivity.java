package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.app1.Adpters.BlogsAdapter;
import com.example.app1.Adpters.platesAdapter;
import com.example.app1.activities.openBlog;
import com.example.app1.activities.sendBlog;
import com.example.app1.activities.tools;
import com.example.app1.vo.blog;
import com.example.app1.vo.plate;

import java.io.InputStream;
import java.util.List;

import Http.MyHttpConnection;

public class blogsActivity extends Activity {
private ListView lv;
    private List<blog> blogList;
    private Button button4;
    private int a;
    private ImageButton shoucang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools.golive2(this,R.layout.activity_blogs,getIntent().getStringExtra("platename"));
        ((ImageButton)findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blogsActivity.this.finish();
            }
        });
        shoucang=(ImageButton)findViewById(R.id.imageButton2);
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new insertFollow().execute(getIntent().getIntExtra("plateid",0));
            }
        });
       View v= LayoutInflater.from(this).inflate(R.layout.ui,null);
        addContentView(v, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        button4=(Button)findViewById(R.id.button4);
        a=getIntent().getIntExtra("plateid",0);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyApplication.getContext(),sendBlog.class);
                intent.putExtra("plateid",a);
                startActivity(intent);
            }
        });
        lv=(ListView)findViewById(R.id.listView1);
        new loadBlogs().execute(a);
        new lookif().execute();
    }


class lookif extends AsyncTask<Void,Void,String>{

    @Override
    protected String doInBackground(Void... params) {
        return MyHttpConnection.getStringContent("ifExists.do?plateid="+String.valueOf(a));
    }
    @Override
    protected void onPostExecute(String result){
if(result!=null&&result.equals("yes"))
    shoucang.setBackgroundResource(R.drawable.a1);
else shoucang.setBackgroundResource(R.drawable.a2);
    }
}
    class loadBlogs extends AsyncTask<Integer,Void,List<blog>>{

        @Override
        protected List<blog> doInBackground(Integer... params) {
            return MyHttpConnection.enterBlogs(params[0].toString());
        }
        @Override
        protected void onPostExecute(List<blog> result){
            blogList=result;
            BlogsAdapter p=new BlogsAdapter(blogsActivity.this,result);
            lv.setAdapter(p);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(MyApplication.getContext(),openBlog.class);
                    intent.putExtra("blogid",blogList.get(position).getBlogid());
                    startActivity(intent);
                }
            });
        }
    }
    class insertFollow extends AsyncTask<Integer,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Integer... params) {
            return MyHttpConnection.insertFollow(params[0]);
        }
        @Override
        protected void onPostExecute(Boolean result){
            if(result)
             shoucang.setBackgroundResource(R.drawable.a1);
            else shoucang.setBackgroundResource(R.drawable.a2);
        }
    }
}
