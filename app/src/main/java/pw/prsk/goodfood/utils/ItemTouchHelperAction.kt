package pw.prsk.goodfood.utils

interface ItemTouchHelperAction {
    fun itemSwiped(position: Int, direction: Int)
    fun itemMoved(startPosition: Int, endPosition: Int)
}