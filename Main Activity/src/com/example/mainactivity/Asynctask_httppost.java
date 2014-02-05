// Midterm
//Asynctask_httppost.java
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
import android.widget.ListView;
import android.widget.Toast;

public class Asynctask_httppost extends AsyncTask<String, Void, ArrayList<Favorites>> {

	BufferedReader reader=null;
	Context currentContext;

	public Asynctask_httppost(Context context) {
		currentContext = context;
	}

	protected ArrayList<Favorites> doInBackground(String... params) {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);

		try {
			// Add your data
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(currentContext);
			String name = sharedPreferences.getString("username", "");
			String uid = Config.getUid(name);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("uid", uid));
			// nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			//String responseBody = EntityUtils.toString(response.getEntity());
			HttpEntity httpEntity  = response.getEntity();
			InputStream in= httpEntity.getContent();
			reader = new BufferedReader(new InputStreamReader(in));
			/*StringBuilder sb = new StringBuilder();
	            String line = null;

	            // Read Server Response
	            while((line = reader.readLine()) != null)
	                {
	                       // Append server response in string
	                       sb.append(line + "\n");
	                       line = reader.readLine();
	                }




	            Log.d("post1",sb.toString());*/
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
						//Log.d("idea",String.valueOf(ids.get(0).getId()));
						Log.d("idea4","idea");
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
			Toast.makeText(currentContext,"Deleted all Favorites Successfully", Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(currentContext,result.get(0).getErrmesage(), Toast.LENGTH_SHORT).show();

		//Log.d("post",result.toString());

	}
}
