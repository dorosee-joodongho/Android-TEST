package com.example.myapplication.ui.activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.service.AuthService

class UserActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAction: Button

    private val userService = AuthService()

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
            onBackPressed() // 뒤로가기 동작
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
            userService.addMember(name, phone, email, password) { success ->
                if (success) {
                    // 처리 성공
                } else {
                    // 에러 처리
                }
            }
        } else {
            // 비밀번호 확인 에러
        }
    }

    private fun updateUser() {
        // 사용자 정보 수정 로직 호출
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()

        userService.updateMember(name, phone, password) { success -> // 올바른 메서드 이름 사용
            if (success) {
                // 처리 성공
            } else {
                // 에러 처리
            }
        }
    } // updateUser
}
