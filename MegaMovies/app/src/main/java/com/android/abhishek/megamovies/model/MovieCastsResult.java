package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

public class MovieCastsResult {

    @SerializedName(EndPoint.CHARACTER)
    private String character;
    @SerializedName(EndPoint.NAME)
    private String name;
    @SerializedName(EndPoint.PROFILE_PATH)
    private String profilePath;
    @SerializedName(EndPoint.ID)
    private String id;

    public MovieCastsResult(String character, String name, String profilePath, String id) {
        this.character = character;
        this.name = name;
        this.profilePath = profilePath;
        this.id = id;
    }

    public String getCharacter() {
        return character;
    }

    public String getName() {
        return name;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public String getId() {
        return id;
    }
}
