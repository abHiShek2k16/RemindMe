package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

public class VideosResults {

    @SerializedName(EndPoint.KEY)
    private String videoKey;

    public VideosResults(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getVideoKey() {
        return videoKey;
    }
}
