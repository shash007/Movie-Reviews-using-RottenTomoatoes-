// Midterm
//MoviesActivity.java
//Shashank G Hebbale (800773977)


package com.example.mainactivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.ViewConfiguration;

import android.widget.ListView;

public class MoviesActivity extends Activity {

	static ArrayList<Movie> movieinfo;
	static Handler mHandler;
	static String MOVIEINFO = "movie_information";
	static String POSITION = "position";
	String OPTION = "options";
	ArrayList<String> array;
	static String username = "username";
	SharedPreferences sharedPreferences;
	static ArrayList<Movie> favoritelist = new ArrayList<Movie>();
	MovieAdapter adapter;
	String options;
	ListView myListView;
	int check =0;
	Editor editor ;
	int post = -1;
	String name;

	ProgressDialog progressDialog;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movies);
		OptionsMenu();
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		SharedPreferences sharedPreferences1 = getPreferences(MODE_PRIVATE);
		name = sharedPreferences.getString("username", "");
		final String PREF_FILE_NAME = "PrefFile";
		SharedPreferences sharedPreferences = getSharedPreferences(
				PREF_FILE_NAME, 0);
		editor = sharedPreferences.edit();
		editor.putString("REFRESH","");
		editor.commit();
		myListView = (ListView) findViewById(R.id.listView2);
		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Movies");
		Log.d("test","test");

		options = getIntent().getExtras().getString(MainActivity.movie_type);


		if (options.equals("Box Office Movies"))
			new Asynctask(this,myListView).execute("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?limit=50&country=us&apikey=vq3ayzagjfe2myvvhre7p235");

		if (options.equals("In Theatre Movies"))
			new Asynctask(this,myListView).execute("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/in_theaters.json?page_limit=50&page=1&country=us&apikey=vq3ayzagjfe2myvvhre7p235");

		if (options.equals("Upcoming Movies"))
			new Asynctask(this,myListView).execute("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?page_limit=50&page=1&country=us&apikey=vq3ayzagjfe2myvvhre7p235");

		if (options.equals("Opening Movies"))
			new Asynctask(this,myListView).execute("http://api.rottentomatoes.com/api/public/v1.0/lists/movies/opening.json?limit=50&country=us&apikey=vq3ayzagjfe2myvvhre7p235");

		if (options.equals("My Favorite Movies"))

			new Asynctask_getallfavorites(MoviesActivity.this,myListView).execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/getAllFavorites.php");


	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movies, menu);
		return true;
	}

	public static void getarraylist(ArrayList<Movie> movie)
	{
		movieinfo = movie;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{	
		case R.id.item1:

			new Asynctask_httppost(this).execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteAllFavorites.php");

			break;

		case R.id.item2:

			Intent intent1 = new Intent(MoviesActivity.this, UsernameActivity.class);
			startActivity(intent1);
			break;
		}
		return false;

	}


	@SuppressLint("NewApi")
	@Override
	protected void onResume() 
	{
		// TODO Auto-generated method stub
		super.onResume();

		if (options.equals("My Favorite Movies"))
		{

			final String PREF_FILE_NAME = "PrefFile";
			SharedPreferences sharedPreferences = getSharedPreferences(PREF_FILE_NAME,0);			
			editor = sharedPreferences.edit();
			String name = sharedPreferences.getString("REFRESH", "");
			String name1 = sharedPreferences.getString("CHECKVALUE","");
			Log.d("name", name);
			if(!(name.equals("")))
			{
				if (!(name1.equals("")))
				{
					editor.remove("REFRESH");
					editor.commit();
					editor.remove("CHECKVALUE");
					editor.commit();
					Log.d("movieinfo", movieinfo.toString());
					movieinfo.remove(Integer.parseInt(name));
					MovieAdapter adapter = (MovieAdapter) myListView.getAdapter();
					adapter.notifyDataSetChanged();

				}
			}


		}


	}


	public void OptionsMenu()
	{

		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}







