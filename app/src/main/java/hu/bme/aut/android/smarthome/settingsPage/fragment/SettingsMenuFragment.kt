package hu.bme.aut.android.smarthome.settingsPage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentSettingsMenuBinding

class SettingsMenuFragment : Fragment() {

    private lateinit var binding: FragmentSettingsMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsNotificationsBackground.setOnClickListener {
        }
        binding.settingsCreateHomeBackground.setOnClickListener {
            findNavController().navigate(R.id.action_swipeMenuFragment_to_createHomeFragment)
        }
        binding.settingsJoinHomeBackground.setOnClickListener {
            findNavController().navigate(R.id.action_swipeMenuFragment_to_joinHomeFragment)
        }
        binding.settingsRenameHomeBackground.setOnClickListener {
            findNavController().navigate(R.id.action_swipeMenuFragment_to_changeHomeNameFragment)
        }
    }
}