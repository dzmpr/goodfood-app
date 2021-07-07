package ru.cookedapp.cooked.data.local.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(item: T): Long

    @Delete
    fun delete(item: T)

    @Update
    fun update(item: T)
}
