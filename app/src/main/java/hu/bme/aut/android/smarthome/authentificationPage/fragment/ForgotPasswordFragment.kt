package hu.bme.aut.android.smarthome.authentificationPage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

private lateinit var binding: FragmentForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowImage.setOnClickListener {
            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
        }
        binding.buttonSendCode.setOnClickListener {
            if(binding.emailForgotInput.text.isEmpty()){
                Snackbar.make(binding.root, "Enter a valid e-mail adress!", Snackbar.LENGTH_LONG).show()
            }
            else {
                val emailAddress = binding.emailForgotInput.text.toString()

                Firebase.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Snackbar.make(binding.root, "E-mail sent!", Snackbar.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                        } else {
                            Snackbar.make(binding.root, "Enter a valid e-mail adress!", Snackbar.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}