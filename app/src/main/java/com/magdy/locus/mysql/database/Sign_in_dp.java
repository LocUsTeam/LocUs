package com.magdy.locus.mysql.database;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.magdy.locus.interfacs.Isregested;
import com.magdy.locus.resorces.Company_userinfo;
import com.magdy.locus.resorces.Place;
import com.magdy.locus.resorces.User_info;
import com.magdy.locus.sqlite.databas.Dpoepnhelper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;


public class Sign_in_dp {


	ProgressDialog progressDialoge;
	private static final int requistTimeOut = 1000 * 15;
	private static final String SERVER_ADDRESS = "http://et7arak.net78.net";
	Context ctx;

	public Sign_in_dp(Context context) {
		this.ctx = context;
		progressDialoge = new ProgressDialog(context);
		progressDialoge.setCancelable(false);
		progressDialoge.setTitle("Processing...");
		progressDialoge.setMessage("Please wait");

	}


	public void fetchUserDataAsyncTask(Company_userinfo user, Isregested userCallBack) {
		progressDialoge.show();
		new fetchUserDataAsyncTask(user, userCallBack).execute();
	}

	public void getfavplaces(User_info user_info, Isregested isregested) {
		//progressDialoge.show();
	//	new getfavplaces(user_info, isregested).execute();
	}

	public class fetchUserDataAsyncTask extends AsyncTask<Void, Void, Company_userinfo> {
		Company_userinfo user;
		Isregested usercallback;

		public fetchUserDataAsyncTask(Company_userinfo user, Isregested usercallback) {
			super();
			this.user = user;
			this.usercallback = usercallback;
		}

		@Override
		protected Company_userinfo doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> datatosend = new ArrayList<>();

			datatosend.add(new BasicNameValuePair("place_name", user.getPlacename()));
			datatosend.add(new BasicNameValuePair("password", user.getPass()));

			HttpParams httprequistparams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httprequistparams, requistTimeOut);
			HttpConnectionParams.setSoTimeout(httprequistparams, requistTimeOut);

			HttpClient client = new DefaultHttpClient(httprequistparams);

			HttpPost post = new HttpPost(SERVER_ADDRESS + "/" + "company_signin.php");

			Company_userinfo returnuser = null;

			try {

				post.setEntity(new UrlEncodedFormEntity(datatosend));
				HttpResponse httpResponse = client.execute(post);

				HttpEntity entity = httpResponse.getEntity();
				String result = EntityUtils.toString(entity);
				Log.e("no data", result);
				JSONObject object = new JSONObject(result);
				if (object.length() != 0) {
					//String name =object.getString("username");

					int roomnum = object.getInt("roomnum");
					String place_name = object.getString("company_name");
					String pass = object.getString("password");


					returnuser = new Company_userinfo(roomnum, pass, place_name);
				} else Log.e("no data", "error");


			} catch (Exception e) {
				e.printStackTrace();
			}


			return returnuser;
		}

		@Override
		protected void onPostExecute(Company_userinfo returnuser) {
			// TODO Auto-generated method stub
			super.onPostExecute(returnuser);
			progressDialoge.dismiss();
			usercallback.regester_done(returnuser);


		}
	}

	/*public class getfavplaces extends AsyncTask<Void, Void, Place>
	{
		User_info user;
		Isregested usercallback;

		public getfavplaces(User_info user, Isregested usercallback) {
			super();
			this.user = user;
			this.usercallback = usercallback;
		}

		@Override
		protected Place doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> datatosend=new ArrayList<>();

			datatosend.add(new BasicNameValuePair("email", user.getEmail()));


			HttpParams httprequistparams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httprequistparams, requistTimeOut);
			HttpConnectionParams.setSoTimeout(httprequistparams, requistTimeOut);

			HttpClient client =new DefaultHttpClient(httprequistparams);

			HttpPost post=new HttpPost(SERVER_ADDRESS+"/"+"getfavplaces.php");

			Place returnuser=null ;

			try{
				Dpoepnhelper dp=new Dpoepnhelper(ctx);
				dp.clear(Dpoepnhelper.TABLE_FAVPLACES);

				post.setEntity(new UrlEncodedFormEntity(datatosend));
				HttpResponse httpResponse=client.execute(post);

				HttpEntity entity=httpResponse.getEntity();
				String result =EntityUtils.toString(entity);
				Log.e("no data", result);
				JSONObject object =new JSONObject(result);
				if(object.length()!=0)
				{
					//String name =object.getString("username");
					String placename=object.getString("place");
					returnuser=new Place(placename);

					dp.addplace(new Place(placename));

				}else Log.e("no data", "error");



			}catch(Exception e){
				e.printStackTrace();
			}


			return returnuser;
		}
		@Override
		protected void onPostExecute(Place returnuser) {
			// TODO Auto-generated method stub
			super.onPostExecute(returnuser);
			progressDialoge.dismiss();
			usercallback.place_added(returnuser);


		}
	}*/
	

}