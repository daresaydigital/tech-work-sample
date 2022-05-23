package ir.sass.base_ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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

/**
 * this is the base class for all fragments
 * @param setting is for setting of the fragment, it holds layout and title
 * @param DataBinding is generic type for dataBinding
 * @param DataType is generic type for data type
 *
 * @property connectViewModelForError is a function which connect fragment to MotherViewModel for collecting errors
 * @property connectViewModelForLoading is a function which connect fragment to MotherViewModel for collecting loading states
 * @property connectViewModelForLoadingAndError is a wrapper function for doing both above functions
 * @property binding you will write your code here, you must override this function
 * @property loadingOn will manually show loading
 * @property loadingOff will manually dismiss loading
 * @property coroutinesLauncher you can handle your suspend functions here,it takes one parameter which is named
state and that's from Lifecycle.State, you will pass your action and the
action will be invoked
 */

abstract class MotherFragment<DataBinding : ViewDataBinding>(
    private val setting: MotherFragmentSetting
) : Fragment(R.layout.fragment_base) {

    private lateinit var dataBindingOuter: FragmentBaseBinding
    protected lateinit var dataBinding: DataBinding

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
            LayoutInflater.from(requireContext()), setting.layout, dataBindingOuter.linAdd, true
        )
        binding()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as NavcontrollerHelper).onBack()
            }
        };
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    fun connectViewModelForLoadingAndError(viewModel: MotherViewModel) {
        connectViewModelForError(viewModel)
        connectViewModelForLoading(viewModel)
    }

    fun connectViewModelForError(viewModel: MotherViewModel) {
        coroutinesLauncher(Lifecycle.State.STARTED) {
            viewModel.error.collect {
                // todo show error
            }
        }
    }

    fun connectViewModelForLoading(viewModel: MotherViewModel) {
        coroutinesLauncher(Lifecycle.State.RESUMED) {
            viewModel.loading.collect {
                if (it) loadingOn() else loadingOff()
            }
        }
    }

    abstract fun binding()


    fun loadingOn() {
        dataBindingOuter.loading = true
    }

    fun loadingOff() {
        dataBindingOuter.loading = false
    }

    fun coroutinesLauncher(state: Lifecycle.State, action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                action.invoke()
            }
        }
    }

    fun getParentNavControllerHelper() = (requireActivity() as NavcontrollerHelper)


}

/**
 * this is the setting class for MotherFragment
 * @param layout is layout reference
 * @param title is title of the fragment
 */

class MotherFragmentSetting(
    @LayoutRes
    val layout: Int,
    val title: String
)