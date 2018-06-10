package com.android.abhishek.megamovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.abhishek.megamovies.R;
import com.android.abhishek.megamovies.model.MovieCastsResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieCastsAdapter extends RecyclerView.Adapter<MovieCastsAdapter.CastsCustomAdapter>{

    private ArrayList<MovieCastsResult> movieCastsResultsAl;

    @BindString(R.string.empty)
    String EMPTY;
    @BindString(R.string.image_base_url)
    String IMAGE_BASE_URL;

    public MovieCastsAdapter(ArrayList<MovieCastsResult> movieCastsResultsAl) {
        this.movieCastsResultsAl = movieCastsResultsAl;
    }

    @NonNull
    @Override
    public CastsCustomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_list_item,parent,false);
        return new CastsCustomAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastsCustomAdapter holder, int position) {
        MovieCastsResult movieCastsResult = movieCastsResultsAl.get(position);
        if(movieCastsResult == null){
            return;
        }
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500"+movieCastsResult.getProfilePath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.castIv);
        String name = movieCastsResult.getName().isEmpty()?EMPTY:movieCastsResult.getName();
        String character = movieCastsResult.getCharacter().isEmpty()?EMPTY:movieCastsResult.getCharacter();
        holder.nameTv.setText(name);
        holder.rollTv.setText(character);
    }

    @Override
    public int getItemCount() {
        return movieCastsResultsAl.size();
    }

    public class CastsCustomAdapter extends RecyclerView.ViewHolder{
        @BindView(R.id.castIv)
        ImageView castIv;
        @BindView(R.id.TvOneCast)
        TextView rollTv;
        @BindView(R.id.TvTwoCast)
        TextView nameTv;
        public CastsCustomAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
