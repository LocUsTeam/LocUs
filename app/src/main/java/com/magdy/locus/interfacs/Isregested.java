package com.magdy.locus.interfacs;


import com.magdy.locus.resorces.Company_userinfo;
import com.magdy.locus.resorces.Place;
import com.magdy.locus.resorces.User_info;

public interface Isregested {

	public abstract void regester_done(Company_userinfo user);
	public abstract void place_added(Place place);

}
