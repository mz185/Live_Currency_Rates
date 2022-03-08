package com.example.live_currency_rates.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.live_currency_rates.R
import com.example.live_currency_rates.util.showError
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Created by Marinos Zinonos on 04/03/2022.
 */

class RatesFragment : Fragment() {

    private val vm: RatesVM by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.rates_fragment, container, false)

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val timer = object : CountDownTimer(10000, 1000) {
            override fun onFinish() = Unit
            override fun onTick(timeLeft: Long) = vm.updateTimer(timeLeft)
        }

        view.findViewById<RecyclerView>(R.id.recycler)?.apply {
            adapter = RatesAdapter()
            vm.updatedPrices.observe(viewLifecycleOwner) {
                (adapter as? RatesAdapter)?.apply {
                    currencyPairs = vm.currencyPairs
                    prices = it
                    colors = vm.colors
                    notifyDataSetChanged()
                }
            }
        }

        // Non-blocking coroutine scoped to fragment's view lifecycle
        viewLifecycleOwner.lifecycleScope.launch {
            var isErrorShown = false
            while (isActive) {
                try {
                    vm.getRates{
                        isErrorShown = false
                        timer.start()
                        delay(10000)
                    }
                } catch (e: Exception) {
                    if (!isErrorShown) {
                        isErrorShown = true
                        showError(e.message)
                    }
                    delay(1000)
                }
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}