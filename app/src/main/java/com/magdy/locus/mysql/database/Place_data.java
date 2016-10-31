package com.magdy.locus.mysql.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.magdy.locus.resorces.Place;
import com.magdy.locus.sqlite.databas.Dpoepnhelper;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Place_data {

	
	

	 String jsonResult;
	 String sort;
	 public static List<String> listofusers;
	 Context ctx;
	//	ProgressDialog progressDialoge ;
	private static  final int requistTimeOut=1000*15 ;
	
	public void getarrays(Context ctx) {
		// TODO Auto-generated constructor stub
		this.ctx=ctx;
		new fetchUserDataAsyncTask().execute();
	}
    public Bitmap getimg(String url) throws ExecutionException, InterruptedException {
        Bitmap b=new getImages().execute(url).get();

        return b;
    }

	   class fetchUserDataAsyncTask extends AsyncTask<Void, Void, String>
	  {
		//  Done isregesterd;
	//	  String username;



		@Override
		protected String doInBackground(Void... params) {
			
						// TODO Auto-generated method stub
		//	ArrayList<NameValuePair> datatosend=new ArrayList<>();
			
		//	datatosend.add(new BasicNameValuePair("username", username));
			

		//	HttpParams httprequistparams = new BasicHttpParams();
		//	HttpConnectionParams.setConnectionTimeout(httprequistparams, requistTimeOut);
		//	HttpConnectionParams.setSoTimeout(httprequistparams, requistTimeOut);
		        
			HttpClient client =new DefaultHttpClient();
			
			HttpPost post=new HttpPost("http://et7arak.net78.net/listofplacesinfo.php");//http://letsmove.96.lt/chatbase.php
			try {
		//		post.setEntity(new UrlEncodedFormEntity(datatosend));
				client.execute(post);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			  
			
			try{
				
				HttpResponse httpResponse=client.execute(post);
				
				 jsonResult = inputStreamToString(

					      httpResponse.getEntity().getContent()).toString();

			
			     listofusers=new ArrayList<String>();
			    



				  try {

				   JSONObject jsonResponse = new JSONObject(jsonResult);

				   JSONArray jsonMainNode = jsonResponse.optJSONArray("emp_info");
                      Dpoepnhelper db=new Dpoepnhelper(ctx);
                      db.clear();



				   for (int i = 0; i < jsonMainNode.length(); i++) {

				    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				   
					   String  place_name = jsonChildNode.optString("place_name");
					   String placeDesc =jsonChildNode.optString("place_desc");

					   String place_phone=jsonChildNode.optString("place_phone");
					   String place_long =jsonChildNode.optString("place_long");
					   String place_lat =jsonChildNode.optString("place_lat");
                       String place_img_url="http://et7arak.net78.net/"+jsonChildNode.optString("place_img");
					   Log.e("place names",placeDesc+"names");

					   Place place=new Place(place_name,placeDesc,place_phone,place_long,place_lat,place_img_url);


					   db.addplace(place);





				 
				  

				   }
				   

				  } catch (JSONException e) {

				

				  }
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			return null;
			
			
			
			
			
			
			
		}
		protected void onPostExecute(String x) {
			// TODO Auto-generated method stub
		//	progressDialoge.dismiss();
			//ListDrwaer();
      //   isregesterd.done();
			
		}


          //anothermethod to get respons
   /*       public  String getresponse (String _url) throws IOException {
              String result;
              String url =_url;
              URL obj = new URL(_url);
              HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

              con.setRequestMethod("POST");
              con.setRequestProperty("User-Agent", USER_AGENT);
              con.setRequestProperty("Accept-Language", "UTF-8");

              con.setDoOutput(true);
              OutputStreamWriter outputStreamWriter = new OutputStreamWriter(con.getOutputStream());

              outputStreamWriter.flush();

              int responseCode = con.getResponseCode();
              System.out.println("\nSending 'POST' request to URL : " + url);

              System.out.println("Response Code : " + responseCode);

              BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
              String inputLine;
              StringBuffer response = new StringBuffer();

              while ((inputLine = in.readLine()) != null) {
                  response.append(inputLine + "\n");
              }
              in.close();

              result = response.toString();




          return  result;
      }*/
          }


		class getImages extends  AsyncTask <String,Void,Bitmap>

		{

			Bitmap img;

			@Override
			protected Bitmap doInBackground(String... params) {
				URL url= null;

				try {
					url = new URL(params[0]);

				HttpURLConnection conncetion=(HttpURLConnection)url.openConnection();
				conncetion.connect();
				InputStream in=conncetion.getInputStream();

				img= BitmapFactory.decodeStream(in);

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return img;
			}
		}






		 private StringBuilder inputStreamToString(InputStream is) {

			   String rLine = "";

			   StringBuilder answer = new StringBuilder();

			   BufferedReader rd = new BufferedReader(new InputStreamReader(is));




			   try {

			    while ((rLine = rd.readLine()) != null) {

			     answer.append(rLine);

			    }

			   }




			   catch (IOException e) {

			    // e.printStackTrace();

			

			   }

			   return answer;

			  }
	  }


