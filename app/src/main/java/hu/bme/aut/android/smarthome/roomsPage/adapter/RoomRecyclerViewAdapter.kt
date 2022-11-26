package hu.bme.aut.android.smarthome.roomsPage.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.smarthome.databinding.RowAddBinding
import hu.bme.aut.android.smarthome.databinding.RowRoomBinding
import hu.bme.aut.android.smarthome.roomsPage.model.Room

@Suppress("IMPLICIT_CAST_TO_ANY", "DEPRECATED_IDENTITY_EQUALS")
class RoomRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val roomList = mutableListOf<Room>()

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    var itemClickListener: RoomItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_ONE) {
            return ViewHolderHome(
                RowRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        return ViewHolderHomeAdd(
            RowAddBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (roomList[position].viewType === VIEW_TYPE_ONE) {
            (holder as ViewHolderHome).bind(position)
        } else {
            (holder as ViewHolderHomeAdd).bind(position)
        }
    }

    fun addItem(room: Room) {
        val size = roomList.size
        roomList.add(room)
        notifyItemInserted(size)
    }

    fun addAll(rooms: List<Room>) {
        val size = roomList.size
        roomList += rooms
        notifyItemRangeInserted(size, rooms.size)
    }

    fun deleteRow(position: Int) {
        roomList.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = roomList.size

    override fun getItemViewType(position: Int): Int {
        return roomList[position].viewType
    }

    inner class ViewHolderHome(val binding: RowRoomBinding) : RecyclerView.ViewHolder(binding.root) {
        var room: Room? = null

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            val room = roomList[position]

            binding.roomNameHomeTV.text = room.name
            binding.deviceNumberHomeTV.text = room.deviceNumber.toString() + " devices"

//            val resource = when (room.type) {
//               Room.Type.bathroom -> R.drawable.custom_room_image
//               Room.Type.bedroom -> R.drawable.custom_room_image
//               Room.Type.livingroom -> R.drawable.custom_room_image
//               Room.Type.study -> R.drawable.custom_room_image
//               Room.Type.diningroom -> R.drawable.custom_room_image
//               Room.Type.garage -> R.drawable.custom_room_image
//               Room.Type.kitchen -> R.drawable.custom_room_image
//                else -> {}
//         }
//            binding.roomImage.setImageResource(resource as Int)

//            itemView.setOnClickListener {
//                room?.let { room -> itemClickListener?.onItemClick(room) }
//            }

            binding.roomImage.setOnClickListener {
                room?.let { room -> itemClickListener?.onItemClick(room) }
            }
            binding.roomHomeButton.setOnClickListener {
                room?.let { room -> itemClickListener?.onItemClick(room) }
            }
            binding.deviceNumberHomeTV.setOnClickListener {
                room?.let { room -> itemClickListener?.onItemClick(room) }
            }
            binding.roomNameHomeTV.setOnClickListener {
                room?.let { room -> itemClickListener?.onItemClick(room) }
            }


            binding.roomImage.setOnLongClickListener {
                room?.let { room -> itemClickListener?.onItemLongClick(adapterPosition, room) }
                true
            }
            binding.roomHomeButton.setOnLongClickListener {
                room?.let { room -> itemClickListener?.onItemLongClick(adapterPosition, room) }
                true
            }
            binding.roomNameHomeTV.setOnLongClickListener {
                room?.let { room -> itemClickListener?.onItemLongClick(adapterPosition, room) }
                true
            }
            binding.deviceNumberHomeTV.setOnLongClickListener {
                room?.let { room -> itemClickListener?.onItemLongClick(adapterPosition, room) }
                true
            }
        }
    }

    inner class ViewHolderHomeAdd(val binding: RowAddBinding) : RecyclerView.ViewHolder(binding.root) {
        var room: Room? = null

        fun bind(position: Int){

            val room = roomList[position]

            binding.addButton.setOnClickListener {
                room?.let { room -> itemClickListener?.onItemClick(room) }
            }
            binding.addImage.setOnClickListener {
                room?.let { room -> itemClickListener?.onItemClick(room) }
            }
        }
    }

    interface RoomItemClickListener {
        fun onItemClick(room: Room)
        fun onItemLongClick(position: Int, room: Room): Boolean
    }
}