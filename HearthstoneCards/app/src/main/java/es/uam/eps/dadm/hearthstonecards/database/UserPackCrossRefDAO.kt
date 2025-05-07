package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.UserPackCrossRef

/**
 * DAO for the N-N relationship between users and packs
 *
 * Provides methods to insert, get, and delete relationships
 */
@Dao
interface UserPackCrossRefDAO {

    /**
     * Gets all user-pack relationships as LiveData
     *
     * @return LiveData containing a list of UserPackCrossRef objects
     */
    @Query("SELECT * FROM user_pack_table")
    fun getUserPackCrossRefs(): LiveData<List<UserPackCrossRef>>

    /**
     * Gets all pack relationships for a specific user
     *
     * @param username The username whose related packs are to be fetched
     * @return LiveData containing a list of UserPackCrossRef entries
     */
    @Query("SELECT * FROM user_pack_table WHERE username = :username")
    fun getPacksFromUsername(username: String): LiveData<List<UserPackCrossRef>>

    /**
     * Gets all user relationships for a specific pack
     *
     * @param idPack ID of the pack
     * @return LiveData containing a list of UserPackCrossRef entries
     */
    @Query("SELECT * FROM user_pack_table WHERE packId = :idPack")
    fun getUsersFromPackId(idPack: Int): LiveData<List<UserPackCrossRef>>

    /**
     * Inserts a new user-pack relationship into the database
     *
     * @param userPackCrossRef The UserPackCrossRef object to insert
     */
    @Insert
    suspend fun addUserPackCrossRef(userPackCrossRef: UserPackCrossRef)

    /**
     * Deletes all user-pack relationship entries from the table
     */
    @Query("DELETE FROM user_pack_table")
    suspend fun removeUserPackCrossRefs()
}