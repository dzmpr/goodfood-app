package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import pw.prsk.goodfood.R
import pw.prsk.goodfood.RecipesOverviewFragment
import pw.prsk.goodfood.data.PhotoGateway
import pw.prsk.goodfood.data.RecipeWithMeta
import javax.inject.Inject

class RecipeAdapter(private val recipeClickCallback: RecipesOverviewFragment.onRecipeItemClicked) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    @Inject lateinit var photoGateway: PhotoGateway
    private var recipeList: List<RecipeWithMeta> = listOf()

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvMealName)
        private val category: TextView = view.findViewById(R.id.tvMealCategoryName)
        private val image: ImageView = view.findViewById(R.id.ivRecipePhoto)
        private val favoriteButton: ToggleButton = view.findViewById(R.id.tbFavorites)
        private val recipeCard: CardView = view.findViewById(R.id.cvRecipeCard)

        private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private var loadImageJob: Job? = null

        private var recipeId: Int = 0

        fun bind(
            recipe: RecipeWithMeta,
            photoGateway: PhotoGateway,
            callback: RecipesOverviewFragment.onRecipeItemClicked
        ) {
            recipeId = recipe.id!!

            name.text = recipe.name
            category.text = recipe.category?.name
                ?: itemView.context.resources.getString(R.string.label_uncategorized)
            favoriteButton.isChecked = recipe.inFavorites

            loadImage(recipe, photoGateway)

            favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                callback.onFavoriteToggle(recipeId, isChecked)
            }

            recipeCard.setOnClickListener {
                callback.onRecipeClicked(recipeId)
            }
        }

        private fun loadImage(recipe: RecipeWithMeta, photoGateway: PhotoGateway) {
            if (loadImageJob != null) {
                loadImageJob?.cancel()
            }
            loadImageJob = coroutineScope.launch {
                val photo = if (recipe.photoFilename != null) {
                    val uri = photoGateway.getUriForPhoto(recipe.photoFilename!!)
                    photoGateway.loadScaledPhoto(uri, 100, 120)
                } else null
                withContext(Dispatchers.Main) {
                    image.setImageBitmap(photo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipeList[position], photoGateway, recipeClickCallback)
    }

    override fun getItemCount(): Int = recipeList.size

    fun setList(list: List<RecipeWithMeta>) {
        if (list.isEmpty()) {
            recipeList = list
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(MealDiffUtilCallback(recipeList, list))
            recipeList = list
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class MealDiffUtilCallback(
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