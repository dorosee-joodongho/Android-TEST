package com.example.myapplication.ui.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.store.Store
import com.example.myapplication.ui.listener.OnItemClickListener

class StoreAdapter(
    private val storeList: List<Store>,
    private val listener : OnItemClickListener
) : RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.store_item, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]

        holder.storeName.text = store.storeName
        holder.storeLocation.text = store.location
        holder.storeCongestion.text = store.storeStatus
        // Glide를 사용하여 이미지 URL을 ImageView에 로드
        Glide.with(holder.itemView.context)
            .load(store.storeLogoImg)  // 이미지 URL을 로드
            .placeholder(R.drawable.ic_launcher_background)  // 로딩 중 이미지
            .error(R.drawable.error_image)  // 실패 시 이미지
            .into(holder.storeImage)     // ImageView에 이미지 넣기

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        println("StoreAdapter : Store list size: ${storeList.size}") // storeList 크기 확인
        return storeList.size
    }

    inner class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeName: TextView = itemView.findViewById(R.id.storeName)
        val storeLocation: TextView = itemView.findViewById(R.id.storeLocation)
        val storeCongestion: TextView = itemView.findViewById(R.id.storeCongestion)
        val storeImage: ImageView = itemView.findViewById(R.id.storeImage)
    }

}
