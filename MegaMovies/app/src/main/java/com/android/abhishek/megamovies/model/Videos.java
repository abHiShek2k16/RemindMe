package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Videos {

    @SerializedName(EndPoint.RESULTS)
    private ArrayList<VideosResults> movieVideosResults;

    public Videos(ArrayList<VideosResults> movieVideosResults) {
        this.movieVideosResults = movieVideosResults;
    }

    public ArrayList<VideosResults> getVideosResults() {
        return movieVideosResults;
    }
}

