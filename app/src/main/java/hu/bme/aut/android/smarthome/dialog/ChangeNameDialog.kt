package hu.bme.aut.android.smarthome.dialog


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import hu.bme.aut.android.smarthome.databinding.ChangeNameDialogBinding

class ChangeNameDialog : CustomDialogFragment() {

    private lateinit var binding: ChangeNameDialogBinding
    private var positiveListener: OnClickListener? = null
    private var negativeListener: OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ChangeNameDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        arguments?.getInt(ARG_KEY_TITLE)?.let {
            if (it != 0) {
                binding.changeNameTitle.setText(it)
            }
        }
        arguments?.getString(ARG_KEY_DESCRIPTION)?.let {
            if (it.isNotEmpty()) {
                binding.newNameTV.text = it
            }
        }
        arguments?.getString(ARG_KEY_INPUT)?.let {
            if (it.isNotEmpty()) {
                //binding.changeNameInput.text = it.toString()
            }
        }
        arguments?.getInt(ARG_KEY_BUTTON_POSITIVE)?.let {
            if (it != 0) {
                binding.cancelChangeBtn.setText(it)
            }
        }
        arguments?.getInt(ARG_KEY_BUTTON_NEGATIVE)?.let {
            if (it != 0) {
                binding.cancelChangeBtn.setText(it)
            }
        }

        binding.changeNameBtn.setOnClickListener {
            dismiss()
            positiveListener?.invoke()
        }
        binding.cancelChangeBtn.setOnClickListener {
            dismiss()
            negativeListener?.invoke()
        }
    }
//
//    fun setOnPositiveClickListener(listener: OnClickListener) {
//        positiveListener = listener
//    }
//
//    fun setOnNegativeClickListener(listener: OnClickListener) {
//        negativeListener = listener
//    }

    companion object {
        const val TAG = "ChangeNameDialogInstance"
        private const val ARG_KEY_TITLE = "arg_key_title"
        private const val ARG_KEY_DESCRIPTION = "arg_key_description"
        private const val ARG_KEY_INPUT ="arg_key_input"
        private const val ARG_KEY_BUTTON_POSITIVE = "arg_key_button_positive"
        private const val ARG_KEY_BUTTON_NEGATIVE = "arg_key_button_negative"


        fun newInstance(
            @DrawableRes inputResId: Int?,
            @StringRes titleResId: Int?,
            description: String?,
            @StringRes positiveButtonResId: Int?,
            @StringRes negativeButtonResId: Int?
        ) = ChangeNameDialog().apply {
            arguments = Bundle().apply {
                titleResId?.let {
                    putInt(ARG_KEY_TITLE, it)
                }
                description?.let {
                    putString(ARG_KEY_DESCRIPTION, it)
                }
                inputResId?.let{
                    putString(ARG_KEY_INPUT, it.toString())
                }
                positiveButtonResId?.let {
                    putInt(ARG_KEY_BUTTON_POSITIVE, it)
                }
                negativeButtonResId?.let {
                    putInt(ARG_KEY_BUTTON_NEGATIVE, it)
                }
            }
        }
    }
}

typealias OnClickListener = () -> Unit