package com.example.samplesensorproviderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class TempSensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor mTemperature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_sensor);
        // Temp sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        TextView textView = (TextView) findViewById(R.id.textView4);

        TempSensorAcess tempSensorAcess = new TempSensorAcess(sensorManager, textView);
    }
}