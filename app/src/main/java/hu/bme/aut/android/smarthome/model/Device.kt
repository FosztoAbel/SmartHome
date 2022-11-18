package hu.bme.aut.android.smarthome.model

data class Device(
    val viewType: Int,
    val id: Int,
    val name: String,
    val state: Int
)