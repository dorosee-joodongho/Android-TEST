package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.network.RetrofitClient
import com.example.myapplication.service.MemberService
import com.example.myapplication.utils.ToastUtils
import kotlinx.coroutines.launch
import android.util.Patterns

class UserActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var btnAction: Button

    private var memberId = -1L

    private val userService by lazy {
        MemberService(applicationContext, RetrofitClient.instance)
    }

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
                saveMember()
            }
        }
    }

    private fun loadUserData() {
        lifecycleScope.launch {
            try {
                val currentUser = userService.getMember()

                if (currentUser != null) {
                    memberId = currentUser.memberID
                    etName.setText(currentUser.memberName)
                    etPhone.setText(currentUser.memberPhone)
                    etEmail.setText(currentUser.memberEmail)
                } else {
                    ToastUtils.showToast(this@UserActivity, "정보 로드 실패")
                }
            } catch (e: Exception) {
                println("사용자 정보 로드 실패 : ${e.message}")
            }
        }
    }

    private fun saveMember() {
        // 회원가입 로직 호출
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // 유효성 검사
        if (!isValidInput(name, phone, email, password, confirmPassword)) return

        if (password == confirmPassword) {
            lifecycleScope.launch {
                try {
                    val success = userService.saveMember(name, phone, email, password)
                    if (success) {
                        ToastUtils.showToast(this@UserActivity, "회원 가입에 성공하셨습니다.")
                        val intent = Intent(this@UserActivity, LoginActivity::class.java)
                        startActivity(intent)
                    } else {
                        ToastUtils.showToast(this@UserActivity, "회원 가입에 실패하셨습니다. 다시 시도 해주세요.")
                    }
                } catch (e: Exception) {
                    ToastUtils.showToast(this@UserActivity, "회원 가입 중 오류가 발생했습니다.")
                }
            }
        } else {
            ToastUtils.showToast(this, "비밀번호를 확인해주세요.")
        }
    }

    private fun updateUser() {
        // 사용자 정보 수정 로직 호출
        val name = etName.text.toString()
        val phone = etPhone.text.toString()
        val password = etPassword.text.toString()

        // 유효성 검사
        if (!isValidUpdateInput(name, phone, password)) return

        lifecycleScope.launch {
            try {
                val success = userService.updateMember(memberId, name, phone, password)
                if (success) {
                    ToastUtils.showToast(this@UserActivity, "정보 수정에 성공하셨습니다.")
                } else {
                    ToastUtils.showToast(this@UserActivity, "정보 수정에 실패하셨습니다. 다시 시도해주세요.")
                }
            } catch (e: Exception) {
                ToastUtils.showToast(this@UserActivity, "정보 수정 중 오류가 발생했습니다.")
            }
        }
    }

    private fun isValidInput(name: String, phone: String, email: String, password: String, confirmPassword: String): Boolean {
        if (name.isEmpty()) {
            ToastUtils.showToast(this, "이름을 입력해주세요.")
            return false
        }
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            ToastUtils.showToast(this, "유효한 전화번호를 입력해주세요.")
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ToastUtils.showToast(this, "유효한 이메일 주소를 입력해주세요.")
            return false
        }
        if (password.isEmpty() || password.length < 4) {
            ToastUtils.showToast(this, "비밀번호는 4자리 이상이어야 합니다.")
            return false
        }
        if (confirmPassword.isEmpty() || confirmPassword != password) {
            ToastUtils.showToast(this, "비밀번호가 일치하지 않습니다.")
            return false
        }
        return true
    }

    private fun isValidUpdateInput(name: String, phone: String, password: String): Boolean {
        if (name.isEmpty()) {
            ToastUtils.showToast(this, "이름을 입력해주세요.")
            return false
        }
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            ToastUtils.showToast(this, "유효한 전화번호를 입력해주세요.")
            return false
        }
        if (password.isEmpty() || password.length < 4) {
            ToastUtils.showToast(this, "비밀번호는 4자리 이상이어야 합니다.")
            return false
        }
        return true
    }
}
