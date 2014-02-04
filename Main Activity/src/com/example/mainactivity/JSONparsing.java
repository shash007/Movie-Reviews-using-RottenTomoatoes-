// Midterm
//JSONparsing.java
//Shashank G Hebbale (800773977)

package com.example.mainactivity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class JSONparsing 
{
	static public class JSONParser
	{

		static ArrayList<Movie> parseParams(String jsonString) throws JSONException 
		{
			ArrayList<Movie> movies = new ArrayList<Movie>();	
			JSONArray personsJSONArray = null;
			Movie movie = null;
			JSONObject jsonobject = new JSONObject(jsonString);

			personsJSONArray = jsonobject.getJSONArray("movies");

			Log.d("json10",String.valueOf(personsJSONArray.length()));
			//personsJSONArray = new JSONArray(jsonString);
			for(int i=0; i<personsJSONArray.length(); i++)
			{
				JSONObject personJSONObject = null;
				JSONObject jsonlink = null;
				JSONObject jsonlink1 = null;
				JSONObject jsonlink2 = null;
				JSONObject jsonlink3 = null;
				personJSONObject = personsJSONArray.getJSONObject(i);
				String title = personJSONObject.optString("title");
				String mpaa = personJSONObject.optString("mpaa_rating");
				int id = personJSONObject.optInt("id");
				int year = personJSONObject.optInt("year");
				//int runtime = personJSONObject.getInt("runtime");
				jsonlink = personJSONObject.getJSONObject("ratings");
				jsonlink1 = personJSONObject.getJSONObject("links");
				jsonlink2 = personJSONObject.getJSONObject("posters");
				jsonlink3 = personJSONObject.getJSONObject("release_dates");
				String link = jsonlink1.optString("alternate");
				int critics_score = jsonlink.optInt("critics_score");
				int audience_score = jsonlink.optInt("audience_score");
				String thumbnail = jsonlink2.optString("thumbnail");
				String original = jsonlink2.optString("detailed");
				String critics_rating = jsonlink.optString("critics_rating");
				String audience_rating = jsonlink.optString("audience_rating");
				String release_date = jsonlink3.optString("theater");
				int runtime = personJSONObject.optInt("runtime");
				movie = new Movie(title,mpaa,link,id,year,critics_score,audience_score,
						critics_rating,audience_rating,thumbnail,original,release_date,
						runtime);
				movies.add(movie);


			}

			return movies;

		}
	}
}
