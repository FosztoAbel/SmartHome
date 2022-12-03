package hu.bme.aut.android.smarthome.network

import retrofit2.http.GET
import retrofit2.http.Query

interface LedControllerAPI {
    @GET("/sendcmd")
    fun turnOnLed(
        @Query("device") device: String?,
        @Query("state") state: String?
    )

    @GET("/sendcmd")
    fun turnOffLed(
        @Query("device") device: String?,
        @Query("state") state: String?
    )

    @GET("/sendcmd")
    fun changeLedColor(
        @Query("device") device: String?,
        @Query("color") color: String?
    )

    @GET("/list")
    fun getAvailableDevices()

    fun scanNetwork(

    )
}