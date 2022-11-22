package hu.bme.aut.android.smarthome.model

data class AvailableDevice (
    val viewType: Int,
    val id: Int,
    val name: String,
    val state: Boolean
    )
