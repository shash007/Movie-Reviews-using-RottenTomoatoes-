// Midterm
//Asynctask.java
//Shashank G Hebbale (800773977)

package com.example.mainactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

;

public class Asynctask extends AsyncTask<String, Void, ArrayList<Movie>> {
	public static ArrayList<Movie> movie = new ArrayList<Movie>();
	public static ArrayList<Movie> resultList = new ArrayList<Movie>();

	ListView listview;
	Context currentContext = null;
	ProgressDialog progressDialog;

	public Asynctask(Context context, ListView list) {
		currentContext = context;
		listview = list;
	}

	protected ArrayList<Movie> doInBackground(String... params) {

		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(params[0]);
			HttpEntity httpEntity = null;
			InputStream inputStream = null;
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

			Log.d("json", sb.toString());

			movie = JSONparsing.JSONParser.parseParams(sb.toString());
			// Log.d("demo1", movie.toString());
			Log.d("movieees", movie.toString());
			return movie;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	protected void onPostExecute(ArrayList<Movie> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progressDialog.dismiss();
		resultList = result;
		// resultList= new ArrayList<Movie>();
		/*
		 * movie = result; Log.d("testing", movie.toString()); Message msg=new
		 * Message(); msg.obj=result; MoviesActivity.mHandler.sendMessage(msg);
		 */
		MoviesActivity.getarraylist(result);
		MovieAdapter adapter = new MovieAdapter(currentContext,
				R.layout.activity_movielist, result);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				int position=	(int) arg0.getItemIdAtPosition(arg2);
				Intent movieactivity = new Intent(currentContext, MovieActivity.class);
				/* movieactivity.putExtra(TITLE, title);
			 movieactivity.putExtra(IMAGE, image);
			 movieactivity.putExtra(CONCAT, concat);
			 movieactivity.putExtra(AUDIENCE_RATINGS, audience_rating);
			 movieactivity.putExtra(CRITICS_RATINGS, critics_rating);
			 movieactivity.putExtra(AUDIENCE_IM, audience_image);
			 movieactivity.putExtra(CRITICS_IM, critics_image);
				 */
				movieactivity.putParcelableArrayListExtra(MoviesActivity.MOVIEINFO, resultList);
				movieactivity.putExtra(MoviesActivity.POSITION, position);
				currentContext.startActivity(movieactivity);
			}


		});

	}

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
