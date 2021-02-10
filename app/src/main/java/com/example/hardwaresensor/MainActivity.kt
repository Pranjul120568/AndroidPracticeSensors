package com.example.hardwaresensor

import android.graphics.Color
import android.graphics.Color.rgb
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.hardwaresensor.databinding.ActivityMainBinding
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding? =null
    lateinit var sensorEventListener: SensorEventListener
    lateinit var sensorEventListener1: SensorEventListener
    lateinit var sensorManager: SensorManager
    lateinit var proxSensor: Sensor
    lateinit var accelSensor: Sensor
    val colors = arrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding!!.root)
        sensorManager=getSystemService<SensorManager>()!!
         proxSensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)
         accelSensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
         sensorEventListener=object :SensorEventListener{
    override fun onSensorChanged(event: SensorEvent?) {

        /*if (event!!.values[0] > 0) {

            binding!!.fLProximity.setBackgroundColor(colors[Random.nextInt(6)])
           }*/
            Log.d("HWSENS","""
                      ax=  onSensorChanged: ${event!!.values[0]}
                      ay=  onSensorChanged: ${event.values[1]}
                      az=  onSensorChanged: ${event.values[2]}
    
                    """.trimIndent())
        val bgColor=Color.rgb(
            accel2color(event.values[0]),
            accel2color(event.values[0]),
            accel2color(event.values[0]))
        binding!!.fLAccelorometer.setBackgroundColor(colors[Random.nextInt(6)])
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
       //nothing
    }
}

        sensorEventListener1=object :SensorEventListener{
            override fun onSensorChanged(event: SensorEvent?) {

                if (event!!.values[0] > 0) {

                    binding!!.fLProximity.setBackgroundColor(colors[Random.nextInt(6)])
                   }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //nothing
            }
        }


        }
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(
                sensorEventListener, accelSensor,10000*1000000
        )
        sensorManager.registerListener(
            sensorEventListener1, proxSensor,SensorManager.SENSOR_DELAY_FASTEST
        )
    }
    override fun onPause() {
        sensorManager.unregisterListener(sensorEventListener)
        super.onPause()

    }
    private fun accel2color(accel:Float)= run { (((accel+12)/24)*255).roundToInt() }


    }
