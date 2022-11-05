package hu.bme.aut.android.smarthome.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.databinding.RowAddBinding
import hu.bme.aut.android.smarthome.databinding.RowRoomDeviceBinding
import hu.bme.aut.android.smarthome.model.RoomDevice

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class RoomDevicesRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val roomDeviceList = mutableListOf<RoomDevice>()

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    var itemClickListener: RoomDevicesItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return ViewHolderRoomDevice(
                RowRoomDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return ViewHolderRoomDeviceAdd(
            RowAddBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (roomDeviceList[position].viewType === VIEW_TYPE_ONE) {
            (holder as ViewHolderRoomDevice).bind(position)
        } else {
            (holder as ViewHolderRoomDeviceAdd).bind(position)
        }
    }

    fun addItem(roomDevice: RoomDevice) {
        val size = roomDeviceList.size
        roomDeviceList.add(roomDevice)
        notifyItemInserted(size)
    }

    fun addAll(roomDevices: List<RoomDevice>) {
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

    inner class ViewHolderRoomDevice(val binding: RowRoomDeviceBinding) : RecyclerView.ViewHolder(binding.root) {
        var roomDevice: RoomDevice? = null

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val roomDevice = roomDeviceList[position]
            binding.deviceButton.setOnClickListener {
                roomDevice?.let { roomDevice -> itemClickListener?.onItemClick(roomDevice)
                }
            }
        }
    }

    inner class ViewHolderRoomDeviceAdd(val binding: RowAddBinding) : RecyclerView.ViewHolder(binding.root) {
        var roomDevice: RoomDevice? = null

        fun bind(position: Int){
            val roomDevice = roomDeviceList[position]
            binding.addButton.setOnClickListener {
                roomDevice?.let { roomDevice -> itemClickListener?.onItemClick(roomDevice) }
            }
            binding.addImage.setOnClickListener {
                roomDevice?.let { roomDevice -> itemClickListener?.onItemClick(roomDevice) }
            }
        }
    }

    interface RoomDevicesItemClickListener {
        fun onItemClick(roomDevice: RoomDevice)
        fun onItemLongClick(position: Int, view: View): Boolean
    }
}