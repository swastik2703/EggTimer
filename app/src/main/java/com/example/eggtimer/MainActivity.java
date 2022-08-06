package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    CountDownTimer countDown;
    Button goButton;
    public void resetTimer(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDown.cancel();
        goButton.setText("Go");
        counterIsActive = false;
    }

    public void clickButton(View view){
        if(counterIsActive){
            resetTimer();
        }
        else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);
            goButton.setText("STOP");


            countDown = new CountDownTimer((timerSeekBar.getProgress() * 1000) + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mpSound = MediaPlayer.create(getApplicationContext(), R.raw.timer);
                    mpSound.start();
                    resetTimer();
                }
            }.start();
        }
    }

    public void updateTimer(int progress){
        int minute = progress/60;
        int seconds = progress - (minute*60);
        //condition agar 0 hoti hai toh sirf 0 display hoga 00 nhi
        //00 display karane ke liye

        String secondString = Integer.toString(seconds);
        if(seconds <= 9){
            secondString = "0" + secondString;
        }
        timerTextView.setText(Integer.toString(minute)+ ":"+ secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //seek bar pe kaam kro
        timerSeekBar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.goButton);

        //set max limit of seek bar
        //since 1 min 60 sec
        //therefore 30 min =
        timerSeekBar.setMax(600);
        //set progress to 30 secs
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //calculate minute and second
                updateTimer(progress);
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