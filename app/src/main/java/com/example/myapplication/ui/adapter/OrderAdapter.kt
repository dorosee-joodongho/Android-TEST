package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.Order
import com.example.myapplication.R

class OrderAdapter(
    private val orders: List<Order>,
    private val onOrderClick: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderDateText: TextView = view.findViewById(R.id.orderDateText)
        val storeNameText: TextView = view.findViewById(R.id.storeNameText)
        val orderMenuText: TextView = view.findViewById(R.id.orderMenuText)
        val orderAmountText: TextView = view.findViewById(R.id.orderAmountText)

        init {
            view.setOnClickListener {
                val order = orders[adapterPosition]
                onOrderClick(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderDateText.text = order.date
        holder.storeNameText.text = order.storeName
        holder.orderMenuText.text = order.menuSummary
        holder.orderAmountText.text = "₩${order.amount}"
    }

    override fun getItemCount(): Int = orders.size
}
