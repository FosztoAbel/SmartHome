package hu.bme.aut.android.smarthome.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.databinding.RowAvailableClimateBinding
import hu.bme.aut.android.smarthome.databinding.RowAvailableLedBinding
import hu.bme.aut.android.smarthome.model.AvailableDevice

//I Will add some features for future improvements
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class AddNewDeviceScreenRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val availableDeviceList = mutableListOf<AvailableDevice>()

    var itemClickListener: AddNewDeviceScreenItemClickListener? = null

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return ViewHolderAvailableLedStrip(
                RowAvailableLedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return ViewHolderAvailableClimate(
            RowAvailableClimateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (availableDeviceList[position].viewType === VIEW_TYPE_ONE) {
            (holder as AddNewDeviceScreenRecyclerViewAdapter.ViewHolderAvailableLedStrip).bind(position)
        } else {
            (holder as AddNewDeviceScreenRecyclerViewAdapter.ViewHolderAvailableClimate).bind(position)
        }
    }

    fun addItem(availableDevice: AvailableDevice) {
        val size = availableDeviceList.size
        availableDeviceList.add(availableDevice)
        notifyItemInserted(size)
    }

    fun addAll(availableDevices: List<AvailableDevice>) {
        val size = availableDeviceList.size
        availableDeviceList += availableDevices
        notifyItemRangeInserted(size, availableDevices.size)
    }

    fun deleteRow(position: Int) {
        availableDeviceList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = availableDeviceList.size

    override fun getItemViewType(position: Int): Int {
        return availableDeviceList[position].viewType
    }

    inner class ViewHolderAvailableLedStrip(val binding: RowAvailableLedBinding) : RecyclerView.ViewHolder(binding.root) {
        var availableDevice: AvailableDevice? = null

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
              val availableDevice = availableDeviceList[position]
            binding.availableDeviceNameTV.setOnClickListener {
                availableDevice.let { availableDevice -> itemClickListener?.onItemClick(availableDevice)
                }
            }
            binding.lightbulbImage.setOnClickListener {
                availableDevice.let { availableDevice -> itemClickListener?.onItemClick(availableDevice)
                }
            }
        }
    }

    //Here for future updates
    inner class ViewHolderAvailableClimate(val binding: RowAvailableClimateBinding) : RecyclerView.ViewHolder(binding.root) {
        var availableDevice: AvailableDevice? = null

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val availableDevice = availableDeviceList[position]
            binding.availableDeviceNameTV.setOnClickListener {
                availableDevice.let { availableDevice -> itemClickListener?.onItemClick(availableDevice)
                }
            }
            binding.cloudImage.setOnClickListener {
                availableDevice.let { availableDevice -> itemClickListener?.onItemClick(availableDevice)
                }
            }
        }
    }

    interface AddNewDeviceScreenItemClickListener {
        fun onItemClick(availableDevice: AvailableDevice)
        fun onItemLongClick(position: Int, view: View): Boolean
    }
}