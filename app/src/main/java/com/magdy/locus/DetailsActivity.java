package com.magdy.locus;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.magdy.locus.resorces.Place;
import com.magdy.locus.sqlite.databas.Dpoepnhelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailsActivity extends AppCompatActivity {
    private ArrayAdapter<String> roomsAdapter ;
    private String[] dots ;
    ListView listView;
    private TextView dotLayout ;
    private  int [] pics;
    private ViewPager viewPager ;
    int counter=0;
    private CustomSwipeAdapter viewPagerAdapter;
    List<String> rooms = new ArrayList<String>();
    ListView list;
    String selectedLocation = "shoubra";
    String selectedPlace = "Et3lem w 3lem";
    String selectedRoom = "1";
    //List<String> temp = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<Boolean> ava = new ArrayList<Boolean>();
    private ListCustomAdapter adapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();                    // Realtime Database Root
    DatabaseReference mLocationRef = mRootRef.child("location");
    DatabaseReference mSelectedLocationRef, mPlacesRef, mSpaceRef, mRoomRef;
    TextView title,des;
    @Override
    protected void onStart() {
        super.onStart();
       roomsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        listView = (ListView)findViewById(R.id.rooms_list);
        title=(TextView)findViewById(R.id.title);
        des=(TextView)findViewById(R.id.description);
        list = (ListView)findViewById(R.id.listview);

        Button map=(Button)findViewById(R.id.btn_opnemap);


        pics = new int[]{
                R.drawable.makan3,
                R.drawable.makan2,
                R.drawable.makan5,
                R.drawable.makan4
        };
        //
        Intent intent=getIntent();
        final int pos=(int)intent.getExtras().get("pos");


        final Dpoepnhelper dpoepnhelper=new Dpoepnhelper(this);
        final Place place= dpoepnhelper.getallplaces().get(pos);

        title.setText(place.getPLACE_NAME());
        Log.e("dsa",place.getPLACE_DESC());
        des.setText(place.getPLACE_DESC());
        mSelectedLocationRef = mLocationRef.child(selectedLocation);
        mPlacesRef = mSelectedLocationRef.child("places");
        mSpaceRef = mPlacesRef.child(selectedPlace);
        Log.v("Places Ref", mSpaceRef.getKey());
        mRoomRef = mSpaceRef.child(selectedRoom);
        //changeRoomAvailablity(mRoomRef, "16");
        fetchRoomSchedule(mRoomRef);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longtude=dpoepnhelper.getallplaces().get(pos).getPLACE_LONGETUDE();
                String lat=dpoepnhelper.getallplaces().get(pos).getPLACE_LAT();
                final Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("geo:0,0?q="+lat+","+longtude+"&z=16 (" + place.getPLACE_NAME() + ")"));
                startActivity(intent);
            }
        });
        mSpaceRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String roomNumber = dataSnapshot.getKey();
                rooms.add("Room " + roomNumber);
                updateLength();
                roomsAdapter.notifyDataSetChanged();
                Log.v("ROOM_CHILD-ADDED", rooms.size()+"");
                Log.v("ROOM_CHILD-ADDED", listView.getCount()+"");


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String roomNumber = dataSnapshot.getKey();
                mRoomRef = mSpaceRef.child(selectedRoom);
                //DatabaseReference mSelectedLocRef = mPlacesRef.child(SelectedLoc);
                ava.clear();
                time.clear();
                fetchRoomSchedule(mRoomRef);
                Log.v("LOCATION_CHILD-CHANGED", roomNumber);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String roomNumber = dataSnapshot.getKey();
                mRoomRef = mSpaceRef.child(selectedRoom);
                //DatabaseReference mSelectedLocRef = mPlacesRef.child(SelectedLoc);
                fetchRoomSchedule(mRoomRef);
                ava.clear();
                time.clear();
                fetchRoomSchedule(mRoomRef);
                Log.v("LOCATION_CHILD-REMOVED", roomNumber);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        roomsAdapter = new ArrayAdapter<String>(
                this,
                R.layout.room_item,
                R.id.rt,
                rooms);


        listView.setAdapter(roomsAdapter);
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPagerAdapter = new CustomSwipeAdapter(this,pics);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewListener);
        dotLayout = (TextView) findViewById(R.id.layout_dots);
        dots = new String[pics.length];
        updateButtonDots(0);



        updateLength();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = roomsAdapter.getItem(position);
                //Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
                selectedRoom = (position + 1)+"";
                Intent intent = new Intent(getBaseContext(), TableActivity.class);
                intent.putExtra("room", s);
                intent.putExtra("location", selectedLocation);
                intent.putExtra("place", selectedPlace);
                intent.putExtra("roomNum", selectedRoom);
                startActivity(intent);
            }
        });


    }
    private void updateLength()
    {

        int totalHeight = 0;
        for (int i = 0; i < roomsAdapter.getCount(); i++) {
            View listItem = roomsAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (roomsAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void updateButtonDots (int pos)
    {


        dotLayout.setText("");

        int colorActive = Color.parseColor("#009688") ;
        //int colorInActive = Color.parseColor("#81D4FA") ;

        for (int i = 0 ; i <  dots.length;i++)
        {

            //dots[i].setText("•");
            //•••••
            if(i != pos )
            { dots[i]="○"; }//˳
            else { dots[i]="●";}
           // dots[i].setTextSize(35);
            dotLayout.setText(dotLayout.getText()+dots[i]);
            dotLayout.setTextColor(colorActive);
            dotLayout.setTextSize(15);


        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            updateButtonDots(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    } ;
    // Fetch Room Schedule From Database
    void fetchRoomSchedule(DatabaseReference mRef){
        mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String keyTime = dataSnapshot.getKey();
                time.add(keyTime);
                Log.v("5555555555555555555",keyTime);
                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
                ava.add(valueAvailablity);
                //adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String keyTime = dataSnapshot.getKey();
                time.add(keyTime);
                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
                ava.add(valueAvailablity);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String keyTime = dataSnapshot.getKey();
                time.add(keyTime);
                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
                ava.add(valueAvailablity);
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // IGNORE
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    // Change Room availablity From Database
    void changeRoomAvailablity(final DatabaseReference mRef, final String hour){
        mRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String keyTime = dataSnapshot.getKey();
                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
                //time.add(keyTime);
                if(keyTime.equals(hour)){
                    DatabaseReference mValueRef = mRef.child(keyTime);
                    mValueRef.setValue(!valueAvailablity);
                    ava.add(!valueAvailablity);
                    //adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                String keyTime = dataSnapshot.getKey();
//                time.add(keyTime);
//                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
//                ava.add(valueAvailablity);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                String keyTime = dataSnapshot.getKey();
//                time.add(keyTime);
//                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
//                ava.add(valueAvailablity);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                // IGNORE
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
