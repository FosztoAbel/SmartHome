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
import hu.bme.aut.android.smarthome.databinding.FragmentClimateSettingsBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog

class ClimateSettingsFragment : Fragment() {
    private lateinit var binding : FragmentClimateSettingsBinding
    private lateinit var dialog: ChangeNameDialog
    private val args: ClimateSettingsFragmentArgs by navArgs()


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
    ): View? {
        binding = FragmentClimateSettingsBinding.inflate(inflater,container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.climateSettingsTV.text = args.deviceNameString

        binding.buttonSaveSettings.setOnClickListener {
            val action = ClimateSettingsFragmentDirections.actionClimateSettingsFragmentToRoomDevicesScreenFragment(args.roomNameString)
            findNavController().navigate(action)
        }
        binding.arrowImage.setOnClickListener {
            val action = ClimateSettingsFragmentDirections.actionClimateSettingsFragmentToRoomDevicesScreenFragment(args.roomNameString)
            findNavController().navigate(action)
        }
        binding.climateSettingsTV.setOnLongClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
            true
        }

    }
}