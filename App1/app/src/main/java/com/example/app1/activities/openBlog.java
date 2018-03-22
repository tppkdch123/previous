package com.example.app1.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app1.Adpters.responseAdapter;
import com.example.app1.MyApplication;
import com.example.app1.R;
import com.example.app1.vo.follow;
import com.example.app1.vo.response;
import com.example.app1.vo.user;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.w3c.dom.Text;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Http.MyHttpConnection;

public class openBlog extends Activity implements AbsListView.OnScrollListener{
private ListView L;
    private boolean top;
    private TextView T;
    private int pageNum;
    private int currentPage=1;
    private List<response> responseList;
    private List<follow> followList;
    private int blogid;
    private View UIView;
    private Button response;
    private Button update;
    private ImageButton rollback;
    private ImageButton just;
    int firstVisibleItem;
    int visibleItemCount;
    int totalItemCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tools.golive3(this,R.layout.activity_open_blog,"主题详情");
        rollback=(ImageButton)findViewById(R.id.imageButton3);
        just=(ImageButton)findViewById(R.id.imageButton4);
        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBlog.this.finish();
            }
        });
        blogid=getIntent().getIntExtra("blogid",0);
        T=(TextView)findViewById(R.id.textView17);
        L=(ListView)findViewById(R.id.listView2);
        L.setOnScrollListener(this);
        followList=new LinkedList<follow>();
        UIView= View.inflate(this,R.layout.ui2,null);
        RelativeLayout.LayoutParams L=new RelativeLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        addContentView(UIView,L);
        update=(Button)findViewById(R.id.button5);
        this.response=(Button)findViewById(R.id.button6);
        response.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MyApplication.getContext(),responseActivity.class);
                intent.putExtra("blogid",blogid);
                startActivity(intent);
            }
        });
        new getTitle().execute(new Integer(blogid).toString());
        new getResponse().execute(new Integer(blogid).toString(),"1");
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
       switch(scrollState){
           case SCROLL_STATE_TOUCH_SCROLL:

               break;
           case SCROLL_STATE_FLING:
               break;
           case SCROLL_STATE_IDLE:
               if (firstVisibleItem+visibleItemCount == totalItemCount) {
                   if(pageNum>currentPage)
                       new getResponse().execute(new Integer(blogid).toString(),new Integer(Integer.valueOf(currentPage)+1).toString());
               }
               else  if (firstVisibleItem==0) {
                   if(pageNum>1)
                       new getResponse().execute(new Integer(blogid).toString(),new Integer(Integer.valueOf(currentPage)-1).toString());
               }
              break;
       }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem=firstVisibleItem;
        this.visibleItemCount=visibleItemCount;
        this.totalItemCount=totalItemCount;
    }

    class getTitle extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... params) {
            return MyHttpConnection.getStringContent("getBlogTitle.do?blogid="+params[0]);
        }
        @Override
        protected void onPostExecute(String result){
            T.setText(result);
        }
    }
    class getResponse extends AsyncTask<String,Void,List<response>>{

        @Override
        protected List<response> doInBackground(String... params) {
            String json= MyHttpConnection.getStringContent("getAllResponse.do?blogid="+params[0]+"&page="+params[1]);
            String number=MyHttpConnection.getStringContent("getResponseNum.do?blogid="+params[0]);
            int n=Integer.valueOf(number);
            if(n<8)
                pageNum=1;
            else if(n/8==0)
            pageNum=Integer.valueOf(number)/8;
            else pageNum=Integer.valueOf(number)/8+1;
            currentPage=Integer.valueOf(params[1]);
            List<response> L=MyHttpConnection.G.fromJson(json,new TypeToken<List<response>>(){}.getType());
            if(L!=null){
            for(response r:L){
                String result=MyHttpConnection.getPlateid(r.getResponseid());
                if(result!=null)
                followList.add(MyHttpConnection.getFollowById(r.getUsername(),Integer.valueOf(result)));
                else followList.add(null);
            }
            return L;
            }
            else return null;
        }
        @Override
        protected void onPostExecute(List<response> result){
            if(result!=null){
        L.setAdapter(new responseAdapter(openBlog.this,result,followList));
                update.setText(new Integer(currentPage).toString()+"/"+new Integer(pageNum).toString());
            }
        }
    }
}
