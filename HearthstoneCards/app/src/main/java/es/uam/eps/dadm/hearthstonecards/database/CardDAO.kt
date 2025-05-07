package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card


/**
 * DAO to access the Card model data
 *
 * Defines the get, add and remove functions
 */
@Dao
interface CardDAO {

    /**
     * Returns a list with all the cards from the database
     *
     * @return List of Card objects
     */
    @Query("SELECT * FROM card_table")
    fun getCards(): List<Card>

    /**
     * Returns a card given its id
     *
     * @return Card or null if it doesn't exist
     */
    @Query("SELECT * FROM card_table WHERE id = :idCard")
    fun getCard(idCard: Int): Card?

    /**
     * Adds a card to the database, and if it already exists gets replaced
     *
     * @param card Card to insert in the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: Card)

    /**
     * Removes all cards from the database
     */
    @Query("DELETE FROM card_table")
    suspend fun removeCards()
}