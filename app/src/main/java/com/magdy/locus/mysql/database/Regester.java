package com.magdy.locus.mysql.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.magdy.locus.interfacs.Isregested;
import com.magdy.locus.resorces.Place;
import com.magdy.locus.resorces.User_info;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.ArrayList;


public class Regester {

	ProgressDialog progressDialoge ;
	private static  final int requistTimeOut=1000*15 ;
	private static  final String SERVER_ADDRESS= "http://et7arak.net78.net" ;
	
	public Regester (Context context)
	{
		progressDialoge=new ProgressDialog(context);
		progressDialoge.setCancelable(false);
		progressDialoge.setTitle("Processing...");
		progressDialoge.setMessage("Please wait");
		
	}

	public Regester() {
	}

	public void storinbackground (User_info user , Isregested userCallBack, Context context){
		progressDialoge.show();
		new StoreUserDataAsyncTask(user, userCallBack,context ).execute();
	
		
	}
	public void addplace (User_info user ,Place place,Isregested userCallBack){

		new addfavplace(place,user, userCallBack ).execute();


	}
	 

	
	
	
	
  public class StoreUserDataAsyncTask extends AsyncTask<Void, Void, Void>
	{

		User_info user;
		Isregested usercallback ;
		Context ctx;
		
		public StoreUserDataAsyncTask (User_info user,Isregested usercallback ,Context ctx)
		{
			this.ctx=ctx;
			this.user=user;
			this.usercallback=usercallback;
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> datatosend=new ArrayList<>();
			
		datatosend.add(new BasicNameValuePair("username", user.getUsername()));
		datatosend.add(new BasicNameValuePair("password", user.getPassword()));
		datatosend.add(new BasicNameValuePair("email", user.getEmail()));
			
			HttpParams httpparams = gethttpparams();
			
			HttpClient clieint=new DefaultHttpClient(httpparams);
			HttpPost post =new HttpPost(SERVER_ADDRESS+"/"+"happy_regester.php");
			
			try {
				post.setEntity(new UrlEncodedFormEntity(datatosend));
				clieint.execute(post);
			//	HttpResponse httpResponse=clieint.execute(post);
				
			//	HttpEntity entity=httpResponse.getEntity();
			//	String result =EntityUtils.toString(entity);
				//Log.e("", result);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			

			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			progressDialoge.dismiss();
			usercallback.regester_done(null);
		}
		
		private HttpParams gethttpparams ()
		{
			HttpParams httprequistparams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httprequistparams, requistTimeOut);
			HttpConnectionParams.setSoTimeout(httprequistparams, requistTimeOut);
			
			return httprequistparams;
			
		}
		
	}
	public class addfavplace extends AsyncTask<Void, Void, Void>
	{

		User_info user;
		Isregested usercallback ;
		Context ctx;
		Place place;
		public addfavplace (Place place, User_info user, Isregested usercallback )
		{
			//this.ctx=ctx;
			this.user=user;
			this.usercallback=usercallback;
			this.place=place;
		}
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> datatosend=new ArrayList<>();
			Log.e("place",place.getPLACE_NAME());
			Log.e("email",user.getEmail());
			datatosend.add(new BasicNameValuePair("place", place.getPLACE_NAME()));
			datatosend.add(new BasicNameValuePair("email", user.getEmail()));

			HttpParams httpparams = gethttpparams();

			HttpClient clieint=new DefaultHttpClient(httpparams);
			HttpPost post =new HttpPost(SERVER_ADDRESS+"/"+"addfavplace.php");

			try {
				post.setEntity(new UrlEncodedFormEntity(datatosend));
				clieint.execute(post);
				//	HttpResponse httpResponse=clieint.execute(post);

				//	HttpEntity entity=httpResponse.getEntity();
				//	String result =EntityUtils.toString(entity);
				//Log.e("", result);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		//	progressDialoge.dismiss();
			usercallback.regester_done(null);
		}

		private HttpParams gethttpparams ()
		{
			HttpParams httprequistparams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httprequistparams, requistTimeOut);
			HttpConnectionParams.setSoTimeout(httprequistparams, requistTimeOut);

			return httprequistparams;

		}

	}
}
