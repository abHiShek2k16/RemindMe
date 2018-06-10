package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

public class ListResults {

    @SerializedName(EndPoint.ID)
    private String id;
    @SerializedName(EndPoint.POSTER_PATH)
    private String posterPath;

    public ListResults(String id, String posterPath){
        this.id = id;
        this.posterPath = posterPath;
    }

    public String getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }
}
