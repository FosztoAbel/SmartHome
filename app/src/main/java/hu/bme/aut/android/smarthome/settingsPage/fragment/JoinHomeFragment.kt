package hu.bme.aut.android.smarthome.settingsPage.fragment

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
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentJoinHomeBinding
import hu.bme.aut.android.smarthome.roomsPage.model.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinHomeFragment : Fragment() {

    private lateinit var binding: FragmentJoinHomeBinding
    private val firestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentJoinHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_joinHomeFragment_to_swipeMenuFragment)
        }

        binding.buttonJoinHome.setOnClickListener {
            joinHome()
        }
    }

    private fun joinHome(){
        if(checkFields()) {
            val user = FirebaseAuth.getInstance().currentUser
            val homeName = binding.joinHomeNameInput.text.toString()
            val password = binding.joinHomePasswordInput.text.toString()

            var isValid : Boolean
            firestore.collection("homes")
                .get()
                .addOnSuccessListener { result ->
                    isValid = false
                    for(document in result){
                        val currentDocument = document.toObject<Home>()
                        if(currentDocument.name == homeName && currentDocument.password == password){
                            for(documentDelete in result){
                                val currentDocumentDelete = documentDelete.toObject<Home>()
                                for(iterator in currentDocumentDelete.joinedUsers!!){
                                    if(iterator.equals(user?.uid)){
                                        val list = currentDocumentDelete.joinedUsers
                                        list.remove(iterator)
                                        firestore.collection("homes").document(currentDocumentDelete.id.toString())
                                            .update("joinedUsers", list)
                                    }
                                }
                            }
                            val list = currentDocument.joinedUsers
                            list?.add(user?.uid)
                            firestore.collection("homes").document(currentDocument.id.toString())
                                .update("joinedUsers", list)
                            isValid = true
                        }
                    }
                    showSnackBarAndNavigate(isValid)
                }
        }
        else{
            Snackbar.make(binding.root,"Please fill out all the fields!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showSnackBarAndNavigate(isValid: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (isValid) {Snackbar.make(
                binding.root, "Successfully joined a new home!", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_joinHomeFragment_to_swipeMenuFragment)
            }
            else Snackbar.make(
                binding.root, "This home does not exist or you entered wrong credentials!", Snackbar.LENGTH_LONG).show()
                //no need of navigation
        }
    }

    private fun checkFields(): Boolean{
        return !(binding.joinHomeNameInput.text.isEmpty() || binding.joinHomePasswordInput.text.isEmpty())
    }

    private fun togglePasswordVisibility() {
        var toggleVisibility = false
        binding.passwordJoinHomeToggleVisibility.setOnClickListener {
            toggleVisibility = if (!toggleVisibility) {
                binding.passwordJoinHomeToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.joinHomePasswordInput.transformationMethod = null
                true
            } else {
                binding.passwordJoinHomeToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.joinHomePasswordInput.transformationMethod = PasswordTransformationMethod()
                false
            }
        }
    }
}