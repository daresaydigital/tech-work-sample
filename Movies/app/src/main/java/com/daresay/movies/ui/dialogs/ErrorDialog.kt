package com.daresay.movies.ui.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.daresay.movies.R
import com.daresay.movies.databinding.DialogErrorBinding
import com.daresay.movies.databinding.FragmentHomeViewPagerBinding

class ErrorDialog(context: Context, private val errorMessage: String) : Dialog(context) {
    private lateinit var binding: DialogErrorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_error)

        binding.errorText.text = errorMessage
        binding.close.setOnClickListener {
            dismiss()
        }
    }
}