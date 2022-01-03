package ru.cookedapp.cooked.ui.manageItems.viewHolders

import kotlin.math.truncate
import ru.cookedapp.common.baseList.BaseViewHolder
import ru.cookedapp.common.baseList.ViewHolderFactory
import ru.cookedapp.common.baseList.data.Item
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.databinding.ItemSimpleChipBinding
import ru.cookedapp.cooked.extensions.inflater
import ru.cookedapp.cooked.ui.manageItems.data.ManageItemModel

class ManageItemHolder(
    private val binding: ItemSimpleChipBinding,
) : BaseViewHolder(binding.root) {

    private val backgroundColors = arrayOf(
        R.color.chip_red,
        R.color.chip_orange,
        R.color.chip_yellow,
        R.color.chip_green,
        R.color.chip_light_blue,
        R.color.chip_blue
    )

    override fun onBind(item: Item) {
        item as ManageItemModel

        with(binding) {
            cItem.text = item.name
            cItem.setChipBackgroundColorResource(nameToColor(item.name))
            root.setOnClickListener {
                dispatchClickEvent(item)
            }
        }

    }

    private fun nameToColor(name: String): Int {
        val firstLetter = name[0].code.toByte()
        val colorCount = backgroundColors.size
        val fraction = firstLetter / colorCount.toDouble()
        val res = ((fraction - truncate(fraction)) * colorCount).toInt()
        return backgroundColors[res]
    }

    companion object {
        fun getFactory() = ViewHolderFactory { parent ->
            ManageItemHolder(ItemSimpleChipBinding.inflate(parent.inflater, parent, false))
        }
    }
}
