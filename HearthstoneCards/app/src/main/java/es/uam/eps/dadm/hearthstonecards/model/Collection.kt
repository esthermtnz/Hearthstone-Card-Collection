/**
 * Data class created to define the functioning of the card collections.
 */

package es.uam.eps.dadm.hearthstonecards.model
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Definition of the data class collection
 *
 * @param id ID of the collection
 * @param name Name of the collection
 * //@param cards All cards included in the collection
 * //@param obtained Cards that a determined user has collected from a specific collection
 */
@Entity(tableName="collection_table")
data class Collection(
    @PrimaryKey
    val id: Int,
    var name: String,
)
