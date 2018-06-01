package com.framgia.anhnt.vmusic.screen.offline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framgia.anhnt.vmusic.R;
import com.framgia.anhnt.vmusic.data.model.Track;
import com.framgia.anhnt.vmusic.utils.TrackUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<Track> mTracks;
    private OnTrackClickListener mListener;

    public OfflineAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mTracks = new ArrayList<>();
    }

    public void setListener(OnTrackClickListener listener) {
        mListener = listener;
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
        return new ViewHolder(mInflater.inflate(R.layout.item_track_offline, parent,
                false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindData(track);
    }

    @Override
    public int getItemCount() {
        return 1;
//        return mTracks == null ? 0 : mTracks.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView mImageArtwork;
        private TextView mTextTitle;
        private TextView mTextDuration;
        private OnTrackClickListener mListener;

        public ViewHolder(View itemView, OnTrackClickListener listener) {
            super(itemView);

            mListener = listener;
            mImageArtwork = itemView.findViewById(R.id.circle_cover);
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

        void bindData(Track track) {
            if (track == null) return;
            mTextTitle.setText(track.getTitle());
            mTextDuration.setText(TrackUtils.getDuration(track.getDuration()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
            new LoadImage().execute(Uri.parse(track.getUri()));
        }

        class LoadImage extends AsyncTask<Uri, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(Uri... uris) {
                Log.d("TAG", "doInBackground: ");
                return getAlbumImage(uris[0]);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap == null) {
                    Log.d("TAG", "onPostExecute: null");
//                    mImageArtwork.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Log.d("TAG", "onPostExecute: "+bitmap);
                    mImageArtwork.setImageBitmap(bitmap);
                }
            }

            Bitmap getAlbumImage(Uri uri) {
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                byte[] rawArt;
                Bitmap art = null;
                BitmapFactory.Options bfo = new BitmapFactory.Options();

                mmr.setDataSource(mImageArtwork.getContext(), uri);
                rawArt = mmr.getEmbeddedPicture();

                if (null != rawArt) {
                    return art = BitmapFactory.decodeByteArray(rawArt, 0, rawArt.length, bfo);
                }
                return null;
            }
        }
    }

    public interface OnTrackClickListener {
        void onTrackClick(int position);
    }

}
