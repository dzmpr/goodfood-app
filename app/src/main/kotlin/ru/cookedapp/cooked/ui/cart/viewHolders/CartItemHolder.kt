package ru.cookedapp.cooked.ui.cart.viewHolders

import android.text.SpannableString
import ru.cookedapp.common.baseList.BaseViewHolder
import ru.cookedapp.common.baseList.ViewHolderFactory
import ru.cookedapp.common.baseList.data.Item
import ru.cookedapp.common.baseList.data.ItemPayload
import ru.cookedapp.common.extensions.getColorById
import ru.cookedapp.common.extensions.resolveAttribute
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.ItemCartRowBinding
import ru.cookedapp.cooked.extensions.addStrikethroughSpan
import ru.cookedapp.cooked.extensions.inflater
import ru.cookedapp.cooked.ui.cart.data.CartItemBoughtStateChanged
import ru.cookedapp.cooked.ui.cart.data.CartProductItem

class CartItemHolder(
    private val binding: ItemCartRowBinding,
) : BaseViewHolder(binding.root) {

    override fun onBind(item: Item) {
        item as CartProductItem

        with(binding) {
            cbBought.isChecked = item.isBought
            tvAmount.text = item.amount
            tvAmountUnit.text = item.amountUnitName
        }
        setProductName(item.productName, item.isBought)

        binding.cbBought.setOnCheckedChangeListener { _, isChecked ->
            dispatchCheckedEvent(item, isChecked)
        }
    }

    override fun onPartialBind(payload: ItemPayload) = when (payload) {
        is CartItemBoughtStateChanged -> with(payload) {
            binding.cbBought.isChecked = isBought
            setProductName(productName, isBought)
        }
        else -> super.onPartialBind(payload)
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
