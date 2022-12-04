package hu.bme.aut.android.smarthome.presetsPage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewPresetBinding

class CreateNewPresetFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPresetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNewPresetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_createNewPresetFragment_to_swipeMenuFragment)
        }
        binding.buttonSavePreset.setOnClickListener {
            findNavController().navigate(R.id.action_createNewPresetFragment_to_swipeMenuFragment)
        }
    }
}