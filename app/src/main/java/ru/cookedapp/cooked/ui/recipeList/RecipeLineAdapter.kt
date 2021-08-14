package ru.cookedapp.cooked.ui.recipeList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.entity.Recipe
import ru.cookedapp.cooked.data.gateway.PhotoGateway

class RecipeLineAdapter(
    private val recipeClickCallback: RecipeListFragment.RecipeClickCallback
) : RecyclerView.Adapter<RecipeLineAdapter.RecipeLineViewHolder>() {

    @Inject
    lateinit var photoGateway: PhotoGateway
    private var recipeList: List<Recipe> = listOf()

    class RecipeLineViewHolder(
        view: View,
        recipeClickCallback: RecipeListFragment.RecipeClickCallback
    ) : RecyclerView.ViewHolder(view) {
        private val container: LinearLayout = view.findViewById(R.id.llRecipeItemContainer)
        private val name: TextView = view.findViewById(R.id.tvRecipeName)
        private val category: TextView = view.findViewById(R.id.tvRecipeCategoryName)
        private val thumbnail: ImageView = view.findViewById(R.id.ivRecipeThumbnail)
        private val favoriteMark: CheckBox = view.findViewById(R.id.cbFavoriteMark)

        private val holderScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private var loadImageJob: Job? = null

        private var recipeId = 0

        init {
            container.setOnClickListener {
                recipeClickCallback.onRecipeClicked(recipeId)
            }

            favoriteMark.setOnCheckedChangeListener { _, isChecked ->
                recipeClickCallback.onFavoriteToggle(recipeId, isChecked)
            }
        }

        fun bind(
            recipe: Recipe,
            photoGateway: PhotoGateway
        ) {
            recipeId = recipe.id!!

            name.text = recipe.name
            category.text = recipe.category.name
            favoriteMark.isChecked = recipe.inFavorites

            loadImage(recipe, photoGateway)
        }

        private fun loadImage(recipe: Recipe, photoGateway: PhotoGateway) {
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
                .inflate(R.layout.item_recipe_line, parent, false),
            recipeClickCallback
        )
    }

    override fun onBindViewHolder(holder: RecipeLineViewHolder, position: Int) {
        holder.bind(recipeList[position], photoGateway)
    }

    override fun getItemCount(): Int = recipeList.size

    fun setList(list: List<Recipe>) {
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
        private val oldList: List<Recipe>,
        private val newList: List<Recipe>
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
