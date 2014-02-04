// Midterm
//Movie.java
//Shashank G Hebbale (800773977)


package com.example.mainactivity;

import java.io.Serializable;


import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{

	String title,mpaa,link;
	int id,year,critics_score,audience_score,runtime;
	String critics_image, audience_image, thumbnails,original,release_date;
	//int runtime;
	
	public int getRuntime() {
		return runtime;
	}


	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}


	public String getRelease_date() {
		return release_date;
	}


	public void setRelease_date(String release_date) {
		this.release_date = release_date;
	}
	
	public String getOriginal() {
		return original;
	}


	public void setOriginal(String original) {
		this.original = original;
	}


	public Movie(String title, String mpaa, String link, int id, int year
			, int critics_score, int audience_score,String critics_image,
			String audience_image,String thumbnails,String original,String 
			release_date,int runtime) {
		this.title = title;
		this.mpaa = mpaa;
		this.link = link;
		this.id = id;
		this.year = year;
		//this.runtime = runtime;
		this.critics_score = critics_score;
		this.audience_score = audience_score;
		this.critics_image = critics_image;
		this.audience_image = audience_image;
		this.thumbnails =thumbnails;
		this.original = original;
		this.release_date = release_date;
		this.runtime =runtime;
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Movie [mpaa=" + mpaa + ", title=" + title + ", link=" + link + ", id ="
				+ id + ", year =" + year + ",critics_score" + critics_score
				+ ",audience_score" + audience_score  + ",critics_image=" + critics_image
				+",audience_image=" +audience_image + ",thumbnails=" +thumbnails + "]";
	}


	public String getCritics_image() {
		return critics_image;
	}


	public void setCritics_image(String critics_image) {
		this.critics_image = critics_image;
	}


	public String getAudience_image() {
		return audience_image;
	}


	public void setAudience_image(String audience_image) {
		this.audience_image = audience_image;
	}


	public String getThumbnails() {
		return thumbnails;
	}


	public void setThumbnails(String thumbnails) {
		this.thumbnails = thumbnails;
	}


	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMpaa() {
		return mpaa;
	}
	public void setMpaa(String mpaa) {
		this.mpaa = mpaa;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	/*public int getRuntime() {
		return runtime;
	}
	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}*/
	public int getCritics_score() {
		return critics_score;
	}
	public void setCritics_score(int critics_score) {
		this.critics_score = critics_score;
	}
	public int getAudience_score() {
		return audience_score;
	}
	public void setAudience_score(int audience_score) {
		this.audience_score = audience_score;
	}
	

	public Movie(Parcel in) {
		// TODO Auto-generated constructor stub
        this.title =  in.readString();;
		this.mpaa =  in.readString();;
		this.link =  in.readString();;
		this.id = in.readInt();;
		this.year = in.readInt();;
		this.critics_score = in.readInt();;
		this.audience_score = in.readInt();;
		this.critics_image =  in.readString();;
		this.audience_image =  in.readString();;
		this.thumbnails = in.readString();;
		this.original =  in.readString();;
		this.release_date = in.readString();;
		this.runtime =in.readInt();;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		 dest.writeString(title);
		 dest.writeString(mpaa);
		 dest.writeString(link);
		 dest.writeInt(id);
		 dest.writeInt(year);
		 dest.writeInt(critics_score);
		 dest.writeInt(audience_score);
		 dest.writeString(critics_image);
		 dest.writeString(audience_image);
		 dest.writeString(thumbnails);
		 dest.writeString(original);
		 dest.writeString(release_date);
		 dest.writeInt(runtime);

	}
	
		
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
