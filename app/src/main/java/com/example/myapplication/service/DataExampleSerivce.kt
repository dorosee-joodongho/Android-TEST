package com.example.myapplication.service

import com.example.myapplication.data.Menu
import com.example.myapplication.network.APICall
import com.example.myapplication.network.APIService
import com.example.myapplication.network.RetrofitClient

class DataExampleService {

    private val apiService = RetrofitClient.instance.create(APIService::class.java)
    private val apiCall = APICall(apiService)

    // GET request example
    suspend fun getTest() {
        val menuResponse: List<Menu>? = apiCall.apiCall("GET", "/api/v1/menus/special")

        if (menuResponse != null) {
            println("Menus: $menuResponse")
        } else {
            println("Failed to retrieve menu.")
        }
    }

    // POST request example
    suspend fun sendMenu() {

        val newMenu = Menu(
            id = 7,
            name = "추천 메뉴 7",
            price = 9500,
            menuImg = "https://img.freepik.com/free-vector/flat-design-korean-food-illustration_23-2149285208.jpg",
            storeId = 7
        )


        println(apiCall.apiCall("POST", "/api/v1/menus", newMenu))

        val response: Menu? = apiCall.apiCall("POST", "/api/v1/menus", newMenu)

        if (response != null) {
            println("New menu added: $response")
        } else {
            println("Failed to add new menu.")
        }
    }
}

suspend fun main() {
    val dataExampleService = DataExampleService()
    dataExampleService.getTest()  // Get menus (GET request)
    dataExampleService.sendMenu()  // Send a new menu (POST request)
}
