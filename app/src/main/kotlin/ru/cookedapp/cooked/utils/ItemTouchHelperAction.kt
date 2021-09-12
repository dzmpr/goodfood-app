package ru.cookedapp.cooked.utils

interface ItemTouchHelperAction {
    fun itemSwiped(position: Int, direction: Int)
    fun itemMoved(startPosition: Int, endPosition: Int)
}
