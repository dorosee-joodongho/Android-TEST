package com.example.myapplication.ui.manager

import com.example.myapplication.data.CartItem

object CartManager {
    private val cartItems = mutableListOf<CartItem>()

    // 장바구니에 항목 추가
    fun addItem(item: CartItem) {
        // 동일한 메뉴가 있으면 수량 업데이트
        val existingItem = cartItems.find { it.name == item.name && it.storeId == item.storeId }

        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cartItems.add(item)
        }
    }

    // 장바구니에서 항목 제거
    fun removeItem(itemName: String) {
        cartItems.removeAll { it.name == itemName }
    }

    // 장바구니 항목 업데이트
    fun updateItemQuantity(itemName: String, newQuantity: Int) {
        val item = cartItems.find { it.name == itemName }

        if (item != null) {
            item.quantity = newQuantity
            if (newQuantity <= 0) removeItem(itemName)
        }
    }

    // 장바구니 목록 가져오기
    fun getItems(): MutableList<CartItem> {
        return cartItems
    }

    // 장바구니 비우기
    fun clearCart() {
        cartItems.clear()
    }

    // 총 결제 금액 계산
    fun getTotalAmount(): Int {
        return cartItems.sumOf { it.price * it.quantity }
    }

    fun getItemQuantity(itemName: String , storeId : Long): Int {
        return cartItems.find { it.name == itemName && it.storeId == storeId }?.quantity ?: 1
    }


}
