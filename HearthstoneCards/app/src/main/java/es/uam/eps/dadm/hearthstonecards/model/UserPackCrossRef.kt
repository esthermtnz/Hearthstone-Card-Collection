package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

@Entity(primaryKeys = ["username", "packId"], tableName = "user_pack_table")
data class UserPackCrossRef(
    val username: String,
    val packId: Int
)