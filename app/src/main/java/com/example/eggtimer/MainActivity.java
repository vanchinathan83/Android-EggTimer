package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button timerButton;
    CountDownTimer countDownTimer;
    SeekBar timeSeekBar;
    TextView timeText;
    boolean timerStarted = false;
    int totalSeconds;


    public void startTimer(View view){
        if(timerStarted){
            reset();
        }else{
            timerButton = (Button) findViewById(R.id.timerButton);
            timerButton.setText("STOP");
            timeSeekBar.setEnabled(false);
            timerStarted = true;
            totalSeconds = timeSeekBar.getProgress();
            countDownTimer = new CountDownTimer(totalSeconds * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    totalSeconds--;
                    int minute = totalSeconds / 60;
                    int seconds = totalSeconds % 60;
                    timeText.setText(Integer.toString(minute) +":" + Integer.toString(seconds));
                    timeSeekBar.setProgress(totalSeconds);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
                    mediaPlayer.start();
                    reset();
                }
            }.start();
        }
    }

    private void reset() {
        timerButton.setText("START");
        timeSeekBar.setEnabled(true);
        timeSeekBar.setProgress(30);
        timeText.setText("00:30");
        countDownTimer.cancel();
        timerStarted = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeSeekBar = (SeekBar) findViewById(R.id.seekBar);
        timeSeekBar.setMax(600);
        timeSeekBar.setProgress(30);

        timeText = findViewById(R.id.timerTextView);

        timeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int minute = i / 60;
                int seconds = i % 60;
                String secondsString = seconds >= 10? Integer.toString(seconds): "0" + Integer.toString(seconds);
                timeText.setText(Integer.toString(minute) + ":" + secondsString);
                seekBar.setProgress(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}