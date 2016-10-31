package com.magdy.locus.resorces;

/**
 * Created by moham on 25/10/2016.
 */

public class Place {

      String PLACE_NAME="placename";
      String PLACE_DESC="description";
      String PLACE_PHONE="phone";
      String PLACE_LONGETUDE="longtude";
      String PLACE_IMG_URL;
      String PLACE_LAT;

    public Place(String PLACE_NAME) {
        this.PLACE_NAME = PLACE_NAME;
    }

    public Place(String PLACE_NAME, String PLACE_DESC, String PLACE_PHONE, String PLACE_LONGETUDE, String PLACE_LAT, String PLACE_IMG_URL) {
        this.PLACE_NAME = PLACE_NAME;
        this.PLACE_DESC = PLACE_DESC;
        this.PLACE_PHONE = PLACE_PHONE;
        this.PLACE_LONGETUDE = PLACE_LONGETUDE;
        this.PLACE_LAT=PLACE_LAT;
        this.PLACE_IMG_URL=PLACE_IMG_URL;
    }

    public String getPLACE_LAT() {
        return PLACE_LAT;
    }

    public void setPLACE_NAME(String PLACE_NAME) {
        this.PLACE_NAME = PLACE_NAME;
    }

    public void setPLACE_DESC(String PLACE_DESC) {
        this.PLACE_DESC = PLACE_DESC;
    }

    public void setPLACE_LONGETUDE(String PLACE_LONGETUDE) {
        this.PLACE_LONGETUDE = PLACE_LONGETUDE;
    }

    public void setPLACE_PHONE(String PLACE_PHONE) {
        this.PLACE_PHONE = PLACE_PHONE;
    }

    public String getPLACE_NAME() {
        return PLACE_NAME;
    }

    public String getPLACE_DESC() {
        return PLACE_DESC;
    }

    public String getPLACE_PHONE() {
        return PLACE_PHONE;
    }

    public String getPLACE_LONGETUDE() {
        return PLACE_LONGETUDE;
    }

    public String getPLACE_IMG_URL() {
        return PLACE_IMG_URL;
    }
}
