package hu.bme.aut.android.smarthome.roomsPage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewRoomBinding
import hu.bme.aut.android.smarthome.roomsPage.model.Home
import hu.bme.aut.android.smarthome.roomsPage.model.Room
import kotlin.random.Random

class CreateNewRoomFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewRoomBinding
    private val firestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        binding.buttonCreateNewRoom.setOnClickListener {
            val roomName = binding.roomNameInput.text.toString()
            val roomType = binding.roomTypeInput.text.toString()
            val deviceNumber = 0
            val viewType = 1
            var id = Random.nextInt()
            if(id < 0) id *= (-1)
            val newRoom = Room(viewType,id,roomName,roomType,deviceNumber)

            if(checkFields())
            {
                firestore.collection("homes")
                    .get()
                    .addOnSuccessListener { result ->
                        for(document in result){
                            val currentDocument = document.toObject<Home>()
                            for(iterator in currentDocument.joinedUsers!!){
                                if(iterator.equals(user?.uid)){
                                    val dbRef = firestore.collection("homes").document(currentDocument.id.toString()).collection("rooms").document(newRoom.id.toString())
                                    dbRef.set(newRoom)
                                        .addOnSuccessListener {
                                            findNavController().navigate(R.id.action_createNewRoomFragment_to_swipeMenuFragment)
                                        }
                                }
                            }
                        }
                    }
            }
            else{
                Snackbar.make(binding.root,"Please fill out all the fields!", Snackbar.LENGTH_LONG).show()
            }
        }

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_createNewRoomFragment_to_swipeMenuFragment)
        }

    }
    private fun checkFields(): Boolean{
        return !(binding.roomNameInput.text.isEmpty() or binding.roomTypeInput.text.isEmpty())
    }
}