package com.android.abhishek.megamovies.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.abhishek.megamovies.MovieDetailAct;
import com.android.abhishek.megamovies.R;
import com.android.abhishek.megamovies.adapter.ListAdapter;
import com.android.abhishek.megamovies.listener.RecyclerItemClickListener;
import com.android.abhishek.megamovies.model.List;
import com.android.abhishek.megamovies.network.BuildUrl;
import com.android.abhishek.megamovies.utils.ApiInterface;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    @BindView(R.id.movieFragmentRv) RecyclerView recyclerView;
    @BindView(R.id.movieFragmentPb) ProgressBar progressBar;
    @BindView(R.id.movieFragmentPreviousBtn) Button previousBtn;
    @BindView(R.id.movieFragmentNextBtn) Button nextBtn;

    @BindString(R.string.api_key) String API_KEY;
    @BindString(R.string.popular_movie) String POPULAR;
    @BindString(R.string.top_rated_movie) String TOP_RATED;
    @BindString(R.string.upcoming) String UPCOMING;
    @BindString(R.string.now_playing) String NOW_PLAYING;
    @BindString(R.string.now_playing) String SORT_BY;

    private int NO_OF_IMAGE = 2;
    private int CURRENT_PAGE = 1;
    private int TOTAL_PAGE = 1;

    private List movieList;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadMovies();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getActivity(), MovieDetailAct.class);
                        intent.putExtra(getResources().getString(R.string.intent_id_passing),movieList.getResults().get(position).getId());
                        startActivity(intent);
                    }
                })
        );

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CURRENT_PAGE < TOTAL_PAGE) {
                    CURRENT_PAGE++;
                    loadMovies();
                }
            }
        });

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CURRENT_PAGE > 1) {
                    CURRENT_PAGE--;
                    loadMovies();
                }
            }
        });

    }

    public void loadMovies() {
        progressBar.setVisibility(View.VISIBLE);

        if (API_KEY.isEmpty()) {
            closeOnError();
        }

        ApiInterface apiInterface = BuildUrl.getRetrofit(getActivity()).create(ApiInterface.class);
        retrofit2.Call<List> movieListCall;

        if (SORT_BY.equals(POPULAR)) {
            movieListCall = apiInterface.getPopularMovies(API_KEY,CURRENT_PAGE);
        } else if(SORT_BY.equals(TOP_RATED)){
            movieListCall = apiInterface.getTopRatedMovies(API_KEY,CURRENT_PAGE);
        }else if(SORT_BY.equals(UPCOMING)){
            movieListCall = apiInterface.getUpcomingMovies(API_KEY,CURRENT_PAGE);
        }else {
            movieListCall = apiInterface.getNowPlayingMovies(API_KEY,CURRENT_PAGE);
        }

        movieListCall.enqueue(new Callback<List>() {
            @Override
            public void onResponse(Call<List> call, Response<List> response) {
                movieList = response.body();
                setMovieList();
            }

            @Override
            public void onFailure(Call<List> call, Throwable t) {
                closeOnError();
            }
        });
    }

    public void setMovieList() {

        progressBar.setVisibility(View.GONE);
        TOTAL_PAGE = movieList.getTotalPages();

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NO_OF_IMAGE));
        ListAdapter movieListAdapter = new ListAdapter(movieList.getResults());
        recyclerView.setAdapter(movieListAdapter);

        if (TOTAL_PAGE > CURRENT_PAGE) {
            nextBtn.setVisibility(View.VISIBLE);
        } else {
            nextBtn.setVisibility(View.INVISIBLE);
        }

        if (CURRENT_PAGE == 1) {
            previousBtn.setVisibility(View.INVISIBLE);
        } else {
            previousBtn.setVisibility(View.VISIBLE);
        }
    }

    public void closeOnError() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.sort_by_movie,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.popularMv :
                if(SORT_BY.equalsIgnoreCase(POPULAR)){
                    return true;
                }
                item.setChecked(true);
                SORT_BY = POPULAR;
                loadMovies();
                return true;

            case R.id.mostRatedMv :
                if(SORT_BY.equalsIgnoreCase(TOP_RATED)){
                    return true;
                }
                item.setChecked(true);
                SORT_BY = TOP_RATED;
                loadMovies();
                return true;

            case R.id.upcomingMv :
                if(SORT_BY.equalsIgnoreCase(UPCOMING)){
                    return true;
                }
                item.setChecked(true);
                SORT_BY = UPCOMING;
                loadMovies();
                return true;

            case R.id.nowPlayMv :
                if(SORT_BY.equalsIgnoreCase(NOW_PLAYING)){
                    return true;
                }
                item.setChecked(true);
                SORT_BY = NOW_PLAYING;
                loadMovies();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
