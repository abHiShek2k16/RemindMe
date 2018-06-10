package com.android.abhishek.megamovies.network;

import android.content.Context;

import com.android.abhishek.megamovies.R;

import butterknife.BindString;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuildUrl {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(Context context){
        String BASE_URL = context.getResources().getString(R.string.movie_base_url);
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
