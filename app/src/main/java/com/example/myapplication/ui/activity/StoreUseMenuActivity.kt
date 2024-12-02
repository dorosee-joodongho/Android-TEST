package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R

class StoreUseMenuActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_use_menu)

        val backButton = findViewById<TextView>(R.id.backButton)
        backButton.text = "←  사용자 목록" // 헤더 제목 변경
        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }

        val btnMenuList: Button = findViewById(R.id.btnMenuList)
        val btnLogout: Button = findViewById(R.id.btnLogout)

        btnMenuList.setOnClickListener {
            val intent = Intent(this, MenuListActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener { // 로그아웃
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("로그아웃 확인")
            .setMessage("정말로 로그아웃 하시겠습니까?")
            .setPositiveButton("네") { dialog, _ ->
                logout()
                dialog.dismiss() // 다이얼로그 닫기
            }
            .setNegativeButton("아니오") { dialog, _ ->
                dialog.dismiss()
            }

        // 다이얼로그 표시
        builder.create().show()
    }


    private fun logout() {
        // Preferences 삭제
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // 모든 데이터 제거
        editor.apply()

        Toast.makeText(this, "로그아웃 성공", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish() // 현재 화면 종료
    }
}