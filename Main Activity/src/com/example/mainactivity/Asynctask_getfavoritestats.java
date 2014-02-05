// Midterm
//Asynctask_getfavoritestats.java
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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class Asynctask_getfavoritestats extends AsyncTask<String, Void, String> {

	BufferedReader reader = null;
	String check;
	int mid;
	String message;
	Favorites id = null;
	String iderr;
	String idcheck;
	String mess;
	ArrayList<Favorites> ids = null;
	ArrayList<Movie> movies;
	Context context;
	WebView webview;
	String content = "";
	ProgressDialog progressDialog;

	public Asynctask_getfavoritestats(Context cont, WebView web) {
		// TODO Auto-generated constructor stub
		context = cont;
		webview = web;
	}

	// public Asynctask_getallfavorites(Context context) {
	// currentContext = context;
	// }

	protected String doInBackground(String... params) {

		// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(params[0]);

		try {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(context);
			String name = sharedPreferences.getString("username", "");
			Log.d("checking", "checking");
			Log.d("name", name);
			String uid = Config.getUid(name);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			nameValuePairs.add(new BasicNameValuePair("uid", uid));
			// nameValuePairs.add(new BasicNameValuePair("mid",
			// String.valueOf(mid)));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			// String responseBody = EntityUtils.toString(response.getEntity());
			HttpEntity httpEntity = response.getEntity();
			InputStream in = httpEntity.getContent();
			Log.d("idea4", in.toString());
			reader = new BufferedReader(new InputStreamReader(in));
			XmlPullParser parser = XmlPullParserFactory.newInstance()
					.newPullParser();
			parser.setInput(in, "UTF-8");
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					ids = new ArrayList<Favorites>();
					// idcheck = null;
					break;
				case XmlPullParser.START_TAG:
					// id = new Favorites();
					if (parser.getName().equals("id")) {
						id = new Favorites();
						// idcheck = null;
						id.setId((parser.nextText().trim()));
						// idcheck = parser.nextText().trim();
						// Log.d("idea",String.valueOf(ids.get(0).getId()));
						Log.d("idea4", "idea");
					} else if (parser.getName().equals("message")) {
						id.setErrmesage(parser.nextText().trim());
					} else if (parser.getName().equals("count")) {
						id.setCount(parser.nextText().trim());
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals("favorite"))
						ids.add(id);
					else if (parser.getName().equals("error")) {
						ids.add(id);
					}
					break;
					// id = new Favorites();
				default:
					break;
				}
				event = parser.next();
			}

			if (ids.get(0).getId().equals("0")) {
				movies = new ArrayList<Movie>();

				for (int i = 1; i < ids.size(); i++) {
					JSONObject personJSONObject = null;
					JSONObject jsonlink = null;
					JSONObject jsonlink1 = null;
					JSONObject jsonlink2 = null;
					JSONObject jsonlink3 = null;
					HttpClient httpClient = new DefaultHttpClient();
					String url = "http://api.rottentomatoes.com/api/public/v1.0/movies/"
							+ ids.get(i).getId()
							+ ".json?apikey=vq3ayzagjfe2myvvhre7p235";
					HttpGet httpGet = new HttpGet(url);
					HttpEntity httpEntity1 = null;

					httpEntity1 = httpClient.execute(httpGet).getEntity();
					InputStream input = httpEntity1.getContent();

					// InputStream in = con.getInputStream();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(input));
					StringBuilder sb = new StringBuilder();
					String line = null;
					line = reader.readLine();
					while (line != null) {
						sb.append(line);
						line = reader.readLine();
					}

					Log.d("shash", sb.toString());

					personJSONObject = new JSONObject(sb.toString());

					String title = personJSONObject.optString("title");
					String mpaa = personJSONObject.optString("mpaa_rating");
					int id = personJSONObject.optInt("id");
					int year = personJSONObject.optInt("year");
					jsonlink = personJSONObject.getJSONObject("ratings");
					jsonlink1 = personJSONObject.getJSONObject("links");
					jsonlink2 = personJSONObject.getJSONObject("posters");
					jsonlink3 = personJSONObject.getJSONObject("release_dates");
					String counter = ids.get(i).getCount();
					int critics_score = jsonlink.optInt("critics_score");
					int audience_score = jsonlink.optInt("audience_score");
					String thumbnail = jsonlink2.optString("thumbnail");
					String original = jsonlink2.optString("detailed");
					// Log.d("favorite",String.valueOf(critics_score));
					// Log.d("favorite1",String.valueOf(audience_score));

					String critics_rating = jsonlink
							.optString("critics_rating");
					String audience_rating = jsonlink
							.optString("audience_rating");
					// Log.d("favorite",critics_rating.toString());

					String release_date = jsonlink3.optString("theater");
					int runtime = personJSONObject.optInt("runtime");
					Movie movie = new Movie(title, mpaa, counter, id, year,
							critics_score, audience_score, critics_rating,
							audience_rating, thumbnail, original, release_date,
							runtime);
					movies.add(movie);

				}
				String title1 = movies.get(0).getTitle();
				String title2 = movies.get(1).getTitle();
				String title3 = movies.get(2).getTitle();
				String title4 = movies.get(3).getTitle();
				String title5 = movies.get(4).getTitle();
				int count1 = Integer.parseInt(movies.get(0).getLink());
				int count2 = Integer.parseInt(movies.get(1).getLink());
				int count3 = Integer.parseInt(movies.get(2).getLink());
				int count4 = Integer.parseInt(movies.get(3).getLink());
				int count5 = Integer.parseInt(movies.get(4).getLink());

				content = "<html>"
						+ "  <head>"
						+ "    <script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
						+ "    <script type=\"text/javascript\">"
						+ "      google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
						+ "      google.setOnLoadCallback(drawChart);"
						+ "      function drawChart() {"
						+ "        dataTable = new google.visualization.DataTable();"
						+ "         var newData = [['Movie Title', 'Favorite Count'],"
						+ "         ['$2004$',  "
						+ count1
						+ ",],"
						+ "          ['$2005$',  "
						+ count2
						+ ",],"
						+ "          ['$2006$', "
						+ count3
						+ ",],"
						+ "          ['$2007$',  "
						+ count4
						+ ",],"
						+ "          ['$2008$',  "
						+ count5
						+ ",]];"
						+ "          var numRows = newData.length;"
						+ "        var numCols = newData[0].length;"
						+ "        dataTable.addColumn('string', newData[1][0]);"
						+ "         for (var i = 1; i < numCols; i++)"
						+ "         dataTable.addColumn('number', newData[0][i]);"
						+ "         for (var i = 1; i < numRows; i++)"
						+ "          dataTable.addRow(newData[i]);"
						+ "          var options = {"
						+ "          title: 'Favorites',"
						+ "          hAxis: {title: 'Favorite Count', titleTextStyle: {color: 'red'}},"
						+ "           vAxis: {title: 'Movie Title', titleTextStyle: {color: 'red'}}"
						+ "        };"
						+ "        var chart = new google.visualization.BarChart(document.getElementById('chart_div'));"
						+ "        chart.draw(dataTable, options);"
						+ "      }"
						+ "    </script>"
						+ "  </head>"
						+ "  <body>"
						+ "    <div id=\"chart_div\" style=\"width: 400px; height: 400px;\"></div>"
						+ "  </body>" + "</html>";
				content = content.replace("$2004$", title1);
				content = content.replace("$2005$", title2);
				content = content.replace("$2006$", title3);
				content = content.replace("$2007$", title4);
				content = content.replace("$2008$", title5);

				return content;
			} else {
				content = "error";
				return content;
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		progressDialog.dismiss();
		if (result.equals("error")) {
			Toast.makeText(context, ids.get(0).getErrmesage(),
					Toast.LENGTH_SHORT).show();

		} else {
			WebSettings webSettings = webview.getSettings();
			webSettings.setJavaScriptEnabled(true);
			webview.requestFocusFromTouch();
			webview.loadDataWithBaseURL("file:///android_asset/", result,
					"text/html", "utf-8", null);
		}

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setCancelable(false);
		progressDialog.setMessage("Loading Statistics");
		progressDialog.show();
	}

}
