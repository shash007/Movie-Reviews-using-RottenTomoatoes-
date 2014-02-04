
// Midterm
//UsernameActivity.java
//Shashank G Hebbale (800773977)


package com.example.mainactivity;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UsernameActivity extends Activity {

	static String username = "username";
	SharedPreferences sharedPreferences;
	String name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_username);
		OptionsMenu();
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		SharedPreferences sharedPreferences1 = getPreferences(MODE_PRIVATE);
		name = sharedPreferences.getString("username", "");
		Button button = (Button) findViewById(R.id.button1);
		final EditText edittext = (EditText)findViewById(R.id.editText1);

		
		if (!name.equals(null))
		edittext.setText(name);
		
		button.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(edittext.getText().toString().contains(" "))
				{
					edittext.setError("Please enter valid username");
					//Toast.makeText(getBaseContext(),"Please enter username", Toast.LENGTH_SHORT).show();
				}
				else
				{
					SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(UsernameActivity.this);
					Editor editor = sharedPreferences.edit();
					Editable edString = edittext.getText();
					editor.putString("username", edString.toString() + "" );
					editor.commit();
					finish();
				}
			}
		});
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{	
		case R.id.item1:

			new Asynctask_httppost(this).execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/deleteAllFavorites.php");

			break;

		}
		return false;

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.username, menu);
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
