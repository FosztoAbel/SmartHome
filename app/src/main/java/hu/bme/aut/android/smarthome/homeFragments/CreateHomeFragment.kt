package hu.bme.aut.android.smarthome.homeFragments

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateHomeBinding
import hu.bme.aut.android.smarthome.model.Home
import hu.bme.aut.android.smarthome.model.Room


class CreateHomeFragment : Fragment() {

    private lateinit var binding: FragmentCreateHomeBinding
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_createHomeFragment_to_swipeMenuFragment)
        }

        binding.buttonCreateNewHome.setOnClickListener {
            createNewHome()
        }
    }

    private fun createNewHome() {
        val user = FirebaseAuth.getInstance().currentUser
        val newHomePassword = binding.creatHomePasswordInput.text.toString()
        val newHomeName = binding.homeNameInput.text.toString()
        val rooms: MutableList<Room> = mutableListOf()
        val users: MutableList<String?> = mutableListOf()
        if(chechFields()) {
            users.add(user?.uid)

            if(chechIfUserHasHome(user)){
                    //TODO: If has home true then delete previous home

            }
            else{
                val newHome = Home(1, newHomePassword, newHomeName, rooms, users)
                val dbRef = firestore.collection("homes").document(newHomeName)

                //TODO: add Snackbar binding.root???
                dbRef.set(newHome)
                    .addOnSuccessListener {
                        findNavController().navigate(R.id.action_createHomeFragment_to_swipeMenuFragment)
                        // Snackbar.make(binding.root,"New home successfully added!", Snackbar.LENGTH_LONG).show()
                    }
            }
        }
        else{
            Snackbar.make(binding.root,"Please fill out all the fields!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun chechIfUserHasHome(user: FirebaseUser?): Boolean{
        var value = false
         firestore.collection("homes")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    val currDoc = document.toObject<Home>()
                    if(currDoc.joinedUsers?.get(0) == user?.uid) {
                        return@addOnSuccessListener
                    }
                }
            }
        return value
    }

    private fun chechFields(): Boolean{
        return !(binding.homeNameInput.text.isEmpty() || binding.creatHomePasswordInput.text.isEmpty())
    }

    private fun togglePasswordVisibility() {
        var toggleVisibility = false
        binding.passwordCreateHomeToggleVisibility.setOnClickListener {
            if (!toggleVisibility) {
                binding.passwordCreateHomeToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.creatHomePasswordInput.setTransformationMethod(null)
                toggleVisibility = true
            } else {
                binding.passwordCreateHomeToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.creatHomePasswordInput.setTransformationMethod(PasswordTransformationMethod())
                toggleVisibility = false
            }
        }
    }
}
