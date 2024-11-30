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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
        holder.itemView.setOnClickListener { onOrderClick(order) }
    }

    override fun getItemCount(): Int = orders.size

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvOrderName: TextView = view.findViewById(R.id.tvOrderName)

        fun bind(order: Order) {
            tvOrderName.text = order.storeName
        }
    }
}
