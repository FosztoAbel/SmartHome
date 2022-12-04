package hu.bme.aut.android.smarthome.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LedControllerAPI {
    @GET("/sendcmd/{device}/{state}")
    fun turnOnLed(
        @Path("device", encoded = true) device: String?,
        @Path("state", encoded = true) state: String?
    ): Call<ResponseBody>

    @GET("/sendcmd/{device}/{state}")
    fun turnOffLed(
        @Path("device", encoded = true) device: String?,
        @Path("state", encoded = true) state: String?
    ): Call<ResponseBody>

    @GET("/sendcmd/{device}/{color}")
    fun changeLedColor(
        @Path("device", encoded = true) device: String?,
        @Path("color", encoded = true) color: String?
    ): Call<ResponseBody>

    @GET("/list")
    fun getAvailableDevices()

    fun scanNetwork(

    )
}