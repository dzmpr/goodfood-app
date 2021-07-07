package ru.cookedapp.cooked.ui.manageProducts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.entity.Product
import kotlin.math.truncate

class ProductsAdapter(private val callback: ProductItemCallback) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    private var productsList: List<Product> = emptyList()

    class ProductsViewHolder(private val callback: ProductItemCallback, private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val backgroundColors = arrayOf(
            R.color.chip_red,
            R.color.chip_orange,
            R.color.chip_yellow,
            R.color.chip_green,
            R.color.chip_light_blue,
            R.color.chip_blue
        )

        fun bind(item: Product) {
            (view as Chip).apply {
                text = item.name
                setChipBackgroundColorResource(nameToColor(item.name))
                setOnClickListener {
                    callback.onClick(item.id!!, item.name)
                }
            }
        }

        private fun nameToColor(name: String): Int {
            val firstLetter = name[0].toByte()
            val colorCount = backgroundColors.size
            val fraction = firstLetter / colorCount.toDouble()
            val res = ((fraction - truncate(fraction)) * colorCount).toInt()
            return backgroundColors[res]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        return ProductsViewHolder(
            callback,
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_simple_chip, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount() = productsList.size

    fun setList(newList: List<Product>) {
        if (productsList.isEmpty()) {
            productsList = newList.toList()
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(ProductsDiffUtilCallback(productsList, newList))
            productsList = newList
            diffResult.dispatchUpdatesTo(this)
        }
    }

    interface ProductItemCallback {
        fun onClick(id: Int, name: String)
    }

    class ProductsDiffUtilCallback(
        private val oldList: List<Product>,
        private val newList: List<Product>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
