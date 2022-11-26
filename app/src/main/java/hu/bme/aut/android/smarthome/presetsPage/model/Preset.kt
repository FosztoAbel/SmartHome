package hu.bme.aut.android.smarthome.presetsPage.model

//TODO: time what type it is.

data class Preset(
    val viewType: Int,
    val id: Int,
    val name: String,
    val time: Int,
    val state: Int
)