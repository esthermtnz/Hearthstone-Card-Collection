package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

/**
 * Cross-reference entity for the N-N relationship between collections and cards
 *
 * @property collectionId ID of the collection
 * @property cardId ID of the card
 */
@Entity(primaryKeys = ["collectionId", "cardId"], tableName = "collection_card_table")
data class CollectionCardCrossRef (
    val collectionId: Int,
    val cardId: Int
)