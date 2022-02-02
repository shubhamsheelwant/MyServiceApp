package com.example.myserviceapp

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import kotlin.random.Random

class MyService : JobService() {
    private val TAG = "MyService"
    val MAX = 100
    val MIN = 0

    var isStop: Boolean = false
    var randomNumber: Int = 0

    lateinit var jobParameters: JobParameters

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onStartJob(p0: JobParameters?): Boolean {
        Log.i(TAG, "onStartJob:")
        doInBackground()
        jobParameters = p0!!
        return true
    }

    private fun doInBackground() {
      Thread(Runnable {
          isStop = true
          startRandomNumberGenerator()
      }).start()
    }

    private fun startRandomNumberGenerator() {
        var count = 0
        while (count < 5) {
            Thread.sleep(1000)
            randomNumber = Random.nextInt(MAX) + MIN
            Log.i(TAG, "Thread id is: ${Thread.currentThread().id} startRandomNumberGenerator: $randomNumber")
            count++;
        }

        this.jobFinished(jobParameters, true)

    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy:")
        super.onDestroy()
        isStop = false

    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }
}