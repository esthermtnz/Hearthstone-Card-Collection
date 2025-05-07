package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

/**
 * Cross-reference for the N-N relationship between users and packs
 *
 * @property username The username of the user
 * @property packId ID of the pack
 */
@Entity(primaryKeys = ["username", "packId"], tableName = "user_pack_table")
data class UserPackCrossRef(
    val username: String,
    val packId: Int
)