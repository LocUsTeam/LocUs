package com.magdy.locus;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by engma on 10/19/2016.
 */
public class CustomSwipeAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context ctx ;
    public CustomSwipeAdapter(Context ctx,int [] images)
    {
        this.ctx = ctx;
        images_resources = images;


    }
    private  int [] images_resources ;
    @Override
    public int getCount() {

        return images_resources.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.image_slide,container,false);
        ImageView imageView =(ImageView) v.findViewById(R.id.image_place);
        imageView.setImageResource(images_resources[position]);

        container.addView(v);
        return v;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return (view ==(LinearLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((LinearLayout)object);

    }
}
