package ru.cookedapp.storage.entity

interface Identifiable {

    val id: Long

    fun isNew(): Boolean = id == 0L
}
