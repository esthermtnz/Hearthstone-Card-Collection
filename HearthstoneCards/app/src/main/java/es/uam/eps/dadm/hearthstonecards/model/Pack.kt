/**
 * Data class created to define a pack of cards in the app
 */
package es.uam.eps.dadm.hearthstonecards.model

/**
 * Definition of the data class Pack
 *
 * @param id ID of the pack
 * @param name Name of the pack
 * @param picture ID of the image in res/drawable
 * @param cards Cards contained in the pack itself (obtained through the method shuffleCards in the Collection data class
 * @param collection All the cards that are part of the card pool for the pack
 */
data class Pack(
    val id: Int,
    var name: String,
    var picture: Int, //id in res/drawable
    var cards:  List<Card> = listOf(),
    var collection: Collection
) {
    fun openPack(){
        cards = collection.shuffleCards()
    }
}