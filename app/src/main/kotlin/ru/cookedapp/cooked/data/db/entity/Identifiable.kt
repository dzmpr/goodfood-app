package ru.cookedapp.cooked.data.db.entity

interface Identifiable {

    val id: Long

    fun isNew(): Boolean = id == 0L
}
