package com.example.samplesensorproviderapp;

import static com.example.samplesensorproviderapp.MainActivity.brokerURI;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;

import java.util.UUID;

public class TempSensorAcess implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor mTemperature;
    private TextView sensor_field;

    public TempSensorAcess(SensorManager sm, TextView tv) {
        sensorManager = sm;
        sensor_field = tv;
        mTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(this, mTemperature, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float temp = event.values[0];
        sensor_field.setText(String.valueOf(temp));
        publishMessage(String.valueOf(temp));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void publishMessage(String value) {
        Mqtt5BlockingClient client = Mqtt5Client.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(brokerURI)
                .buildBlocking();

        client.connect();
        client.publishWith()
                .topic("temp")
                .qos(MqttQos.AT_LEAST_ONCE)
                .payload(value.getBytes())
                .send();
        client.disconnect();
    }
}
