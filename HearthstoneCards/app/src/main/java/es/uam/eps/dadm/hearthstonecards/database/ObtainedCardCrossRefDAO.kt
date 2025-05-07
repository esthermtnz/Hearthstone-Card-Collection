package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.ObtainedCardCrossRef

/**
 * DAO for the N-N relationship between users and obtained cards
 */
@Dao
interface ObtainedCardCrossRefDAO {

    /**
     * Gets all entries in the obtained card table as LiveData
     *
     * @return LiveData containing a list of ObtainedCardCrossRef objects
     */
    @Query("SELECT * FROM obtained_card_table")
    fun getObtainedCardCrossRefs(): LiveData<List<ObtainedCardCrossRef>>

    /**
     * Gets all obtained card references for a user
     *
     * @param username The username whose obtained cards are to be gotten
     * @return List of ObtainedCardCrossRef objects
     */
    @Query("SELECT * FROM obtained_card_table WHERE username = :username")
    fun getObtainedCardsFromUsername(username: String): List<ObtainedCardCrossRef>

    /**
     * Gets all cards obtained by a user in a given collection
     *
     * @param username The username of the user
     * @param idCollection ID of the collection
     * @return A list of Card objects linked to the user and collection
     */
    @Query("""
    SELECT card_table.*
    FROM card_table
    INNER JOIN obtained_card_table
    ON card_table.id = obtained_card_table.cardId
    WHERE obtained_card_table.username = :username
    AND obtained_card_table.collectionId = :idCollection
""")
    suspend fun getObtainedCardsFromUsernameAndCollectionId(
        username: String,
        idCollection: Int
    ): List<Card>

    /**
     * Gets the number of a specific card from a user and a collection
     *
     * @param username The username of the user
     * @param idCollection ID of the collection
     * @param idCard ID of the card
     * @return The number of cards owned or null
     */
    @Query("SELECT quantity FROM obtained_card_table WHERE username = :username AND collectionId = :idCollection AND cardId = :idCard")
    fun getQuantity(username: String, idCollection: Int, idCard: Int): Int?

    /**
     * Inserts a new card entry to the database
     *
     * @param obtainedCardCrossRef The ObtainedCardCrossRef entry to insert
     */
    @Insert
    suspend fun addObtainedCard(obtainedCardCrossRef: ObtainedCardCrossRef)

    /**
     * Updates the number of a specific cards from a user and collection
     *
     * @param username The username of the user
     * @param idCollection ID of the collection
     * @param idCard ID of the card
     * @param newQuantity The number of cards
     */
    @Query("UPDATE obtained_card_table SET quantity=:newQuantity WHERE username = :username AND collectionId = :idCollection AND cardId = :idCard")
    suspend fun updateQuantity(username: String, idCollection: Int, idCard: Int, newQuantity: Int)

    /**
     * Deletes all entries from the obtained card table
     */
    @Query("DELETE FROM obtained_card_table")
    suspend fun removeObtainedCardCrossRef()
}