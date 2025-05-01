package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

@Entity(primaryKeys = ["collectionId", "cardId"], tableName = "collection_card_table")
data class CollectionCardCrossRef (
    val collectionId: Int,
    val cardId: Int
)