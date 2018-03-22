package com.example.app1;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.app1.Adpters.allAdapter;
import com.example.app1.vo.plate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import Http.MyHttpConnection;

public class paltesActivity extends Activity {
  private List<plate> plates=null;
    private List<plate> plates2;
    private TabHost tabHost;
    private int a;
    private allAdapter a1;
    private allAdapter a2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paltes);
    tabHost=(TabHost)findViewById(R.id.tabhost);
        tabHost.setup();
        new getPlatesTask().execute();
        new getPlatesTask2().execute();
    }



    class getPlatesTask extends AsyncTask<Void,Void,List<plate>>{

        @Override
        protected List<plate> doInBackground(Void... params) {
            List<plate> platess=MyHttpConnection.getPlatesActivity();
            a1=new allAdapter(paltesActivity.this,platess);
            return platess;
        }
        @Override
        protected void onPostExecute(List<plate> result){
            plates=result;
            GridView G2=((GridView)paltesActivity.this.findViewById(R.id.g3));
            tabHost.addTab(tabHost.newTabSpec("two").setIndicator("全部版块").setContent(R.id.tab2));
            G2.setAdapter(a1);
            G2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(MyApplication.getContext(),blogsActivity.class);
                    intent.putExtra("plateid",plates.get(position).getPalteid());
                    intent.putExtra("platename",plates.get(position).getPlatename());
                    startActivity(intent);
                }
            });
        }
    }
    class getPlatesTask2 extends AsyncTask<Void,Void,List<plate>> {

        @Override
        protected List<plate> doInBackground(Void... params) {
            List<plate> platess2 = MyHttpConnection.getMyPlates();
            a2 = new allAdapter(paltesActivity.this, platess2);
            return platess2;
        }
        @Override
        protected void onPostExecute(List<plate> result) {
            plates2 = result;
            GridView G1 = ((GridView) paltesActivity.this.findViewById(R.id.g2));
            tabHost.addTab(tabHost.newTabSpec("two").setIndicator("我的收藏").setContent(R.id.tab1));
            G1.setAdapter(a2);
            G1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MyApplication.getContext(), blogsActivity.class);
                    intent.putExtra("plateid", plates2.get(position).getPalteid());
                    intent.putExtra("platename", plates2.get(position).getPlatename());
                    startActivity(intent);
                }
            });
        }
    }

}
