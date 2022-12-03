package hu.bme.aut.android.smarthome.network

import retrofit2.http.GET
import retrofit2.http.Query

interface LedControllerAPI {
    @GET()
    fun turnOnLed(
        @Query("device") device: String?,
        @Query("state") state: String?
    )

    fun turnOffLed(
        @Query("device") device: String?,
        @Query("state") state: String?
    )

    fun changeLedColor(

    )

    fun getAvailableDevices(

    )

    fun scanNetwork(

    )
}