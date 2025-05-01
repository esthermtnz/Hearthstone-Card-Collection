package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "packId"], tableName = "user_pack_table")
data class UserPackCrossRef(
    val userId: Int,
    val packId: Int
)