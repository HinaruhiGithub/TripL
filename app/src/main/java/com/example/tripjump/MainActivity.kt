package com.example.tripjump

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.tripjump.Destination.BaseMap.IShowBaseMap
import com.example.tripjump.Destination.PlaceCollection.ISetPlace
import com.example.tripjump.Destination.WaitShow.IShowWait
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.HandlerCompat.postDelayed
import com.example.tripjump.Destination.BaseMap.Presenter
import com.example.tripjump.Destination.PlaceCollection.Destination
import com.example.tripjump.Destination.Search.IRequireNowPosition
import com.example.tripjump.Destination.Search.ITestRequest
import com.example.tripjump.Destination.Search.Provider
import com.example.tripjump.Destination.Search.RequestAcception
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.Lazy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.security.AccessController.getContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Unit.toString
import kotlin.coroutines.EmptyCoroutineContext.toString
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Singleton
class MainActivity @Inject constructor(): AppCompatActivity(),
    IShowBaseMap,
    ISetPlace,
    IShowWait,
    IRequireNowPosition,
    OnMapReadyCallback {

    private val TAG: String = MainActivity::class.simpleName.toString()
    private val PERMISSIONS: Array<String> = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val REQ_PERMISSIONS: Int = 1234

    private var nowLocation: Location? = null
    private lateinit var context: Context
    private lateinit var infoView: TextView
    private lateinit var map: GoogleMap

    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var request: LocationRequest
    private lateinit var locationCallback: LocationCallback


//    @Inject lateinit var requestAcception: ITestRequest
    @Inject lateinit var entryPoints: EntryPoints
    @Inject lateinit var requestAcception: RequestAcception

     companion object{
        private lateinit var instance: MainActivity

        @JvmStatic
        public fun getInstance(): MainActivity {
            return instance
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate")
        setContentView(R.layout.activity_main)

        infoView = findViewById(R.id.info_view)
        (applicationContext as MyApplication).appComponent.inject(this)
        (applicationContext as MyApplication).firstActivity = this

        ShowBaseMap()
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        request = LocationRequest.create()
        request.interval = 10000L
        request.fastestInterval = 5000L
        request.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                Log.d(TAG, "onLocationResult")
                val location: Location = locationResult.lastLocation
                val ll = LatLng(location.getLatitude(), location.getLongitude())
                nowLocation = location
                infoView.text = getString(R.string.latlng_format, ll.latitude, ll.longitude)
                if (map == null) {
                    Log.e(TAG, "onLocationResult: map == null")
                    return
                }
                map.animateCamera(CameraUpdateFactory.newLatLng(ll))
//                stopLocationUpdate()
            }
        }
        startLocationUpdate(true)
        instance = this
//        context = this
        Log.d(TAG, "うえーい")
        Log.d(TAG, ContextCompat.checkSelfPermission(this, "AAAA").toString())

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
        //テストコード
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed( {
            requestAcception.RequestAccept()
        }, 20000L)

        Log.d(TAG, "Finish onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        startLocationUpdate(true)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        stopLocationUpdate()
    }

    override fun onStop() {
        Log.d(TAG, "onStop")
        super.onStop()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        Log.d(TAG, "onMapReady")
        val tokyo: LatLng = LatLng((35).toDouble(), (135).toDouble())
        map.moveCamera(CameraUpdateFactory.newLatLng(tokyo))
        this.map = map

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            this.map.isMyLocationEnabled = true
            return
        }

/*        with(map.uiSettings) {
            isMyLocationButtonEnabled = true
        }*/
    }

    fun AddNewPeg(destination: Destination) {
        map.addMarker(destination.markerOptions)
    }

    override fun ShowBaseMap() {
        val fragment =
            supportFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment?
        if (fragment != null) {
            Log.d(TAG, "onCreate: getMapAsync")
            fragment.getMapAsync(this)
        } else {
            Log.e(TAG, "no fragment")
        }
    }

    override suspend fun RequireNowPosition(): Location {
        return suspendCoroutine { continuation ->
/*            locationCallback = object: LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    val location: Location = locationResult.lastLocation
                    Log.d(TAG, "やっほー")
                    stopLocationUpdate()
                    continuation.resume(location)
                }
            }
            startLocationUpdate(true)*/
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                locationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null){
                        Log.d(TAG, "位置情報取得")
                        continuation.resume(location)
                    }
                }
            }

            return@suspendCoroutine
        }
    }

    override fun JustCallPosition(): Location {
        (applicationContext as MyApplication).IsSameActivity(this)
        return nowLocation ?: run {
//            Log.e(TAG, "あ")
            throw NullPointerException("there are no location")
        }
//        startLocationUpdate(true)
    }

    /**
     * ロケーションの常時更新を開始する。
     */
    @SuppressLint("MissingPermission")
    private fun startLocationUpdate(reqPermission: Boolean){
//        this.context = this
        Log.d(TAG, "startLocationUpdate")
        for (permission: String in PERMISSIONS) {
            Log.d(TAG, "permission: $permission")
            Log.d(TAG, ContextCompat.checkSelfPermission(this, permission).toString())
            Log.d(TAG, PackageManager.PERMISSION_GRANTED.toString())
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                if (reqPermission) {
                    Log.d(TAG, "requested")
                    ActivityCompat.requestPermissions(this, PERMISSIONS, REQ_PERMISSIONS)

                } else {
                    val text: String = getString(R.string.toast_requires_permission_format, permission)
                    Log.e(TAG, "ダメぽ")
                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
                    return
                }
                Log.d(TAG, "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
                break
            }
        }
        locationClient.lastLocation.addOnSuccessListener { location: Location? ->
            Log.d(TAG,"BBBBBBBBBBBBBBBBBBBBBBBBB")
            if(location != null){
                Log.d(TAG, "Get now Location")
                nowLocation = location
                Log.d(TAG, location.latitude.toString())
            } else {
                Log.e(TAG, "cannot get location")
            }
        }
    }


    /**
     * ちゃんとロケーションのリクエスト返ってくる？
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQ_PERMISSIONS){
            startLocationUpdate(false)
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    /**
     * ロケーションの常時更新をストップする。
     */
    private fun stopLocationUpdate() {
        Log.d(TAG, "stopLocationUpdate")
        locationClient.removeLocationUpdates(locationCallback)
    }


}