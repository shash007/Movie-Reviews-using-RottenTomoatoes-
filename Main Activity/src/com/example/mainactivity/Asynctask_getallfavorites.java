// Midterm
//Asynctask_getallfavorites.java
//Shashank G Hebbale (800773977


package com.example.mainactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;


public class Asynctask_getallfavorites extends AsyncTask<String, Void, ArrayList<String>> {

	BufferedReader reader=null;
	Context currentContext;
	String check;
	int mid;
	String message;
	String id;
	String iderr;
	String mess;
	ProgressDialog progressDialog;
	ListView listview;


	public Asynctask_getallfavorites(Context context, ListView view) {
		currentContext = context;
		listview = view;
	}

	protected ArrayList<String> doInBackground(String... params) {

		// Log.d("idea4","idea");
		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);

		try {
			// Add your data
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentContext);
			String name = sharedPreferences.getString("username", "");
			String uid = Config.getUid(name);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("uid", uid));
			//nameValuePairs.add(new BasicNameValuePair("mid", String.valueOf(mid)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			//String responseBody = EntityUtils.toString(response.getEntity());
			HttpEntity httpEntity  = response.getEntity();
			InputStream in= httpEntity.getContent();
			reader = new BufferedReader(new InputStreamReader(in));
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(in, "UTF-8");
			ArrayList<String> ids = null;
			int event = parser.getEventType();			
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					ids = new ArrayList<String>();
					break;
				case XmlPullParser.START_TAG:
					//id = new Favorites();
					if(parser.getName().equals("id"))
					{
						id =null;
						id = ((parser.nextText().trim()));

					}
					else if(parser.getName().equals("message"))
					{
						message = null;
						message= (parser.nextText().trim());
					}
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("favorite"))
					{
						ids.add(id);
					}
					else
						if(parser.getName().equals("error"))
						{
							mess =message;
							iderr = id;


						}
					break;
					//id = new Favorites();
				default:
					break;
				}
				event = parser.next();
			}


			return ids;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	protected void onPostExecute(ArrayList<String> result) {
		super.onPostExecute(result);
		progressDialog.dismiss();
		if (iderr.equals("0"))
		{
			Log.d("plzzzz", String.valueOf(result.size()));
			if(result.isEmpty())
			{
				Toast.makeText(currentContext, "List Empty", Toast.LENGTH_SHORT).show();
			}
			else
				new Asynctask_json_getallfavorites(currentContext,listview).execute(result);
		}
		else
		{
			Toast.makeText(currentContext, message, Toast.LENGTH_SHORT).show();

		}

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(currentContext);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Movies");
		progressDialog.show();
	}

}
