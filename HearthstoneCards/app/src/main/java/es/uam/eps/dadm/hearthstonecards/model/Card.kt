/**
 * Data class made to define a card in the app
 */
package es.uam.eps.dadm.hearthstonecards.model

/**
 * Definition of the data class Card
 *
 * @param id ID of the card
 * @param pictureRes ID from the image in res/drawable
 * @param rarity Rarity of the card. Expressed in a range from (0-1)
 */
data class Card (
    val id: Int ,
    val pictureRes: Int, //Id image in res/drawable
    var rarity: Double
)