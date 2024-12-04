package com.example.myapplication.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.storeOrder.StoreOrderItem

class StoreOrderAdapter(
    var orders: List<StoreOrderItem>,
    private val onViewDetails: (StoreOrderItem) -> Unit
) : RecyclerView.Adapter<StoreOrderAdapter.StoreOrderViewHolder>() {

    class StoreOrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderIdTextView: TextView = view.findViewById(R.id.storeOrderIdTextView)
        val orderDateTextView: TextView = view.findViewById(R.id.storeOrderDateTextView)
        val totalPriceTextView: TextView = view.findViewById(R.id.storeTotalPriceTextView)
        val statusTextView: TextView = view.findViewById(R.id.storeStatusTextView)
        val viewDetailsButton: Button = view.findViewById(R.id.storeViewDetailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_store_order, parent, false)
        return StoreOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreOrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderIdTextView.text = "주문번호: ${order.orderId}"
        holder.statusTextView.text = order.status
        holder.orderDateTextView.text = order.orderDate
        holder.totalPriceTextView.text = "₩${order.totalPrice}"
        holder.viewDetailsButton.setOnClickListener { onViewDetails(order) }

        if (order.status == "cancel") {
            holder.statusTextView.setTextColor(Color.RED)
        }
    }

    fun updateData(newOrders: List<StoreOrderItem>) {
        orders = newOrders
        notifyDataSetChanged()
    }

    override fun getItemCount() = orders.size
}
