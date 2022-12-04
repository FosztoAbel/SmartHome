package hu.bme.aut.android.smarthome.network

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val ledControllerApi: LedControllerAPI

    private const val SERVICE_URL = "http://84.0.243.138:5000"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ledControllerApi = retrofit.create(LedControllerAPI::class.java)
    }

    fun turnOnLed(deviceNetworkName: String): Call<ResponseBody> {
        return ledControllerApi.turnOnLed(deviceNetworkName, "rgb+toggle+True")
    }

    fun turnOffLed(deviceNetworkName: String): Call<ResponseBody>{
        return ledControllerApi.turnOffLed(deviceNetworkName, "rgb+toggle+False")
    }

    fun changeLedColor(deviceNetworkName: String, color: String): Call<ResponseBody>{
        return ledControllerApi.changeLedColor(deviceNetworkName, color)
    }
    fun getAvailableDevices(){
        return ledControllerApi.getAvailableDevices()
    }
    fun scanNetwork(){
        return ledControllerApi.scanNetwork()
    }
}