package com.example.myserviceapp

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.myserviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var jobSchedular: JobScheduler
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        jobSchedular = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        binding.btnStart.setOnClickListener {
            startJob()
        }

        binding.btnStop.setOnClickListener {
            stopJob()
        }
    }

    private fun stopJob() {
        jobSchedular.cancel(101)
    }

    private fun startJob() {
        val componentName = ComponentName(this, MyService::class.java)
        val jobInfo = JobInfo.Builder(101, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
            .setPeriodic(15 * 60 * 1000)
            .setRequiresCharging(false)
            .setPersisted(true)
            .build()

        if (jobSchedular.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS) {
            Log.i(TAG, "Thread id is: ${Thread.currentThread().id} job started successfully")
        } else {
            Log.i(TAG, "job not started")
        }
    }

}