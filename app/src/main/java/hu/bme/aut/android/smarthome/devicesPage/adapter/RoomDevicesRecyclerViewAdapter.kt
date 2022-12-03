package hu.bme.aut.android.smarthome.devicesPage.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.databinding.RowAddBinding
import hu.bme.aut.android.smarthome.databinding.RowRoomDeviceClimateBinding
import hu.bme.aut.android.smarthome.databinding.RowRoomDeviceLedBinding
import hu.bme.aut.android.smarthome.devicesPage.model.Device
import hu.bme.aut.android.smarthome.network.NetworkManager

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class RoomDevicesRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val roomDeviceList = mutableListOf<Device>()

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
        const val VIEW_TYPE_THREE = 3
    }

    var itemClickListener: RoomDevicesItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return ViewHolderRoomDeviceLed(
                RowRoomDeviceLedBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        if (viewType == VIEW_TYPE_THREE) {
            return ViewHolderRoomDeviceClimate(
                RowRoomDeviceClimateBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return ViewHolderRoomDeviceAdd(
            RowAddBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (roomDeviceList[position].viewType === VIEW_TYPE_ONE) {
            (holder as ViewHolderRoomDeviceLed).bind(position)
        }
        else if(roomDeviceList[position].viewType === VIEW_TYPE_TWO) {
            (holder as ViewHolderRoomDeviceAdd).bind(position)
        }
        else {
            (holder as ViewHolderRoomDeviceClimate).bind(position)
        }
    }

    fun addAll(roomDevices: List<Device>) {
        val size = roomDeviceList.size
        roomDeviceList += roomDevices
        notifyItemRangeInserted(size, roomDevices.size)
    }

    fun deleteRow(position: Int) {

        roomDeviceList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = roomDeviceList.size

    override fun getItemViewType(position: Int): Int {
        return roomDeviceList[position].viewType
    }

    inner class ViewHolderRoomDeviceLed(val binding: RowRoomDeviceLedBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val roomDevice = roomDeviceList[position]
            binding.deviceButton.setOnClickListener {
                roomDevice.let { roomDevice -> itemClickListener?.onItemClick(roomDevice)
                }
            }
            binding.deviceButton.setOnLongClickListener {
                roomDevice.let { roomDevice -> itemClickListener?.onItemLongClick(adapterPosition, roomDevice) }
                true
            }
            binding.deviceNameTV.text = roomDevice.name

            binding.switchOnOff.setOnClickListener {
                if(binding.switchOnOff.isChecked){
                    NetworkManager.turnOnLed("ESP32")
                }
                else{
                    NetworkManager.turnOffLed("ESP32")
                }
            }
        }
    }

    //for future updates
    inner class ViewHolderRoomDeviceClimate(val binding: RowRoomDeviceClimateBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val roomDevice = roomDeviceList[position]
            binding.deviceButton.setOnClickListener {
                roomDevice.let { roomDevice -> itemClickListener?.onItemClick(roomDevice) }
            }
            binding.deviceButton.setOnLongClickListener {
                roomDevice.let { roomDevice -> itemClickListener?.onItemLongClick(adapterPosition, roomDevice) }
                true
            }

            binding.deviceNameTV.text = roomDevice.name

            binding.switchOnOff.setOnClickListener {

            }

        }
    }

    inner class ViewHolderRoomDeviceAdd(val binding: RowAddBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int){
            val roomDevice = roomDeviceList[position]
            binding.addButton.setOnClickListener {
                roomDevice.let { roomDevice -> itemClickListener?.onItemClick(roomDevice) }
            }
            binding.addImage.setOnClickListener {
                roomDevice.let { roomDevice -> itemClickListener?.onItemClick(roomDevice) }
            }

        }
    }

    interface RoomDevicesItemClickListener {
        fun onItemClick(roomDevice: Device)
        fun onItemLongClick(position: Int, roomDevice: Device): Boolean
    }
}