// Midterm
//Asynctask_isfavorite.java
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

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class Asynctask_isfavorite extends AsyncTask<String, Void, String> {

	BufferedReader reader=null;
	Context currentContext;
	String check;
	int mid;
	String condition;
	ImageView imageview;
	boolean iconchange;
	int position;
	ArrayList<Movie> movie;
	Drawable drawable;
	final String PREF_FILE_NAME = "PrefFile";
	SharedPreferences sharedPreferences;
	Editor editor;



	public Asynctask_isfavorite(Context context,ArrayList<Movie> movie , ImageView image, int position) {
		currentContext = context;
		this.movie = movie;
		imageview =image;
		this.position = position;
	}

	protected String doInBackground(String... params) {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);
		iconchange = false;

		try {
			sharedPreferences = currentContext.getSharedPreferences(
					PREF_FILE_NAME, 0);
			editor = sharedPreferences.edit();
			mid = movie.get(position).getId();
			SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(currentContext);
			String name = sharedPreferences.getString("username", "");
			String uid = Config.getUid(name);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("uid", uid));
			nameValuePairs.add(new BasicNameValuePair("mid", String.valueOf(mid)));
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
			int event = parser.getEventType();
			String istrue = null;
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					istrue = null;
					break;
				case XmlPullParser.START_TAG:
					//id = new Favorites();
					if(parser.getName().equals("isFavorite"))
					{
						istrue = null;
						istrue= parser.nextText().trim();					   
					}

					break;
				case XmlPullParser.END_TAG:
					condition = istrue;

				default:
					break;
				}
				event = parser.next();
			}

			return condition;
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

	@SuppressWarnings("deprecation")
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if (result.toString().equals("true"))
		{
			imageview.setColorFilter(currentContext.getResources().getColor(android.R.color.primary_text_light),Mode.SRC_ATOP);
			//drawable.setColorFilter(Color.DKGRAY,Mode.SRC_ATOP);
			//imageview.setBackgroundDrawable(drawable);
			iconchange = true;

		}
		else
		{
			imageview.setColorFilter(currentContext.getResources().getColor(android.R.color.darker_gray),Mode.SRC_ATOP);
			//drawable.setColorFilter(Color.LTGRAY,Mode.SRC_ATOP);
			//imageview.setBackgroundDrawable(drawable);
			iconchange = false;
		}

		imageview.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//star.setColorFilter(getResources().getColor(android.R.color.black),Mode.SRC_ATOP);
				if(iconchange == false)
				{
					iconchange = true;
					imageview.setColorFilter(currentContext.getResources().getColor(android.R.color.primary_text_light),Mode.SRC_ATOP);
					Log.d("true",String.valueOf(movie.get(position).getId()));
					new Asynctask_add_remove_favorite(currentContext, movie.get(position).getId())
					.execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/addToFavorites.php");
					editor.putString("CHECKVALUE","");
					editor.commit();
				}
				else if(iconchange == true)
				{
					iconchange = false;
					imageview.setColorFilter(currentContext.getResources().getColor(android.R.color.darker_gray),Mode.SRC_ATOP);
					Log.d("false",String.valueOf(movie.get(position).getId()));
					new Asynctask_add_remove_favorite(currentContext, movie.get(position).getId()).execute(
							"http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteFavorite.php");
					editor.putString("CHECKVALUE", "Check");
					editor.commit();
				}
			}
		});

	}



}
