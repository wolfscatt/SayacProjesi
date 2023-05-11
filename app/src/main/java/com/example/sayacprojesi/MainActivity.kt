package com.example.sayacprojesi

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.view.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private var timerStarted = false
    private var time = 0.0

    private lateinit var serviceIntent : Intent
    private lateinit var flagAdapter : FlagAdapter
    private var flagList : ArrayList<FlagModel> = arrayListOf()

    private var id : Int = 0
    private var flagText : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flagAdapter = FlagAdapter(flagList)
        flag_recycler_view.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        flag_recycler_view.adapter = flagAdapter

        btnStart.setOnClickListener {
            startFlagTimer()
        }
        btnStop.setOnClickListener {
            stop()
        }
        btnReset.setOnClickListener {
            reset()
        }

        serviceIntent = Intent(applicationContext,TimerService::class.java)
        registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))

    }
    fun startFlagTimer() {
        if (timerStarted)
            flag()
        else
            start()
    }

    private fun start() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        startService(serviceIntent)
        btnStart.text = "Flag"
        timerStarted = true
    }

    private fun flag() {
        id = id + 1
        stop()
        showCustomDialog()
    }

    private fun showCustomDialog() {
        val dialogBinding = LayoutInflater.from(this).inflate(R.layout.custom_dialog,null)
        val alertDialogBuilder = AlertDialog.Builder(this).setView(dialogBinding).setCancelable(false)
        val dialog = alertDialogBuilder.show()

        dialogBinding.btn_submit.setOnClickListener {
            dialog.dismiss()
            flagText = dialogBinding.flag_text.text.toString()

            updateRecyclerView()
            start()

        }
    }

    private fun updateRecyclerView() {
        var flagModel = FlagModel(id,flagText,getTimeStringFromDouble(time))
        flagList.add(flagModel)
        flagAdapter.notifyDataSetChanged()
    }

    fun stop() {
        stopService(serviceIntent)
        btnStart.text = "Start"
        timerStarted = false
    }
    fun reset(){
        id = 0
        flagList.clear()
        flagAdapter.notifyDataSetChanged()
        stop()
        time = 0.0
        textView.text = getTimeStringFromDouble(time)
    }

    private val updateTime : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerService.TIME_EXTRA,0.0)
            textView.text = getTimeStringFromDouble(time)
        }

    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(hours, minutes, seconds)

    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String  = String.format("%02d:%02d:%02d", hour, min, sec)


}