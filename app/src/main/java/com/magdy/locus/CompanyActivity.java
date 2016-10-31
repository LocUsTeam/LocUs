package com.magdy.locus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.magdy.locus.adapters.ComanyListAdapter;
import com.magdy.locus.interfacs.Onclick;

import java.util.ArrayList;
import java.util.List;

public class CompanyActivity extends AppCompatActivity {
    String selectedLocation = "shoubra";
    String selectedPlace = "Et3lem w 3lem";
    String selectedRoom ;
    //List<String> temp = new ArrayList<String>();
    List<String> time = new ArrayList<String>();
    List<Boolean> ava = new ArrayList<Boolean>();
    private ListCustomAdapter adapter;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();                    // Realtime Database Root
    DatabaseReference mLocationRef = mRootRef.child("location");
    DatabaseReference mSelectedLocationRef, mPlacesRef, mSpaceRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        String placename=Splash_screen.companyUserinfo.getPlacename();

        ListView listView=(ListView)findViewById(R.id.lst_companytable);
        mSelectedLocationRef = mLocationRef.child(selectedLocation);
        mPlacesRef = mSelectedLocationRef.child("places");
        mSpaceRef = mPlacesRef.child(selectedPlace);
        Log.v("Places Ref", mSpaceRef.getKey());
        //mRoomRef = mSpaceRef.child(selectedRoom);

        ComanyListAdapter adapter=new ComanyListAdapter(Splash_screen.companyUserinfo.getRoomnum(), this, new Onclick() {
            @Override
            public void onclickelis(int hour, int room) {
                //Here put your code
                room +=1 ;
                selectedRoom = room+"";
                DatabaseReference mRoomRef;
                mRoomRef = mSpaceRef.child(selectedRoom);
                changeRoomAvailablity(mRoomRef, hour+"");
                Toast.makeText(getApplicationContext(),hour+ "",Toast.LENGTH_LONG).show();
            }
        });
        listView.setAdapter(adapter);


    }
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
