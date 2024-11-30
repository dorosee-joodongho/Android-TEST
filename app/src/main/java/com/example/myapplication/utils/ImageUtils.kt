package com.example.myapplication.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.IOException

object ImageUtils {

    // 갤러리에서 이미지 선택
    fun selectImageFromGallery(activity: AppCompatActivity, requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        activity.startActivityForResult(intent, requestCode)
    }

    // Uri를 File로 변환
    fun uriToFile(uri: Uri?, context: Context): File? {
        return uri?.let {
            val fileName = getFileNameFromUri(uri, context) ?: "temp_image"
            val file = File(context.cacheDir, fileName)
            try {
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    file.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                file
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    // Uri에서 파일 이름 추출
    private fun getFileNameFromUri(uri: Uri, context: Context): String? {
        var fileName: String? = null
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = it.getString(nameIndex)
                }
            }
        }
        return fileName
    }

}
