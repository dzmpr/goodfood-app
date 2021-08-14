package ru.cookedapp.cooked.ui.cart

import android.text.Spannable
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.entity.CartItem

class CartAdapter(private val callback: BoughtChangeStateCallback) :
    RecyclerView.Adapter<CartAdapter.CartItemViewHolder>() {
    private var itemsList: List<CartItem> = mutableListOf()

    class CartItemViewHolder(view: View, private val callback: BoughtChangeStateCallback) :
        RecyclerView.ViewHolder(view) {
        private val boughtMark: CheckBox = view.findViewById(R.id.cbBought)
        private val productName: TextView = view.findViewById(R.id.tvProductName)
        private val amount: TextView = view.findViewById(R.id.tvAmount)
        private val amountUnit: TextView = view.findViewById(R.id.tvAmountUnit)

        private var itemId: Int = 0

        fun bind(item: CartItem) {
            itemId = item.id!!
            val spannedText = SpannableString(item.product.name)

            boughtMark.isChecked = item.isBought
            if (item.isBought) {
                spannedText.setSpan(
                    StrikethroughSpan(), 0, spannedText.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                )
                productName.setTextColor(
                    productName.context.resources.getColor(
                        R.color.neutral_gray,
                        productName.context.theme
                    )
                )
            } else {
                val color = TypedValue()
                productName.context.theme.resolveAttribute(R.attr.colorOnSurface, color, true)
                productName.setTextColor(color.data)
            }
            productName.text = spannedText
            val amountInt = item.amount.toInt()
            if (amountInt < item.amount) {
                amount.text = String.format(Locale.getDefault(), "%.2f", item.amount)
            } else {
                amount.text = amountInt.toString()
            }
            amountUnit.text = item.unit.name

            boughtMark.setOnCheckedChangeListener { _, isChecked ->
                callback.changeBoughtState(itemId, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart_row, parent, false),
            callback
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(itemsList[position])
    }

    override fun getItemCount() = itemsList.size

    fun setList(newList: List<CartItem>) {
        if (itemsList.isEmpty()) {
            itemsList = newList.toList()
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(CartItemDiffUtilCallback(itemsList, newList))
            itemsList = newList.toList()
            diffResult.dispatchUpdatesTo(this)
        }
    }

    interface BoughtChangeStateCallback {
        fun changeBoughtState(id: Int, state: Boolean)
    }

    class CartItemDiffUtilCallback(
        private val oldList: List<CartItem>,
        private val newList: List<CartItem>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].id == newList[newItemPosition].id

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}
