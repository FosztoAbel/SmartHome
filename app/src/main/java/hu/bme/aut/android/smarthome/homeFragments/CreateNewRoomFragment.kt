package hu.bme.aut.android.smarthome.homeFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.MainMenuActivity
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewRoomBinding
import hu.bme.aut.android.smarthome.databinding.FragmentLoginBinding
import hu.bme.aut.android.smarthome.databinding.FragmentProfileMenuBinding
import hu.bme.aut.android.smarthome.model.Room

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateNewRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateNewRoomFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewRoomBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewRoomBinding.inflate(inflater, container, false)
        return binding.root;
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateNewRoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateNewRoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}