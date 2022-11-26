package hu.bme.aut.android.smarthome.devicesPage.model

data class Device(
    val viewType: Int,
    val id: Int,
    val name: String,
    val state: Boolean
){
    constructor() : this(1,0,
        "", false
    )
}