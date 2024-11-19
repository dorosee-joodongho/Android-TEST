package com.example.myapplication.ui.activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class UserMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val backButton = findViewById<TextView>(R.id.backButton)

        backButton.text = "←  사용자 목록" // 헤더 제목 변경

        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }

        val btnDietRecord: Button = findViewById(R.id.btnDietRecord)
        val btnDietHistory: Button = findViewById(R.id.btnDietHistory)
        val btnProfileEdit: Button = findViewById(R.id.btnProfileEdit)
        val btnOrderHistory: Button = findViewById(R.id.btnOrderHistory)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnDietRecord.setOnClickListener { // 식단 추가
            val intent = Intent(this, DietAddActivity::class.java)
            startActivity(intent)
        }

        btnDietHistory.setOnClickListener { // 식단 정보
            val intent = Intent(this, DietViewActivity::class.java)
            startActivity(intent)
        }

        btnProfileEdit.setOnClickListener { // 회원 정보 수정
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("isUpdate", true)
            startActivity(intent)
        }

        btnOrderHistory.setOnClickListener { // 주문 내역
            val intent = Intent(this, OrderHistoryActivity::class.java)
            startActivity(intent)
        }

//        btnLogout.setOnClickListener { // 로그아웃
//            val intent = Intent(this, LogoutActivity::class.java)
//            startActivity(intent)
//        }


    }
}
