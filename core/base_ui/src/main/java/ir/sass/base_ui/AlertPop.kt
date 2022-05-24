package ir.sass.base_ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import ir.sass.base_ui.databinding.DialogAlertBinding

class AlertPop(
    context: Context, val setting : AlertPopSetting) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DialogAlertBinding.inflate(
            LayoutInflater.from(context),null,false
        )

        setContentView(binding.root)

        binding.action = {
            setting.buttonAction.invoke()
            dismiss()
        }
        binding.message = setting.message
        binding.button = setting.buttonText

        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }
}

fun showAlertDialog(context: Context,setting: AlertPopSetting){
    val alert = AlertPop(context,setting)
    alert.show()
}

class AlertPopSetting(
    val message : String,
    val buttonText : String = "Ok",
    val buttonAction : () -> Unit = {}
)