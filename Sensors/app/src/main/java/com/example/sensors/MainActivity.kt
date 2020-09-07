package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_PROXIMITY
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sm: SensorManager
    private var ps: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        ps = sm.getDefaultSensor(TYPE_PROXIMITY)
    }

    override fun onSensorChanged (e: SensorEvent?) {
       if (e?.sensor == ps) {
           tvProximity.text = e?.values?.get(0).toString()
           tvAccuracy.text = e?.accuracy.toString()
       }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        sensor.isWakeUpSensor
        tvProximity.text = accuracy.toString()
    }

    override fun onResume() {
        super.onResume()
        ps?.also { proximity ->
            sm.registerListener(this, proximity, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onStop() {
        super.onStop()
        sm.unregisterListener(this)
    }
}