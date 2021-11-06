package ru.cookedapp.cooked.ui.cart.viewHolders

import android.text.SpannableString
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.ItemCartRowBinding
import ru.cookedapp.cooked.extensions.addStrikethroughSpan
import ru.cookedapp.cooked.extensions.checked
import ru.cookedapp.cooked.extensions.getColorById
import ru.cookedapp.cooked.extensions.inflater
import ru.cookedapp.cooked.extensions.resolveAttribute
import ru.cookedapp.cooked.ui.cart.data.CartItemBoughtStateChanged
import ru.cookedapp.cooked.ui.cart.data.CartItemModel
import ru.cookedapp.cooked.ui.cart.data.CartViewType
import ru.cookedapp.cooked.utils.listBase.BaseViewHolder
import ru.cookedapp.cooked.utils.listBase.ViewHolderFactory
import ru.cookedapp.cooked.utils.listBase.data.Item
import ru.cookedapp.cooked.utils.listBase.data.ItemPayload

class CartItemHolder(
    private val binding: ItemCartRowBinding,
) : BaseViewHolder<CartViewType>(binding.root) {

    override fun bindInternal(item: Item<CartViewType>) {
        item as CartItemModel

        with(binding) {
            cbBought.isChecked = item.isBought
            tvAmount.text = item.amount
            tvAmountUnit.text = item.amountUnitName
        }
        setProductName(item.productName, item.isBought)

        binding.cbBought.checked().holderStateAware().onEach { isChecked ->
            dispatchCheckedEvent(item, isChecked)
        }.launchIn(viewHolderScope)
    }

    override fun partialBindInternal(payload: ItemPayload) = when (payload) {
        is CartItemBoughtStateChanged -> with(payload) {
            binding.cbBought.isChecked = isBought
            setProductName(productName, isBought)
        }
        else -> super.partialBindInternal(payload)
    }

    private fun setProductName(name: String, isBought: Boolean) {
        val productNameSpannable = SpannableString(name).apply {
            if (isBought) addStrikethroughSpan(position = 0 to name.length)
        }
        val productNameColor = if (isBought) {
            context.getColorById(R.color.neutral_gray)
        } else {
            context.resolveAttribute(R.attr.colorOnSurface).data
        }
        with(binding.tvProductName) {
            setTextColor(productNameColor)
            text = productNameSpannable
        }
    }

    companion object {
        fun getFactory() = ViewHolderFactory { parent ->
            CartItemHolder(ItemCartRowBinding.inflate(parent.inflater, parent, false))
        }
    }
}
