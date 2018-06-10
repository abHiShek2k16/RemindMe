package com.android.abhishek.megamovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.abhishek.megamovies.model.MovieDetail;
import com.android.abhishek.megamovies.model.PersonProfile;
import com.android.abhishek.megamovies.network.BuildUrl;
import com.android.abhishek.megamovies.utils.ApiInterface;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastProfileAct extends AppCompatActivity {

    @BindView(R.id.profileName) TextView name;
    @BindView(R.id.profileYear) TextView year;
    @BindView(R.id.profileLocation) TextView location;
    @BindView(R.id.profileBio) TextView bio;
    @BindView(R.id.profilePhoto) ImageView photo;

    @BindString(R.string.api_key) String API_KEY;
    @BindString(R.string.empty) String EMPTY;
    @BindString(R.string.info_unavailable) String DATA_NOT_AVAILABLE;
    @BindString(R.string.image_base_url) String IMAGE_BASE_URL;

    private String id;
    private PersonProfile personProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_profile);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        id = intent.getStringExtra(getResources().getString(R.string.intent_id_passing));
        if(id == null){
            closeOnError();
        }

        loadProfile();
    }

    private void loadProfile(){
        if(API_KEY.isEmpty()){
            closeOnError();
            return;
        }

        ApiInterface apiInterface = BuildUrl.getRetrofit(this).create(ApiInterface.class);
        final retrofit2.Call<PersonProfile> personProfileCall = apiInterface.getProfile(id,API_KEY);
        personProfileCall.enqueue(new Callback<PersonProfile>() {
           @Override
           public void onResponse(Call<PersonProfile> call, Response<PersonProfile> response) {
               personProfile = response.body();
               setProfile();
           }

           @Override
           public void onFailure(Call<PersonProfile> call, Throwable t) {
                closeOnError();
           }
        });
    }

    private void setProfile(){
        String nameStr = personProfile.getName()==null?EMPTY:personProfile.getName();
        String yearStr = personProfile.getBirthday()==null?EMPTY:personProfile.getBirthday();
        String locationStr = personProfile.getBirthPlace()==null?DATA_NOT_AVAILABLE:personProfile.getBirthPlace();
        String bioStr;
        try{
            bioStr = personProfile.getBiography().isEmpty()?DATA_NOT_AVAILABLE:personProfile.getBiography();
        }catch (Exception e){
            bioStr = DATA_NOT_AVAILABLE;
        }
        String imageUrl = personProfile.getProfilePath();

        Picasso.get()
                .load(IMAGE_BASE_URL+imageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(photo);

        name.setText(nameStr);
        year.setText(changeFormatOfDate(yearStr));
        location.setText(locationStr);
        bio.setText(bioStr);
    }

    private String changeFormatOfDate(String releaseDate){
        String day = releaseDate.substring(8,10);
        int month;
        try{
            month = Integer.parseInt(releaseDate.substring(5,7));
        }catch (ClassCastException e){
            month = 0;
        }
        String year = releaseDate.substring(0,4);
        String changedFormatDate = day;
        switch (month){
            case 1:
                changedFormatDate += " Jan";
                break;
            case 2:
                changedFormatDate += " Feb";
                break;
            case 3:
                changedFormatDate += " Mar";
                break;
            case 4:
                changedFormatDate += " Apr";
                break;
            case 5:
                changedFormatDate += " May";
                break;
            case 6:
                changedFormatDate += " June";
                break;
            case 7:
                changedFormatDate += " July";
                break;
            case 8:
                changedFormatDate += " Aug";
                break;
            case 9:
                changedFormatDate += " Sept";
                break;
            case 10:
                changedFormatDate += " Oct";
                break;
            case 11:
                changedFormatDate += " Nov";
                break;
            case 12:
                changedFormatDate += " Dec";
                break;
        }

        changedFormatDate += " "+year;
        return changedFormatDate;
    }

    private void closeOnError(){

    }
}
