package hu.bme.aut.android.smarthome.authentificationPage.fragment

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.*
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentCreateNewAccountBinding

class CreateNewAccountFragment : AbstractLoginAndRegister(){

    private lateinit var binding: FragmentCreateNewAccountBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateNewAccountBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_createNewaccountFragment_to_loginFragment)
        }
        binding.createNewAccountButton.setOnClickListener{
            registerClick()
        }
    }

    private fun togglePasswordVisibility() {
        var toggleVisibility = false
        binding.passwordCreateAccountToggleVisibility.setOnClickListener {
            toggleVisibility = if (!toggleVisibility) {
                binding.passwordCreateAccountToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.passwordRegisterInput.transformationMethod = null
                true
            } else {
                binding.passwordCreateAccountToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.passwordRegisterInput.transformationMethod = PasswordTransformationMethod()
                false
            }
        }
    }

        private fun validateForm(): Boolean {
        if (binding.fullNameInput.text.isEmpty()) {
            return false
        }

        if (binding.emailRegisterInput.text.isEmpty()) {
            return false
        }
        if (binding.passwordRegisterInput.text.isEmpty()) {
            return false
        }
        return true
    }

    private fun registerClick() {
        if (!validateForm()) {
            Snackbar.make(binding.root,"Empty fields!", Snackbar.LENGTH_LONG).show()
            return
        }

        showProgressDialog()

        firebaseAuth
            .createUserWithEmailAndPassword(binding.emailRegisterInput.text.toString(), binding.passwordRegisterInput.text.toString())
            .addOnSuccessListener { result ->
                hideProgressDialog()

                val firebaseUser = result.user

                val profileChangeRequest = UserProfileChangeRequest.Builder()
                    .setDisplayName(binding.fullNameInput.text.toString())
                    .build()
                firebaseUser?.updateProfile(profileChangeRequest)

                Snackbar.make(binding.root,"Registration successful!",Snackbar.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_createNewaccountFragment_to_loginFragment)
            }
            .addOnFailureListener { exception ->
                hideProgressDialog()

                Snackbar.make(binding.root,exception.message.toString(),Snackbar.LENGTH_LONG).show()
            }
    }
}