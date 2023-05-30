package com.example.myserviceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myserviceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var thread: Thread? = null
    var num = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            btnStartThread.setOnClickListener {
                if (thread == null) {
                    thread = object : Thread("MyThread") {
                        override fun run() {
                            try {
                                for (i in 0..10) {
                                    Log.i("MyThread $num", "Count : $i")
                                    Thread.sleep(1000)
                                }
                            }catch (e:InterruptedException){
                                Thread.currentThread().interrupt()
                            }

                            thread = null
                        }
                    }
                    thread!!.start()
                    num++
                }

            }
            btnStopThread.setOnClickListener {
                if (thread != null) {
                    thread!!.interrupt()
                }
                thread = null
            }
            btnStartService.setOnClickListener {
                val intent= Intent(this@MainActivity, MyService::class.java)
                startService(intent)
            }
            btnStopService.setOnClickListener {
                val intent= Intent(this@MainActivity, MyService::class.java)
                stopService(intent)

            }
        }
    }
}