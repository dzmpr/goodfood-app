package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.Product

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var productList: List<Product> = listOf()

    class ProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvProductName)
        val category: TextView = view.findViewById(R.id.tvProductCategoryName)
        val unit: TextView = view.findViewById(R.id.tvProductUnitName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        with (productList[position]) {
            holder.name.text = name
            // TODO: Remove after fixing layout
            holder.category.text = "deprecated"
            holder.unit.text = "deprecated"
        }
    }

    override fun getItemCount(): Int = productList.size

    fun setList(list: List<Product>) {
        if (productList.isEmpty()) {
            productList = list
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(productList, list))
            productList = list
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class ProductDiffUtilCallback(private val oldList: List<Product>, private val newList: List<Product>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}