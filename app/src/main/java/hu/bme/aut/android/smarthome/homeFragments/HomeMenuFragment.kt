package hu.bme.aut.android.smarthome.homeFragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.RoomRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentHomeMenuBinding
import hu.bme.aut.android.smarthome.dialog.DeleteItemDialog
import hu.bme.aut.android.smarthome.model.Home
import hu.bme.aut.android.smarthome.model.Room
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeMenuFragment : Fragment(), RoomRecyclerViewAdapter.RoomItemClickListener {

    private lateinit var binding : FragmentHomeMenuBinding
    private lateinit var roomRecyclerViewAdapter: RoomRecyclerViewAdapter
    val firestore = Firebase.firestore
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        setCurrentDateOnTextView()
        setWelcomeMessageTextView(user)

        binding.profileButton.setOnClickListener{
            findNavController().navigate(R.id.action_swipeMenuFragment_to_profileFragment)
        }

        firestore.collection("homes")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val currentDocument = document.toObject<Home>()
                    for(iterator in currentDocument.joinedUsers!!){
                        if(iterator.equals(user?.uid)){
                            binding.currentHome.text = "Current home: " + currentDocument.name
                        }
                    }
                }
            }
        setupRecyclerView(user)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCurrentDateOnTextView(){
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        val formatted = currentDate.format(formatter)
        binding.currentDate.text = formatted.toString()
    }

    @SuppressLint("SetTextI18n")
    fun setWelcomeMessageTextView(user: FirebaseUser?){
        val fullNameOfUser = user?.displayName.toString().split(" ")
        binding.welcomeTV.text = "Welcome, " + fullNameOfUser[0] + "!"

    }

    private fun setupRecyclerView(user: FirebaseUser?) {
        var liveData: MutableList<Room> = mutableListOf()
        roomRecyclerViewAdapter = RoomRecyclerViewAdapter()

        CoroutineScope(Dispatchers.IO).launch {
            val homes = firestore.collection("homes")
                .get()
                .await()
                .toObjects<Home>()
            for (home in homes) {
                for (iterator in home.joinedUsers!!) {
                    if (iterator.equals(user?.uid)) {
                        val rooms =
                            firestore.collection("homes").document(home.id.toString()).collection("rooms")
                                .get()
                                .await()
                                .toObjects<Room>()
                        CoroutineScope(Dispatchers.Main).launch {
                            for (room in rooms) {
                                liveData.add(room)
                            }
                            liveData.add(Room(2, 0, "Add button", "add", 0))
                            roomRecyclerViewAdapter.addAll(liveData)
                        }
                    }
                }
            }
        }
        roomRecyclerViewAdapter.itemClickListener = this
        binding.root.findViewById<RecyclerView>(R.id.rooms).adapter = roomRecyclerViewAdapter
    }

    override fun onItemClick(room: Room) {
        if(room.viewType == 1) {
            val action  = SwipeMenuFragmentDirections.actionSwipeMenuFragmentToRoomDevicesScreenFragment(room.name)
            findNavController().navigate(action)
        }
        if(room.viewType == 2){
           findNavController().navigate(R.id.action_swipeMenuFragment_to_createNewRoomFragment)
        }
    }

    override fun onItemLongClick(position: Int, room: Room): Boolean {
        if(room.viewType == 1){
            val user = FirebaseAuth.getInstance().currentUser
            dialogDelete.show(childFragmentManager,DeleteItemDialog.TAG)
            dialogDelete.setOnPositiveClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val homes = firestore.collection("homes")
                        .get()
                        .await()
                        .toObjects<Home>()
                    for (home in homes) {
                        for (iterator in home.joinedUsers!!) {
                            if (iterator.equals(user?.uid)) {
                                val rooms =
                                    firestore.collection("homes").document(home.id.toString()).collection("rooms")
                                        .get()
                                        .await()
                                        .toObjects<Room>()
                                CoroutineScope(Dispatchers.Main).launch {
                                    for (iteratorRoom in rooms) {
                                        if(iteratorRoom.name == room.name){
                                            firestore.collection("homes").document(home.id.toString())
                                                .collection("rooms").document(iteratorRoom.id.toString()).delete()
                                            roomRecyclerViewAdapter.deleteRow(position)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Snackbar.make(binding.root, "Successfully deleted a room!", Snackbar.LENGTH_LONG).show()
                }
            }
        }
        return true
    }

}