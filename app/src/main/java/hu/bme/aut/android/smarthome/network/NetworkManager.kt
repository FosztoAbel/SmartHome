package hu.bme.aut.android.smarthome.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {
    private val retrofit: Retrofit
    private val ledControllerApi: LedControllerAPI

    private const val SERVICE_URL = "ip:port"

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        ledControllerApi = retrofit.create(LedControllerAPI::class.java)
    }

    fun turnOnLed(deviceNetworkName: String){
        return ledControllerApi.turnOnLed(deviceNetworkName, "rgb+toggle+True")
    }

    fun turnOffLed(deviceNetworkName: String){
        return ledControllerApi.turnOffLed(deviceNetworkName, "rgb+toggle+False")
    }

    fun changeLedColor(deviceNetworkName: String, color: String){
        return ledControllerApi.changeLedColor(deviceNetworkName, color)
    }
    fun getAvailableDevices(){
        return ledControllerApi.getAvailableDevices()
    }
    fun scanNetwork(){
        return ledControllerApi.scanNetwork()
    }
}