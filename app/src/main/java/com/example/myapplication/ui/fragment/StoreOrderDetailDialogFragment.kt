package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R
import com.example.myapplication.data.storeOrder.StoreOrderDetail

class StoreOrderDetailDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store_order_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val status = arguments?.getString("status")
        val orderId = arguments?.getLong("orderId")
        val orderDetail = arguments?.getParcelable<StoreOrderDetail>("orderDetail")
        val totalPayment = orderDetail?.menuList?.sumOf { it.menuPrice * it.quantity }

        view.findViewById<TextView>(R.id.storeOrderStatusTextView).text = "${status}"

        view.findViewById<TextView>(R.id.orderDateTextView).text =
            "주문 일자: ${orderDetail?.orderDate}"

        view.findViewById<TextView>(R.id.customerNameTextView).text =
            "고객명: ${orderDetail?.customerName}"

        view.findViewById<TextView>(R.id.customerPhoneNumberTextView).text =
            "전화번호: ${orderDetail?.customerPhoneNumber}"

        view.findViewById<TextView>(R.id.totalPaymentAmountTextView).text = "${totalPayment}"

        // 메뉴 항목들
        val menuItemsTableLayout = view.findViewById<android.widget.TableLayout>(R.id.menuItemsTableLayout)

        // 메뉴 항목 텍스트 추가
        orderDetail?.menuList?.forEach { item ->
            val tableRow = LayoutInflater.from(context).inflate(R.layout.table_row_item, null) as TableRow

            tableRow.findViewById<TextView>(R.id.menuNameTextView).text = item.menuName
            tableRow.findViewById<TextView>(R.id.menuPriceTextView).text = item.menuPrice.toString()
            tableRow.findViewById<TextView>(R.id.menuQuantityTextView).text = item.quantity.toString()
            tableRow.findViewById<TextView>(R.id.menuTotalAmountTextView).text = (item.menuPrice * item.quantity).toString()

            menuItemsTableLayout.addView(tableRow)
        }

        // 취소 버튼 설정
        val cancelOrderButton = view.findViewById<Button>(R.id.cancelOrderButton)
        if (status == "취소") {
            cancelOrderButton.visibility = View.GONE
        } else {
            cancelOrderButton.setOnClickListener {
                cancelOrder(status)
            }
        }

        // Dialog 크기 설정
        val dialog = dialog
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun cancelOrder(orderStatus: String?) {
        // 주문 취소 로직
        Toast.makeText(requireContext(), "주문 취소: $orderStatus", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(orderDetail: StoreOrderDetail, status: String, orderId: Long): StoreOrderDetailDialogFragment {
            val fragment = StoreOrderDetailDialogFragment()
            val args = Bundle()
            args.putParcelable("orderDetail", orderDetail)
            args.putString("status", status)
            args.putLong("orderId", orderId)
            fragment.arguments = args
            return fragment
        }
    }
}
