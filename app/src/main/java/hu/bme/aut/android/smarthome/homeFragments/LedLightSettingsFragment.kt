package hu.bme.aut.android.smarthome.homeFragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentLedLightSettingsBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog

class LedLightSettingsFragment : Fragment() {

    private lateinit var binding : FragmentLedLightSettingsBinding
    private lateinit var dialog : ChangeNameDialog
    private val args: LedLightSettingsFragmentArgs by navArgs()


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
        binding = FragmentLedLightSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lightSettingsTV.text = args.deviceNameString + " settings"

        binding.buttonSaveSettings.setOnClickListener {
            val action = LedLightSettingsFragmentDirections.actionLedLightSettingsFragmentToRoomDevicesScreenFragment(args.roomNameString)
            findNavController().navigate(action)
        }
        binding.arrowImage.setOnClickListener {
            val action = LedLightSettingsFragmentDirections.actionLedLightSettingsFragmentToRoomDevicesScreenFragment(args.roomNameString)
            findNavController().navigate(action)
        }

        binding.editImage.setOnClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
        }
    }
}


