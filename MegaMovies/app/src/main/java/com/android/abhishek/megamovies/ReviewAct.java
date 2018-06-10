package com.android.abhishek.megamovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.abhishek.megamovies.adapter.ReviewAdapter;
import com.android.abhishek.megamovies.model.ReviewResults;

import java.util.ArrayList;

import butterknife.BindView;

public class ReviewAct extends AppCompatActivity {

    @BindView(R.id.reviewRvAtReview)
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        if(intent == null){
            closeOnError();
        }

        ReviewResults reviewResults;

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        ReviewAdapter reviewAdapter;
       // ArrayList<ReviewResults> movieReviewResults = reviewResults;

    }

    private void closeOnError(){

    }
}
