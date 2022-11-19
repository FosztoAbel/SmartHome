package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentLedLightSettingsBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog

class LedLightSettingsFragment : Fragment() {

    private lateinit var binding : FragmentLedLightSettingsBinding
    private lateinit var dialog : ChangeNameDialog

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveSettings.setOnClickListener {
            findNavController().navigate(R.id.action_ledLightSettingsFragment_to_roomDevicesScreenFragment)
        }
        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_ledLightSettingsFragment_to_roomDevicesScreenFragment)
        }

        binding.editImage.setOnClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
        }
    }
}


