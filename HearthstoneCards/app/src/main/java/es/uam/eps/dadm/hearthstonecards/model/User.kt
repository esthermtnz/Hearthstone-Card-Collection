/**
 * Data class created to model a user in the app
 */
package es.uam.eps.dadm.hearthstonecards.model
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Definition for the data class User
 *
 * //@param id ID of the user
 * @param name Name of the user
 * @param surname Surname of the user
 * @param email Email of the user
 * @param tlf Phone number of the user
 * @param password Password of the user
 * @param username Username of the user
 * @param openTokens Tokens for opening a pack
 * //@param packs Packs available for the user to open
 * //@param collections Collections the user have from the different collections available
 */
@Entity(tableName="user_table")
data class User(

    var name: String,
    var surname: String,
    var email: String,
    var tlf: String,
    var password: String,
    @PrimaryKey
    var username: String,
    var openTokens: Int,
    var icon: String = "default_icon",
)
