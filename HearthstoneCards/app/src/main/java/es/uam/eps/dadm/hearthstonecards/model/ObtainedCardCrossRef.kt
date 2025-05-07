package es.uam.eps.dadm.hearthstonecards.model

import androidx.room.Entity

/**
 * Cross-reference entity for the N-N relationship between obtained cards abd user
 *
 * @property username The username of the user who obtained the card
 * @property collectionId ID of the collection the card belongs to
 * @property cardId ID of the obtained card
 * @property quantity The number of copies of the card the user owns in that collection
 */
@Entity(primaryKeys = ["username", "collectionId", "cardId"], tableName = "obtained_card_table")
data class ObtainedCardCrossRef(
    val username: String,
    val collectionId: Int,
    val cardId: Int,
    val quantity: Int
)