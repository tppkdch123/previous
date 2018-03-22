package com.example.app1.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app1.R;
import com.example.app1.vo.blog;

import java.util.List;

/**
 * Created by 世哲 on 2017/8/6.
 */

public class platesAdapter extends BaseAdapter {
    private Context context;
    private List<blog> blogs;
    public platesAdapter(Context c,List<blog> L){
        context=c;
        blogs=L;
    }
    @Override
    public int getCount() {
        return blogs.size();
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
        if (convertView == null) {
            HolderView holderView=new HolderView();
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_list1,null);
            holderView.textView6=(TextView)convertView.findViewById(R.id.textView6);
            holderView.textView7=(TextView)convertView.findViewById(R.id.textView7);
            holderView.textView8=(TextView)convertView.findViewById(R.id.textView8);
            holderView.textView9=(TextView)convertView.findViewById(R.id.textView9);
            holderView.imageView4=(ImageView)convertView.findViewById(R.id.imageView4);
            holderView.imageView5=(ImageView)convertView.findViewById(R.id.imageView5);
            holderView.imageView6=(ImageView)convertView.findViewById(R.id.imageView6);
            convertView.setTag(holderView);
        }
        else{
            HolderView holderView=(HolderView) convertView.getTag();
            holderView.textView6.setText(blogs.get(position).getTitle());
            holderView.textView7.setText(blogs.get(position).getUsername());
            holderView.textView8.setText(blogs.get(position).getCreatetime().toString());
            holderView.textView9.setText(blogs.get(position).getFloornumber());
        }
        return null;
    }
    private static class HolderView{
     TextView textView6;
        TextView textView7;
        TextView textView8;
        TextView textView9;
        ImageView imageView4;
        ImageView imageView5;
        ImageView imageView6;
    }
}
