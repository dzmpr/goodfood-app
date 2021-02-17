package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.Ingredient

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {
    private val ingredientsList: MutableList<Ingredient> = mutableListOf()

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

    fun addIngredient(item: Ingredient) {
        ingredientsList.add(item)
    }

    fun addIngredientsList(itemList: List<Ingredient>) {
        ingredientsList.addAll(itemList)
    }

    class IngredientViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName: TextView = view.findViewById(R.id.tvIngredientName)
        val ingredientAmount: TextView = view.findViewById(R.id.tvIngredientAmount)
        val amountUnit: TextView = view.findViewById(R.id.tvAmountUnit)

        fun bind(item: Ingredient) {
            ingredientName.text = item.name
            ingredientAmount.text = item.amount.toString()
            amountUnit.text = item.unit.name
        }
    }
}