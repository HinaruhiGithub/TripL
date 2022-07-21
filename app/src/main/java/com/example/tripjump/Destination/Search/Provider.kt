package com.example.tripjump.Destination.Search

import android.location.Location
import android.util.Log
import com.example.tripjump.Category.IGetURLStringCategory
import com.example.tripjump.MainActivity
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.runBlocking
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.lang.Math.floor
import javax.inject.Inject
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.log10

class Provider @Inject constructor(private val updater: IProvidePlace) {
    private val firstPlace: LatLng = LatLng(35.toDouble(), 135.toDouble())
    private val TAG = Provider::class.simpleName.toString()
    var showListener: (() -> Unit)? = null
    var zoomListner: ((Float) -> Unit)? = null

    var distance: Float = 800.0f


    fun StartSearching(origin: Location, category: IGetURLStringCategory) {
        if(showListener == null) {
            Log.e(TAG, "ねぇわ")
            return
        }

        var score = mutableSetOf<Pair<Float, JSONObject>>()

        // Map Searching
        val client = OkHttpClient().newBuilder().build();

        category.GetString().forEach { category ->
            val request = Request.Builder()
                .url(GetGoogleMapURL(origin, category))
                .build();

            Log.d(TAG, GetGoogleMapURL(origin, category))
            client.newCall(request).enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    val responseBody = response.body?.string().orEmpty()
                    Log.d(TAG, responseBody.toString())
                    try{
                        val jsonObject = JSONObject(responseBody)
                        val jsonArray = jsonObject.getJSONArray("results")
                        for (i in 0 until jsonArray.length()) {
                            val jsonData = jsonArray.getJSONObject(i)
                            val place = MakePlaceFromJson(jsonArray.getJSONObject(i))
//                            Log.d(TAG, "${place.position}")
/*                            val geo = jsonArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("viewport")
                            val yoko = abs(geo.getJSONObject("northeast").getDouble("lat") - geo.getJSONObject("southwest").getDouble("lat"))
                            val tate = abs(geo.getJSONObject("northeast").getDouble("lng") - geo.getJSONObject("southwest").getDouble("lng"))
                            Log.d(TAG, "${place.title} : $yoko x $tate = ${yoko * tate}")*/
                            val pair: Pair<Float, JSONObject> = Pair(CalculateScore(origin, jsonData), jsonData)
                            MainActivity.getInstance().runOnUiThread {
//                                score.add(pair)
                                zoomListner?.invoke(CalculateZoomLevel(800.0f))
                                updater.NewPlace(place)
                            }
                        }
                    } catch (e: JSONException) {
                        throw JSONException(e.toString())
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e(TAG, "cannot catch response")
                }
            })

        }

//        showListener?.invoke()
    }


    private fun CalculateScore(origin: Location, jsonData: JSONObject): Float {
        val locationObj = jsonData.getJSONObject("geometry").getJSONObject("location")
        val latLng = LatLng(locationObj.getDouble("lat"), locationObj.getDouble("lng"))
        val yoko = latLng.latitude - locationObj.getDouble("lat")
        val tate = latLng.longitude - locationObj.getDouble("lng")
        val distance = Math.sqrt(yoko * yoko + tate * tate)
        return -distance.toFloat()
    }


    private fun CalculateZoomLevel(distance: Float): Float {
        val loged = log10(distance)
        val largeNum = floor(loged)
        val hasuu = loged - largeNum
        val hasuuNum = 3.0f * hasuu
        return 23.0f - 3.0f * largeNum - hasuuNum
    }



    private fun GetGoogleMapURL(origin: Location, category: String): String {
        var mapURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
        // add nowLocation
        mapURL += "location=" + origin.latitude.toString() + "%2C" + origin.longitude.toString()
        // add radius
        mapURL += "&radius=" + 800.toString()
        // add keyword or type
        mapURL += "&keyword=" + category
        // add language(暫定、日本語のみサポートする)
        mapURL += "&language=ja"
        // add opennow
//        mapURL += "&opennow"
        // add APIKEY
        mapURL += "&key=AIzaSyCoE5ODNCjPVNag26NRJYeV9LsiU3WsZ3Y"

        return mapURL
    }

    /**
     * JSONから、ピンが立つような位置情報に変換する。
     */
    private fun MakePlaceFromJson(jsonData: JSONObject): MarkerOptions {
        val name = jsonData.getString("name")
        val info = jsonData.getJSONObject("plus_code").getString("compound_code")
        val locationObj = jsonData.getJSONObject("geometry").getJSONObject("location")
        val latLng = LatLng(locationObj.getDouble("lat"), locationObj.getDouble("lng"))
        val newPlace = MarkerOptions()
            .title(name)
            .snippet(info)
            .position(latLng)
        return newPlace
    }




    // --------------------- Test Object -----------------------

    private fun TestProvide() {
        val osakaStation = MarkerOptions()
            .title("大阪駅")
            .snippet("JRやで")
            .position(LatLng(34.702485, 135.495951))
        updater.NewPlace(osakaStation)
    }

    private fun TestSearching() = runBlocking() {
        val client = OkHttpClient().newBuilder().build();
        val mediaType = "text/plain".toMediaTypeOrNull();
        val body = "".toRequestBody(mediaType);
        val request = Request.Builder()
            .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=38.8976763%2C-77.0365298&radius=50000&keyword=mcdonalds&key=AIzaSyCoE5ODNCjPVNag26NRJYeV9LsiU3WsZ3Y")
//            .url("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=Museum%20of%20Contemporary%20Art%20Australia&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Copening_hours%2Cgeometry&key=AIzaSyCoE5ODNCjPVNag26NRJYeV9LsiU3WsZ3Y")
//            .method("GET", body)
//            .get()
            .build();

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string().orEmpty()
                Log.d(TAG, responseBody.toString())
                try{
                    val jsonObject = JSONObject(responseBody)
                    val jsonArray = jsonObject.getJSONArray("results")
                    for (i in 0 until jsonArray.length()) {
                        val jsonData = jsonArray.getJSONObject(i)
                        val name = jsonData.getString("name")
                        val info = jsonData.getJSONObject("plus_code").getString("compound_code")
                        val locationObj = jsonData.getJSONObject("geometry").getJSONObject("location")
                        val latLng = LatLng(locationObj.getDouble("lat"), locationObj.getDouble("lng"))
                        Log.d("Check", "$name : $info($latLng)")
                        val mcdStation = MarkerOptions()
                            .title(name)
                            .snippet(info)
                            .position(latLng)
                        MainActivity.getInstance().runOnUiThread {
                            updater.NewPlace(mcdStation)
                        }
                    }
                } catch (e: JSONException) {
                    Log.d(TAG,"sorry there are no mcDonald")
                    throw JSONException(e.toString())
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "cannot catch response")
            }
        })

/*        var response: Response? = null
        launch(Dispatchers.Default) {
            response = client.newCall(request).execute();
        }.join()

        Log.d(TAG, response?.message ?: "omg null")*/
    }
}