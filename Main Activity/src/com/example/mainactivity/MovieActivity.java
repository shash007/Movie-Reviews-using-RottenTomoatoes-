// Midterm
//MovieActivity.java
//Shashank G Hebbale (800773977)

package com.example.mainactivity;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("SimpleDateFormat")
public class MovieActivity extends Activity {

	ArrayList<Movie> movieinfo = new ArrayList<Movie>();
	static Handler mhandler;
	String genre;
	static String LINK = "link";
	int iconColor;
	static boolean iconchange;
	Drawable draw;
	String options;
	static String MOVIEINFOS;
	static String POSITION;
	static String POSIT;
	String concat;
	String name;
	Date d;
	String username = "username";

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		OptionsMenu();
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		name = sharedPreferences.getString("username", "");
		movieinfo = getIntent().getParcelableArrayListExtra(
				MoviesActivity.MOVIEINFO);
		final int position = getIntent().getExtras().getInt(
				MoviesActivity.POSITION);
		String title = movieinfo.get(position).getTitle();
		String image = movieinfo.get(position).getOriginal();
		String date = movieinfo.get(position).getRelease_date();
		String mpaa = movieinfo.get(position).getMpaa();
		POSITION = "position";
		POSIT = "post";
		MOVIEINFOS = "movieinfo";
		int runtime = movieinfo.get(position).getRuntime();
		SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat output = new SimpleDateFormat("MM/dd/yyyy");
		int id = movieinfo.get(position).getId();

		String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"
				+ String.valueOf(id) + ".json?apikey=vq3ayzagjfe2myvvhre7p235";

		final ImageView star = (ImageView) findViewById(R.id.imageView6);

		ImageView imageview = (ImageView) findViewById(R.id.imageView1);
		TextView genrearray = (TextView) findViewById(R.id.textView3);

		String audience_rating = String.valueOf(movieinfo.get(position)
				.getAudience_score());
		String critics_rating = String.valueOf(movieinfo.get(position)
				.getCritics_score());
		String audience_image = movieinfo.get(position).getAudience_image();
		String critics_image = movieinfo.get(position).getCritics_image();
		TextView textview = (TextView) findViewById(R.id.textView1);
		TextView date_mpaa_runtime = (TextView) findViewById(R.id.textView2);
		TextView audience_ra = (TextView) findViewById(R.id.textView6);
		TextView audience = (TextView) findViewById(R.id.textView4);
		TextView critics = (TextView) findViewById(R.id.textView5);
		TextView critics_ra = (TextView) findViewById(R.id.textView7);
		ImageView audience_im = (ImageView) findViewById(R.id.imageView2);
		ImageView critics_im = (ImageView) findViewById(R.id.imageView3);
		ImageView globe = (ImageView) findViewById(R.id.imageView4);
		ImageView back = (ImageView) findViewById(R.id.imageView5);

		back.setVisibility(View.INVISIBLE);
		globe.setVisibility(View.INVISIBLE);
		star.setVisibility(View.INVISIBLE);
		imageview.setVisibility(View.INVISIBLE);
		critics.setVisibility(View.INVISIBLE);
		audience.setVisibility(View.INVISIBLE);

		new Download_Movieimage(imageview).execute(image);
		new Asynctask_isfavorite(MovieActivity.this, movieinfo, star, position)
		.execute("http://cci-webdev.uncc.edu/~mshehab/api-rest/favorites/isFavorite.php");
		new Asynctask_genre(genrearray, star, globe, back, critics, audience,
				imageview, this).execute(url);

		int hours = runtime / 60;
		int minutes = runtime % 60;
		try {
			Date dat = input.parse(date);
			concat = output.format(dat) + "    " + mpaa + "    "
					+ String.valueOf(hours) + " hr " + String.valueOf(minutes)
					+ " min";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		textview.setText(title + "");
		date_mpaa_runtime.setText(concat + "");
		audience_ra.setText(audience_rating + "%");
		critics_ra.setText(critics_rating + "%");
		if (audience_image.equals("Upright"))
			audience_im.setImageResource(R.drawable.upright);
		if (audience_image.equals("Spilled"))
			audience_im.setImageResource(R.drawable.spilled);
		if (audience_image.equals("Certified Fresh"))
			audience_im.setImageResource(R.drawable.certified_fresh);
		if (audience_image.equals("Fresh"))
			audience_im.setImageResource(R.drawable.fresh);
		if (audience_image.equals("Rotten"))
			audience_im.setImageResource(R.drawable.rotten);
		if (audience_image.equals("NotRanked"))
			audience_im.setImageResource(R.drawable.notranked);

		if (critics_image.equals("Upright"))
			critics_im.setImageResource(R.drawable.upright);
		if (critics_image.equals("Spilled"))
			critics_im.setImageResource(R.drawable.spilled);
		if (critics_image.equals("Certified Fresh"))
			critics_im.setImageResource(R.drawable.certified_fresh);
		if (critics_image.equals("Fresh"))
			critics_im.setImageResource(R.drawable.fresh);
		if (critics_image.equals("Rotten"))
			critics_im.setImageResource(R.drawable.rotten);
		if (critics_image.equals("NotRanked"))
			critics_im.setImageResource(R.drawable.notranked);

		globe.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				String weblink = movieinfo.get(position).getLink();
				Intent webactivity = new Intent(MovieActivity.this,
						MovieWebActivity.class);
				webactivity.putExtra(LINK, weblink);
				startActivity(webactivity);
			}
		});

		back.setOnClickListener(new OnClickListener() {

			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String PREF_FILE_NAME = "PrefFile";
				SharedPreferences sharedPreferences = getSharedPreferences(
						PREF_FILE_NAME, 0);
				Editor editor = sharedPreferences.edit();
				editor.putString("REFRESH", String.valueOf(position));
				editor.apply();
				finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.movie, menu);
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

		case R.id.item2:

			Intent intent1 = new Intent(MovieActivity.this,
					UsernameActivity.class);
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
