package com.example.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_PROXIMITY
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    private fun toast(text: String) {
        Toast.makeText(
            this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onSensorChanged (e: SensorEvent?) {
       if (e?.sensor == ps) {
           var distance = e?.values?.get(0).toString()
           tvProximity.text = distance
           tvAccuracy.text = e?.accuracy.toString()

           when(e?.values?.get(0)?.toInt()) {
                in 0..3 -> toast(getString(R.string.near, distance ))
                else -> toast(getString(R.string.far, distance ))
           }
       }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
       Log.d("accuracy", accuracy.toString())
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