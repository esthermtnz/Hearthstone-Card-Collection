package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Collection

/**
 * DAO for the Collection model
 */
@Dao
interface CollectionDAO {

    /**
     * Gets all collections stored in the database.
     *
     * @return List of Collection objects
     */
    @Query("SELECT * FROM collection_table")
    fun getCollections(): List<Collection>

    /**
     * Gets a specific collection from the ID
     *
     * @param idCollection ID of the collection given
     * @return Collection object, or null
     */
    @Query("SELECT * FROM collection_table WHERE id = :idCollection")
    fun getCollection(idCollection: Int): Collection?

    /**
     * Inserts a new collection into the database or replaces it if it exists
     *
     * @param collection Collection object to insert or update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollection(collection: Collection)

    /**
     * Deletes all collections from the database
     */
    @Query("DELETE FROM collection_table")
    suspend fun removeCollections()
}

