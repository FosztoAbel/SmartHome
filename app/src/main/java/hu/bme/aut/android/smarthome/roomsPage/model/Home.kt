package hu.bme.aut.android.smarthome.roomsPage.model

import com.google.firebase.auth.FirebaseUser

data class Home(
    val id: Int,
    val password: String,
    val name: String,
    //val roomNumber: Int,
    val joinedUsers: MutableList<String?>?
){
    constructor() : this(1,"",
        "", mutableListOf()
    )
}