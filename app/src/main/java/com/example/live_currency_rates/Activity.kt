package com.example.live_currency_rates

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.live_currency_rates.ui.RatesFragment
import com.example.live_currency_rates.ui.RatesVM

/**
 * Created by Marinos Zinonos on 04/03/2022.
 */

class Activity : AppCompatActivity() {

    private val vm: RatesVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RatesFragment())
                .commitNow()
        }

        vm.timeUntilUpdate.observe(this) {
            it?.let {
                findViewById<TextView>(R.id.timer)?.text = it
            }
        }
    }
}