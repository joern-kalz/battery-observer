package com.gmail.joern.kalz.batteryobserver

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var batteryCheckService: BatteryCheckService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<SeekBar>(R.id.lowerLimitBar)
            .setOnSeekBarChangeListener(LowerLimitBarListener())

        batteryCheckService.activate()
    }

    private fun updateLowerLimitLabel(lowerLimit: Int) {
        findViewById<TextView>(R.id.lowerLimitLabel).text =
            getString(R.string.notify_when_battery_below, lowerLimit)
    }

    private fun updateUpperLimitLabel(upperLimit: Int) {
        findViewById<TextView>(R.id.upperLimitLabel).text =
            getString(R.string.notify_when_battery_above, upperLimit)
    }

    inner class LowerLimitBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            updateLowerLimitLabel(p1)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) { }

        override fun onStopTrackingTouch(p0: SeekBar?) { }
    }

    inner class UpperLimitBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            updateUpperLimitLabel(p1)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) { }

        override fun onStopTrackingTouch(p0: SeekBar?) { }
    }
}