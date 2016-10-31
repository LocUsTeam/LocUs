package com.magdy.locus;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FirebaseFragment extends Fragment {

    View rootView;
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
    public FirebaseFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ListCustomAdapter(getActivity(), time, ava);
        list.setAdapter(adapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_firebase, container, false);
        list = (ListView)rootView.findViewById(R.id.listview);
        //
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


        return rootView;
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
