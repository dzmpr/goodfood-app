package pw.prsk.goodfood.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    fun insert(item: T)

    @Delete
    fun delete(item: T)

    @Update
    fun update(item: T)
}