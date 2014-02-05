// Midterm
//Asynctask_genre.java
//Shashank G Hebbale (800773977)

package com.example.mainactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Asynctask_genre extends AsyncTask<String, Void, ArrayList<String>> {
	public static ArrayList<String> movies;
	JSONArray personsJSONArray;
	String genre = null;
	TextView textview;
	ImageView star;
	ImageView globe;
	ImageView back;
	TextView critics;
	TextView audience;
	ImageView imageview;
	Context context;
	ProgressDialog progressDialog;

	public Asynctask_genre(TextView textview, ImageView star, ImageView globe,
			ImageView back, TextView critics, TextView audience,
			ImageView imageview, Context context) {
		this.textview = textview;
		this.star = star;
		this.globe = globe;
		this.back = back;
		this.critics = critics;
		this.audience = audience;
		this.imageview = imageview;
		this.context = context;
	}

	protected ArrayList<String> doInBackground(String... params) {

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(params[0]);
			HttpEntity httpEntity = null;
			httpEntity = httpClient.execute(httpGet).getEntity();
			InputStream in = httpEntity.getContent();
			// InputStream in = con.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line = null;
			line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}

			Log.d("sb", sb.toString());

			JSONObject jsonobject = new JSONObject(sb.toString());

			personsJSONArray = jsonobject.getJSONArray("genres");

			Log.d("array", String.valueOf(personsJSONArray.length()));
			Log.d("array1", personsJSONArray.toString());

			movies = new ArrayList<String>();

			// personsJSONArray = new JSONArray(jsonString);
			for (int i = 0; i < personsJSONArray.length(); i++) {
				genre = personsJSONArray.getString(i);
				movies.add(genre);

			}
			Log.d("movi", movies.toString());
			return movies;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	protected void onPostExecute(ArrayList<String> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progressDialog.dismiss();
		// resultList= new ArrayList<Movie>();
		genre = result.get(0);
		for (int i = 1; i < result.size(); i++) {
			genre = genre + "," + result.get(i);
		}
		Log.d("obj1", genre.toString());

		star.setVisibility(View.VISIBLE);
		globe.setVisibility(View.VISIBLE);
		;
		back.setVisibility(View.VISIBLE);
		;
		critics.setVisibility(View.VISIBLE);
		;
		audience.setVisibility(View.VISIBLE);
		;
		imageview.setVisibility(View.VISIBLE);
		;
		textview.setVisibility(View.VISIBLE);

		textview.setText(genre);

	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Movies");
		progressDialog.show();
	}

}
