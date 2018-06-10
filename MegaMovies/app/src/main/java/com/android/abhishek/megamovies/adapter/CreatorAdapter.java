package com.android.abhishek.megamovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.abhishek.megamovies.R;
import com.android.abhishek.megamovies.model.TvCreatedByResults;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CreatorAdapter extends RecyclerView.Adapter<CreatorAdapter.CreatorCustomAdapter>{

    private ArrayList<TvCreatedByResults> tvCreatorResultsAl;

    @BindString(R.string.empty)
    String EMPTY;
    @BindString(R.string.image_base_url)
    String IMAGE_BASE_URL;

    public CreatorAdapter(ArrayList<TvCreatedByResults> tvCreatorResultsAl) {
        this.tvCreatorResultsAl = tvCreatorResultsAl;
    }

    @NonNull
    @Override
    public CreatorCustomAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.creater_list_item,parent,false);
        return new CreatorCustomAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatorCustomAdapter holder, int position) {
        TvCreatedByResults createdByResults = tvCreatorResultsAl.get(position);
        if(createdByResults == null){
            return;
        }
        Picasso.get()
                .load("https://image.tmdb.org/t/p/w500"+createdByResults.getProfilePath())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.creatorIv);
        String name = createdByResults.getName().isEmpty()?EMPTY:createdByResults.getName();
        holder.nameTv.setText(name);
    }

    @Override
    public int getItemCount() {
        return tvCreatorResultsAl.size();
    }

    public class CreatorCustomAdapter extends RecyclerView.ViewHolder{
        @BindView(R.id.createrIv)
        ImageView creatorIv;
        @BindView(R.id.TvOneCreator)
        TextView nameTv;
        public CreatorCustomAdapter(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
