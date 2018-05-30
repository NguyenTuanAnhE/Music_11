package com.framgia.anhnt.vmusic.screen.search;

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
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.screen.online.genre.TrackAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Track> mTracks;
    private OnTrackClickListener mListener;

    public SearchAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mTracks = new ArrayList<>();
    }

    public void setListener(OnTrackClickListener listener) {
        mListener = listener;
    }

    public void updateData(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_track_online, parent,
                false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindData(track);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private OnTrackClickListener mListener;
        private ImageView mImageAlbumCover;
        private ImageView mImageDownload;
        private TextView mTextArtist;
        private TextView mTextTitle;

        public ViewHolder(View itemView, OnTrackClickListener listener) {
            super(itemView);

            mListener = listener;
            mImageAlbumCover = itemView.findViewById(R.id.image_album_cover);
            mImageDownload = itemView.findViewById(R.id.image_download);
            mTextArtist = itemView.findViewById(R.id.text_track_artist);
            mTextTitle = itemView.findViewById(R.id.text_track_title);
            mImageDownload.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onClick(getAdapterPosition());
                }
            });
        }

        void bindData(Track track) {
            if (track == null) {
                return;
            }
            mTextArtist.setText(track.getUsername());
            mTextTitle.setText(track.getTitle());
            Glide.with(mImageAlbumCover.getContext())
                    .load(track.getArtworkUrl())
                    .into(mImageAlbumCover);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_download:
                    if (mListener == null) return;
                    mListener.onClickDownload(getAdapterPosition());
                    break;
            }
        }
    }

    public interface OnTrackClickListener {
        void onClick(int position);

        void onClickDownload(int position);
    }
}
