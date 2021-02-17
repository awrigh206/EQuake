package org.me.gcu.equakestartercode.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EarthQuakeModel {
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

    public void parseDescription (){
        String[] partsOfDescription = this.description.split(";");
        String magString = partsOfDescription[partsOfDescription.length-1];
        String depthString = partsOfDescription[partsOfDescription.length-2];

        String[] partsMag = magString.split(":");
        String[] depthParts = depthString.split(":");

        this.magnitude = Double.parseDouble(partsMag[1]);
        this.depth = depthParts[1];
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
}
