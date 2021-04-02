package org.me.gcu.equakestartercode.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EarthQuakeModel implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private double lat;
    private double lon;
    private double magnitude;
    private String depth;
    private String location;
    private String dateString;

    public EarthQuakeModel(String title, String description, String link, String pubDate, String category, double lat, double lon) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
        this.category = category;
        this.lat = lat;
        this.lon = lon;
        parseDescription();
    }

    public String getLocationWithMagnitude(){
        return location+"-"+magnitude;
    }

    private EarthQuakeModel (Parcel parcel){
        id = parcel.readInt();
        title = parcel.readString();
        description = parcel.readString();
        link = parcel.readString();
        pubDate = parcel.readString();
        category = parcel.readString();
        lat = parcel.readDouble();
        lon = parcel.readDouble();
        magnitude = parcel.readDouble();
        depth = parcel.readString();
    }

    public void parseDescription (){
        String[] partsOfDescription = this.description.split(";");
        this.magnitude = Double.parseDouble(parseInfo(partsOfDescription,1));
        this.depth = parseInfo(partsOfDescription,2);
        this.location = parseInfo(partsOfDescription,4);
        this.dateString = parseInfo(partsOfDescription,5);
    }

    private String parseInfo(String[] parts, int distanceFromEnd){
        String text = parts[parts.length-distanceFromEnd];
        String[] currentParts = text.split(":");
        return currentParts[1];
    }

    public String toString(){
        return "Title: " + title + " Description: " + description;
    }

    public EarthQuakeModel(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public String getDepth() {
        return depth;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(link);
        parcel.writeString(pubDate);
        parcel.writeString(category);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
        parcel.writeDouble(magnitude);
        parcel.writeString(depth);
    }

    public static final Parcelable.Creator<EarthQuakeModel> CREATOR = new Parcelable.Creator<EarthQuakeModel>() {
        public EarthQuakeModel createFromParcel(Parcel in) {
            return new EarthQuakeModel(in);
        }

        public EarthQuakeModel[] newArray(int size) {
            return new EarthQuakeModel[size];
        }
    };

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }
}
