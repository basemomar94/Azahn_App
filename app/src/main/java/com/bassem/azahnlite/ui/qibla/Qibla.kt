package com.bassem.azahnlite.ui.qibla

import android.content.Context
import android.graphics.Color
import androidx.fragment.app.Fragment

import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.Sensor.TYPE_MAGNETIC_FIELD
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.SENSOR_DELAY_GAME
import android.os.Bundle
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.widget.ImageView
import com.bassem.azahnlite.R
import kotlinx.android.synthetic.main.fragment_qibla.*
import java.lang.Math.toDegrees

class Qibla () : Fragment(R.layout.fragment_qibla) ,SensorEventListener{
    lateinit var sensorManager: SensorManager
    lateinit var image: ImageView
    lateinit var accelerometer: Sensor
    lateinit var magnetometer: Sensor

    var currentDegree = 0.0f
    var lastAccelerometer = FloatArray(3)
    var lastMagnetometer = FloatArray(3)
    var lastAccelerometerSet = false
    var lastMagnetometerSet = false
    private var angle : Double?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sensorManager = (activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager?)!!

        accelerometer = sensorManager.getDefaultSensor(TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(TYPE_MAGNETIC_FIELD)
        val preference = context!!.getSharedPreferences("Pref", Context.MODE_PRIVATE)
       angle= preference.getString("angle", 0.toString())!!.toDouble()
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, accelerometer)
        sensorManager.unregisterListener(this, magnetometer)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor === accelerometer) {
            lowPass(event!!.values, lastAccelerometer)
            lastAccelerometerSet = true
        } else if (event!!.sensor === magnetometer) {
            lowPass(event!!.values, lastMagnetometer)
            lastMagnetometerSet = true
        }

        if (lastAccelerometerSet && lastMagnetometerSet) {
            val r = FloatArray(9)
            if (SensorManager.getRotationMatrix(r, null, lastAccelerometer, lastMagnetometer)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(r, orientation)
                val degree = (toDegrees(orientation[0].toDouble()) + 360).toFloat() % 360

                val rotateAnimation = RotateAnimation(
                    currentDegree,
                    -degree,
                    RELATIVE_TO_SELF, 0.5f,
                    RELATIVE_TO_SELF, 0.5f)
                rotateAnimation.duration = 1000
                rotateAnimation.fillAfter = true

                compass.startAnimation(rotateAnimation)
                Qibla_angle.rotation=angle!!.toFloat()
                currentDegree = -degree

                if (-currentDegree<= angle!! +1 && -currentDegree>= angle!!-1 ){
                    kabaa.alpha=1F
                    circle.alpha=1F
                    Qibla_angle.alpha=1F
                    circle.setImageResource(R.drawable.circle_green)
                    Qibla_angle.setImageResource(R.drawable.green)

                } else {
                    circle.alpha=.2F
                    Qibla_angle.alpha=.2F
                    circle.setImageResource(R.drawable.circle)
                    Qibla_angle.setImageResource(R.drawable.blue)


                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    fun lowPass(input: FloatArray, output: FloatArray) {
        val alpha = 0.05f

        for (i in input.indices) {
            output[i] = output[i] + alpha * (input[i] - output[i])
        }
    }


}