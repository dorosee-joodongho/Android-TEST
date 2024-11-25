package com.example.myapplication.service

import com.example.myapplication.data.Menu
import com.example.myapplication.network.APICall
import com.example.myapplication.network.APIService
import com.example.myapplication.network.RetrofitClient

class MenuService {

//    private val apiService = RetrofitClient.instance.create(APIService::class.java)
//    private val apiCall = APICall(apiService)


    //오늘의 추천 메뉴 데이터 가지고 오기 10개
    fun getTodayBestMenu() : List<Menu> {
//         val todayBestMenuResponse : List<Menu>? = apiCall.apiCall("GET", "store/special")

//        println(todayBestMenuResponse)
//
//        if (todayBestMenuResponse != null) {
//            return todayBestMenuResponse
//        } else {
//            println("Failed to retrieve menu.")
//        }

        return mutableListOf()
    }
}