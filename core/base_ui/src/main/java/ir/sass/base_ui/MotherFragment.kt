package ir.sass.base_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import ir.sass.base_ui.databinding.FragmentBaseBinding
import ir.sass.navigator.flow.features.NavcontrollerHelper
import kotlinx.coroutines.launch


abstract class MotherFragment<DataBinding : ViewDataBinding>(
    private val setting : MotherFragmentSetting
) : Fragment(R.layout.fragment_base) {

    private lateinit var dataBindingOuter : FragmentBaseBinding
    protected lateinit var dataBinding : DataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBindingOuter = FragmentBaseBinding.inflate(inflater)
        dataBindingOuter.title = setting.title
        return dataBindingOuter.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),setting.layout,dataBindingOuter.linAdd,true
        )
        binding()
    }

    fun connectViewModelForLoadingAndError(viewModel: MotherViewModel){
        connectViewModelForError(viewModel)
        connectViewModelForLoading(viewModel)
    }

    fun connectViewModelForError(viewModel: MotherViewModel){
        coroutinesLauncher(Lifecycle.State.STARTED){
            viewModel.error.collect {
                 // todo show error
            }
        }
    }

    fun connectViewModelForLoading(viewModel: MotherViewModel){
        coroutinesLauncher(Lifecycle.State.RESUMED){
            viewModel.loading.collect {
                if(it) loadingOn() else loadingOff()
            }
        }
    }

    /*you will write your code here by overriding this function*/
    abstract fun binding()


    /*2 functions for handling loading manual*/
    fun loadingOn(){
        dataBindingOuter.loading = true
    }

    fun loadingOff(){
        dataBindingOuter.loading = false
    }


    /*you can handle your suspend functions here,it takes one parameter which is named
    state and that's from Lifecycle.State, you will pass your action and the
    action will be invoked*/
    fun coroutinesLauncher(state : Lifecycle.State, action :suspend () -> Unit){
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state){
                action.invoke()
            }
        }
    }

    fun getParentNavControllerHelper() = (requireActivity() as NavcontrollerHelper)
}

class MotherFragmentSetting(
    @LayoutRes
    val layout : Int,
    val title : String
)