/**
 * Data class made to define a card in the app
 */
package es.uam.eps.dadm.hearthstonecards.model
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Definition of the data class Card
 *
 * @param id ID of the card
 * @param pictureRes ID from the image in res/drawable
 * @param rarity Rarity of the card. Expressed in a range from (0-1)
 */
@Entity(tableName="card_table")
data class Card (
    @PrimaryKey
    val id: Int ,
    val pictureRes: Int, //Id image in res/drawable
    var rarity: Double
)