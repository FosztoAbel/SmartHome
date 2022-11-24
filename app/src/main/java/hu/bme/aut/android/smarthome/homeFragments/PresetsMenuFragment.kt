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
import hu.bme.aut.android.smarthome.databinding.FragmentPresetsMenuBinding
import hu.bme.aut.android.smarthome.dialog.DeleteItemDialog
import hu.bme.aut.android.smarthome.model.Preset

class PresetsMenuFragment : Fragment(), PresetRecyclerViewAdapter.PresetItemClickListener {

    private lateinit var binding : FragmentPresetsMenuBinding
    private lateinit var presetRecyclerViewAdapter: PresetRecyclerViewAdapter
    private lateinit var dialogDelete: DeleteItemDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dialogDelete = DeleteItemDialog.newInstance(
            titleResId = R.string.delete_item,
            questionResId = R.string.delete_question,
            positiveButtonResId = R.string.delete,
            negativeButtonResId = R.string.cancel
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPresetsMenuBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onItemClick(preset: Preset) {
        if(preset.viewType==1){

        }
        if(preset.viewType==2){
            findNavController().navigate(R.id.action_swipeMenuFragment_to_createNewPresetFragment)
        }
    }

    override fun onItemLongClick(preset: Preset): Boolean {
        //TODO: delete preset from database
        if(preset.viewType == 1) dialogDelete.show(childFragmentManager, DeleteItemDialog.TAG)
        return true
    }
}