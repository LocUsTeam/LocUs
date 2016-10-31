package com.magdy.locus.sqlite.databas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.magdy.locus.resorces.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moham on 25/10/2016.
 */

public class Dpoepnhelper extends SQLiteOpenHelper {

    public  static final  String DATABASE_NAME="locusedp";

    public  static final  int DATABASE_VERSION=1;

    public  static final  String TABLE_PLACES="places1";

    //table columns

    public  static final  String _ID="id";
    public  static final  String PLACE_NAME="placename";
    public  static final  String PLACE_DESC="description";
    public  static final  String PLACE_PHONE="phone";
    public  static final  String PLACE_LONGETUDE="longtude";
    public  static final  String PLACE_LAT="lat";
    public  static final  String PLACE_IMG_URL="url";
    final  String CREAT_CONTACT_TAPLE="CREATE TABLE "+TABLE_PLACES+"("+_ID+" INTEGER PRIMARY KEY ,"+
                                                                    PLACE_NAME+" TEXT,"+
                                                                    PLACE_DESC+" TEXT,"+
                                                                    PLACE_PHONE+" TEXT,"+
                                                                    PLACE_LONGETUDE+" TEXT,"+
                                                                    PLACE_LAT+" TEXT,"+
                                                                    PLACE_IMG_URL+" TEXT"+")";




    public Dpoepnhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(CREAT_CONTACT_TAPLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);

        // Create tables again
        onCreate(db);
    }


    public  void addplace (Place place)
    {
        SQLiteDatabase dp=this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(PLACE_NAME,place.getPLACE_NAME());
        values.put(PLACE_DESC,place.getPLACE_DESC());
        values.put(PLACE_PHONE,place.getPLACE_PHONE());
        values.put(PLACE_LONGETUDE,place.getPLACE_LONGETUDE());
        values.put(PLACE_LAT,place.getPLACE_LAT());
        values.put(PLACE_IMG_URL,place.getPLACE_IMG_URL());
        dp.insert(TABLE_PLACES,null,values);
        dp.close();


    }

    // Getting All places

    public List<Place> getallplaces()
    {
        List<Place> places=new ArrayList<Place>();

        String selectCury="SELECT * FROM "+TABLE_PLACES ;
        SQLiteDatabase db=this.getWritableDatabase();


        Cursor c=db.rawQuery(selectCury,null);
        if(c.moveToFirst())
        {
            do
               {
                Place p=new Place(c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6));
                   places.add(p);

            }while (c.moveToNext());



        }


        return places;
    }
    // Getting single contact with id
    Place getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PLACES, new String[] { _ID,
                        PLACE_NAME, PLACE_DESC ,PLACE_PHONE, PLACE_LONGETUDE }, _ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Place contact = new Place(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6));
        // return contact
        return contact;
    }


    // Updating single contact
 /*   public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PH_NO, contact.getPhoneNumber());

        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getID()) });
    }
*/



    // Deleting single contact
    public void deleteContact(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PLACES, PLACE_NAME + " = ?",
                new String[] { place.getPLACE_NAME()});//String.valueOf(contact.getID()) });
        db.close();
    }



    // Getting contacts Count
   public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_PLACES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        // return count
        return count;
    }

        public  void clear ()

            {
                    SQLiteDatabase db = this.getWritableDatabase();
                     db.delete(TABLE_PLACES, null, null);//String.valueOf(contact.getID()) });
                    db.close();

            }

}
