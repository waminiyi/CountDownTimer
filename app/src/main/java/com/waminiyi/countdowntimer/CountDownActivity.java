package com.waminiyi.countdowntimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CountDownActivity extends AppCompatActivity {
    private CountDownTimer mCountDownTimer;
    private TextView mRemainingTimeTextView, mTotalTimeTextView;
    private Button mStopButton, mPauseButton;
    private ProgressBar mProgressBar;
    private int mSelectedHour, mSelectedMinute, mSelectedSecond;
    private int progress;
    private long mRemainHour, mRemainMinute, mRemainSecond;
    private long mTimeTotalInMillis, mTimeLeftInMillis;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        mStopButton=findViewById(R.id.button_stop);
        mPauseButton=findViewById(R.id.button_start);
        mRemainingTimeTextView=findViewById(R.id.remaining_time);
        mTotalTimeTextView=findViewById(R.id.tv_total_time);
        mProgressBar=findViewById(R.id.progress_bar);


        Bundle bundle=getIntent().getExtras();
        mSelectedHour=bundle.getInt("selectedHour");
        mSelectedMinute=bundle.getInt("selectedMinute");
        mSelectedSecond=bundle.getInt("selectedSecond");

        String remainTimeformated= "Temps total: " + String.format(Locale.getDefault(), "%02d:%02d:%02d",mSelectedHour, mSelectedMinute, mSelectedSecond);
        mTotalTimeTextView.setText(remainTimeformated);
        mProgressBar.setProgress(progress);
        mTimeLeftInMillis= (long) mSelectedHour *3600000 + (long) mSelectedMinute*60000+(long)mSelectedSecond*1000;
        mTimeTotalInMillis=mTimeLeftInMillis;
        //int progressMax=Math.toIntExact(mTimeTotalInMillis);
        int progressMax=(int)mTimeTotalInMillis/1000;
        mProgressBar.setMax(progressMax);

        startCountDown();

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                finish();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPauseButton.getText().toString().equals("Pause")){
                    mCountDownTimer.cancel();
                    mPauseButton.setText("Reprendre");
                }else {
                    startCountDown();
                    mPauseButton.setText("Pause");
                }

            }
        });

    }

    //méthode qui gère le démarrage du minuteur
    private void startCountDown() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                updateProgress(mTimeLeftInMillis);
            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis = 0;
                updateCountDownText();
                updateProgress(mTimeLeftInMillis);
                Toast.makeText(CountDownActivity.this, "Temps écoulé!", Toast.LENGTH_LONG).show();
            }
        }.start();
    }

    // méthode qui gère l'affichage du minuteur
    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600)/60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        //formatage  du minuteur minute:seconde
        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        mRemainingTimeTextView.setText(timeFormatted);

    }

    private void updateProgress(long remainTime){
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
                progress=(int) remainTime/ 1000;
                mProgressBar.setProgress(progress);
        }

    }