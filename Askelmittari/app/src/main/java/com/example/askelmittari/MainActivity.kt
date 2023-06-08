package com.example.askelmittari

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView



import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.*
import android.os.Build

import android.widget.TextView
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.getSystemService
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var running: Boolean = false
    private val ACTIVITY_RECOGNIYION_REQUEST_CODE: Int = 100
    private var sensorManager: SensorManager? = null
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isPermissionGranted()){
            requestPermission()
        }

        resetSteps()
        bottomNavigationView()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    private fun resetSteps() {
        var stepstaken = findViewById<TextView>(R.id.tv_steps)

        stepstaken.setOnClickListener{
            Toast.makeText(this, "Long tap to reset steps", Toast.LENGTH_LONG).show()
        }
        stepstaken.setOnLongClickListener{
        previousTotalSteps = totalSteps
            stepstaken.text = 0.toString()

            true
        }}




    override fun onSensorChanged(event: SensorEvent?) {



        var steps = findViewById<TextView>(R.id.tv_steps)
        var cirbar = findViewById<CircularProgressBar>(R.id.circularProgressBar)
        if (running) {
            totalSteps = event!!.values[0]
            val currentsteps = totalSteps.toInt() - previousTotalSteps.toInt()
            steps.text = currentsteps.toString()



            cirbar.apply {
                setProgressWithAnimation(currentsteps.toFloat())
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not needed for step counter sensor
    }
    override fun onResume() {
        super.onResume()


        running = true
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val mSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)


        when{
            //mSensor != null -> {
               // sensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI)
              //  Toast.makeText(this, "significant motion sensor", Toast.LENGTH_LONG).show()
          //  }
            sensorManager != null -> {
                sensorManager.registerListener(this,countSensor, SensorManager.SENSOR_DELAY_UI )
                Toast.makeText(this, "Counter", Toast.LENGTH_LONG).show()
            }
            //detectorSensor != null -> {
                //sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_UI)
                //Toast.makeText(this, "Detector", Toast.LENGTH_LONG).show()

            //}
            //accelerometer != null -> {
                //sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
               // Toast.makeText(this, "Accelerometer", Toast.LENGTH_LONG).show()



       else -> {
            Toast.makeText(this, "Your device is not compatible", Toast.LENGTH_LONG).show()
        }
            // Step counter sensor not available
            // Handle the error or display a message to the user
        }
    }


    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }
    private fun requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                ACTIVITY_RECOGNIYION_REQUEST_CODE)

        }

    }
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED

    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when(requestCode){
                ACTIVITY_RECOGNIYION_REQUEST_CODE -> {
                    if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                }


            }
        }





    }
    private fun bottomNavigationView (){

        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.home

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard -> {
                    startActivity(Intent(applicationContext, Dashboard::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.home -> return@OnNavigationItemSelectedListener true
                R.id.settings -> {
                    startActivity(Intent(applicationContext, Settings::class.java))
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })
    }
}









