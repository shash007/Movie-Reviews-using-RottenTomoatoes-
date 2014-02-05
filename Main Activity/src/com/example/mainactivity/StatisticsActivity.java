
// Midterm
//StatisticsActivity.java
//Shashank G Hebbale (800773977)

package com.example.mainactivity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.app.Activity;
import android.view.Menu;

public class StatisticsActivity extends Activity {

	static String username = "username";
	SharedPreferences sharedPreferences;
	String name;
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		OptionsMenu();
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		SharedPreferences sharedPreferences1 = getPreferences(MODE_PRIVATE);
		name = sharedPreferences.getString("username", "");
		WebView webview = (WebView) findViewById(R.id.webView1);
		new Asynctask_getfavoritestats(this,webview).execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/getFavoriteStats.php");

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{	
		case R.id.item1:

			new Asynctask_httppost(this).execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteAllFavorites.php");

			break;


		case R.id.item2:

			Intent intent1 = new Intent(StatisticsActivity.this, UsernameActivity.class);
			name = sharedPreferences.getString("username", "");
			intent1.putExtra(username, name);
			startActivity(intent1);
			break;
		}
		return false;

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.statistics, menu);
		return true;
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
