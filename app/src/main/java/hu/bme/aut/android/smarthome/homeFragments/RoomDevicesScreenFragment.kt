package hu.bme.aut.android.smarthome.homeFragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.RoomDevicesRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentRoomDevicesScreenBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog
import hu.bme.aut.android.smarthome.model.Device

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RoomDevicesScreenFragment : Fragment(), RoomDevicesRecyclerViewAdapter.RoomDevicesItemClickListener {

    private lateinit var binding: FragmentRoomDevicesScreenBinding
    private lateinit var roomDevicesRecyclerViewAdapter: RoomDevicesRecyclerViewAdapter
    private lateinit var dialog: ChangeNameDialog
    private var param1: String? = null
    private var param2: String? = null
    private val args: RoomDevicesScreenFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        dialog = ChangeNameDialog.newInstance(
            titleResId = R.string.change_name,
            description = getString(R.string.new_name),
            inputResId = R.drawable.ic_home,   //not sure why ? later delete
            positiveButtonResId = R.string.change,
            negativeButtonResId = R.string.cancel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoomDevicesScreenBinding.inflate(inflater, container, false)
        return binding.root;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.roomNameDevicesTV.text = args.roomNameString
        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_roomDevicesScreenFragment_to_swipeMenuFragment)
        }

        binding.editImage.setOnClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
        }

        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        val demoData = mutableListOf(
            Device(1,1,"light",0),
            Device(3,2,"climate",0),
            Device(2,3,"add",0)
            )
        roomDevicesRecyclerViewAdapter = RoomDevicesRecyclerViewAdapter()
        roomDevicesRecyclerViewAdapter.itemClickListener = this
        roomDevicesRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.room_devices).adapter =
            roomDevicesRecyclerViewAdapter
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RoomDevicesScreenFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(roomDevice: Device) {
        if(roomDevice.viewType == 1) {
            findNavController().navigate(R.id.action_roomDevicesScreenFragment_to_ledLightSettingsFragment)
        }
        if(roomDevice.viewType == 2){
            findNavController().navigate(R.id.action_roomDevicesScreenFragment_to_addNewDeviceFragment)
        }
        if(roomDevice.viewType == 3){
            findNavController().navigate(R.id.action_roomDevicesScreenFragment_to_climateSettingsFragment)
        }


    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }
}

