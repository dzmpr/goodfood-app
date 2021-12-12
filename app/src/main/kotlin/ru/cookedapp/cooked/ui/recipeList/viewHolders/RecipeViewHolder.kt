package ru.cookedapp.cooked.ui.recipeList.viewHolders

import ru.cookedapp.cooked.databinding.ItemRecipeListRecipeBinding
import ru.cookedapp.cooked.extensions.inflater
import ru.cookedapp.cooked.ui.recipeList.data.RecipeFavoriteStateChanged
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.cooked.utils.listBase.BaseViewHolder
import ru.cookedapp.cooked.utils.listBase.ViewHolderFactory
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

class RecipeViewHolder(
    private val binding: ItemRecipeListRecipeBinding,
) : BaseViewHolder(binding.root) {

    override fun onBind(item: Item) {
        item as RecipeListItem

        with(binding) {
            tvRecipeName.text = item.recipeName
            tvRecipeCategoryName.text = item.recipeCategoryName
            cbFavoriteMark.isChecked = item.inFavorites
            ivRecipeThumbnail.setImageURI(item.thumbnailUri)
        }

        binding.cbFavoriteMark.setOnCheckedChangeListener { _, isChecked ->
            dispatchCheckedEvent(item, isChecked)
        }

        binding.root.setOnClickListener {
            dispatchClickEvent(item)
        }
    }

    override fun onPartialBind(payload: ItemPayload) {
        when (payload) {
            is RecipeFavoriteStateChanged -> {
                binding.cbFavoriteMark.isChecked = payload.inFavorites
            }
            else -> super.onPartialBind(payload)
        }
    }

    companion object {
        fun getFactory() = ViewHolderFactory { parent ->
            RecipeViewHolder(ItemRecipeListRecipeBinding.inflate(parent.inflater, parent, false))
        }
    }
}