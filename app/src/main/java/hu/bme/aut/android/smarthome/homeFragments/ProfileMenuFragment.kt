package hu.bme.aut.android.smarthome.homeFragments

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import hu.bme.aut.android.smarthome.LoginActivity
import hu.bme.aut.android.smarthome.MainMenuActivity
import hu.bme.aut.android.smarthome.R
import hu.bme.aut.android.smarthome.databinding.FragmentProfileMenuBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileMenuBinding
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        binding = FragmentProfileMenuBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        val fullNameOfUser = user?.displayName.toString()
        val emailOfUser = user?.email.toString()
        val phoneNumberOfUser = user?.phoneNumber.toString()
        var toggleVisibility = true

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

//        binding.passwordToggleVisibility.setOnClickListener {
//            if(toggleVisibility) {
//                binding.passwordToggleVisibility.setImageResource(R.drawable.ic_visibility_off)
//                binding.changePasswordInput.setInputType(InputType.TYPE_CLASS_TEXT)
//                binding.changePasswordInput.setEms(17)
//                toggleVisibility = false
//            }
//            else {
//                binding.passwordToggleVisibility.setImageResource(R.drawable.ic_visibility)
//                binding.changePasswordInput.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
//                binding.changePasswordInput.setEms(17)
//                toggleVisibility = true
//            }
//        }

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
                user!!.updateEmail(binding.emailProfileInput.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User email address updated.")
                        }
                    }

                //change password of user
                val newPassword = binding.changePasswordInput.text.toString()

                user!!.updatePassword(newPassword)
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

