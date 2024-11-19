package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.CartItem


class CartAdapter(
    private val items: MutableList<CartItem>,
    private val onItemChanged: () -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.item_name)
        val priceTextView: TextView = itemView.findViewById(R.id.item_price)
        val quantityTextView: TextView = itemView.findViewById(R.id.item_quantity)
        val minusButton: Button = itemView.findViewById(R.id.minus_button)
        val plusButton: Button = itemView.findViewById(R.id.plus_button)
        val deleteButton: ImageView = itemView.findViewById(R.id.delete_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.nameTextView.text = item.name
        holder.priceTextView.text = "가격: ${item.price}원"
        holder.quantityTextView.text = item.quantity.toString()

        holder.minusButton.setOnClickListener {
            if (item.quantity > 0) {
                item.quantity--
                onItemChanged()
                notifyItemChanged(position)
            }
        }

        holder.plusButton.setOnClickListener {
            item.quantity++
            onItemChanged()
            notifyItemChanged(position)
        }

        holder.deleteButton.setOnClickListener {
            items.removeAt(position)
            onItemChanged()
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = items.size
}
