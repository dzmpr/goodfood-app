package pw.prsk.goodfood.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.local.db.entity.RecipeCategory
import kotlin.math.truncate

class CategoryAdapter(private val callback: CategoryItemCallback) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoriesList: List<RecipeCategory> = mutableListOf()

    class CategoryViewHolder(private val callback: CategoryItemCallback, private val view: View) :
        RecyclerView.ViewHolder(view) {
        private val backgroundColors = arrayOf(
            R.color.chip_red,
            R.color.chip_orange,
            R.color.chip_yellow,
            R.color.chip_green,
            R.color.chip_light_blue,
            R.color.chip_blue
        )

        fun bind(item: RecipeCategory) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            callback,
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_simple_chip, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoriesList[position])
    }

    override fun getItemCount(): Int = categoriesList.size

    fun setList(newList: List<RecipeCategory>) {
        if (categoriesList.isEmpty()) {
            categoriesList = newList.toList()
            notifyDataSetChanged()
        } else {
            val diffResult =
                DiffUtil.calculateDiff(CategoryDiffUtilCallback(categoriesList, newList))
            categoriesList = newList.toList()
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class CategoryDiffUtilCallback(
        private val oldList: List<RecipeCategory>,
        private val newList: List<RecipeCategory>
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

    interface CategoryItemCallback {
        fun onClick(id: Int, name: String)
    }
}