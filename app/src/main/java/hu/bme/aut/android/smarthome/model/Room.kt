package hu.bme.aut.android.smarthome.model

//TODO: later add type spinner
data class Room(
    val viewType: Int,
    val id: Int,
    val name: String,
    val type: String,
    val deviceNumber: Int,
   // val devices : MutableList<Device>
)