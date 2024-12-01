package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.bind(order) // bind를 통해 데이터 설정
        holder.itemView.setOnClickListener { onOrderClick(order) } // 클릭 리스너 설정
    }

    override fun getItemCount(): Int = orders.size

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val storeNameTextView: TextView = view.findViewById(R.id.storeNameText)
        private val summaryTextView: TextView = view.findViewById(R.id.orderMenuText)
        private val tvOrderNameTextView : TextView = view.findViewById(R.id.tvOrderName)
        private val orderImage: ImageView = view.findViewById(R.id.orderImage)

        // bind 메서드에서 데이터를 설정
        fun bind(order: Order) {
            tvOrderNameTextView.text = order.storeName + " 주문"
            storeNameTextView.text = order.storeName  // 가게명 텍스트 설정
            summaryTextView.text = order.menuSummary  // 메뉴 요약 텍스트 설정

            Glide.with(itemView.context)  // itemView.context를 사용
                .load(order.orderImage)  // 실제 이미지 URL을 로드
                .into(orderImage)  // ImageView에 설정
        }
    }
}
