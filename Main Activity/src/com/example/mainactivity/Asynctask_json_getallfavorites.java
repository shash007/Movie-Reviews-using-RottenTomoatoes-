// Midterm
//Asynctask_json_getallfavorites.java
//Shashank G Hebbale (800773977)


package com.example.mainactivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class Asynctask_json_getallfavorites extends
AsyncTask<ArrayList<String>, Void, ArrayList<Movie>> {

	JSONArray personsJSONArray;
	String genre = null;
	ArrayList<String> array;
	Context currentContext;
	ListView listview;
	ArrayList<Movie> resultList;

	// ProgressDialog progressDialog;

	public Asynctask_json_getallfavorites(Context context, ListView list) {
		currentContext = context;
		listview = list;
	}

	protected ArrayList<Movie> doInBackground(ArrayList<String>... params) {

		ArrayList<Movie> movies = new ArrayList<Movie>();
		array = params[0];
		try {

			for (int i = 0; i < array.size(); i++) {
				JSONObject personJSONObject = null;
				JSONObject jsonlink = null;
				JSONObject jsonlink1 = null;
				JSONObject jsonlink2 = null;
				JSONObject jsonlink3 = null;
				HttpClient httpClient = new DefaultHttpClient();
				String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"
						+ String.valueOf(array.get(i))
						+ ".json?apikey=vq3ayzagjfe2myvvhre7p235";
				HttpGet httpGet = new HttpGet(url);
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

				Log.d("sb1", sb.toString());

				personJSONObject = new JSONObject(sb.toString());

				String title = personJSONObject.optString("title");
				String mpaa = personJSONObject.optString("mpaa_rating");
				int id = personJSONObject.optInt("id");
				int year = personJSONObject.optInt("year");
				jsonlink = personJSONObject.getJSONObject("ratings");
				jsonlink1 = personJSONObject.getJSONObject("links");
				jsonlink2 = personJSONObject.getJSONObject("posters");
				jsonlink3 = personJSONObject.getJSONObject("release_dates");
				String link = jsonlink1.optString("alternate");
				int critics_score = jsonlink.optInt("critics_score");
				int audience_score = jsonlink.optInt("audience_score");
				String thumbnail = jsonlink2.optString("thumbnail");
				String original = jsonlink2.optString("detailed");
				// Log.d("favorite",String.valueOf(critics_score));
				// Log.d("favorite1",String.valueOf(audience_score));

				String critics_rating = jsonlink.optString("critics_rating");
				String audience_rating = jsonlink.optString("audience_rating");
				// Log.d("favorite",critics_rating.toString());

				String release_date = jsonlink3.optString("theater");
				int runtime = personJSONObject.optInt("runtime");
				Movie movie = new Movie(title, mpaa, link, id, year,
						critics_score, audience_score, critics_rating,
						audience_rating, thumbnail, original, release_date,
						runtime);
				movies.add(movie);

			}
			Log.d("favorite", movies.toString());
			return movies;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		resultList = new ArrayList<Movie>();
		resultList = result;
		MoviesActivity.getarraylist(result);
		final MovieAdapter adapter = new MovieAdapter(currentContext,
				R.layout.activity_movielist, resultList);
		listview.setAdapter(adapter);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				new Asynctask_add_remove_favorite(currentContext, resultList
						.get(arg2).getId())
				.execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteFavorite.php");
				resultList.remove(arg2);
				adapter.notifyDataSetChanged();
				return false;
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				int position = (int) arg0.getItemIdAtPosition(arg2);
				Intent movieactivity = new Intent(currentContext,
						MovieActivity.class);
				/*
				 * movieactivity.putExtra(TITLE, title);
				 * movieactivity.putExtra(IMAGE, image);
				 * movieactivity.putExtra(CONCAT, concat);
				 * movieactivity.putExtra(AUDIENCE_RATINGS, audience_rating);
				 * movieactivity.putExtra(CRITICS_RATINGS, critics_rating);
				 * movieactivity.putExtra(AUDIENCE_IM, audience_image);
				 * movieactivity.putExtra(CRITICS_IM, critics_image);
				 */
				movieactivity.putParcelableArrayListExtra(
						MoviesActivity.MOVIEINFO, resultList);
				movieactivity.putExtra(MoviesActivity.POSITION, position);
				currentContext.startActivity(movieactivity);
			}

		});

	}

	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

}
