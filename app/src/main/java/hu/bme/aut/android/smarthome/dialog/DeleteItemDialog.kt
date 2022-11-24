package hu.bme.aut.android.smarthome.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import hu.bme.aut.android.smarthome.databinding.DeleteDialogBinding

class DeleteItemDialog : CustomDialogFragment() {

    private lateinit var binding: DeleteDialogBinding
    private var positiveListener: OnClickListener? = null
    private var negativeListener: OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DeleteDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

//        arguments?.getInt(ARG_KEY_TITLE)?.let {
//            if (it != 0) {
//                binding.changeNameTitle.setText(it)
//            }
//        }
//        arguments?.getInt(ARG_KEY_BUTTON_POSITIVE)?.let {
//            if (it != 0) {
//                binding.cancelChangeBtn.setText(it)
//            }
//        }
//        arguments?.getInt(ARG_KEY_BUTTON_NEGATIVE)?.let {
//            if (it != 0) {
//                binding.cancelChangeBtn.setText(it)
//            }
//        }
        binding.deleteItemBtn.setOnClickListener {
            dismiss()
            positiveListener?.invoke()
        }
        binding.cancelDeleteBtn.setOnClickListener {
            dismiss()
            negativeListener?.invoke()
        }
    }

    fun setOnPositiveClickListener(listener: OnClickListener) {
        positiveListener = listener
    }

    fun setOnNegativeClickListener(listener: OnClickListener) {
        negativeListener = listener
    }

    companion object {
        const val TAG = "DeleteItemDialogInstance"
        private const val ARG_KEY_TITLE = "arg_key_title"
        private const val ARG_KEY_QUESTION ="arg_key_question"
        private const val ARG_KEY_BUTTON_POSITIVE = "arg_key_button_positive"
        private const val ARG_KEY_BUTTON_NEGATIVE = "arg_key_button_negative"


        fun newInstance(
            @StringRes questionResId: Int?,
            @StringRes titleResId: Int?,
            @StringRes positiveButtonResId: Int?,
            @StringRes negativeButtonResId: Int?
        ) = DeleteItemDialog().apply {
            arguments = Bundle().apply {
                titleResId?.let {
                    putInt(ARG_KEY_TITLE, it)
                }
                questionResId?.let{
                    putInt(ARG_KEY_QUESTION, it)
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
