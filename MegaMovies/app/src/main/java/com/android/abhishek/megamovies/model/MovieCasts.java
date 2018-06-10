package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieCasts {

    @SerializedName(EndPoint.CAST)
    private ArrayList<MovieCastsResult> movieCastsResults;

    public MovieCasts(ArrayList<MovieCastsResult> movieCastsResults) {
        this.movieCastsResults = movieCastsResults;
    }

    public ArrayList<MovieCastsResult> getMovieCastsResults() {
        return movieCastsResults;
    }
}
