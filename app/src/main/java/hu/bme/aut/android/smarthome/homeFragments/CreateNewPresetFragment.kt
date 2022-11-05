package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewPresetBinding
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewRoomBinding
import hu.bme.aut.android.smarthome.databinding.FragmentPresetsMenuBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateNewPresetFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateNewPresetFragment : Fragment() {

    private lateinit var binding: FragmentCreateNewPresetBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPresetBinding.inflate(inflater, container, false)
        return binding.root;
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateNewPresetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}