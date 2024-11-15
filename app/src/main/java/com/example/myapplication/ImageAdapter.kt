package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.Menu

class ImageAdapter(todaySpacialMenuList : List<Menu>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    private val imageUrls = todaySpacialMenuList.map { it.menuImg }
    private val menuNames = todaySpacialMenuList.map { it.name }
    private val menuPrices = todaySpacialMenuList.map { it.price }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)

        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        val menuName = menuNames[position]
        val menuPrice = menuPrices[position]

        Glide.with(holder.itemView.context)
            .load(imageUrl)  // 외부 URL을 Glide로 로드
            .placeholder(R.drawable.placeholder_image)  // 로딩 중 표시할 이미지
            .error(R.drawable.error_image)  // 로드 실패 시 표시할 이미지
            .into(holder.imageView)

        // 메뉴 이름과 가격을 TextView에 설정
        holder.menuName.text = menuName
        holder.menuPrice.text = menuPrice
    }

    override fun getItemCount(): Int = imageUrls.size

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val menuName: TextView = itemView.findViewById(R.id.menuName)
        val menuPrice: TextView = itemView.findViewById(R.id.menuPrice)
    }
}

