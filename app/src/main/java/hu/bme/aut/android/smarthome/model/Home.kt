package hu.bme.aut.android.smarthome.model

import com.google.firebase.firestore.auth.User

data class Home(
    val id: Int,
    val password: String,
    val name: String,
    //val roomNumber: Int,
    val rooms: MutableList<Room>,
    val joinedUsers: MutableList<User>
)