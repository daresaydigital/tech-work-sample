package com.arashafsharpour.daresaymovie.infrastructure.platform

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.arashafsharpour.daresaymovie.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel, DB : ViewDataBinding> :
    BottomSheetDialogFragment() {

    protected abstract val layoutResourceId: Int
    protected open lateinit var binding: DB
    protected abstract val viewModel: VM
    private lateinit var dialog: BottomSheetDialog
    private lateinit var behavior: BottomSheetBehavior<View>

    protected abstract fun initializeBindingVariables()
    protected open fun subscribeSpecificObservables() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBottomSheetDialogStyle()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        setupBottomSheetDialogScrollable()
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, layoutResourceId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initializeBindingVariables()
        subscribeSpecificObservables()
        return binding.root
    }

    private fun setBottomSheetDialogStyle() {
        setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.BottomSheetDialogStyle
        )
    }

    private fun setupBottomSheetDialogScrollable() {
        dialog.setOnShowListener { dialog ->
            val bottomSheetDialog = dialog as BottomSheetDialog
            val designBottomSheet =
                bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as View
            behavior = BottomSheetBehavior.from(designBottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.isDraggable = false
            behavior.isHideable = false
        }
    }
}