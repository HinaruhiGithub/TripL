package com.example.tripjump

import android.app.Activity
import android.app.Application
import android.os.StrictMode
import android.util.Log
import com.example.tripjump.DIModule.DaggerApplicationComponent

class MyApplication: Application() {
    val TAG = MyApplication::class.simpleName
    val appComponent = DaggerApplicationComponent.create()
    lateinit var firstActivity: MainActivity

    companion object{
        private lateinit var instance: MyApplication

        @JvmStatic
        public fun getInstance(): MyApplication {
            return instance
        }
    }


    public fun IsSameActivity(activity: MainActivity){
        if(activity == firstActivity) {
            Log.d(TAG, "Same!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        } else {
            Log.e(TAG, "kusoge----ga-----!!!!!!!!!!!!!!!")
        }
    }

    override fun onCreate() {
        super.onCreate()
//        if(BuildConfig.DEBUG) StrictMode.enableDefaults()
        instance = this
    }


}