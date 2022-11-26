package hu.bme.aut.android.smarthome.devicesPage.fragment

import android.os.Bundle
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
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.devicesPage.adapter.AddNewDeviceScreenRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentAddNewDeviceBinding
import hu.bme.aut.android.smarthome.devicesPage.model.AvailableDevice
import hu.bme.aut.android.smarthome.devicesPage.model.Device
import hu.bme.aut.android.smarthome.roomsPage.model.Home
import hu.bme.aut.android.smarthome.roomsPage.model.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlin.random.Random

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
    ): View {
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
            val viewType = 1
            var id = Random.nextInt()
            if(id < 0) id *= (-1)
            val state = false
            val newDevice = Device(viewType,id,deviceName,state)

            if(checkFields()){
                CoroutineScope(Dispatchers.IO).launch {
                    val homes = firestore.collection("homes")
                        .get()
                        .await()
                        .toObjects<Home>()
                    for (home in homes) {
                        for (iterator in home.joinedUsers!!) {
                            if (iterator.equals(user?.uid)) {
                                val rooms =
                                    firestore.collection("homes").document(home.id.toString()).collection("rooms")
                                        .get()
                                        .await()
                                        .toObjects<Room>()
                                CoroutineScope(Dispatchers.Main).launch {
                                    for (room in rooms) {
                                        if(room.name == roomName){
                                            val dbRef = firestore.collection("homes").document(home.id.toString())
                                                .collection("rooms").document(room.id.toString())
                                                .collection("devices").document(newDevice.id.toString())
                                            dbRef.set(newDevice)
                                                .addOnSuccessListener {
                                                    val newDeviceNumber = room.deviceNumber + 1
                                                    firestore.collection("homes").document(home.id.toString()).collection("rooms").document(room.id.toString()).update("deviceNumber", newDeviceNumber)
                                                    val action = AddNewDeviceFragmentDirections.actionAddNewDeviceFragmentToRoomDevicesScreenFragment(args.roomNameString.toString())
                                                    findNavController().navigate(action)
                                                }
                                        }
                                    }
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
            AvailableDevice(1,1,"LED strip",false),
            AvailableDevice(1,2,"LED strip",false),
            AvailableDevice(1,3,"LED strip",false),
            AvailableDevice(1,4,"LED strip",false),
            AvailableDevice(1,5,"LED strip",false)
          //  AvailableDevice(2,1,"SmartBulb", 0)
        )

        addNewDeviceScreenRecyclerView = AddNewDeviceScreenRecyclerViewAdapter()
        addNewDeviceScreenRecyclerView.itemClickListener = this
        addNewDeviceScreenRecyclerView.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.room_devices).adapter =
            addNewDeviceScreenRecyclerView

    }

    override fun onItemClick(availableDevice: AvailableDevice) {
        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }
}