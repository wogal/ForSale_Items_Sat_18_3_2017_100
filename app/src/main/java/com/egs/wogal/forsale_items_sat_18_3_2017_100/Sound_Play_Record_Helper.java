package com.egs.wogal.forsale_items_sat_18_3_2017_100;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by wogal on 3/17/2017.
 */

public class Sound_Play_Record_Helper {


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mSoundFileName = null;
    public int test = 0;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;


    private MediaRecorder mRecorder = null;
    private OnStopTrackEventListener mOnStopTrackEventListener;

    private MediaPlayer mPlayer = null;

    public Sound_Play_Record_Helper () {
        mSoundFileName = Storage_Helper_Class.GetVoiceFilePath();        //
    }

    public void setOnStopTrackEventListener (OnStopTrackEventListener eventListener) {
        mOnStopTrackEventListener = eventListener;
    }

    private void onRecord (boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay (boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying () {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mSoundFileName);
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener((new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion (MediaPlayer mp) {
                    mOnStopTrackEventListener.onStopTrack(1222);
                }
            }));

        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying () {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording () {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mSoundFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording () {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    // will toggle stop / start
    public void RecordButton (boolean _recState) {
        mStartRecording = _recState;
        onRecord(mStartRecording);
    }

    // will toggle stop / start
    public void PlayButton (boolean _recState) {
        mStartRecording = _recState;
        onPlay(mStartPlaying);
    }

    public boolean isSafeToPlayFile () {
        return safeToPlayFile();
    }

    private boolean safeToPlayFile () {

        File file = new File(mSoundFileName);
        if (!file.exists()) {
            return false;
        }
        if (50 > file.length())
            return true;
        else
            return false;
    }

    public interface OnStopTrackEventListener {
        int onStopTrack (int a);
    }


}
