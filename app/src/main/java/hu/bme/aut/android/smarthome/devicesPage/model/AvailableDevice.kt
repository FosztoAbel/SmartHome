package hu.bme.aut.android.smarthome.devicesPage.model

data class AvailableDevice (
    val viewType: Int,
    val id: Int,
    val name: String,
    val state: Boolean
    )
