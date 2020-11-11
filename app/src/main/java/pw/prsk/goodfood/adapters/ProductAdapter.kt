package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.Product

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productList: List<Product>? = null

    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvProductName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.name.text = productList?.get(position)?.name
    }

    override fun getItemCount(): Int = productList?.size ?: 0

    fun setList(list: List<Product>) {
        productList = list
        notifyDataSetChanged()
    }
}