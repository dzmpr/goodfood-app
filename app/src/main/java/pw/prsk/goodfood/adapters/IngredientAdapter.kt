package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.IngredientWithMeta
import pw.prsk.goodfood.utils.IngredientDiffUtilCallback

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
            ingredientsList = newList
            notifyDataSetChanged()
        } else {
            val diffResult = DiffUtil.calculateDiff(IngredientDiffUtilCallback(ingredientsList, newList))
            ingredientsList = newList
            diffResult.dispatchUpdatesTo(this)
        }
    }

    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ingredientName: TextView = view.findViewById(R.id.tvIngredientName)
        private val ingredientAmount: TextView = view.findViewById(R.id.tvIngredientAmount)
        private val amountUnit: TextView = view.findViewById(R.id.tvAmountUnit)

        fun bind(item: IngredientWithMeta) {
            ingredientName.text = item.product_name
            val amount = item.amount.toInt()
            if (amount < item.amount) {
                ingredientAmount.text = item.amount.toString()
            } else {
                ingredientAmount.text = amount.toString()
            }
            amountUnit.text = item.unit_name
        }
    }
}