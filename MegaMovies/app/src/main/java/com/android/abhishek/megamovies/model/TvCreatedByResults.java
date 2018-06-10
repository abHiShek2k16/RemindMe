package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

public class TvCreatedByResults {

    @SerializedName(EndPoint.ID)
    private String id;
    @SerializedName(EndPoint.NAME)
    private String name;
    @SerializedName(EndPoint.PROFILE_PATH)
    private String profilePath;

    public TvCreatedByResults(String id, String name, String profilePath) {
        this.id = id;
        this.name = name;
        this.profilePath = profilePath;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
