package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.Product
import pw.prsk.goodfood.data.RecipeWithMeta

class RecipeLineAdapter: RecyclerView.Adapter<RecipeLineAdapter.RecipeLineViewHolder>() {
    private var recipeList: List<RecipeWithMeta> = listOf()

    class RecipeLineViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvRecipeName)
        private val category: TextView = view.findViewById(R.id.tvRecipeCategoryName)
        private val thumbnail: ImageView = view.findViewById(R.id.ivRecipeThumbnail)
        private val favoriteMark: CheckBox = view.findViewById(R.id.cbFavoriteMark)

        fun bind(recipe: RecipeWithMeta) {
            name.text = recipe.name
            category.text = recipe.category?.name ?: itemView.context.resources.getString(R.string.label_uncategorized)
            favoriteMark.isChecked = recipe.inFavorites
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeLineViewHolder {
        return RecipeLineViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_recipe_line, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeLineViewHolder, position: Int) {
        holder.bind(recipeList[position])
    }

    override fun getItemCount(): Int = recipeList.size

    fun setList(list: List<RecipeWithMeta>) {
        if (recipeList.isEmpty()) {
            recipeList = list
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(ProductDiffUtilCallback(recipeList, list))
            recipeList = list
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class ProductDiffUtilCallback(private val oldList: List<RecipeWithMeta>, private val newList: List<RecipeWithMeta>) :
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