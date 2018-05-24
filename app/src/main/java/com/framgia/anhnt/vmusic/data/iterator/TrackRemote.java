package com.framgia.anhnt.vmusic.data.iterator;

import android.os.AsyncTask;

import com.framgia.anhnt.vmusic.data.source.TrackDataSource;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public abstract class TrackRemote<T> extends
        AsyncTask<String, Void, T> {
    private TrackDataSource.OnFetchDataListener mListener;
    private WrapperData<T> mWrapperData;
    private TrackRemoteHandle mTrackRemoteHandle;

    public abstract T getData(String data) throws JSONException;

    public TrackRemote(TrackDataSource.OnFetchDataListener listener) {
        mListener = listener;
        mWrapperData = new WrapperData<>();
        mTrackRemoteHandle = new TrackRemoteHandle();
    }

    @Override
    protected T doInBackground(String... strings) {
        try {
            String json = mTrackRemoteHandle.getJsonFromApi(strings[0]);
            return getData(json);
        } catch (IOException e) {
            mWrapperData.setException(e);
        } catch (JSONException e) {
            mWrapperData.setException(e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(T data) {
        super.onPostExecute(data);
        if (mWrapperData.getException() == null) {
            mListener.onFetchDataSuccess(data);
        } else {
            mListener.onFetchDataFail(mWrapperData.getException().getMessage());
        }
    }

    public static class WrapperData<T> {
        private T mData;
        private Exception mException;

        public WrapperData() {

        }

        public T getData() {
            return mData;
        }

        public void setData(T data) {
            mData = data;
        }

        public Exception getException() {
            return mException;
        }

        public void setException(Exception exception) {
            mException = exception;
        }
    }

}
