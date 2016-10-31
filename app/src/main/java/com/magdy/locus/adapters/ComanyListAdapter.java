package com.magdy.locus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.magdy.locus.R;
import com.magdy.locus.interfacs.Onclick;

/**
 * Created by moham on 28/10/2016.
 */

public class ComanyListAdapter extends BaseAdapter {

    Context ctx;
    int roomnumbers;
    String[] spinner_adapter={"1","2","3","4","5","6","7","8","9","10", "11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
    Onclick onclick;

    public ComanyListAdapter(int roomnumbers, Context ctx,Onclick onclick) {
        this.roomnumbers = roomnumbers;
        this.ctx = ctx;
        this.onclick=onclick;
    }

    @Override
    public int getCount() {
        return roomnumbers;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.inf_listcompanytable,parent,false);
        TextView roomnum=(TextView)v.findViewById(R.id.txt_roomnum);
        Spinner sp_from=(Spinner) v.findViewById(R.id.sp_from);
        Spinner sp_to=(Spinner) v.findViewById(R.id.sp_to);
        final Button bt=(Button)v.findViewById(R.id.btn_subbmit);

        final int x = position;
        final ArrayAdapter adapter=new ArrayAdapter(ctx,R.layout.support_simple_spinner_dropdown_item,spinner_adapter);
        sp_from.setAdapter(adapter);
        sp_to.setAdapter(adapter);
        String h;
        sp_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                final String hour = (String) adapter.getItem(position);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onclick.onclickelis(Integer.parseInt(hour), x);

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int newpos=position+1;
        roomnum.setText("Room "+ newpos);

        return v;
    }
}
