package com.magdy.locus.resorces;

/**
 * Created by moham on 28/10/2016.
 */

public class Company_userinfo {

    int roomnum;
    String placename;
    String pass;
    boolean didsignin =false;

    public void setDidsignin(boolean didsignin) {
        this.didsignin = didsignin;
    }

    public boolean isDidsignin() {

        return didsignin;
    }

    public int getRoomnum() {
        return roomnum;
    }

    public String getPlacename() {
        return placename;
    }

    public String getPass() {
        return pass;
    }

    public Company_userinfo(int roomnum, String placename, String pass) {
        this.roomnum = roomnum;
        this.placename = placename;
        this.pass = pass;
    }
}
