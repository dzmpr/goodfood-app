package pw.prsk.goodfood.data

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun fromDateToTimestamp(date: LocalDateTime): Long {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    @TypeConverter
    fun toLocalDateTime(millis: Long): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault())
    }

    @TypeConverter
    fun fromIngredientsList(list: List<Ingredient>): String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun toIngredientsList(json: String): List<Ingredient> {
        return Json.decodeFromString(json)
    }

}