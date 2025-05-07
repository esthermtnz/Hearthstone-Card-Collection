package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.CollectionCardCrossRef

/**
 * DAO for the N-N relationship between collections and cards
 */
@Dao
interface CollectionCardCrossRefDAO {

    /**
     * Gets all entries from the collection_card_table
     *
     * @return List of CollectionCardCrossRef entries.
     */
    @Query("SELECT * FROM collection_card_table")
    fun getCollectionCardCrossRefs(): List<CollectionCardCrossRef>

    /**
     * Finds the N-N relationship by an ID given
     *
     * @param idCard ID of the card
     * @return CollectionCardCrossRef if it exists, or null
     */
    @Query("SELECT * FROM collection_card_table WHERE cardId = :idCard")
    fun getCollectionFromCardId(idCard: Int): CollectionCardCrossRef?

    /**
     * Gets all card relationships from a collection ID.
     *
     * @param idCollection ID of the collection
     * @return List of CollectionCardCrossRef entries, or null
     */
    @Query("SELECT * FROM collection_card_table WHERE collectionId = :idCollection")
    fun getCardsFromCollectionId(idCollection: Int): List<CollectionCardCrossRef>?

    /**
     * Inserts a new collection-card entry to the database, or replaces it if it exists
     *
     * @param collectionCardCrossRef Relation entry to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollectionCardCrossRef(collectionCardCrossRef: CollectionCardCrossRef)

    /**
     * Deletes all collection-card relationships
     */
    @Query("DELETE FROM collection_card_table")
    suspend fun removeCollectionCardCrossRefs()
}