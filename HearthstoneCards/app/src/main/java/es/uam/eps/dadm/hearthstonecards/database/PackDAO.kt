package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Pack

/**
 * DAO for the Pack model
 *
 * Provides functions to get, insert, and delete packs
 */
@Dao
interface PackDAO {

    /**
     * Gets all packs in the database
     *
     * @return List of Pack objects
     */
    @Query("SELECT * FROM pack_table")
    suspend fun getPacks(): List<Pack>

    /**
     * Gets a specific pack by its ID
     *
     * @param idPack ID of the pack
     * @return The Pack object, or null
     */
    @Query("SELECT * FROM pack_table WHERE id= :idPack")
    fun getPack(idPack: Int): Pack?

    /**
     * Inserts a pack into the database or replaces it if it already exists
     *
     * @param pack The Pack object to insert or update
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPack(pack: Pack)

    /**
     * Deletes all packs from the database
     */
    @Query("DELETE FROM pack_table")
    suspend fun removePacks()
}