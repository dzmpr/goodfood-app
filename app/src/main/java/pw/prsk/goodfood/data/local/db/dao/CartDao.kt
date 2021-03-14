package pw.prsk.goodfood.data.local.db.dao

import androidx.room.Dao
import androidx.room.Query
import pw.prsk.goodfood.data.local.db.entity.CartItem

@Dao
interface CartDao: BaseDao<CartItem> {

}