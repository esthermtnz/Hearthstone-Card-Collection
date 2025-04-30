/**
 * Data class created to model a user in the app
 */
package es.uam.eps.dadm.hearthstonecards.model
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * Definition for the data class User
 *
 * @param id ID of the user
 * @param name Name of the user
 * @param surname Surname of the user
 * @param email Email of the user
 * @param tlf Phone number of the user
 * @param password Password of the user
 * @param username Username of the user
 * @param openToken Tokens for opening a pack
 * @param packs Packs available for the user to open
 * @param collections Collections the user have from the different collections available
 */
@Entity(tableName="user_table")
data class User(
    @PrimaryKey
    val id: Int,
    var name: String,
    var surname: String,
    var email: String,
    var tlf: String,
    var password: String,
    var username: String,
    var openToken: String,
    var packs: MutableList<Pack>,
    var collections: MutableList<Collection>
) {
    /**
     * Changes the password for an username
     *
     * @param newPassword New password for the user
     */
    fun changePassword(newPassword: String) {
        password = newPassword
    }

    /**
     * Opens a pack and saves the cards obtained in the appropriate collection, and removes the opened
     * pack from the available ones
     *
     * @param packId ID of the pack that is going to be open
     */
    fun openPack(packId: Int) {
        val packToRemove = packs.find { it.id == packId }
        if (packToRemove != null) {
            packToRemove.openPack()

            //Add card to obtained and update number of repetitions
            for (carta in packToRemove.cards) {
                val cantidadActual = packToRemove.collection.obtained[carta] ?: 0
                packToRemove.collection.obtained[carta] = cantidadActual + 1
            }

            println("Cartas obtenidas al abrir el sobre:")
            for (carta in packToRemove.cards) {
                println("- ${carta.id} (rareza: ${carta.rarity})")
            }

            //Delete pack
            packs.remove(packToRemove)
        } else {
            println("Pack con id $packId no encontrado.")
        }
    }

}
