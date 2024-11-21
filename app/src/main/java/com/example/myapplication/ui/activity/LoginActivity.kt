package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.squareup.picasso.Picasso

class LoginActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var loginBut: Button
    private lateinit var signUpBut: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        val imageUrl = "https://cdn-icons-png.flaticon.com/512/5223/5223909.png"
        Picasso.get().load(imageUrl).into(imageView)

        // 로그인
        loginBut.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // 유효성 검사
            if (validateInput(email, password)) {
                loginUser(email, password)
            }
        }

        // 회원가입
        signUpBut.setOnClickListener {
            val intent = Intent(this, UserActivity::class.java)
            intent.putExtra("isUpdate", false)
            startActivity(intent)
        }
    }

    private fun initViews() {
        imageView = findViewById(R.id.imageView)
        loginBut = findViewById(R.id.loginBut)
        signUpBut = findViewById(R.id.signUpBut)
        editTextEmail = findViewById(R.id.editTextTextEmailAddress)
        editTextPassword = findViewById(R.id.editTextTextPassword)
    }

    // 이메일과 비밀번호 유효성 검사
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "유효한 이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    // 로그인 처리 (예시, 실제로는 API 호출 등 필요)
    private fun loginUser(email: String, password: String) {
        // 여기서는 로그인 성공 여부를 간단히 예시로 처리
        if (email == "test@naver.com" && password == "1234") {
            // 로그인 성공 시 메인 화면으로 이동
            Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            // val intent = Intent(this, MainActivity::class.java)
            val intent = Intent(this, StoreUseMenuActivity::class.java)
            startActivity(intent)
            finish() // 로그인 후 현재 화면 종료
        } else {
            Toast.makeText(this, "잘못된 이메일 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
