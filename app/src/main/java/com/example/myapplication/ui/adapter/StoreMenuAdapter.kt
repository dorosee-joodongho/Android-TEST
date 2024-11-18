import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.data.Menu

class StoreMenuAdapter(
    private val context: Context,
    private val menuList: List<Menu>, // 메뉴 데이터 리스트
    private val itemClickListener: (Menu) -> Unit // 클릭 리스너
) : RecyclerView.Adapter<StoreMenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        // 항목 레이아웃을 인플레이트하여 ViewHolder 생성
        val view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        // 메뉴 데이터를 ViewHolder에 바인딩
        val menu = menuList[position]

        holder.menuName.text = menu.name
        holder.menuPrice.text = menu.price

        // Glide로 이미지 로드
        Glide.with(context)
            .load(menu.menuImg)
            .placeholder(R.drawable.ic_launcher_background)  // 로딩 중에 표시될 이미지
            .error(R.drawable.error_image)  // 로드 실패 시 표시될 이미지
            .into(holder.menuImage)


        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            itemClickListener(menu) // 클릭 시 해당 메뉴 객체를 리스너에 전달
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    // ViewHolder 정의
    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val menuImage: ImageView = itemView.findViewById(R.id.menuImage) // 메뉴 이미지
        val menuName: TextView = itemView.findViewById(R.id.menuName) // 메뉴 이름
        val menuPrice: TextView = itemView.findViewById(R.id.menuPrice) // 메뉴 가격
    }
}
