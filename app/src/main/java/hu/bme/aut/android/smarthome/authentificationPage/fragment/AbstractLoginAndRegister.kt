package hu.bme.aut.android.smarthome.authentificationPage.fragment

import android.app.ProgressDialog
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Suppress("DEPRECATION")
abstract class AbstractLoginAndRegister : Fragment() {

    private var progressDialog: ProgressDialog? = null

    private val firebaseUser: FirebaseUser?
        get() = FirebaseAuth.getInstance().currentUser

    protected val uid: String?
        get() = firebaseUser?.uid

    protected val userName: String?
        get() = firebaseUser?.displayName

    protected val userEmail: String?
        get() = firebaseUser?.email


    fun showProgressDialog() {
        if (progressDialog != null) {
            return
        }

        progressDialog = ProgressDialog(activity).apply {
            setCancelable(false)
            setMessage("Loading...")
            show()
        }
    }

    protected fun hideProgressDialog() {
        progressDialog?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
        progressDialog = null
    }
}