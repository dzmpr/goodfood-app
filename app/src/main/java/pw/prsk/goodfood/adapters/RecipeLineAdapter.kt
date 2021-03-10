package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.PhotoGateway
import pw.prsk.goodfood.data.RecipeWithMeta
import javax.inject.Inject

class RecipeLineAdapter : RecyclerView.Adapter<RecipeLineAdapter.RecipeLineViewHolder>() {

    @Inject lateinit var photoGateway: PhotoGateway
    private var recipeList: List<RecipeWithMeta> = listOf()

    class RecipeLineViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvRecipeName)
        private val category: TextView = view.findViewById(R.id.tvRecipeCategoryName)
        private val thumbnail: ImageView = view.findViewById(R.id.ivRecipeThumbnail)
        private val favoriteMark: CheckBox = view.findViewById(R.id.cbFavoriteMark)

        private val holderScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private var loadImageJob: Job? = null

        fun bind(
            recipe: RecipeWithMeta,
            photoGateway: PhotoGateway
        ) {
            name.text = recipe.name
            category.text = recipe.category.name
            favoriteMark.isChecked = recipe.inFavorites

            loadImage(recipe, photoGateway)
        }

        private fun loadImage(recipe: RecipeWithMeta, photoGateway: PhotoGateway) {
            if (loadImageJob != null) {
                loadImageJob?.cancel()
            }
            loadImageJob = holderScope.launch {
                val photo = if (recipe.photoFilename != null) {
                    val uri = photoGateway.getUriForPhoto(recipe.photoFilename!!)
                    photoGateway.loadScaledPhoto(uri, 56, 56)
                } else null
                withContext(Dispatchers.Main) {
                    thumbnail.setImageBitmap(photo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeLineViewHolder {
        return RecipeLineViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recipe_line, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeLineViewHolder, position: Int) {
        holder.bind(recipeList[position], photoGateway)
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

    class ProductDiffUtilCallback(
        private val oldList: List<RecipeWithMeta>,
        private val newList: List<RecipeWithMeta>
    ) :
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