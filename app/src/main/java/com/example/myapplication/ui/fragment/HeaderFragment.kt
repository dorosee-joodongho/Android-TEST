package com.example.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class HeaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // XML 레이아웃 파일 inflate
        return inflater.inflate(R.layout.fragment_header, container, false)
    }
}

/*
    // 공통 헤더 설정
    val backButton = findViewById<TextView>(R.id.backButton)

    backButton.text = "←  개인 정보 수정" // 헤더 제목 변경

    backButton.setOnClickListener {
        onBackPressed() // 뒤로 가기 동작
    }

 */
