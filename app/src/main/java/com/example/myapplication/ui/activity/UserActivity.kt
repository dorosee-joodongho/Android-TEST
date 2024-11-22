package com.example.myapplication.ui.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.service.MemberService
import kotlinx.coroutines.launch

class UserActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAction: Button

    private val userService = MemberService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        // View 초기화
        etName = findViewById(R.id.et_name)
        etPhone = findViewById(R.id.et_phone)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnAction = findViewById(R.id.btn_action)

        // 화면 유형 가져오기
        val isUpdate = intent.getBooleanExtra("isUpdate", false)
        setupUI(isUpdate)
    }

    private fun setupUI(isUpdate: Boolean) {
        // 공통 헤더 설정
        val backButton = findViewById<TextView>(R.id.backButton)

        backButton.setOnClickListener {
            onBackPressed() // 뒤로 가기 동작
        }

        if (isUpdate) { // 정보 수정
            btnAction.text = "정보 수정"
            etEmail.isEnabled = false // 이메일 비활성화
            loadUserData() // 사용자 정보 로드
            backButton.text = "←  개인 정보 수정" // 헤더 제목 변경
        } else { // 회원가입
            btnAction.text = "회원가입"
            backButton.text = "←  회원가입" // 헤더 제목 변경
        }

        btnAction.setOnClickListener {
            if (isUpdate) {
                updateUser()
            } else {
                registerUser()
            }
        }
    }

    private fun loadUserData() {
        // 현재 로그인된 사용자 정보 불러오기
        val currentUser = userService.getCurrentUser()
        etName.setText(currentUser.memberName)
        etPhone.setText(currentUser.memberPhone)
        etEmail.setText(currentUser.memberEmail)
    }

    private fun registerUser() {
        // 회원가입 로직 호출
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        if (password == confirmPassword) {
            // 코루틴을 사용하여 비동기 처리
            lifecycleScope.launch {
                try {
                    val success = userService.saveMember(name, phone, email, password)
                    if (success) {
                        Toast.makeText(this@UserActivity, "회원 가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@UserActivity, "회원 가입에 실패하셨습니다. 다시 시도 해주세요.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@UserActivity, "회원 가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUser() {
        // 사용자 정보 수정 로직 호출
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()

        lifecycleScope.launch {
            try {
                val success = userService.updateMember(name, phone, password)
                if (success) {
                    Toast.makeText(this@UserActivity, "정보 수정에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "정보 수정에 실패하셨습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@UserActivity, "정보 수정 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
