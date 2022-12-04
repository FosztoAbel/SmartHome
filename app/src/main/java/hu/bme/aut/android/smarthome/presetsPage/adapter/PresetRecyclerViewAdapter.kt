package hu.bme.aut.android.smarthome.presetsPage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.databinding.RowAddBinding
import hu.bme.aut.android.smarthome.databinding.RowPresetBinding
import hu.bme.aut.android.smarthome.presetsPage.model.Preset

@Suppress("IMPLICIT_CAST_TO_ANY", "DEPRECATED_IDENTITY_EQUALS")
class PresetRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val presetList = mutableListOf<Preset>()

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    var itemClickListener: PresetItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return ViewHolderPreset(
                RowPresetBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return ViewHolderPresetAdd(
            RowAddBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (presetList[position].viewType === VIEW_TYPE_ONE) {
            (holder as ViewHolderPreset).bind(position)
        } else {
            (holder as ViewHolderPresetAdd).bind(position)
        }
    }

    fun addItem(preset: Preset) {
        val size = presetList.size
        presetList.add(preset)
        notifyItemInserted(size)
    }

    fun addAll(presets: List<Preset>) {
        val size = presetList.size
        presetList += presets
        notifyItemRangeInserted(size, presets.size)
    }

    fun deleteRow(position: Int) {
        presetList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = presetList.size

    override fun getItemViewType(position: Int): Int {
        return presetList[position].viewType
    }

    inner class ViewHolderPreset(val binding: RowPresetBinding) : RecyclerView.ViewHolder(binding.root) {
         @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val preset = presetList[position]

            binding.presetNameHomeTV.text = preset.name
            binding.presetTimeTV.text = preset.time.toString()

            binding.presetNameHomeTV.setOnClickListener {
                preset.let { preset -> itemClickListener?.onItemClick(preset) }
            }
            binding.presetTimeTV.setOnClickListener {
                preset.let { preset -> itemClickListener?.onItemClick(preset) }
            }
            binding.presetButton.setOnClickListener {
                preset.let { preset -> itemClickListener?.onItemClick(preset) }
            }
            binding.roomImage.setOnClickListener {
                preset.let { preset -> itemClickListener?.onItemClick(preset) }
            }

            binding.roomImage.setOnLongClickListener {
                preset.let { preset -> itemClickListener?.onItemLongClick(preset) }
                true
            }
            binding.presetTimeTV.setOnLongClickListener {
                preset.let { preset -> itemClickListener?.onItemLongClick(preset) }
                true
            }
            binding.presetNameHomeTV.setOnLongClickListener {
                preset.let { preset -> itemClickListener?.onItemLongClick(preset) }
                true
            }
            binding.presetButton.setOnLongClickListener {
                preset.let { preset -> itemClickListener?.onItemLongClick(preset) }
                true
            }
        }
    }

    inner class ViewHolderPresetAdd(val binding: RowAddBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){

            val preset = presetList[position]

            binding.addButton.setOnClickListener {
                preset.let { preset -> itemClickListener?.onItemClick(preset) }
            }
            binding.addImage.setOnClickListener {
                preset.let { preset -> itemClickListener?.onItemClick(preset) }
            }
        }
    }

    interface PresetItemClickListener {
        fun onItemClick(preset: Preset)
        fun onItemLongClick(preset: Preset):Boolean
    }
}