package com.magdy.locus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TableActivity extends AppCompatActivity {
    String selectedLocation;
    String selectedPlace ;
    String selectedRoom ;
    //List<String> temp = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<Boolean> ava = new ArrayList<Boolean>();
    //private ListCustomAdapter adapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();                    // Realtime Database Root
    DatabaseReference mLocationRef = mRootRef.child("location");
    DatabaseReference mSelectedLocationRef, mPlacesRef, mSpaceRef, mRoomRef;
    private CustomAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = this.getIntent();
        selectedLocation =  (String) intent.getExtras().get("location");
        selectedPlace =  (String) intent.getExtras().get("place");
        selectedRoom =  (String) intent.getExtras().get("roomNum");

        setTitle("Room" + selectedRoom);
        mSelectedLocationRef = mLocationRef.child(selectedLocation);
        mPlacesRef = mSelectedLocationRef.child("places");
        mSpaceRef = mPlacesRef.child(selectedPlace);
        Log.v("Places Ref", mSpaceRef.getKey());
        mRoomRef = mSpaceRef.child(selectedRoom);
        //changeRoomAvailablity(mRoomRef, "16");
        fetchRoomSchedule(mRoomRef);
        mSpaceRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String roomNumber = dataSnapshot.getKey();
                //mRoomRef = mSpaceRef.child(selectedRoom);
                //DatabaseReference mSelectedLocRef = mPlacesRef.child(SelectedLoc);
//                ava.clear();
//                time.clear();
                //fetchPlaces(mRoomRef);
                Log.v("ROOM_CHILD-ADDED", roomNumber);
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

        ListView listView = (ListView)findViewById(R.id.time_list);
        List <String> hours = new ArrayList<String>();

                hours.add("11");
                hours.add("12");
                hours.add("1");
                hours.add("2");
                hours.add("3");
                hours.add("4");
                hours.add("5");
                hours.add("6");
        List<Boolean> available = new ArrayList<Boolean>();

        available.add(true);
        available.add(false);
        available.add(false);
        available.add(false);
        available.add(false);
        available.add(true);
        available.add(true);
        available.add(true);


        adapter= new CustomAdapter(this,time,ava);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = adapter.getItem(position).toString();
                Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
            //    Intent intent = new Intent(getBaseContext(),SigninActivity.class);
             //   startActivity(intent);

            }
        });
    }
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
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                String keyTime = dataSnapshot.getKey();
                time.add(keyTime);
                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
                ava.add(valueAvailablity);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String keyTime = dataSnapshot.getKey();
                time.add(keyTime);
                boolean valueAvailablity = (boolean) dataSnapshot.getValue();
                ava.add(valueAvailablity);
                adapter.notifyDataSetChanged();
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
                    adapter.notifyDataSetChanged();
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
 class  CustomAdapter extends BaseAdapter {


     private Context mContext;
     List<String> hour;
     List<Boolean> available ;

 public CustomAdapter(Context c , List<String> hour, List<Boolean> avail)
 {
     mContext = c ;
     this.hour = hour ;
     available = avail ;

 }
    @Override
    public int getCount() {
        return hour.size();
    }

    @Override
    public Object getItem(int position) {
        return hour.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View  convertView1 = inflater.inflate(R.layout.time_item,parent,false) ;

        TextView text1 = (TextView)convertView1.findViewById(R.id.time_hour);
        TextView text2 = (TextView)convertView1.findViewById(R.id.time_state);
        text1.setText(hour.get(position));
        ImageView imageView =(ImageView) convertView1.findViewById(R.id.time_state_icon) ;
        if(available.get(position))
        {
            text2.setText("Available ");
            text2.setTextColor(Color.parseColor("#FF00B12E") );
            imageView.setImageResource(R.drawable.ic_check_circle_green_24dp);



        }
        else
        {
            text2.setText("Not Available");
            text2.setTextColor(Color.parseColor("#ed0000") );
            imageView.setImageResource(R.drawable.ic_cancel_red_24dp);
        }

        return convertView1;
    }

}
