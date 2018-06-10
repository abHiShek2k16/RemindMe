package com.android.abhishek.megamovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PersonProfile {
    @SerializedName(EndPoint.BIRTHDAY)
    String birthday;
    @SerializedName(EndPoint.NAME)
    String name;
    @SerializedName(EndPoint.BIOGRAPHY)
    String biography;
    @SerializedName(EndPoint.BIRTH_PLACE)
    String birthPlace;
    @SerializedName(EndPoint.PROFILE_PATH)
    String profilePath;

    public PersonProfile(String birthday, String name, String biography, String birthPlace, String profilePath) {
        this.birthday = birthday;
        this.name = name;
        this.biography = biography;
        this.birthPlace = birthPlace;
        this.profilePath = profilePath;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getName() {
        return name;
    }

    public String getBiography() {
        return biography;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getProfilePath() {
        return profilePath;
    }
}
