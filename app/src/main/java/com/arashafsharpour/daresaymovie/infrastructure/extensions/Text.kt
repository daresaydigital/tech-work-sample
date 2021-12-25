package com.arashafsharpour.daresaymovie.infrastructure.extensions

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*

@BindingAdapter("splitText")
fun splitYears(view: AppCompatTextView, text: String?) {
    view.text = text?.split("-")?.toTypedArray()?.get(0) ?: ""
}

@BindingAdapter("convertRate")
fun convertRate(view: AppCompatTextView, rate: Float?) {
    if (rate != null) {
        view.text = (rate * 10).toInt().toString() + "%"
    } else {
        view.text = "0%"
    }
}

@BindingAdapter("thousandSeparator")
fun thousandSeparator(view: AppCompatTextView, budget: Long?) {
    val filteredBudget = NumberFormat.getNumberInstance(Locale.US)
        .format(budget)
    view.text = "$filteredBudget$"
}


@BindingAdapter("convertDate")
fun convertDate(view: AppCompatTextView, date: String) {
    if (!date.isNullOrBlank()) {
        view.text = date.split("T")[0]
    }

}