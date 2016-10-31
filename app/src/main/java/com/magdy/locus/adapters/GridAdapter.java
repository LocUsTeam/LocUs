package com.magdy.locus.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.magdy.locus.R;
import com.magdy.locus.resorces.Place;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.R.id.list;

/**
 * Created by moham on 26/10/2016.
 */

public class GridAdapter extends BaseAdapter {

    List<String> imgs;
    Context context;
    List<Place> txt;

    public GridAdapter(List<Place> txt, List<String> imgs, Context context) {
        this.txt = txt;
        this.imgs = imgs;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgs.size();
    }

    @Override
    public Object getItem(int position) {
        return imgs.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v=inflater.inflate(R.layout.inf_grid,parent,false);
        TextView txt1=(TextView)v.findViewById(R.id.inf_gridtxt);
        ImageView img=(ImageView)v.findViewById(R.id.inf_gridimg);

        txt1.setText(txt.get(position).getPLACE_NAME());
        Picasso.with(context)
                .load(imgs.get(position)).fit()
                .into(img);

        return v;
    }
}
