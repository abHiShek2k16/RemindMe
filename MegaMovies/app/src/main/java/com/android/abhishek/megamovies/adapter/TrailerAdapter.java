package com.android.abhishek.megamovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.abhishek.megamovies.R;
import com.android.abhishek.megamovies.model.VideosResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerCustomAdapter>{

    private ArrayList<VideosResults> movieVideosResultsAl;

    @BindString(R.string.trailer_image_base_url)
    String TRAILER_IMAGE_BASE_URL;
    @BindString(R.string.trailer_image_type_url)
    String IMAGE_TYPE;

    public TrailerAdapter(ArrayList<VideosResults> movieVideosResultsAl) {
        this.movieVideosResultsAl = movieVideosResultsAl;
    }

    @NonNull
    @Override
    public TrailerCustomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list_item,parent,false);
        return new TrailerCustomAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerCustomAdapter holder, int position) {
        VideosResults movieVideosResults = movieVideosResultsAl.get(position);
        if(movieVideosResults == null){
            return;
        }
        Picasso.get()
                .load("https://img.youtube.com/vi/"+movieVideosResults.getVideoKey()+"/0.jpg")
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.trailerIv);
    }

    @Override
    public int getItemCount() {
        return movieVideosResultsAl.size();
    }

    public class TrailerCustomAdapter extends RecyclerView.ViewHolder{
        @BindView(R.id.trailerImage)
        ImageView trailerIv;
        public TrailerCustomAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
