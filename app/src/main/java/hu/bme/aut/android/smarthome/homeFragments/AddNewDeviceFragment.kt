package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.AddNewDeviceScreenRecyclerViewAdapter
import hu.bme.aut.android.smarthome.adapter.RoomRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentAddNewDeviceBinding
import hu.bme.aut.android.smarthome.model.AvailableDevice

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddNewDeviceFragment : Fragment(), AddNewDeviceScreenRecyclerViewAdapter.AddNewDeviceScreenItemClickListener {

    private lateinit var binding : FragmentAddNewDeviceBinding
    private lateinit var addNewDeviceScreenRecyclerView : AddNewDeviceScreenRecyclerViewAdapter

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
        binding = FragmentAddNewDeviceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_addNewDeviceFragment_to_roomDevicesScreenFragment)
        }
        binding.buttonSaveDevice.setOnClickListener {
            findNavController().navigate(R.id.action_addNewDeviceFragment_to_roomDevicesScreenFragment)
        }
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val demoData = mutableListOf(
            AvailableDevice(1,1,"LEDstrip",0),
            AvailableDevice(1,2,"LEDstrip",0),
            AvailableDevice(1,3,"LEDstrip",0),
            AvailableDevice(1,4,"LEDstrip",0),
            AvailableDevice(1,5,"LEDstrip",0)
          //  AvailableDevice(2,1,"SmartBulb", 0)
        )
        addNewDeviceScreenRecyclerView = AddNewDeviceScreenRecyclerViewAdapter()
        addNewDeviceScreenRecyclerView.itemClickListener = this
        addNewDeviceScreenRecyclerView.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.room_devices).adapter =
            addNewDeviceScreenRecyclerView

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNewDeviceFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(availableDevice: AvailableDevice) {
       System.out.println("asdasd")
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }
}