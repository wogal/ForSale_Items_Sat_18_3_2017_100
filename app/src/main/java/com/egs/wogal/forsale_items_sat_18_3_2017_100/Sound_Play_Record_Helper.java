package com.egs.wogal.forsale_items_sat_18_3_2017_100;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;

import java.io.File;
import java.io.IOException;


public class Sound_Play_Record_Helper {


    private static final String LOG_TAG = "AudioRecordTest";
    private static String mSoundFileName = null;
    public int test = 0;
    private boolean mStartRecording = true;
    private boolean mStartPlaying = true;


    private MediaRecorder mRecorder = null;
    private OnStopTrackEventListener mOnStopTrackEventListener;

    private MediaPlayer mPlayer = null;

    private boolean m_IsRecording = false;
    private boolean m_IsPlaying = false;


    public Sound_Play_Record_Helper () {
        mSoundFileName = Storage_Helper_Class.GetVoiceFilePath();        //
    }

    public int DummyInVokeEvent (int aa) {
        if (1 == 2)
            return 1;
        test = 890;
        if (mOnStopTrackEventListener != null) {
            test = 123;
            mOnStopTrackEventListener.onStopTrack( 12 );

        }
        return 44;
    }

    public boolean isM_IsRecording () {
        return m_IsRecording;
    }

    public boolean isM_IsPlaying () {
        return m_IsPlaying;
    }

    public void setOnStopTrackEventListener (OnStopTrackEventListener eventListener) {
        m_IsRecording = false;
        m_IsPlaying = false;

        mOnStopTrackEventListener = eventListener;
    }

    private void onRecord (boolean start) {
        if (start) {
            m_IsRecording = true;
            startRecording();
        } else {
            m_IsRecording = false;
            stopRecording();
        }
    }

    private void onPlay (boolean start) {
        if (start) {
            m_IsPlaying = true;
            startPlaying();
        } else {
            m_IsPlaying = false;
            stopPlaying();
        }
    }

    private void startPlaying () {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource( mSoundFileName );
            mPlayer.prepare();
            mPlayer.start();
            mPlayer.setOnCompletionListener( (new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion (MediaPlayer mp) {
                    // IS EVENT FUNCTION TO CAALER function
                    m_IsPlaying = false;
                    mOnStopTrackEventListener.onStopTrack( 1222 );
                }
            }) );

        } catch (IOException e) {
            Log.e( LOG_TAG, "prepare() failed" );
        }
        m_IsPlaying = true;
    }

    private void stopPlaying () {
        mPlayer.release();
        m_IsPlaying = false;
        mPlayer = null;
    }

    private void startRecording () {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource( MediaRecorder.AudioSource.MIC );
        mRecorder.setOutputFormat( MediaRecorder.OutputFormat.THREE_GPP );
        mRecorder.setOutputFile( mSoundFileName );
        mRecorder.setAudioEncoder( MediaRecorder.AudioEncoder.AMR_NB );
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e( LOG_TAG, "prepare() failed" );
        }
        m_IsRecording = true;
        mRecorder.start();
    }

    private void stopRecording () {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        m_IsRecording = false;
    }

    // will toggle stop / start
    public void Set_Record_Action (boolean _recState) {
        mStartRecording = _recState;
        onRecord( mStartRecording );
    }

    // will toggle stop / start
    public void Set_Play_Action (boolean _recState) {
        mStartRecording = _recState;
        onPlay( mStartPlaying );
    }

    public boolean isSafeToPlayFile () {
        return safeToPlayFile();
    }

    private boolean safeToPlayFile () {

        File file = new File( mSoundFileName );
        if (!file.exists()) {
            return false;
        }
        if (file.length() > 1000)
            return true;
        else
            return false;
    }

    public interface OnStopTrackEventListener {
        int onStopTrack (int a);
    }

    /*
    public interface OnStopTrackEventListener {
        int onStopTrack (int a);
    }
    */


}
