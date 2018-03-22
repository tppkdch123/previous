package com.example.app1.Adpters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app1.MyApplication;
import com.example.app1.R;
import com.example.app1.vo.follow;
import com.example.app1.vo.response;
import com.example.app1.vo.user;

import org.w3c.dom.Text;

import java.util.List;

import Http.MyHttpConnection;

/**
 * Created by 世哲 on 2017/8/8.
 */

public class responseAdapter extends BaseAdapter {
    private Context context;
    public responseAdapter(Context context,List<response> L,List<follow> F){
        this.context=context;
        this.L=L;
        this.F=F;
    }
    List<response> L;
    List<follow> F;
    @Override
    public int getCount() {
        return L.size()*3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int a=position%3;
        int b=position/3%2;
        response res=L.get(position/3);
            switch (a){
                case 0:
                    convertView= LayoutInflater.from(context).inflate(R.layout.user_layout,null);
                    if(b==1)convertView.setBackgroundColor(MyApplication.getContext().getColor(R.color.yellow));
                    ((TextView)convertView.findViewById(R.id.textView12)).setText(res.getUsername());
                    ((TextView)convertView.findViewById(R.id.textView13)).setText(res.getFloor()+"楼");
                    if(F.get(position/3)!=null)
                        ((TextView)convertView.findViewById(R.id.textView14)).setText("声望："+String.valueOf(F.get(position/3).getPrestige()));
                    else ((TextView)convertView.findViewById(R.id.textView14)).setText("声望：--");
                    break;
                case 1:
                    convertView= LayoutInflater.from(context).inflate(R.layout.activity_login3,null);
                    if(b==1)convertView.setBackgroundColor(MyApplication.getContext().getColor(R.color.yellow));
                    ((TextView)convertView.findViewById(R.id.textView16)).setText(res.getContent());
                    ((TextView)convertView.findViewById(R.id.textView19)).setText(res.getResponseTime().toString());
                    break;
                case 2:
                    convertView= LayoutInflater.from(context).inflate(R.layout.floor_down,null);
                    if(b==1)convertView.setBackgroundColor(MyApplication.getContext().getColor(R.color.yellow));
                   ImageView i1= ((ImageView)convertView.findViewById(R.id.imageView3));
                   ImageView i2=((ImageView)convertView.findViewById(R.id.imageView9));
                    TextView T1=((TextView)convertView.findViewById(R.id.textView11));
                    T1.setText(String.valueOf(res.getFabulous()));
                    TextView T2=((TextView)convertView.findViewById(R.id.textView15));
                    T2.setText(String.valueOf(res.getStep()));
                    holderView1 h1=new holderView1(T1,res.getResponseid());
                    holderView1 h2=new holderView1(T2,res.getResponseid());
                    i1.setTag(h1);
                    i2.setTag(h2);
                    i1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holderView1 h=(holderView1) v.getTag();
                            new dianZan(h.T).execute(new Integer(h.responseId).toString());
                        }
                    });
                    i2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            holderView1 h=(holderView1) v.getTag();
                            new dianCai(h.T).execute(new Integer(h.responseId).toString());
                        }
                    });
                    break;
                default:break;
        }
        return convertView;
    }
    class holderView1{
        TextView T;
        int responseId;
        holderView1(TextView T,int responseId){
            this.T=T;
            this.responseId=responseId;
        }
    }
    class holderView2{
        TextView textView11;
        TextView textView15;
        ImageView imageView3;
        ImageView imageView9;
    }
    class holderView3{
        TextView t;
    }
    class dianCai extends AsyncTask<String,Void,String> {
        private TextView im;

        dianCai(TextView a) {
            this.im = a;
        }

        @Override
        protected String doInBackground(String... params) {
            String A = MyHttpConnection.getStringContent("doCai.do?responseId=" + params[0]);
            return A;
        }

        @Override
        protected void onPostExecute(String result) {
            im.setText(result);
        }
    }
    class dianZan extends AsyncTask<String,Void,String>{
        private TextView im;
        dianZan(TextView a){
            this.im=a;
        }
        @Override
        protected String doInBackground(String... params) {
            String A=MyHttpConnection.getStringContent("doZan.do?responseId="+params[0]);
            return A;
        }
        @Override
        protected void onPostExecute(String result){
            im.setText(result);
        }
    }
}
