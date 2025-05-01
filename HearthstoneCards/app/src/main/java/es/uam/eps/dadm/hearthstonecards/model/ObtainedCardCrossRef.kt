package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

@Entity(primaryKeys = ["userId", "collectionId", "cardId"], tableName = "obtained_card_table")
data class ObtainedCardCrossRef(
    val userId: Int,
    val collectionId: Int,
    val cardId: Int,
    val quantity: Int
)