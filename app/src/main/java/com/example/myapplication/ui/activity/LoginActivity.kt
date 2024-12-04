package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MemberService
import com.example.myapplication.utils.ToastUtils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var loginBut: Button
    private lateinit var signUpBut: Button
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        RetrofitClient.initialize(this) // Context 전달

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

    // 로그인
    private fun loginUser(email: String, password: String) {
        lifecycleScope.launch {
            val memberService = MemberService(this@LoginActivity, RetrofitClient.instance)

            try {
                val loginResult = memberService.login(email, password)

                if (loginResult != null) {
                    val targetActivity = if (loginResult) { // 고객 로그인
                        MainActivity::class.java
                    } else {// 가게 로그인
                        StoreOrderActivity::class.java
                    }
                    ToastUtils.showToast(this@LoginActivity, "로그인 성공")
                    startActivity(Intent(this@LoginActivity, targetActivity))
                    finish()
                } else {
                    ToastUtils.showToast(this@LoginActivity,"잘못된 이메일 또는 비밀번호입니다.")
                }
            } catch (e: Exception) {
                val errorMessage = e.message ?: "로그인 중 오류가 발생했습니다. 다시 시도해주세요."
                ToastUtils.showToast(this@LoginActivity, errorMessage)
            }
        }
    }
}
