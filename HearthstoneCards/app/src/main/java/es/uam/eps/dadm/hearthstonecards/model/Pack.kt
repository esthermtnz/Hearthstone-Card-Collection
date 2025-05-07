/**
 * Data class created to define a pack of cards in the app
 */
package es.uam.eps.dadm.hearthstonecards.model
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Definition of the data class Pack
 *
 * @param id ID of the pack
 * @param name Name of the pack
 * @param picture ID of the image in res/drawable
 */
@Entity(tableName="pack_table")
data class Pack(
    @PrimaryKey
    val id: Int,
    var name: String,
    var picture: Int,
)