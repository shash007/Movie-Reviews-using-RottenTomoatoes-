// Midterm
//Asynctask_add_remove_favorite.java
//Shashank G Hebbale (800773977)




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

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class Asynctask_add_remove_favorite extends AsyncTask<String, Void, ArrayList<Favorites>> {

	BufferedReader reader=null;
	Context currentContext;
	String check;
	int mid;

	public Asynctask_add_remove_favorite(Context context,int id) {
		currentContext = context;
		mid =id;
	}

	protected ArrayList<Favorites> doInBackground(String... params) {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);
		check = params[0];

		try {
			// Add your data
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentContext);
			String name = sharedPreferences.getString("username", "");
			String uid = Config.getUid(name);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("uid", uid));
			nameValuePairs.add(new BasicNameValuePair("mid", String.valueOf(mid)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity httpEntity  = response.getEntity();
			InputStream in= httpEntity.getContent();
			Log.d("idea5","idea");
			reader = new BufferedReader(new InputStreamReader(in));
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(in, "UTF-8");
			Favorites id = new Favorites();
			ArrayList<Favorites> ids = null;
			int event = parser.getEventType();			
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					ids = new ArrayList<Favorites>();
					break;
				case XmlPullParser.START_TAG:
					//id = new Favorites();
					if(parser.getName().equals("id"))
					{
						id = new Favorites();
						id.setId((parser.nextText().trim()));
					}
					else if(parser.getName().equals("message"))
						id.setErrmesage(parser.nextText().trim());
					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("error"))
						ids.add(id);

				default:
					break;
				}
				event = parser.next();
			}
			Log.d("idea",ids.get(0).getId().toString());
			Log.d("idea1",ids.get(0).getErrmesage().toString());
			Log.d("idea2",String.valueOf(ids.size()).toString());
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

	protected void onPostExecute(ArrayList<Favorites> result) {
		super.onPostExecute(result);

		if(result.get(0).getId().equals("0"))
		{
			if(check.equals("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/addToFavorites.php"))

				Toast.makeText(currentContext,"Added Successfully", Toast.LENGTH_SHORT).show();

			else

				Toast.makeText(currentContext,"Deleted Successfully", Toast.LENGTH_SHORT).show();


		}
		else
			Toast.makeText(currentContext,result.get(0).getErrmesage(), Toast.LENGTH_SHORT).show();


	}


}
