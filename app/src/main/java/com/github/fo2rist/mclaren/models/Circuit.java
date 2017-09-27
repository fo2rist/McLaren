package com.github.fo2rist.mclaren.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Circuit implements Serializable {
    public String id; // e.g "austria"
    public String country; // e.g "AUS",
    public String track; // e.g "Melbourne Grand Prix Circuit",
    public String city; // e.g "Melbourne",
    public String name; // e.g "Australian Grand Prix"
    public int laps;
    public double length;
    public double distance;
    public String seasons; // e.g "2009–2016",
    @SerializedName("gp_held")
    public int gpHeld;
    @SerializedName("wiki_link")
    public String wikiLink;
}
