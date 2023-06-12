package com.shahruie.www.gohome;

/**
 * Created by MR on 05/10/2017.
 */
import android.app.Activity;
import android.widget.LinearLayout;
import android.os.Bundle;
import android.os.Environment;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;


public class AudioRecordTest {
    public static final String LOG_TAG = "AudioRecordTest";
    public static String mFileName = null;
    MediaRecorder mRecorder = null;
    public MediaPlayer   mPlayer = null;
    /*private RecordButton mRecordButton = null;
    private PlayButton   mPlayButton = null;*/
    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    public void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    public void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    public void MediaRecorderReady(){
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

    }

    public void startRecording() {
        MediaRecorderReady();
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            System.out.println(""+e);
        }
        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    public AudioRecordTest() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath().substring(8);
        mFileName += "/audiorecordtest.3gp";
    }

}