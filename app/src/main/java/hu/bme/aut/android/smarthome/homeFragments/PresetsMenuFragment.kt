package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.PresetRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentHomeMenuBinding
import hu.bme.aut.android.smarthome.databinding.FragmentPresetsMenuBinding
import hu.bme.aut.android.smarthome.model.Preset
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PresetsMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PresetsMenuFragment : Fragment(), PresetRecyclerViewAdapter.PresetItemClickListener {

    private lateinit var binding : FragmentPresetsMenuBinding
    private lateinit var presetRecyclerViewAdapter: PresetRecyclerViewAdapter
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

        binding = FragmentPresetsMenuBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val demoData = mutableListOf(
            Preset(1, 1,"Good morning!", 10,0),
            Preset(1, 2,"Good Evening!", 20,0),
            Preset(2, 1,"Good morning!", 10,0)

        )
        presetRecyclerViewAdapter = PresetRecyclerViewAdapter()
        presetRecyclerViewAdapter.itemClickListener = this
        presetRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.preset_list).adapter =
            presetRecyclerViewAdapter

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PresetsMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PresetsMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onItemClick(preset: Preset) {
        if(preset.viewType==1){

        }
        if(preset.viewType==2){
            findNavController().navigate(R.id.action_swipeMenuFragment_to_createNewPresetFragment)
        }
    }

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }
}