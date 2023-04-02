package com.example.musicmediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Widgets
    Button forward_button, rewind_button, play_button, pause_button;
    TextView time_text, title_text;
    SeekBar seekBar;

    //Media Player
    MediaPlayer mediaPlayer;

    //Handlers
    Handler handler;

    //Variables
    double startTime = 0;
    double finalTime = 0;
    int forwardTime = 10000;
    int backwardTime = 10000;
    static int oneTimeOnly = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play_button = findViewById(R.id.play_btn);
        pause_button = findViewById(R.id.pause_btn);
        rewind_button = findViewById(R.id.rewind_btn);
        forward_button = findViewById(R.id.forward_btn);

        title_text = findViewById(R.id.song_title);
        time_text = findViewById(R.id.time_left);

        seekBar = findViewById(R.id.seek_bar);
        
    }
}