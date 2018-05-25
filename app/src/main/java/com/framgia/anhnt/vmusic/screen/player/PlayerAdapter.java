package com.framgia.anhnt.vmusic.screen.player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Track;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Track> mTracks;

    public PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mTracks = new ArrayList<>();
    }

    public void addData(List<Track> tracks) {
        if (tracks == null) {
            return;
        }
        mTracks.clear();
        mTracks.addAll(tracks);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_player, parent, false));
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

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextPosition;
        private TextView mTextTitle;
        private TextView mTextDuration;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextPosition = itemView.findViewById(R.id.text_position);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextDuration = itemView.findViewById(R.id.text_duration);
        }

        void bindData(Track track) {
            if (track == null) {
                return;
            }
            mTextPosition.setText(String.valueOf(getAdapterPosition() + 1));
            mTextTitle.setText(track.getTitle());
            mTextDuration.setText(String.valueOf(track.getDuration()));
        }
    }
}
