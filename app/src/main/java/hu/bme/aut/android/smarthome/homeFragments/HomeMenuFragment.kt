package hu.bme.aut.android.smarthome.homeFragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.adapter.RoomRecyclerViewAdapter
import hu.bme.aut.android.smarthome.databinding.FragmentHomeMenuBinding
import hu.bme.aut.android.smarthome.model.Home
import hu.bme.aut.android.smarthome.model.Room
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class HomeMenuFragment : Fragment(), RoomRecyclerViewAdapter.RoomItemClickListener {

    private lateinit var binding : FragmentHomeMenuBinding
    private lateinit var roomRecyclerViewAdapter: RoomRecyclerViewAdapter
    val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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


        var correctName: String
        firestore.collection("homes")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val currDoc = document.toObject<Home>()
                    if(currDoc.joinedUsers?.get(0) == user?.uid) {
                        correctName = currDoc.name
                        val docRef = firestore.collection("homes").document(correctName)
                        docRef.get().addOnSuccessListener { documentSanpshot ->
                            val currentHome = documentSanpshot.toObject<Home>()
                            binding.currentHome.text = "Current home: " + currentHome!!.name
                        }
                    }
                }
            }
            .addOnFailureListener{

            }
        setupRecyclerView()
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

    private fun setupRecyclerView() {
        //TODO: get items from firebase firestore
        val demoData = mutableListOf(
            Room(1,1, "Living room","living room" , 4),
            Room(1,2, "Bathroom", "bathroom", 3),
            Room(1,3, "Kitchen", "kitchen", 6),
            //always add viewType 2 as the last element
            Room(2,3, "Kitchen", "kitchen", 6)
        )
        roomRecyclerViewAdapter = RoomRecyclerViewAdapter()
        roomRecyclerViewAdapter.itemClickListener = this
        roomRecyclerViewAdapter.addAll(demoData)
        binding.root.findViewById<RecyclerView>(R.id.rooms).adapter =
            roomRecyclerViewAdapter

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

    override fun onItemLongClick(position: Int, view: View): Boolean {
        TODO("Not yet implemented")
    }

}