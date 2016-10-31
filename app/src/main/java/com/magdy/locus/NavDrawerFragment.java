package com.magdy.locus;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavDrawerFragment extends Fragment {


    public NavDrawerFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        ListView list=(ListView)v.findViewById(R.id.drawerlist);
        String[] titles=getResources().getStringArray(R.array.drowerlistTitels);
        int [] img={R.drawable.ic_room_black_24dp,R.drawable.ic_room_black_24dp,
                R.drawable.ic_room_black_24dp,R.drawable.ic_room_black_24dp};




        NavListAdapter adapter=new NavListAdapter(titles,img,getContext());

        list.setAdapter(adapter);
       



        return v;
    }

   public  void setup(DrawerLayout Dlayot , final Activity ctx , Toolbar toolbar) {


       final ActionBarDrawerToggle ac=new ActionBarDrawerToggle(ctx,Dlayot,toolbar,R.string.open,R.string.close)
        {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ctx.invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ctx.invalidateOptionsMenu();
            }

        };

        Dlayot.addDrawerListener(ac);
      //  toolbar.setNavigationIcon(R.drawable.ic_drawer1);
       Dlayot.post(new Runnable() {
            @Override
            public void run() {
                ac.syncState();
            }
        });

    }


}
