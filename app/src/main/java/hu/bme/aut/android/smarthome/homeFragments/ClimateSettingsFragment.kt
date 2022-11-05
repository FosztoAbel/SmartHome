package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentClimateSettingsBinding
import hu.bme.aut.android.smarthome.dialog.ChangeNameDialog

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ClimateSettingsFragment : Fragment() {
    private lateinit var binding : FragmentClimateSettingsBinding
    private lateinit var dialog: ChangeNameDialog
    private var param1: String? = null
    private var param2: String? = null

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
        binding = FragmentClimateSettingsBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveSettings.setOnClickListener {
            findNavController().navigate(R.id.action_climateSettingsFragment_to_roomDevicesScreenFragment)
        }
        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_climateSettingsFragment_to_roomDevicesScreenFragment)
        }
        binding.editImage.setOnClickListener {
            dialog.show(childFragmentManager,ChangeNameDialog.TAG)
        }

    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClimateSettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}