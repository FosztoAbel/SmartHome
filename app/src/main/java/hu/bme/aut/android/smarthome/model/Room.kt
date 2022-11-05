package hu.bme.aut.android.smarthome.model

data class Room(
    val viewType: Int,
    val id: Int,
    val name: String,
    val type: Type,
    val deviceNumber: Int
) {
    enum class Type {
        bedroom, bathroom, study, kitchen, livingroom, garage, diningroom
    }
}