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
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateHomeBinding
import hu.bme.aut.android.smarthome.model.Home
import hu.bme.aut.android.smarthome.model.Room


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateHomeFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentCreateHomeBinding
    val firestore = Firebase.firestore

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
        binding = FragmentCreateHomeBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var toggleVisibility = false

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_createHomeFragment_to_swipeMenuFragment)
        }
        binding.passwordCreateHomeToggleVisibility.setOnClickListener {
            if(!toggleVisibility) {
                binding.passwordCreateHomeToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.creatHomePasswordInput.setTransformationMethod(null)
                toggleVisibility = true
            }
            else {
                binding.passwordCreateHomeToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.creatHomePasswordInput.setTransformationMethod(PasswordTransformationMethod())
                toggleVisibility = false
            }
        }
        binding.buttonCreateNewHome.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser
            val newHomePassword = binding.creatHomePasswordInput.text.toString()
            val newHomeName = binding.homeNameInput.text.toString()
            val rooms : MutableList<Room> = mutableListOf()
            val users : MutableList<String?> = mutableListOf()
            users.add(user?.uid)
            val newHome = Home(1,newHomePassword, newHomeName, rooms, users)
            val dbRef = firestore.collection("homes").document(newHomeName)

            //TODO: add Snackbar binding.root???
            dbRef.set(newHome)
                .addOnSuccessListener {
                   // Snackbar.make(binding.root,"New home successfully added!", Snackbar.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                   // Snackbar.make(binding.root,"Cannot add new home!", Snackbar.LENGTH_LONG).show()
                }


            findNavController().navigate(R.id.action_createHomeFragment_to_swipeMenuFragment)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}