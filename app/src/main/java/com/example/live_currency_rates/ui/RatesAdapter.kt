package com.example.live_currency_rates.ui

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.live_currency_rates.R

/**
 * Created by Marinos Zinonos on 04/03/2022.
 */

class RatesAdapter : RecyclerView.Adapter<RatesAdapter.ItemHolder>() {

    inner class ItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        val symbolTv: TextView = item.findViewById(R.id.symbol_tv)
        val priceTv: TextView = item.findViewById(R.id.price_tv)
    }

    var currencyPairs = listOf<String>()
    var prices = listOf<String>()
    var colors = listOf<Int>()

    private val Int.positionColor get() =
        if (colors.isEmpty())
            Color.BLACK
        else colors[this]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.rate_item, parent, false))

    override fun onBindViewHolder(holder: ItemHolder, position: Int): Unit =
        holder.run {
            symbolTv.text = currencyPairs[position]
            priceTv.apply {
                text = prices[position]
                setTextColor(position.positionColor)
            }
        }

    override fun getItemCount() = currencyPairs.size
}