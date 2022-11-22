package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.AddNewDeviceScreenRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentAddNewDeviceBinding
import hu.bme.aut.android.smarthome.model.AvailableDevice
import hu.bme.aut.android.smarthome.model.Device
import hu.bme.aut.android.smarthome.model.Home

class AddNewDeviceFragment : Fragment(), AddNewDeviceScreenRecyclerViewAdapter.AddNewDeviceScreenItemClickListener {

    private lateinit var binding : FragmentAddNewDeviceBinding
    private val firestore = Firebase.firestore
    private lateinit var addNewDeviceScreenRecyclerView : AddNewDeviceScreenRecyclerViewAdapter
    private val args: AddNewDeviceFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val user = FirebaseAuth.getInstance().currentUser

        binding.arrowImage.setOnClickListener {
            val action = AddNewDeviceFragmentDirections.actionAddNewDeviceFragmentToRoomDevicesScreenFragment(args.roomNameString.toString())
            findNavController().navigate(action)
        }
        binding.buttonSaveDevice.setOnClickListener {

            val roomName = args.roomNameString.toString()
            val deviceName = binding.deviceNameInput.text.toString()
            val newDevice = Device(1,1,deviceName,false)

            if(checkFields()){
                firestore.collection("homes")
                    .get()
                    .addOnSuccessListener { result ->
                        for(document in result){
                            val currentDocument = document.toObject<Home>()
                            for(iterator in currentDocument.joinedUsers!!){
                                if(iterator.equals(user?.uid)){
                                    val dbRef = firestore.collection("homes").document(currentDocument.name)
                                        .collection("rooms").document(roomName)
                                        .collection("devices").document(deviceName)
                                    dbRef.set(newDevice)
                                        .addOnSuccessListener {
                                            val action = AddNewDeviceFragmentDirections.actionAddNewDeviceFragmentToRoomDevicesScreenFragment(args.roomNameString.toString())
                                            findNavController().navigate(action)
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
        setupRecyclerView()
    }

    private fun checkFields(): Boolean{
        return binding.deviceNameInput.text.isNotEmpty()
    }

    private fun setupRecyclerView() {
        //TODO: get available items from network
        val demoData = mutableListOf(
            AvailableDevice(1,1,"LEDstrip",false),
            AvailableDevice(1,2,"LEDstrip",false),
            AvailableDevice(1,3,"LEDstrip",false),
            AvailableDevice(1,4,"LEDstrip",false),
            AvailableDevice(1,5,"LEDstrip",false)
          //  AvailableDevice(2,1,"SmartBulb", 0)
        )

        addNewDeviceScreenRecyclerView = AddNewDeviceScreenRecyclerViewAdapter()
        addNewDeviceScreenRecyclerView.itemClickListener = this
        addNewDeviceScreenRecyclerView.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.room_devices).adapter =
            addNewDeviceScreenRecyclerView

    }

    override fun onItemClick(availableDevice: AvailableDevice) {

    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }
}