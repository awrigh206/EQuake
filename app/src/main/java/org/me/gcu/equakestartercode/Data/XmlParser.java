package org.me.gcu.equakestartercode.Data;

import android.util.Log;

import org.me.gcu.equakestartercode.Models.EarthQuakeModel;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
/**
 * Name: Andrew Wright
 * Student ID: S1711082
 */
public class XmlParser implements Callable<List<EarthQuakeModel>> {
    private String url="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    @Override
    public List<EarthQuakeModel> call() throws IOException {
        String data = getDataFromServer();
        List modelList = parseEarthQuakes(data);
        return modelList;

    }

    private List<EarthQuakeModel> parseEarthQuakes(String rawData) throws IOException {
        Log.e("Parsing","Starting to parse the data");
        LinkedList<EarthQuakeModel> models = new LinkedList<>();
        EarthQuakeModel currentModel = null;
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(rawData));
            int eventType = xpp.getEventType();
            boolean hasHitItems = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tag = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.END_TAG:
                        //a start tag has been hit
                        if (tag.equalsIgnoreCase("item")) {
                            currentModel.parseDescription();
                            models.add(currentModel);
                        }
                        break;
                    case XmlPullParser.START_TAG:
                        // a end tag has been hit
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            currentModel = new EarthQuakeModel();
                            hasHitItems = true;
                        }
                        if(hasHitItems){
                            if (tag.equalsIgnoreCase("title")) {
                                currentModel.setTitle(xpp.nextText());
                            }
                            else if (tag.equalsIgnoreCase("description")) {
                                currentModel.setDescription(xpp.nextText());
                            }
                            else if (tag.equalsIgnoreCase("link")) {
                                currentModel.setLink(xpp.nextText());
                            }
                            else if (tag.equalsIgnoreCase("category")) {
                                currentModel.setCategory(xpp.nextText());
                            }
                            else if (tag.equalsIgnoreCase("pubDate")) {
                                currentModel.setPubDate(xpp.nextText());
                            }
                            else if (tag.equalsIgnoreCase("geo:lat")) {
                                currentModel.setLat(Double.parseDouble(xpp.nextText()));
                            }
                            else if (tag.equalsIgnoreCase("geo:long")) {
                                currentModel.setLon(Double.parseDouble(xpp.nextText()));
                            }
                        }
                        break;
                }
                eventType = xpp.next();
            }
            return models;
        }
        catch (XmlPullParserException pullParserException){
            pullParserException.printStackTrace();
        }
        return new LinkedList<>();
    }

    private String getDataFromServer(){
        URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        StringBuilder resultBuilder = new StringBuilder();
        try
        {
            String inputLine = "";
            aurl = new URL(url);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            int skip = 0; // number of lines to skip at the start
            int currentLine = 0;
            while ((inputLine = in.readLine()) != null)
            {
                if(currentLine < skip){
                    currentLine ++;
                }
                else{
                    resultBuilder.append(inputLine);
                }

            }
            in.close();
        }
        catch (IOException ae)
        {
            Log.e("IO error", "ioexception when getting data from server");
        }
        return resultBuilder.toString();
    }
}
