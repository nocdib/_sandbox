package com.nocdib.musicplayerexample;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    final String TAG = "MainActivity";
    Button playButton, pauseButton, stopButton;
    MediaPlayer mediaPlayer;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = (Button) findViewById(R.id.play);
        pauseButton = (Button) findViewById(R.id.pause);
        stopButton = (Button) findViewById(R.id.stop);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.mobb_deep);
        position = 0;

        playButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                playAudio();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                pauseAudio();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                stopAudio();
            }
        });
    }

    protected void playAudio(){
        if(!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(position);
            mediaPlayer.start();
        }
        else
        Log.i(TAG, "isPlaying() = " + mediaPlayer.isPlaying());
    }

    protected void pauseAudio(){
        if(mediaPlayer.isPlaying()){
            position = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
        Log.i(TAG, "isPlaying() = " + mediaPlayer.isPlaying());

    }

    protected void stopAudio(){
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            position = 0;
        }
        Log.i(TAG, "isPlaying() = " + mediaPlayer.isPlaying());
    }

}
