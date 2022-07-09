package com.example.tripjump.Destination.Search

import android.location.Location
import com.example.tripjump.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RequestAcception @Inject constructor(private val provider: Provider): ITestRequest{

    private var nowLocation: Location? = null

    override fun RequestAccept() = runBlocking() {
        //現在地取得するまで待つ
        launch(Dispatchers.Default) {
            nowLocation = MainActivity.getInstance().JustCallPosition()
//            nowLocation = request.RequireNowPosition()
        }.join()
        val location: Location = nowLocation ?: run{
            throw NullPointerException("location couldn't catch")
        }
        provider.StartSearching(location)
    }
}