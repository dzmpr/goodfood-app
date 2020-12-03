package pw.prsk.goodfood.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name= "name") var name: String,
    @ColumnInfo(name="description") var description: String,
    @ColumnInfo(name="last_eaten") var last_eaten: LocalDateTime,
    @ColumnInfo(name="eat_count") var eat_count: Int,
    @ColumnInfo(name="category_id") var category_id: Int
)