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

import com.android.abhishek.megamovies.adapter.MovieCastsAdapter;
import com.android.abhishek.megamovies.adapter.ReviewAdapter;
import com.android.abhishek.megamovies.adapter.TrailerAdapter;
import com.android.abhishek.megamovies.listener.RecyclerItemClickListener;
import com.android.abhishek.megamovies.model.MovieDetail;
import com.android.abhishek.megamovies.model.ProductionCompany;
import com.android.abhishek.megamovies.model.ReviewResults;
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

public class MovieDetailAct extends AppCompatActivity {

    private String movieId;
    private MovieDetail movieDetail;

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
    @BindString(R.string.append_query_movie)
    String APPEND_QUERY;

    @BindView(R.id.posterImageAtMovieDetail)
    ImageView posterImageIv;
    @BindView(R.id.movieNameAtMovieDetail)
    TextView movieNameTv;
    @BindView(R.id.productionNameAtMovie)
    TextView productionNameTv;
    @BindView(R.id.movieFavBtn)
    LikeButton likeButton;
    @BindView(R.id.logoIv)
    ImageView movieLogoIv;
    @BindView(R.id.lengthTvAtMovieDetail)
    TextView lengthTv;
    @BindView(R.id.ratingTvAtMovieDetail)
    TextView ratingTv;
    @BindView(R.id.totalVoteTvAtMovieDetail)
    TextView totalVoteTv;
    @BindView(R.id.dateTvAtMovieDetail)
    TextView releaseDateTv;
    @BindView(R.id.overviewAtMovieDetail)
    TextView overviewTv;
    @BindView(R.id.trailerRv)
    RecyclerView trailerRv;
    @BindView(R.id.castRvAtMovieDetail)
    RecyclerView castRv;
    @BindView(R.id.reviewRvAtMovieDetail)
    RecyclerView reviewRv;
    @BindView(R.id.readAllReviewAtMovieDetail)
    TextView readAll;
    @BindView(R.id.toolBarATMovieDetail)
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

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

        movieId = intent.getStringExtra(getResources().getString(R.string.intent_id_passing));
        if(movieId.isEmpty()){
            closeOnError();
        }

        loadMovieDetail();

        trailerRv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, trailerRv, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String key = movieDetail.getMovieVideos().getVideosResults().get(position).getVideoKey();
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

        castRv.addOnItemTouchListener(new RecyclerItemClickListener(this, castRv, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String id = movieDetail.getMovieCasts().getMovieCastsResults().get(position).getId();
                Intent intent = new Intent(MovieDetailAct.this,CastProfileAct.class);
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

    private void loadMovieDetail(){
        if(API_KEY.isEmpty()){
            closeOnError();
            return;
        }

        ApiInterface apiInterface = BuildUrl.getRetrofit(this).create(ApiInterface.class);
        retrofit2.Call<MovieDetail> movieDetailCall = apiInterface.getMovieDetail(movieId,API_KEY,APPEND_QUERY);
        movieDetailCall.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                movieDetail = response.body();
                setMovieDetail();
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                closeOnError();
            }
        });
    }

    private void setMovieDetail(){
        if(movieDetail == null){
            return;
        }

        String posterImageUrl = movieDetail.getBackdropPath();
        String movieLogo = movieDetail.getPosterPath();

        String productionName = EMPTY;
        ArrayList<ProductionCompany> movieProductionCompanies = movieDetail.getProductionCompanies();
        for(int i=0;i<movieProductionCompanies.size();i++){
            if(movieProductionCompanies.get(i).getLogoPath()!=null && movieProductionCompanies.get(i).getName()!=null){
                productionName = "by "+movieDetail.getProductionCompanies().get(i).getName();
                break;
            }
        }

        String length = DATA_NOT_AVAILABLE;
        try{
            length = movieDetail.getRuntime().isEmpty() ? DATA_NOT_AVAILABLE
                    : String.valueOf(Integer.parseInt(movieDetail.getRuntime())/60)+"h "+String.valueOf(Integer.parseInt(movieDetail.getRuntime())%60)+"min";
        }catch (Exception e){

        }

        String movieName = movieDetail.getTitle().isEmpty()?DATA_NOT_AVAILABLE:movieDetail.getTitle();
        String rating = movieDetail.getVoteAvg().isEmpty()?DATA_NOT_AVAILABLE:movieDetail.getVoteAvg();
        String totalVote = movieDetail.getVoteCount().isEmpty()?DATA_NOT_AVAILABLE:movieDetail.getVoteCount();
        String releaseDate = movieDetail.getReleaseDate().isEmpty()?DATA_NOT_AVAILABLE:movieDetail.getReleaseDate();
        String overView = movieDetail.getOverview().isEmpty()?DATA_NOT_AVAILABLE:movieDetail.getOverview();

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
        releaseDateTv.setText(changeFormatOfDate(releaseDate));
        overviewTv.setText(overView);

        trailerRv.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        TrailerAdapter trailerAdapter = new TrailerAdapter(movieDetail.getMovieVideos().getVideosResults());
        trailerRv.setAdapter(trailerAdapter);

        castRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        MovieCastsAdapter castsAdapter = new MovieCastsAdapter(movieDetail.getMovieCasts().getMovieCastsResults());
        castRv.setAdapter(castsAdapter);

        reviewRv.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ReviewAdapter reviewAdapter;
        ArrayList<ReviewResults> movieReviewResults = movieDetail.getMovieReview().getMovieReviewResults();
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

