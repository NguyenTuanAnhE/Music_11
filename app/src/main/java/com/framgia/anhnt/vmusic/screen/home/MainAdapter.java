package com.framgia.anhnt.vmusic.screen.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Genre> mGenres;
    private static OnGenreClickListener mListener;

    public MainAdapter(Context context, OnGenreClickListener listener) {
        mContext = context;
        mGenres = new ArrayList<>();
        mInflater = LayoutInflater.from(mContext);
        mListener = listener;
    }

    public void updateGenres(List<Genre> genres) {
        mGenres = genres;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_home, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre genre = mGenres.get(position);
        holder.bindData(genre);

    }

    @Override
    public int getItemCount() {
        return mGenres == null ? 0 : mGenres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTextGenre;
        private TextView mTextTrack;
        private ImageView mImagePlay;
        private ImageView mImageCover;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextGenre = itemView.findViewById(R.id.text_home);
            mTextTrack = itemView.findViewById(R.id.text_track);
            mImagePlay = itemView.findViewById(R.id.image_play);
            mImageCover = itemView.findViewById(R.id.image_cover);

            mImageCover.setOnClickListener(this);
            mImagePlay.setOnClickListener(this);
        }

        void bindData(Genre genre) {
            if (genre == null) {
                return;
            }
            mTextGenre.setText(genre.getTitle());
            mTextTrack.setText(genre.getTrack());
            Glide.with(mImageCover.getContext())
                    .load(genre.getImage())
                    .into(mImageCover);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_cover:
                    mListener.onItemClick(getAdapterPosition());
                    break;
                case R.id.image_play:
                    mListener.onPlayClick(getAdapterPosition());
                    break;
                default:
                    break;
            }
        }
    }

    public interface OnGenreClickListener {
        void onItemClick(int position);

        void onPlayClick(int position);
    }
}
