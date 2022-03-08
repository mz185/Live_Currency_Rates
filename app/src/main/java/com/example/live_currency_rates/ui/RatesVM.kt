package com.example.live_currency_rates.ui

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.live_currency_rates.data.API
import kotlin.math.roundToInt

/**
 * Created by Marinos Zinonos on 04/03/2022.
 */

class RatesVM : ViewModel() {
    private val String.middleSlash get() =
        try {
            substring(0, length / 2) + '/' + substring(length / 2)
        } catch (e: Exception) {
            apply {
                e.printStackTrace()
            }
        }

    private val Double.maxFourDecimals get() =
        try {
            ((this * 10000.0).roundToInt() / 10000.0)
        } catch (e: Exception) {
            apply {
                e.printStackTrace()
            }
        }

    private var prices = listOf<Double>()

    var currencyPairs = listOf<String>()
    var colors = listOf<Int>()

    val updatedPrices = MutableLiveData<List<String>>()
    val timeUntilUpdate = MutableLiveData<String>()

    fun updateTimer(timeLeft: Long) {
        timeUntilUpdate.value =
            (timeLeft / 1000 % 60).toString()
    }

    suspend fun getRates(onSuccess: suspend () -> Unit) =
        API.createEndpoint().getRates().rates.run {
            if (isNotEmpty()) {
                updatedPrices.value.isNullOrEmpty().let { isInit ->

                    // Because symbols from API never change
                    if (isInit)
                        currencyPairs = map {
                            it.symbol.middleSlash
                        }

                    if (!isInit)
                        colors = mapIndexed { i, rate ->
                            if (rate.price >= prices[i])
                                Color.GREEN
                            else Color.RED
                        }

                    updatedPrices.value = mapIndexed { i, rate ->
                        rate.price.maxFourDecimals.run {
                            if (!isInit)
                                appendDiffPercent(prices[i])
                            else toString()
                        }
                    }

                    prices = map {
                        it.price
                    }

                    onSuccess()
                }
            }
    }


        private fun Double.appendDiffPercent(oldPrice: Double) =
            try {
                ((this - oldPrice) / oldPrice * 100).maxFourDecimals.let { diff ->
                    StringBuilder(toString())
                        .append("\n(${
                            if (diff > 0) "+" else ""
                        }${ diff }%)").toString()
                }
            } catch (e: Exception) {
                toString().apply {
                    e.printStackTrace()
                }
            }
}
