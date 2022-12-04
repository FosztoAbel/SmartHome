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
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentChangeHomeNameBinding
import hu.bme.aut.android.smarthome.roomsPage.model.Home
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChangeHomeNameFragment : Fragment() {

    private lateinit var binding : FragmentChangeHomeNameBinding
    private val firestore = Firebase.firestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeHomeNameBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_changeHomeNameFragment_to_swipeMenuFragment)
        }

        binding.buttonRenameHome.setOnClickListener {
            renameHome()
        }
    }

    private fun renameHome(){
        if(checkFields()) {
            val user = FirebaseAuth.getInstance().currentUser
            val homeNewName = binding.renameHomeNameInput.text.toString()
            val password = binding.renameHomePasswordInput.text.toString()

            var isValid : Boolean
            CoroutineScope(Dispatchers.IO).launch {
                isValid = false
                val homes = firestore.collection("homes")
                    .get()
                    .await()
                    .toObjects<Home>()
                for (home in homes) {
                    for (iterator in home.joinedUsers!!) {
                        if (iterator.equals(user?.uid)) {
                            if(home.password == password){
                           firestore.collection("homes").document(home.id.toString()).update("name",homeNewName)
                            isValid = true
                            }
                        }
                    }
                    showSnackBarAndNavigate(isValid)
                }
            }
        }
        else{
            Snackbar.make(binding.root,"Please fill out all the fields!", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun showSnackBarAndNavigate(isValid: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (isValid) {
                Snackbar.make(
                binding.root, "Successfully renamed your home!", Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_changeHomeNameFragment_to_swipeMenuFragment)
            }
            else Snackbar.make(
                binding.root, "You entered a wrong password!", Snackbar.LENGTH_LONG).show()
            //no need of navigation
        }
    }

    private fun checkFields(): Boolean{
        return !(binding.renameHomeNameInput.text.isEmpty() || binding.renameHomePasswordInput.text.isEmpty())
    }

    private fun togglePasswordVisibility() {
        var toggleVisibility = false
        binding.passwordRenameHomeToggleVisibility.setOnClickListener {
            toggleVisibility = if (!toggleVisibility) {
                binding.passwordRenameHomeToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.renameHomePasswordInput.transformationMethod = null
                true
            } else {
                binding.passwordRenameHomeToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.renameHomePasswordInput.transformationMethod = PasswordTransformationMethod()
                false
            }
        }
    }
}