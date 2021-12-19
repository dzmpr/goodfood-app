package ru.cookedapp.cooked.ui.recipesOverview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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

class RecipeCardAdapter(
    private val recipeClickCallback: RecipesOverviewFragment.RecipeClickCallback,
    private val listType: Int,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    @Inject lateinit var photoGateway: PhotoGateway
    private var recipeList: List<Recipe> = listOf()

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val name: TextView = view.findViewById(R.id.tvMealName)
        private val category: TextView = view.findViewById(R.id.tvMealCategoryName)
        private val image: ImageView = view.findViewById(R.id.ivRecipePhoto)
        private val favoriteButton: CheckBox = view.findViewById(R.id.tbFavorites)
        private val recipeCard: CardView = view.findViewById(R.id.cvRecipeCard)

        private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
        private var loadImageJob: Job? = null

        private var recipeId: Long = 0

        fun bind(
            recipe: Recipe,
            photoGateway: PhotoGateway,
            clickCallback: RecipesOverviewFragment.RecipeClickCallback
        ) {
            recipeId = recipe.id

            name.text = recipe.name
            category.text = recipe.category.name
            favoriteButton.isChecked = recipe.inFavorites

            loadImage(recipe, photoGateway)

            favoriteButton.setOnCheckedChangeListener { _, isChecked ->
                clickCallback.onFavoriteToggle(recipeId, isChecked)
            }

            recipeCard.setOnClickListener {
                clickCallback.onRecipeClicked(recipeId)
            }
        }

        private fun loadImage(recipe: Recipe, photoGateway: PhotoGateway) {
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

    class EndButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val card: CardView = view.findViewById(R.id.cvMoreCard)

        fun bind(clickCallback: RecipesOverviewFragment.RecipeClickCallback, listType: Int) {
            card.setOnClickListener {
                clickCallback.onMoreButtonClick(listType)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_RECIPE_ITEM -> RecipeViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recipe_card, parent, false)
            )
            else -> EndButtonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_card_more, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            TYPE_END_BUTTON -> (holder as EndButtonViewHolder).bind(recipeClickCallback, listType)
            else -> (holder as RecipeViewHolder).bind(
                recipeList[position],
                photoGateway,
                recipeClickCallback
            )
        }
    }

    override fun getItemViewType(position: Int): Int = if (recipeList.size < END_BUTTON_THRESHOLD) {
        TYPE_RECIPE_ITEM
    } else {
        when (position) {
            recipeList.size -> TYPE_END_BUTTON
            else -> TYPE_RECIPE_ITEM
        }
    }

    override fun getItemCount(): Int = when (recipeList.size < END_BUTTON_THRESHOLD) {
        true -> recipeList.size
        else -> recipeList.size + 1
    }

    fun setList(list: List<Recipe>) {
        if (list.isEmpty()) {
            recipeList = list
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(MealDiffUtilCallback(recipeList, list))
            if (recipeList.size >= END_BUTTON_THRESHOLD && list.size < END_BUTTON_THRESHOLD) {
                notifyItemRemoved(recipeList.size)
            }
            recipeList = list
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class MealDiffUtilCallback(
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

    companion object {
        private const val TYPE_RECIPE_ITEM = 0
        private const val TYPE_END_BUTTON = 1

        private const val END_BUTTON_THRESHOLD = 5
    }
}
