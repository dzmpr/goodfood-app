package ru.cookedapp.cooked.ui.recipeDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.cookedapp.cooked.R
import ru.cookedapp.cooked.data.db.entity.IngredientWithMeta
import java.util.*

class IngredientListAdapter: RecyclerView.Adapter<IngredientListAdapter.IngredientListViewHolder>() {
    private var ingredientsList: List<IngredientWithMeta> = mutableListOf()
    private var baseCount = 1
    private var selectedCount = 1

    inner class IngredientListViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val ingredientName: TextView = view.findViewById(R.id.tvIngredientName)
        private val ingredientAmount: TextView = view.findViewById(R.id.tvIngredientAmount)
        private val amountUnit: TextView = view.findViewById(R.id.tvAmountUnit)

        fun bind(item: IngredientWithMeta) {
            ingredientName.text = item.product.name
            val amountF = item.amount * selectedCount / baseCount
            val amount = amountF.toInt()
            if (amount < amountF) {
                ingredientAmount.text = String.format(Locale.getDefault(), "%.2f", amountF)
            } else {
                ingredientAmount.text = amount.toString()
            }
            amountUnit.text = item.unit.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientListViewHolder {
        return IngredientListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientListViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    override fun getItemCount() = ingredientsList.size

    fun setList(newList: List<IngredientWithMeta>) {
        if (ingredientsList.isEmpty()) {
            ingredientsList = newList.toList()
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(
                IngredientDiffUtilCallback(
                    ingredientsList,
                    newList
                )
            )
            ingredientsList = newList.toList()
            diffResult.dispatchUpdatesTo(this)
        }
    }

    fun setBaseCount(count: Int) {
        baseCount = count
        notifyItemRangeChanged(0, ingredientsList.size)
    }

    fun setSelectedCount(count: Int) {
        selectedCount = count
        notifyItemRangeChanged(0, ingredientsList.size)
    }

    class IngredientDiffUtilCallback(
        private val oldList: List<IngredientWithMeta>,
        private val newList: List<IngredientWithMeta>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].product.id == newList[newItemPosition].product.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
