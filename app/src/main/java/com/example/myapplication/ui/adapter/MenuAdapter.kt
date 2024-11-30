package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.MenuDetail

class MenuAdapter(
    private var menuList: List<MenuDetail>,
    private val onItemClick: (MenuDetail) -> Unit
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivMenuImage: ImageView = itemView.findViewById(R.id.ivMenuImage)
        private val tvMenuName: TextView = itemView.findViewById(R.id.tvMenuName)
        private val tvMenuDescription: TextView = itemView.findViewById(R.id.tvMenuDescription)
        private val tvMenuPrice: TextView = itemView.findViewById(R.id.tvMenuPrice)

        fun bind(menu: MenuDetail) {
            // 메뉴 데이터 바인딩
            tvMenuName.text = menu.menuName
            tvMenuDescription.text = menu.menuDescription
            tvMenuPrice.text = "₩${menu.menuPrice}"

            // 메뉴 이미지 바인딩 (Glide 사용)
            Glide.with(itemView.context)
                .load(menu.menuImage) // 이미지 URI 또는 URL
                .placeholder(R.drawable.placeholder_image) // 이미지 로드 중 보여줄 기본 이미지
                .error(R.drawable.error_image) // 이미지 로드 실패 시 보여줄 기본 이미지
                .into(ivMenuImage)

            itemView.setOnClickListener {
                onItemClick(menu)
            }
        }
    }

    fun updateMenuList(newMenuList: List<MenuDetail>) {
        menuList = newMenuList
        notifyDataSetChanged() // 데이터 변경 알림
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuList[position])
    }

    override fun getItemCount(): Int = menuList.size
}
