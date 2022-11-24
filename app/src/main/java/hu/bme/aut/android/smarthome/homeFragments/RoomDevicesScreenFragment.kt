package hu.bme.aut.android.smarthome.homeFragments

import android.os.Build
import android.os.Bundle
import android.util.Log
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
import hu.bme.aut.android.smarthome.adapter.RoomDevicesRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentRoomDevicesScreenBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog
import hu.bme.aut.android.smarthome.dialog.DeleteItemDialog
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
    private lateinit var dialogRename: ChangeNameDialog
    private lateinit var dialogDelete: DeleteItemDialog
    val firestore = Firebase.firestore
    private val args: RoomDevicesScreenFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogRename = ChangeNameDialog.newInstance(
            titleResId = R.string.change_name,
            inputResId = R.string.input,
            positiveButtonResId = R.string.change,
            negativeButtonResId = R.string.cancel
        )
        dialogDelete = DeleteItemDialog.newInstance(
            titleResId = R.string.delete_item,
            questionResId = R.string.delete_question,
            positiveButtonResId = R.string.delete,
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

        binding.roomNameDevicesTV.setOnLongClickListener {
            dialogRename.show(childFragmentManager,ChangeNameDialog.TAG)
            dialogRename.setOnPositiveClickListener {
                val newName = dialogRename.getNewName()
                val oldName = args.roomNameString
                binding.roomNameDevicesTV.text = newName

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
                                        if(room.name == oldName){
                                            firestore.collection("homes").document(home.id.toString()).collection("rooms").document(room.id.toString()).update("name",newName)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            true
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
                        val rooms =
                            firestore.collection("homes").document(home.id.toString()).collection("rooms")
                                .get()
                                .await()
                                .toObjects<Room>()
                        CoroutineScope(Dispatchers.Main).launch {
                            for (room in rooms) {
                                if(room.name == args.roomNameString.toString()){
                                    val devices = firestore.collection("homes").document(home.id.toString())
                                        .collection("rooms")
                                        .document(room.id.toString())
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
                }
            }
        }

        roomDevicesRecyclerViewAdapter.itemClickListener = this
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

    override fun onItemLongClick(device: Device): Boolean {
        //TODO: Delete device from database
        if(device.viewType == 1) dialogDelete.show(childFragmentManager,DeleteItemDialog.TAG)
        return true
    }
}

