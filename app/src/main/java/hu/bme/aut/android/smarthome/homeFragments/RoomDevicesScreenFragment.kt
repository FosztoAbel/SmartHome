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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.AddNewDeviceScreenRecyclerViewAdapter
import hu.bme.aut.android.smarthome.adapter.RoomDevicesRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentRoomDevicesScreenBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog
import hu.bme.aut.android.smarthome.model.Device
import hu.bme.aut.android.smarthome.model.Home
import hu.bme.aut.android.smarthome.model.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RoomDevicesScreenFragment : Fragment(), RoomDevicesRecyclerViewAdapter.RoomDevicesItemClickListener {

    private lateinit var binding: FragmentRoomDevicesScreenBinding
    private lateinit var roomDevicesRecyclerViewAdapter: RoomDevicesRecyclerViewAdapter
    private lateinit var dialog: ChangeNameDialog
    val firestore = Firebase.firestore
    private val args: RoomDevicesScreenFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        binding.roomNameDevicesTV.text = args.roomNameString

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_roomDevicesScreenFragment_to_swipeMenuFragment)
        }

        binding.editImage.setOnClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
        }

        setupRecyclerView(user)
    }

    private fun setupRecyclerView(user: FirebaseUser?) {

        var liveData: MutableList<Device> = mutableListOf()
        roomDevicesRecyclerViewAdapter = RoomDevicesRecyclerViewAdapter()

        CoroutineScope(Dispatchers.IO).launch {
            val homes = firestore.collection("homes")
                .get()
                .await()
                .toObjects<Home>()
            for (home in homes) {
                for (iterator in home.joinedUsers!!) {
                    if (iterator.equals(user?.uid)) {
                        val devices = firestore.collection("homes").document(home.name)
                                .collection("rooms")
                                .document(args.roomNameString.toString())
                                .collection("devices")
                                .get()
                                .await()
                                .toObjects<Device>()
                        CoroutineScope(Dispatchers.Main).launch {
                           for(device in devices){
                               liveData.add(device)
                           }
                            liveData.add(Device(2, 0, "Add button", false))
                            roomDevicesRecyclerViewAdapter.addAll(liveData)
                        }
                    }
                }
            }
        }

        roomDevicesRecyclerViewAdapter.itemClickListener = this
        roomDevicesRecyclerViewAdapter.addAll(liveData)
        binding.root.findViewById<RecyclerView>(R.id.room_devices).adapter =
            roomDevicesRecyclerViewAdapter
    }

    override fun onItemClick(roomDevice: Device) {
        if(roomDevice.viewType == 1) {
            val action = RoomDevicesScreenFragmentDirections.actionRoomDevicesScreenFragmentToLedLightSettingsFragment(args.roomNameString.toString(), roomDevice.name)
            findNavController().navigate(action)
        }
        if(roomDevice.viewType == 2){
            val action = RoomDevicesScreenFragmentDirections.actionRoomDevicesScreenFragmentToAddNewDeviceFragment(args.roomNameString.toString())
            findNavController().navigate(action)
        }
        if(roomDevice.viewType == 3){
            val action = RoomDevicesScreenFragmentDirections.actionRoomDevicesScreenFragmentToClimateSettingsFragment(args.roomNameString.toString(), roomDevice.name)
            findNavController().navigate(action)
        }
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }
}

