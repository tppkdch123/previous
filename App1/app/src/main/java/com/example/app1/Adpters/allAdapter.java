package com.example.app1.Adpters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app1.R;
import com.example.app1.vo.plate;

import java.util.List;

/**
 * Created by 世哲 on 2017/8/10.
 */

public class allAdapter extends BaseAdapter {
    private List<plate> L;
    private Context context;
    public allAdapter(Context context,List<plate> L){
        this.L=L;
        this.context=context;
    }
    @Override
    public int getCount() {
        return L.size();
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
        holdView h=null;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.plate,null);
            h=new holdView();
            h.iv=(ImageView) convertView.findViewById(R.id.imageView);
            h.tv=(TextView)convertView.findViewById(R.id.textView20);
            convertView.setTag(h);
        }else h=(holdView) convertView.getTag();
        h.tv.setText(L.get(position).getPlatename());
        h.iv.setImageResource(R.drawable.image1);
        return convertView;
    }
    class holdView{
        ImageView iv;
        TextView tv;
    }
}
