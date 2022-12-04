package hu.bme.aut.android.smarthome.profilePage

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.authentificationPage.activity.LoginActivity
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentProfileMenuBinding

@Suppress("NAME_SHADOWING")
class ProfileFragment : Fragment() {

    private lateinit var binding :FragmentProfileMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val fullNameOfUser = user?.displayName.toString()
        val emailOfUser = user?.email.toString()
        val phoneNumberOfUser = user?.phoneNumber.toString()
        var toggleVisibility = false

        binding.arrowImage.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment_to_swipeMenuFragment)
        }

        binding.logOutImage.setOnClickListener {
            Firebase.auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.fullNameProfileInput.hint = fullNameOfUser

        binding.emailProfileInput.hint = emailOfUser

        binding.passwordProfileToggleVisibility.setOnClickListener {
            toggleVisibility = if(!toggleVisibility) {
                binding.passwordProfileToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
                binding.changePasswordInput.transformationMethod = null
                true
            } else {
                binding.passwordProfileToggleVisibility.setImageResource(R.drawable.ic_visibility)
                binding.changePasswordInput.transformationMethod = PasswordTransformationMethod()
                false
            }
        }

        binding.buttonSaveChanges.setOnClickListener{

            val user = Firebase.auth.currentUser


            if(binding.emailProfileInput.text.isEmpty() || binding.fullNameProfileInput.text.isEmpty() || binding.changePasswordInput.text.toString().isEmpty()){
                Snackbar.make(binding.root,"Fill out all the fields!", Snackbar.LENGTH_LONG).show()
            }
            else{
                //change full name of user
                val profileUpdates = userProfileChangeRequest {
                    displayName = binding.fullNameProfileInput.text.toString()
                }
                user!!.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User profile updated.")
                        }
                    }

                //change email of user
                user.updateEmail(binding.emailProfileInput.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User email address updated.")
                        }
                    }

                //change password of user
                val newPassword = binding.changePasswordInput.text.toString()

                user.updatePassword(newPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User password updated.")
                        }
                    }
                //TODO: maybe start Login activity to apply the changes
                findNavController().navigate(R.id.action_profileFragment_to_swipeMenuFragment)

            }
        }
    }
}

