package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewRoomBinding

class CreateNewRoomFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //TODO: add new items to the firebase firestore
        binding.buttonCreateNewRoom.setOnClickListener {
            val roomName = binding.roomNameInput.text.toString()
            val roomType = binding.roomTypeInput.text.toString()
            val deviceNumber = 0
            val viewType = 1
            val id = 1
            // get last id from database and +1 will be the new id
            //val newRoom = Room(viewType,id,roomName,roomType,deviceNumber)







            findNavController().navigate(R.id.action_createNewRoomFragment_to_swipeMenuFragment)
        }
        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_createNewRoomFragment_to_swipeMenuFragment)
        }

    }
}