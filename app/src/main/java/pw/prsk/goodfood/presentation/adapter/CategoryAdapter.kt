package pw.prsk.goodfood.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.local.db.entity.RecipeCategory

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    private var categoriesList: List<RecipeCategory> = mutableListOf()

    class CategoryViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: RecipeCategory) {
            (view as Chip).apply {
                text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_simple_chip, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoriesList.get(position))
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
    ): DiffUtil.Callback() {
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