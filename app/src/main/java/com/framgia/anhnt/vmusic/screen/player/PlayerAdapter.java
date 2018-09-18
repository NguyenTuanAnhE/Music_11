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
import com.framgia.anhnt.vmusic.utils.TrackUtils;

import java.util.ArrayList;
import java.util.List;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Track> mTracks;
    private OnTrackClickListener mListener;
    private int mPosition;

    public PlayerAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mTracks = new ArrayList<>();
    }

    public void setListener(OnTrackClickListener listener) {
        mListener = listener;
    }

    public void setPosition(int position) {
        notifyItemChanged(mPosition);
        mPosition = position;
        notifyItemChanged(position);
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
        return new ViewHolder(mInflater.inflate(R.layout.item_player, parent,
                false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindData(track, mPosition);
    }

    @Override
    public int getItemCount() {
        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextPosition;
        private TextView mTextTitle;
        private TextView mTextDuration;
        private OnTrackClickListener mListener;

        public ViewHolder(View itemView, OnTrackClickListener listener) {
            super(itemView);

            mListener = listener;
            mTextPosition = itemView.findViewById(R.id.text_position);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mTextDuration = itemView.findViewById(R.id.text_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener == null) return;
                    mListener.onTrackClick(getAdapterPosition());
                }
            });
        }

        void bindData(Track track, int position) {
            if (track == null) return;

            mTextPosition.setText(String.valueOf(getAdapterPosition() + 1));
            mTextTitle.setText(track.getTitle());
            mTextDuration.setText(TrackUtils.getDuration(track.getDuration()));

            setColor(position);
        }

        void setColor(int position) {
            if (position == getAdapterPosition()) {
                int colorBlue = itemView.getResources().getColor(R.color.color_deep_sky_blue);
                mTextPosition.setTextColor(colorBlue);
                mTextTitle.setTextColor(colorBlue);
                mTextDuration.setTextColor(colorBlue);
            } else {
                int colorWhite = itemView.getResources().getColor(R.color.color_white);
                mTextPosition.setTextColor(colorWhite);
                mTextTitle.setTextColor(colorWhite);
                mTextDuration.setTextColor(colorWhite);
            }
        }
    }

    public interface OnTrackClickListener {
        void onTrackClick(int position);
    }
}
