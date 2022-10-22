package com.example.sayacprojesi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var numara = 0
    var runnable : Runnable = Runnable {  }
    var handler = Handler(Looper.myLooper()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Abstract Class
/*
        object : CountDownTimer(30000,1000)
        {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "Kalan: ${millisUntilFinished/1000}"
            }

            override fun onFinish() {
                textView.text = "Kalan: 0"

            }

        }.start()

 */
    }
    fun baslat(view: View)
    {
        numara = 0
        runnable = object : Runnable
        {
            override fun run()
            {
                numara = numara + 1
                textView.text = "Sayaç: ${numara}"
                handler.postDelayed(runnable,1000)
            }

        }
        handler.post(runnable)

    }
    fun durdur(view:View)
    {
        handler.removeCallbacks(runnable)
        numara = 0
        textView.text = "Sayaç: 0"
    }
}