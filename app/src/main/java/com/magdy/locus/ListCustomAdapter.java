package com.magdy.locus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hope on 10/26/2016.
 */
public class ListCustomAdapter extends BaseAdapter {


    List<String> timelist;
    List<Boolean> avaList;
    Context ctx;

    public ListCustomAdapter(Context ctx, List<String> timelist, List<Boolean> avaList) {
        this.ctx = ctx;
        this.timelist = timelist;
        this.avaList = avaList;
    }

    @Override
    public int getCount() {
        return timelist.size();
    }

    @Override
    public Object getItem(int position) {
        return timelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void clearAdapter(){
        timelist.clear();
        avaList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item_schedule, parent, false);
        TextView time = (TextView) view.findViewById(R.id.time_textview);
        TextView ava = (TextView) view.findViewById(R.id.availablity_textview);
        time.setText(timelist.get(position));
        ava.setText(avaList.get(position)+"");
        return view;
    }
}
