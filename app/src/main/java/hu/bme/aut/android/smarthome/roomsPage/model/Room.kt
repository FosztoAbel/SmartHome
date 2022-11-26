package hu.bme.aut.android.smarthome.roomsPage.model

//TODO: later add type spinner
data class Room(
    val viewType: Int,
    val id: Int,
    val name: String,
    val type: String,
    var deviceNumber: Int,
){
    constructor() : this(1,1,
        "", "default", 0
    )
}