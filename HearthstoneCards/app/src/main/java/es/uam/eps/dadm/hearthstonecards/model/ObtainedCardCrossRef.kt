package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

@Entity(primaryKeys = ["username", "collectionId", "cardId"], tableName = "obtained_card_table")
data class ObtainedCardCrossRef(
    val username: String,
    val collectionId: Int,
    val cardId: Int,
    val quantity: Int
)