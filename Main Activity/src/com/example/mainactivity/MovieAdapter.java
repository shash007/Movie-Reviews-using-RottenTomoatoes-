// Midterm
//MovieAdapter.java
//Shashank G Hebbale (800773977)


package com.example.mainactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.mainactivity.R.id;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MovieAdapter extends ArrayAdapter<Movie>{

	Context context;
	int resource;
	ArrayList<Movie> objects;
	Movie movie =null;
	TextView textview1;
	TextView textview2;
	TextView textview3;
	ImageView imageview1;
	ImageView imageview2;
	ImageView imageview3;
	static Handler mHandler;
	ViewHolder holder;

	public MovieAdapter(Context context, int resource, ArrayList<Movie> objects) {
		super(context, resource, objects);
		// TODO Auto-generated constructor stub
		this.context =context;
		this.resource = resource;
		this.objects = objects;
	}

	public static class ViewHolder{

		TextView textview1;
		TextView textview2;
		TextView textview3;
		ImageView imageview1;
		ImageView imageview2;
		ImageView imageview3;
		int position;


	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub


		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		if(convertView == null)
		{

			convertView = mInflater.inflate(R.layout.activity_movielist, null);
			holder = new ViewHolder();
			holder.imageview1 = (ImageView) convertView.findViewById(R.id.imageView1);
			holder.imageview2 = (ImageView) convertView.findViewById(R.id.imageView2);
			holder.imageview3 = (ImageView) convertView.findViewById(R.id.imageView3);
			holder.textview1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.textview2 = (TextView) convertView.findViewById(R.id.textView2);
			holder.textview3 = (TextView) convertView.findViewById(R.id.textView3);
			convertView.setTag(holder);
		}


		holder = (ViewHolder) convertView.getTag();
		movie = (Movie) objects.get(position);
		holder.position =position;
		String thumbimage = movie.getThumbnails();
		new DownloadImage(position,holder).execute(thumbimage);

		holder.textview1.setText(movie.getTitle()+"");
		String year = String.valueOf(movie.getYear());
		String mpaa = String.valueOf(movie.getMpaa());
		String audience_image = movie.getAudience_image();
		String critics_image = movie.getCritics_image();
		if (audience_image.equals("Upright"))
			holder.imageview3.setImageResource(R.drawable.upright);
		if (audience_image.equals("Spilled"))
			holder.imageview3.setImageResource(R.drawable.spilled);
		if (audience_image.equals("Certified Fresh"))
			holder.imageview3.setImageResource(R.drawable.certified_fresh);
		if (audience_image.equals("Fresh"))
			holder.imageview3.setImageResource(R.drawable.fresh);
		if (audience_image.equals("Rotten"))
			holder.imageview3.setImageResource(R.drawable.rotten);
		if (audience_image.equals("NotRanked"))
			holder.imageview2.setImageResource(R.drawable.notranked);
		if (critics_image.equals("Upright"))
			holder.imageview2.setImageResource(R.drawable.upright);
		if (critics_image.equals("Spilled"))
			holder.imageview2.setImageResource(R.drawable.spilled);
		if (critics_image.equals("Certified Fresh"))
			holder.imageview2.setImageResource(R.drawable.certified_fresh);
		if (critics_image.equals("Fresh"))
			holder.imageview2.setImageResource(R.drawable.fresh);
		if (critics_image.equals("Rotten"))
			holder.imageview2.setImageResource(R.drawable.rotten);
		if (critics_image.equals("Rotten"))
			holder.imageview2.setImageResource(R.drawable.notranked);
		/*	mHandler = new Handler() { 
				   @Override public void handleMessage(Message msg) { 
				      @SuppressWarnings("unchecked")
				    Bitmap thumbnail =(Bitmap)msg.obj;
					Log.d("image1",thumbnail.toString());
				    imageview1.setImageBitmap(thumbnail);
				    }
				  }; */
		holder.textview2.setText(year + "");
		holder.textview3.setText(mpaa + "");


		return convertView;

	}



}
