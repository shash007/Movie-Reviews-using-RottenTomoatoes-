// Midterm
//MainActivity.java
//Shashank G Hebbale (800773977)

package com.example.mainactivity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	static String movie_type = "movies";
	static String SERIAL = "serial";
	static ListView myListView;
	static Handler mhandler;
	static String username = "username";
	SharedPreferences sharedPreferences;
	String name;

	// ArrayList<Movie> movie;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		myListView = (ListView) findViewById(R.id.listView1);
		OptionsMenu();
		List<String> options = new ArrayList<String>();
		options.add("My Favorite Movies");
		options.add("Box Office Movies");
		options.add("In Theatre Movies");
		options.add("Opening Movies");
		options.add("Upcoming Movies");
		options.add("Favorite Statistics");

		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		SharedPreferences sharedPreferences1 = getPreferences(MODE_PRIVATE);
		name = sharedPreferences.getString("username",null);

		if(name.equals(null))
		{
		Intent intent = new Intent(MainActivity.this, UsernameActivity.class);
		startActivity(intent);
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				options);
		myListView.setAdapter(adapter);

		myListView.setTextFilterEnabled(true);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				Object position = arg0.getItemAtPosition(arg2);
				String position1 = String.valueOf(position);
				if (position1.equals("Favorite Statistics")) {
					Log.d("checking", "checking");
					Intent statisticsactivity = new Intent(MainActivity.this,
							StatisticsActivity.class);
					startActivity(statisticsactivity);

				} else {
					Intent moviesactivity = new Intent(MainActivity.this,
							MoviesActivity.class);
					moviesactivity.putExtra(movie_type, position1);
					startActivity(moviesactivity);

				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.item1:

			new Asynctask_httppost(this)
			.execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteAllFavorites.php");

			break;

		case R.id.item3:

			finish();
			break;

		case R.id.item2:

			Intent intent1 = new Intent(MainActivity.this,
					UsernameActivity.class);
			name = sharedPreferences.getString("username", "");
			intent1.putExtra(username, name);
			startActivity(intent1);
			break;
		}
		return false;

	}

	public void OptionsMenu() {

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
