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
    @Inject lateinit var preferencesService: PreferencesService
    @Inject lateinit var batteryNotificationUpdater: BatteryNotificationUpdater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lowerLimit = preferencesService.lowerStateOfChargeLimit
        val upperLimit = preferencesService.upperStateOfChargeLimit

        updateLowerLimitLabel(lowerLimit)
        updateUpperLimitLabel(upperLimit)

        findViewById<SeekBar>(R.id.lowerLimitBar).apply {
            progress = lowerLimit
            setOnSeekBarChangeListener(LowerLimitBarListener())
        }

        findViewById<SeekBar>(R.id.upperLimitBar).apply {
            progress = upperLimit
            setOnSeekBarChangeListener(UpperLimitBarListener())
        }

        batteryNotificationUpdater.update()
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

        override fun onStopTrackingTouch(p0: SeekBar?) {
            if (p0 != null) {
                preferencesService.lowerStateOfChargeLimit = p0.progress
                batteryNotificationUpdater.update()
            }
        }
    }

    inner class UpperLimitBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            updateUpperLimitLabel(p1)
        }

        override fun onStartTrackingTouch(p0: SeekBar?) { }

        override fun onStopTrackingTouch(p0: SeekBar?) {
            if (p0 != null) {
                preferencesService.upperStateOfChargeLimit = p0.progress
                batteryNotificationUpdater.update()
            }
        }
    }
}