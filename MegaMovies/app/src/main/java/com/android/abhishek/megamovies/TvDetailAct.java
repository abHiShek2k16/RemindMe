package com.android.abhishek.megamovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.abhishek.megamovies.adapter.CreatorAdapter;
import com.android.abhishek.megamovies.adapter.ReviewAdapter;
import com.android.abhishek.megamovies.adapter.TrailerAdapter;
import com.android.abhishek.megamovies.listener.RecyclerItemClickListener;
import com.android.abhishek.megamovies.model.ProductionCompany;
import com.android.abhishek.megamovies.model.ReviewResults;
import com.android.abhishek.megamovies.model.TvDetail;
import com.android.abhishek.megamovies.network.BuildUrl;
import com.android.abhishek.megamovies.utils.ApiInterface;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvDetailAct extends AppCompatActivity {

    private String tvId;
    private TvDetail tvDetail;

    @BindString(R.string.api_key)
    String API_KEY;
    @BindString(R.string.image_base_url)
    String IMAGE_BASE_URL;
    @BindString(R.string.empty)
    String EMPTY;
    @BindString(R.string.info_unavailable)
    String DATA_NOT_AVAILABLE;
    @BindString(R.string.video_app_base_url)
    String VIDEO_APP_BASE_URL;
    @BindString(R.string.video_web_base_url)
    String VIDEO_WEB_BASE_URL;
    @BindString(R.string.append_query_tv)
    String APPEND_QUERY;

    @BindView(R.id.posterImageAtTv)
    ImageView posterImageIv;
    @BindView(R.id.movieNameAtTv)
    TextView movieNameTv;
    @BindView(R.id.productionNameAtTv)
    TextView productionNameTv;
    @BindView(R.id.tvFavBtn)
    LikeButton likeButton;
    @BindView(R.id.logoIvAtTv)
    ImageView movieLogoIv;
    @BindView(R.id.lengthTvAtTv)
    TextView lengthTv;
    @BindView(R.id.ratingTvAtTv)
    TextView ratingTv;
    @BindView(R.id.totalVoteTvAtTv)
    TextView totalVoteTv;
    @BindView(R.id.firstDateAtTv)
    TextView firstEpisodeDateTv;
    @BindView(R.id.lastDateAtTv)
    TextView lastEpisodeDateTv;
    @BindView(R.id.overviewAtTv)
    TextView overviewTv;
    @BindView(R.id.trailerRvAtTv)
    RecyclerView trailerRv;
    @BindView(R.id.createrRvAtTv)
    RecyclerView creatorRv;
    @BindView(R.id.reviewRvAtTv)
    RecyclerView reviewRv;
    @BindView(R.id.readAllReviewAtTv)
    TextView readAll;
    @BindView(R.id.toolBarAtTv)
    android.support.v7.widget.Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_detail);

        ButterKnife.bind(this);

        final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_white_24);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        tvId = intent.getStringExtra(getResources().getString(R.string.intent_id_passing));
        if(tvId.isEmpty()){
            closeOnError();
        }

        loadTvDetail();

        trailerRv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, trailerRv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String key = tvDetail.getVideos().getVideosResults().get(position).getVideoKey();
                        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(VIDEO_APP_BASE_URL+key));
                        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(VIDEO_WEB_BASE_URL+key));
                        try {
                            startActivity(appIntent);
                        } catch (ActivityNotFoundException ex) {
                            startActivity(webIntent);
                        }
                    }
                })
        );

        creatorRv.addOnItemTouchListener(new RecyclerItemClickListener(this, creatorRv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = tvDetail.getTvCreatedByResults().get(position).getId();
                Intent intent = new Intent(TvDetailAct.this,CastProfileAct.class);
                intent.putExtra(getResources().getString(R.string.intent_id_passing),id);
                startActivity(intent);
            }
        }));

        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                likeButton.setLiked(true);
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                likeButton.setLiked(false);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadTvDetail(){
        if(API_KEY.isEmpty()){
            closeOnError();
            return;
        }

        ApiInterface apiInterface = BuildUrl.getRetrofit(this).create(ApiInterface.class);
        retrofit2.Call<TvDetail> tvDetailCall = apiInterface.getTvDetail(tvId,API_KEY,APPEND_QUERY);
        tvDetailCall.enqueue(new Callback<TvDetail>() {
            @Override
            public void onResponse(Call<TvDetail> call, Response<TvDetail> response) {
                tvDetail = response.body();
                setTvDetail();
            }

            @Override
            public void onFailure(Call<TvDetail> call, Throwable t) {
                closeOnError();
            }
        });
    }

    private void setTvDetail(){
        if(tvDetail == null){
            closeOnError();
        }

        String posterImageUrl = tvDetail.getBackdropPath();
        String movieLogo = tvDetail.getPosterPath();

        String productionName = EMPTY;
        ArrayList<ProductionCompany> movieProductionCompanies = tvDetail.getProductionCompanies();
        for(int i=0;i<movieProductionCompanies.size();i++){
            if(movieProductionCompanies.get(i).getLogoPath()!=null && movieProductionCompanies.get(i).getName()!=null){
                productionName = "by "+tvDetail.getProductionCompanies().get(i).getName();
                break;
            }
        }

        ArrayList<Integer> runTime = tvDetail.getRunTime();
        String length = "";
        for(int i=0;i<runTime.size();i++){
            if(i==runTime.size()-1){
                length += String.valueOf(runTime.get(i))+" min";
            }else{
                length += String.valueOf(runTime.get(i))+" min,";
            }
        }

        String movieName = tvDetail.getName().isEmpty()?DATA_NOT_AVAILABLE:tvDetail.getName();
        String rating = tvDetail.getVoteAvg().isEmpty()?DATA_NOT_AVAILABLE:tvDetail.getVoteAvg();
        String totalVote = tvDetail.getVoteCount().isEmpty()?DATA_NOT_AVAILABLE:tvDetail.getVoteCount();
        String firstEpisode = tvDetail.getFirstAirDate().isEmpty()?DATA_NOT_AVAILABLE:tvDetail.getFirstAirDate();
        String lastEpisode = tvDetail.getLastAirDate().isEmpty()?DATA_NOT_AVAILABLE:tvDetail.getLastAirDate();
        String overView = tvDetail.getOverview().isEmpty()?DATA_NOT_AVAILABLE:tvDetail.getOverview();

        Picasso.get()
                .load(IMAGE_BASE_URL+posterImageUrl)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(posterImageIv);
        Picasso.get()
                .load(IMAGE_BASE_URL+movieLogo)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(movieLogoIv);

        movieNameTv.setText(movieName);
        productionNameTv.setText(productionName);
        lengthTv.setText(length);
        ratingTv.setText(rating+" / 10");
        totalVoteTv.setText(totalVote);
        firstEpisodeDateTv.setText(changeFormatOfDate(firstEpisode));
        lastEpisodeDateTv.setText(changeFormatOfDate(lastEpisode));
        overviewTv.setText(overView);

        trailerRv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        TrailerAdapter trailerAdapter = new TrailerAdapter(tvDetail.getVideos().getVideosResults());
        trailerRv.setAdapter(trailerAdapter);

        creatorRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        CreatorAdapter creatorAdapter = new CreatorAdapter(tvDetail.getTvCreatedByResults());
        creatorRv.setAdapter(creatorAdapter);

        reviewRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ReviewAdapter reviewAdapter;
        ArrayList<ReviewResults> movieReviewResults = tvDetail.getReview().getMovieReviewResults();
        if(movieReviewResults.size()>3) {
            ArrayList<ReviewResults> movieReviewResultsArrayList = new ArrayList<>();
            movieReviewResultsArrayList.add(movieReviewResults.get(0));
            movieReviewResultsArrayList.add(movieReviewResults.get(1));
            movieReviewResultsArrayList.add(movieReviewResults.get(2));
            readAll.setVisibility(View.VISIBLE);
            reviewAdapter = new ReviewAdapter(movieReviewResultsArrayList);
        }else {
            reviewAdapter = new ReviewAdapter(movieReviewResults);
        }
        reviewRv.setAdapter(reviewAdapter);
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
