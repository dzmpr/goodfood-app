package ru.cookedapp.cooked.ui.recipeList.viewHolders

import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.cookedapp.cooked.databinding.ItemRecipeListRecipeBinding
import ru.cookedapp.cooked.extensions.checked
import ru.cookedapp.cooked.extensions.clicks
import ru.cookedapp.cooked.extensions.inflater
import ru.cookedapp.cooked.ui.recipeList.data.RecipeFavoriteStateChanged
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListItem
import ru.cookedapp.cooked.ui.recipeList.data.RecipeListViewType
import ru.cookedapp.cooked.utils.listBase.BaseViewHolder
import ru.cookedapp.cooked.utils.listBase.ViewHolderFactory
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

class RecipeViewHolder(
    private val binding: ItemRecipeListRecipeBinding,
) : BaseViewHolder<RecipeListViewType>(binding.root) {

    override fun bindInternal(item: Item<RecipeListViewType>) {
        item as RecipeListItem

        with(binding) {
            tvRecipeName.text = item.recipeName
            tvRecipeCategoryName.text = item.recipeCategoryName
            cbFavoriteMark.isChecked = item.inFavorites
            ivRecipeThumbnail.setImageURI(item.thumbnailUri)
        }

        binding.cbFavoriteMark.checked().holderStateAware().onEach { isChecked ->
            dispatchCheckedEvent(item, isChecked)
        }.launchIn(viewHolderScope)

        binding.root.clicks().holderStateAware().onEach {
            dispatchClickEvent(item)
        }.launchIn(viewHolderScope)
    }

    override fun partialBindInternal(payload: ItemPayload) {
        when (payload) {
            is RecipeFavoriteStateChanged -> {
                binding.cbFavoriteMark.isChecked = payload.inFavorites
            }
            else -> super.partialBindInternal(payload)
        }
    }

    companion object {
        fun getFactory() = ViewHolderFactory { parent ->
            RecipeViewHolder(ItemRecipeListRecipeBinding.inflate(parent.inflater, parent, false))
        }
    }
}
