package com.gmail.joern.kalz.batteryobserver

import android.os.Build
import javax.inject.Inject

class BuildVersionProvider @Inject constructor() {
    fun isBuildVersionBefore26(): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.O
    }
}