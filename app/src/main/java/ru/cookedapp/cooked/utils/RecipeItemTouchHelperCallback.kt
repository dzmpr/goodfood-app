package ru.cookedapp.cooked.utils

import android.graphics.*
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecipeItemTouchHelperCallback(
    private val handler: ItemTouchHelperAction,
    private val swipeDecorator: ItemSwipeDecorator
) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = 0
        val swipeFlags = ItemTouchHelper.LEFT
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun isLongPressDragEnabled(): Boolean = false

    override fun isItemViewSwipeEnabled(): Boolean = true

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        swipeDecorator.decorate(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        if (direction == ItemTouchHelper.LEFT) {
            handler.itemSwiped(viewHolder.adapterPosition, direction)
        }
    }
}
