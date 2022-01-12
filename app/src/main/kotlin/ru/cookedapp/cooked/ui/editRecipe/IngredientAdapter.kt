package ru.cookedapp.cooked.ui.editRecipe

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.cookedapp.cooked.R
import ru.cookedapp.storage.entity.IngredientWithMeta

class IngredientAdapter : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    private var ingredientsList: List<IngredientWithMeta> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient, parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    override fun getItemCount(): Int = ingredientsList.size

    fun setList(newList: List<IngredientWithMeta>) {
        if (ingredientsList.isEmpty()) {
            ingredientsList = newList.toList()
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(IngredientDiffUtilCallback(ingredientsList, newList))
            ingredientsList = newList.toList()
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientName: TextView = view.findViewById(R.id.tvIngredientName)
        private val ingredientAmount: TextView = view.findViewById(R.id.tvIngredientAmount)
        private val amountUnit: TextView = view.findViewById(R.id.tvAmountUnit)

        fun bind(item: IngredientWithMeta) {
            ingredientName.text = item.product.name
            val amount = item.amount.toInt()
            if (amount < item.amount) {
                ingredientAmount.text = item.amount.toString()
            } else {
                ingredientAmount.text = amount.toString()
            }
            amountUnit.text = item.unit.name
        }
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
