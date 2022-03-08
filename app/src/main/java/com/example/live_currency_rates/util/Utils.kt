package com.example.live_currency_rates.util

import android.app.Activity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.live_currency_rates.R
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Marinos Zinonos on 04/03/2022.
 */

fun Fragment.showError(s: String?) = activity?.showError(s)
fun Activity.showError(s: String?) = s?.run {
    findViewById<FragmentContainerView>(R.id.container)?.let {
        Snackbar.make(it, this, Snackbar.LENGTH_LONG).apply {
            view.findViewById<TextView>(
                com.google.android.material.R.id.snackbar_text
            )?.maxLines = Int.MAX_VALUE
        }.show()
    }
}
