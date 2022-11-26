package hu.bme.aut.android.smarthome.devicesPage.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentLedLightSettingsBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog
import hu.bme.aut.android.smarthome.devicesPage.model.Device
import hu.bme.aut.android.smarthome.roomsPage.model.Home
import hu.bme.aut.android.smarthome.roomsPage.model.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LedLightSettingsFragment : Fragment() {

    private lateinit var binding : FragmentLedLightSettingsBinding
    private lateinit var dialog : ChangeNameDialog
    private val firestore = Firebase.firestore
    private val args: LedLightSettingsFragmentArgs by navArgs()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = ChangeNameDialog.newInstance(
            titleResId = R.string.change_name,
            inputResId = R.string.input,
            positiveButtonResId = R.string.change,
            negativeButtonResId = R.string.cancel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLedLightSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        binding.lightSettingsTV.text = args.deviceNameString

        binding.buttonSaveSettings.setOnClickListener {
            val action = LedLightSettingsFragmentDirections.actionLedLightSettingsFragmentToRoomDevicesScreenFragment(args.roomNameString)
            findNavController().navigate(action)
        }
        binding.arrowImage.setOnClickListener {
            val action = LedLightSettingsFragmentDirections.actionLedLightSettingsFragmentToRoomDevicesScreenFragment(args.roomNameString)
            findNavController().navigate(action)
        }
        binding.lightSettingsTV.setOnLongClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
            dialog.setOnPositiveClickListener {
                val newDeviceName = dialog.getNewName()
                val roomName = args.roomNameString
                val deviceOldName = args.deviceNameString

                binding.lightSettingsTV.text = newDeviceName

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
                                            val devices = firestore.collection("homes").document(home.id.toString()).collection("rooms").document(room.id.toString()).collection("devices")
                                                .get()
                                                .await()
                                                .toObjects<Device>()
                                            for(device in devices){
                                                if(device.name == deviceOldName){
                                                    firestore.collection("homes").document(home.id.toString())
                                                        .collection("rooms").document(room.id.toString())
                                                        .collection("devices").document(device.id.toString()).update("name",newDeviceName)
                                                }
                                            }
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
    }
}


