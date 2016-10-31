package com.magdy.locus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by moham on 16/10/2016.
 */

public class NavListAdapter extends BaseAdapter {

    String[] title;
    int [] image;
    Context ctx;

    public NavListAdapter(String[] title, int[] image, Context ctx) {
        this.title = title;
        this.image = image;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public Object getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v= inflater.inflate(R.layout.inf_navlist,parent,false);

        ImageView img=(ImageView)v.findViewById(R.id.inf_img);
        TextView txt= (TextView)v.findViewById(R.id.inf_txt);

        img.setImageResource(image [position]);
        txt.setText(title[position]);


        return v;
    }
}
