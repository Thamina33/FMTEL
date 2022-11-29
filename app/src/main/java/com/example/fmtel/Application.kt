package com.example.fmtel

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.WindowManager


class Application : Application() {
    private val ONESIGNAL_APP_ID = "a1aac259-9b5f-4748-ba83-fa8566bade50"
    override fun onCreate() {
        super.onCreate()
        SharedPrefManager.with(this)
//        // OneSignal Initialization
//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)
//        OneSignal.initWithContext(this)
//        OneSignal.setAppId(ONESIGNAL_APP_ID)
//        OneSignal.sendTag("ID", "USER")
//
//        OneSignal.setNotificationOpenedHandler { result ->
//            startActivity(Intent(applicationContext , NotificationList::class.java))
//        }
       var  context = applicationContext
       // setupActivityListener();


    }
    private fun setupActivityListener() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.window.setFlags(
                    WindowManager.LayoutParams.FLAG_SECURE,
                    WindowManager.LayoutParams.FLAG_SECURE
                )
            }

            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}