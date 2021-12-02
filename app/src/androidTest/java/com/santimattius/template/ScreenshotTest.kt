package com.santimattius.template

import android.app.Activity
import com.facebook.testing.screenshot.Screenshot

interface ScreenshotTest {
    fun compareScreenshot(activity: Activity, name: String) {
        Screenshot.snapActivity(activity).setName(name).record()
    }
}