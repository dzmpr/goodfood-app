package pw.prsk.goodfood.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pw.prsk.goodfood.R
import pw.prsk.goodfood.data.Meal

class MealAdapter : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {
    private var mealList: List<Meal>? = null

    fun setList(list: List<Meal>) {
        mealList = list
        notifyDataSetChanged()
    }

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvMealName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        return MealViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_meal, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.name.text = mealList?.get(position)?.name ?: "Not provided"
    }

    override fun getItemCount(): Int = mealList?.size ?: 0
}