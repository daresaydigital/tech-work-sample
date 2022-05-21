package ir.sass.base_ui.utils

import android.view.View
import androidx.databinding.BindingAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@BindingAdapter("ext:click")
fun setClick(view : View,action : () ->  Unit){
    view.setOnClickListener {
        CoroutineScope(Dispatchers.Main).launch {
            it.isClickable = false
            action.invoke()
            delay(200)
            it.isClickable = true
        }
    }
}