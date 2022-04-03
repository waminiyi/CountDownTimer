package com.waminiyi.countdowntimer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    private NumberPicker mHourPicker, mMinutePicker, mSecondPicker;
    private Button mLaunchButton;
    private int mSelectedHour, mSelectedMinute, mSelectedSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHourPicker=findViewById(R.id.NP_Hour);
        mMinutePicker=findViewById(R.id.NP_Minutes);
        mSecondPicker=findViewById(R.id.NP_Second);
        mLaunchButton=findViewById(R.id.BT_Launch);
        setPickersValues();

        mHourPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                mSelectedHour=newValue;
            }
        });

        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                mSelectedMinute=newValue;
            }
        });

        mSecondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                mSelectedSecond=newValue;
            }
        });

        mLaunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent countActivityIntent = new Intent(MainActivity.this, CountDownActivity.class);
                //enregistrement des infos dans le bundle
                countActivityIntent.putExtra ("selectedHour", mSelectedHour);
                countActivityIntent.putExtra ("selectedMinute", mSelectedMinute);
                countActivityIntent.putExtra ("selectedSecond", mSelectedSecond);
                startActivity(countActivityIntent);
            }
        });

    }

    //@SuppressLint("DefaultLocale")
    private void setPickersValues(){
        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(23);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mSecondPicker.setMinValue(0);
        mSecondPicker.setMaxValue(59);

        String[] hourPickerVals = new String[24];
        String[] minutePickerVals = new String[60];
        String [] secondPickerVals=new String[60];

        for (int i=0; i<hourPickerVals.length; i++){
            hourPickerVals[i]= String.format("%02d", i);
        }

        for (int i=0; i<minutePickerVals.length; i++){
            minutePickerVals[i]= String.format("%02d", i);
        }

        for (int i=0; i<secondPickerVals.length; i++){
            secondPickerVals[i]= String.format("%02d", i);
        }

        mHourPicker.setValue(0);
        mMinutePicker.setValue(1);
        mSecondPicker.setValue(0);

        mHourPicker.setDisplayedValues(hourPickerVals);
        mMinutePicker.setDisplayedValues(minutePickerVals);
        mSecondPicker.setDisplayedValues(secondPickerVals);

        mSelectedHour=mHourPicker.getValue();
        mSelectedMinute=mMinutePicker.getValue();
        mSelectedSecond=mSecondPicker.getValue();
    }
}