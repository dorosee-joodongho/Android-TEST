package com.example.myapplication.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

object TransRequestBody {
    // 텍스트 데이터를 RequestBody로 변환
    fun prepareStringPart(value: String): RequestBody {
        return RequestBody.create("text/plain".toMediaTypeOrNull(), value)
    }

    // 이미지 파일을 MultipartBody.Part로 변환
    fun prepareFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("menuImg", file.name, requestFile)
    }
}