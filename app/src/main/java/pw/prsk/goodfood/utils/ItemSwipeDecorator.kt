package pw.prsk.goodfood.utils

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.text.TextPaint
import android.util.TypedValue
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class ItemSwipeDecorator private constructor() {
    // Left side swipe icon
    private var leftSideIconId: Int? = null
    private var leftSideIconTintColor: Int? = null

    // Right swipe itemText
    private var leftSideTextId: Int? = null
    private var leftSideTextSize: Float = 14f
    private var leftSideTextColorId: Int? = null

    // Right swipe background color
    private var leftSideBackgroundColor: Int? = null

    // Left swipe icon
    private var rightSideIconId: Int? = null
    private var rightSideIconTintColor: Int? = null

    // Left swipe itemText
    private var rightSideTextId: Int? = null
    private var rightSideTextSize: Float = 14f
    private var rightSideTextColorId: Int? = null

    // Left swipe background color
    private var rightSideBackgroundColor: Int? = null

    // Common
    private var iconMargin: Int = 10

    fun decorate(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState != ItemTouchHelper.ACTION_STATE_SWIPE) return

        if (dX > 0) {
            rightSwipeDecorate(c, recyclerView, viewHolder, dX)
        } else if (dX < 0) {
            leftSwipeDecorate(c, recyclerView, viewHolder, dX)
        }
    }

    private fun rightSwipeDecorate(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float
    ) {
        val itemView = viewHolder.itemView
        val resources = itemView.resources

        c.clipRect(
            itemView.left,
            itemView.top,
            itemView.left + dX.toInt(),
            itemView.bottom
        )

        // Draw background
        val background = if (leftSideBackgroundColor != null) {
            ColorDrawable(resources.getColor(leftSideBackgroundColor!!, itemView.context.theme))
        } else {
            ColorDrawable(Color.GRAY)
        }
        background.setBounds(
            itemView.left,
            itemView.top,
            itemView.left + dX.toInt(),
            itemView.bottom
        )
        background.draw(c)

        // Draw icon
        var iconSize = 0
        if (leftSideIconId != null && dX > iconMargin) {
            val icon = ContextCompat.getDrawable(recyclerView.context, leftSideIconId!!)
            if (icon != null) {
                iconSize = icon.intrinsicHeight
                val halfIcon = iconSize / 2
                val top = itemView.top + ((itemView.bottom - itemView.top) / 2 - halfIcon)
                icon.setBounds(
                    itemView.left + iconMargin,
                    top,
                    itemView.left + iconMargin + icon.intrinsicWidth,
                    top + icon.intrinsicHeight
                )
                if (leftSideIconTintColor != null) {
                    val filter =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            BlendModeColorFilter(
                                resources.getColor(leftSideIconTintColor!!, itemView.context.theme),
                                BlendMode.SRC_IN
                            )
                        } else {
                            PorterDuffColorFilter(
                                resources.getColor(leftSideIconTintColor!!, itemView.context.theme),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    icon.colorFilter = filter
                }
                icon.draw(c)
            }
        }

        // Draw itemText
        if (leftSideTextId != null && dX > iconMargin + iconSize) {
            val textPaint = TextPaint()
            textPaint.isAntiAlias = true
            textPaint.textSize =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    leftSideTextSize,
                    recyclerView.context.resources.displayMetrics
                )
            textPaint.color = if (leftSideTextColorId != null) {
                resources.getColor(leftSideTextColorId!!, itemView.context.theme)
            } else {
                Color.WHITE
            }
            // TODO: add ability to change typeface
            textPaint.typeface = Typeface.SANS_SERIF
            val textTop =
                (itemView.top + (itemView.bottom - itemView.top) / 2.0 + textPaint.textSize / 2).toFloat()
            c.drawText(
                resources.getString(leftSideTextId!!),
                (itemView.left + iconMargin + iconSize + if (iconSize > 0) iconMargin / 2 else 0).toFloat(),
                textTop,
                textPaint
            )
        }
    }

    private fun leftSwipeDecorate(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float
    ) {
        val itemView = viewHolder.itemView
        val resources = itemView.resources

        c.clipRect(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )

        // Draw background
        val background = if (rightSideBackgroundColor != null) {
            ColorDrawable(resources.getColor(rightSideBackgroundColor!!, itemView.context.theme))
        } else {
            ColorDrawable(Color.GRAY)
        }
        background.setBounds(
            itemView.right + dX.toInt(),
            itemView.top,
            itemView.right,
            itemView.bottom
        )
        background.draw(c)

        // Draw icon
        var iconSize = 0
        var imgLeft = itemView.right
        if (rightSideIconId != null && dX < -iconMargin) {
            val icon = ContextCompat.getDrawable(recyclerView.context, rightSideIconId!!)
            if (icon != null) {
                iconSize = icon.intrinsicHeight
                val halfIcon = iconSize / 2
                val top =
                    viewHolder.itemView.top + ((itemView.bottom - itemView.top) / 2 - halfIcon)
                imgLeft = itemView.right - iconMargin - halfIcon * 2
                icon.setBounds(
                    imgLeft,
                    top,
                    itemView.right - iconMargin,
                    top + icon.intrinsicHeight
                )
                if (rightSideIconTintColor != null) {
                    val filter =
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            BlendModeColorFilter(
                                resources.getColor(rightSideIconTintColor!!, itemView.context.theme),
                                BlendMode.SRC_IN
                            )
                        } else {
                            PorterDuffColorFilter(
                                resources.getColor(rightSideIconTintColor!!, itemView.context.theme),
                                PorterDuff.Mode.SRC_IN
                            )
                        }
                    icon.colorFilter = filter
                }
                icon.draw(c)
            }
        }

        // Draw itemText
        if (rightSideTextId != null && dX < -iconMargin - iconSize) {
            val textPaint = TextPaint()
            textPaint.isAntiAlias = true
            textPaint.textSize =
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_SP,
                    rightSideTextSize,
                    recyclerView.context.resources.displayMetrics
                )
            textPaint.color = if (rightSideTextColorId != null) {
                resources.getColor(rightSideTextColorId!!, itemView.context.theme)
            } else {
                Color.WHITE
            }
            // TODO: add ability to change typeface
            textPaint.typeface = Typeface.SANS_SERIF
            val width = textPaint.measureText(resources.getString(rightSideTextId!!))
            val textTop =
                (viewHolder.itemView.top + (viewHolder.itemView.bottom - viewHolder.itemView.top) / 2.0 + textPaint.textSize / 2).toFloat()
            c.drawText(
                resources.getString(rightSideTextId!!),
                imgLeft - width - if (imgLeft == itemView.right) iconMargin else iconMargin / 2,
                textTop,
                textPaint
            )
        }
    }

    companion object {
        class Builder {
            private val itemDecorator = ItemSwipeDecorator()

            fun setLeftSideIcon(leftSideIconId: Int? = null, leftSideIconTintId: Int? = null): Builder {
                itemDecorator.leftSideIconId = leftSideIconId
                itemDecorator.leftSideIconTintColor = leftSideIconTintId
                return this
            }

            fun setRightSideIcon(rightSideIconId: Int? = null, rightSideIconTintId: Int? = null): Builder {
                itemDecorator.rightSideIconId = rightSideIconId
                itemDecorator.rightSideIconTintColor = rightSideIconTintId
                return this
            }

            fun setBackgroundColor(rightSideColorId: Int? = null, leftSideColorId: Int? = null): Builder {
                itemDecorator.rightSideBackgroundColor = rightSideColorId
                itemDecorator.leftSideBackgroundColor = leftSideColorId
                return this
            }

            fun setLeftSideText(leftSideTextId: Int? = null, leftSideTextColorId: Int? = null, leftSideTextSize: Float? = null): Builder {
                itemDecorator.leftSideTextId = leftSideTextId
                itemDecorator.leftSideTextColorId = leftSideTextColorId
                if (leftSideTextSize != null) {
                    itemDecorator.leftSideTextSize = leftSideTextSize
                }
                return this
            }

            fun setRightSideText(rightSideTextId: Int? = null, rightSideTextColorId: Int? = null, rightSideTextSize: Float? = null): Builder {
                itemDecorator.rightSideTextId = rightSideTextId
                itemDecorator.rightSideTextColorId = rightSideTextColorId
                if (rightSideTextSize != null) {
                    itemDecorator.rightSideTextSize = rightSideTextSize
                }
                return this
            }

            fun setIconMargin(margin: Int): Builder {
                itemDecorator.iconMargin = margin
                return this
            }

            fun getDecorator(): ItemSwipeDecorator {
                return itemDecorator
            }
        }
    }
}