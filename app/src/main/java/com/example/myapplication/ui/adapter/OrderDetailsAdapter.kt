//package com.example.myapplication.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.myapplication.data.OrderItem
//import com.example.myapplication.R
//import com.example.myapplication.data.Order
//import com.example.myapplication.network.RetrofitClient
//import com.example.myapplication.service.OrderService
//
//class OrderDetailsAdapter(private val items: List<OrderItem>) :
//    RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailsViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order_row, parent, false)
//        return OrderDetailsViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: OrderDetailsViewHolder, position: Int) {
//        val item = items[position]
//        val orderService  = OrderService(RetrofitClient.instance, this@OrderDetailsAdapter)
//        val order = orderService.getOrderById()
//        holder.bind(item , order)
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    class OrderDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        private val tvItemName: TextView = view.findViewById(R.id.tvItemName)
//        private val tvUnitPrice: TextView = view.findViewById(R.id.tvUnitPrice)
//        private val tvQuantity: TextView = view.findViewById(R.id.tvQuantity)
//        private val tvItemTotalPrice: TextView = view.findViewById(R.id.tvItemTotalPrice)
//
//        // 추가된 정보들을 위한 TextView
//        private val tvStoreName: TextView = view.findViewById(R.id.tvStoreName)
//        private val tvOrderDate: TextView = view.findViewById(R.id.tvOrderDate)
//        private val tvEstimatedTime: TextView = view.findViewById(R.id.tvEstimatedTime)
//        private val tvWaitingTime: TextView = view.findViewById(R.id.tvWaitingTime)
//        private val tvCrowdLevel: TextView = view.findViewById(R.id.tvCrowdLevel)
//
//        fun bind(item: OrderItem , order: Order) {
//            tvItemName.text = item.name
//            tvUnitPrice.text = "${item.unitPrice}원"
//            tvQuantity.text = "${item.quantity}개"
//            tvItemTotalPrice.text = "${item.unitPrice * item.quantity}원" // 총 금액 계산
//
//
//            // 추가된 정보들
//            tvStoreName.text = "상호명: ${order.storeName}"
//            tvOrderDate.text = "주문일시: ${order.orderDate}"
//            tvEstimatedTime.text = "예상시간: ${order.estimatedTime}"
//            tvWaitingTime.text = "대기시간: ${order.waitingTime}"
//            tvCrowdLevel.text = "혼잡도: ${order.crowdLevel}"
//        }
//    }
//}
