
// Midterm
//XMLparsing.java
//Shashank G Hebbale (800773977)



package com.example.mainactivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;


public class XMLparsing {

	static public class PersonsXMLPullParser {
		static ArrayList<String> parsePersons(InputStream xmlIn) throws XmlPullParserException, NumberFormatException, IOException{						
			XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			parser.setInput(xmlIn, "UTF-8");
			String id = null;
			ArrayList<String> ids = null;
			int event = parser.getEventType();			
			while(event != XmlPullParser.END_DOCUMENT){
				switch (event) {
				case XmlPullParser.START_DOCUMENT:
					ids = new ArrayList<String>();
					break;
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("id"))
						id = (parser.nextText().trim());

					break;
				case XmlPullParser.END_TAG:
					if(parser.getName().equals("error")){
						ids.add(id);
					}
				default:
					break;
				}
				event = parser.next();
			}
			return ids;
		}
	}

}
