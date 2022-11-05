package hu.bme.aut.android.smarthome.homeFragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.RoomRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentHomeMenuBinding
import hu.bme.aut.android.smarthome.model.Room
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeMenuFragment : Fragment(), RoomRecyclerViewAdapter.RoomItemClickListener {

    private lateinit var binding : FragmentHomeMenuBinding
    private lateinit var roomRecyclerViewAdapter: RoomRecyclerViewAdapter
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMenuBinding.inflate(inflater, container, false)
        return binding.root;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //current date textView
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formatted = currentDate.format(formatter)
        binding.currentDate.text = formatted.toString()

        binding.profileButton.setOnClickListener{
            findNavController().navigate(R.id.action_swipeMenuFragment_to_profileFragment)
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val demoData = mutableListOf(
            Room(1,1, "Living room", Room.Type.livingroom, 4),
            Room(1,2, "Bathroom", Room.Type.bathroom, 3),
            Room(1,3, "Kitchen", Room.Type.kitchen, 6),
            Room(2,3, "Kitchen", Room.Type.kitchen, 6)
        )
        roomRecyclerViewAdapter = RoomRecyclerViewAdapter()
        roomRecyclerViewAdapter.itemClickListener = this
        roomRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.rooms).adapter =
            roomRecyclerViewAdapter

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(room: Room) {
        if(room.viewType == 1) {
           //val action  = SwipeMenuFragmentDirections.actionSwipeMenuFragmentToRoomDevicesScreenFragment()
            findNavController().navigate(R.id.action_swipeMenuFragment_to_roomDevicesScreenFragment)
        }
        if(room.viewType == 2){
           findNavController().navigate(R.id.action_swipeMenuFragment_to_createNewRoomFragment)
        }


    }
    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }

}