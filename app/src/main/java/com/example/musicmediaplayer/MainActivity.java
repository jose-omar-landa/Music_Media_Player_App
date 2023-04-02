package com.example.musicmediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

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

        //Media Player
        mediaPlayer = MediaPlayer.create(this, R.raw.astronaut);

        title_text.setText(getResources().getIdentifier("astronaut", "raw", getPackageName()));

        seekBar.setClickable(false);

        //Button Functionalities
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic();
            }
        });

        pause_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
            }
        });

        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;
                if (temp + forwardTime <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    Toast.makeText(MainActivity.this, "Error! Can't jump forwards!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rewind_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = (int) startTime;
                if (temp - backwardTime >= 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int)startTime);
                } else {
                    Toast.makeText(MainActivity.this, "Error! Can't jump backwards!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void playMusic() {
        mediaPlayer.start();

        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekBar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }

        time_text.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime)-
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        seekBar.setProgress((int)startTime);

        Handler handler1 = new Handler();
        handler1.postDelayed(UpdateSongTime, 100);
    }

    //Creating the Runnable
    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            time_text.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)-
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
            );

            seekBar.setProgress((int)startTime);

            Handler handler1 = new Handler();
            handler1.postDelayed(this, 100);
        }
    };
}