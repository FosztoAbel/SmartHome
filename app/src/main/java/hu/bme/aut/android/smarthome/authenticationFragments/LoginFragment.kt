package hu.bme.aut.android.smarthome.authenticationFragments

import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import hu.bme.aut.android.smarthome.MainMenuActivity
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentLoginBinding


class LoginFragment : AbstractLoginAndRegister() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        togglePasswordVisibility()

        binding.loginButton.setOnClickListener {
            loginClick()
        }
        binding.registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createNewaccountFragment)
        }
        binding.forgotPasswordTV.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

    }

    private fun togglePasswordVisibility() {
        var toggleVisibility = false
        binding.passwordLoginToggleVisibility.setOnClickListener {
            if (!toggleVisibility) {
                binding.passwordLoginToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.passwordInput.setTransformationMethod(null)
                toggleVisibility = true
            } else {
                binding.passwordLoginToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.passwordInput.setTransformationMethod(PasswordTransformationMethod())
                toggleVisibility = false
            }
        }
    }

    private fun validateForm(): Boolean {
        if (binding.emailInput.text.isEmpty()) {
            return false
        }
        if (binding.passwordInput.text.isEmpty()) {
            return false
        }
        return true
    }


    private fun loginClick() {
        if (!validateForm()) {
            Snackbar.make(binding.root,"Empty fields!",Snackbar.LENGTH_LONG).show()
            return
        }

        showProgressDialog()

        firebaseAuth
            .signInWithEmailAndPassword(binding.emailInput.text.toString(), binding.passwordInput.text.toString())
            .addOnSuccessListener {
                hideProgressDialog()

                val intent = Intent(activity, MainMenuActivity::class.java)
                startActivity(intent)

            }
            .addOnFailureListener { exception ->
                hideProgressDialog()

                Snackbar.make(binding.root,exception.message.toString(),Snackbar.LENGTH_LONG).show()
            }
    }
}