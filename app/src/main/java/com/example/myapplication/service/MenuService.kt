package com.example.myapplication.service

import com.example.myapplication.data.Menu

class MenuService {

    //오늘의 추천 메뉴 데이터 가지고 오기 10개
    fun getTodayBestMenu() : List<Menu> {
        return listOf(
            Menu(1 , "추천 메뉴 1" , 8000 , "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg" , 1),
            Menu(2 , "추천 메뉴 2" , 8200 , "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",2),
            Menu(3 , "추천 메뉴 3" , 7600 , "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",3),
            Menu(4 , "추천 메뉴 4" , 5500 , "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",4),
            Menu(5 , "추천 메뉴 5" , 2400 , "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",5),
            Menu(6 , "추천 메뉴 6" , 6600 , "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",6),
        )
    }
}