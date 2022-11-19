package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentJoinHomeBinding

class JoinHomeFragment : Fragment() {
    private lateinit var binding: FragmentJoinHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_joinHomeFragment_to_swipeMenuFragment)
        }

        binding.buttonJoinHome.setOnClickListener {
            findNavController().navigate(R.id.action_joinHomeFragment_to_swipeMenuFragment)
        }
    }

    private fun togglePasswordVisibility() {
        var toggleVisibility = false
        binding.passwordJoinHomeToggleVisibility.setOnClickListener {
            if (!toggleVisibility) {
                binding.passwordJoinHomeToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.joinHomePasswordInput.setTransformationMethod(null)
                toggleVisibility = true
            } else {
                binding.passwordJoinHomeToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.joinHomePasswordInput.setTransformationMethod(PasswordTransformationMethod())
                toggleVisibility = false
            }
        }
    }
}