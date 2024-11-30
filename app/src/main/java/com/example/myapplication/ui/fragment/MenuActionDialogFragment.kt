package com.example.myapplication.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.data.menu.Menu
import com.example.myapplication.databinding.DialogMenuActionBinding

class MenuActionDialogFragment(
    private val menu: Menu,
    private val onActionSelected: (Action) -> Unit
) : DialogFragment() {

    // 카트에 넣기랑 오더하기 enum 선택
    enum class Action {
        ADD_TO_CART,
        ORDER_NOW
    }

    // DialogFragment에서 뷰를 생성하는 메서드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // ViewBinding을 사용해 레이아웃을 바인딩
        val binding = DialogMenuActionBinding.inflate(inflater, container, false)

        // 버튼 클릭 리스너 설정
        binding.btnAddToCart.setOnClickListener {
            onActionSelected(Action.ADD_TO_CART)
            dismiss()  // 다이얼로그 닫기
        }

        binding.btnOrderNow.setOnClickListener {
            onActionSelected(Action.ORDER_NOW)
            dismiss()  // 다이얼로그 닫기
        }

        return binding.root
    }

    // 다이얼로그의 속성 설정
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(android.view.Window.FEATURE_NO_TITLE)  // 타이틀 바 없애기
        return dialog
    }
}
