package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.UserPackCrossRef

@Dao
interface UserPackCrossRefDAO {
    @Query("SELECT * FROM user_pack_table")
    fun getUserPackCrossRefs(): LiveData<List<UserPackCrossRef>>

    @Query("SELECT * FROM user_pack_table WHERE username = :username")
    fun getPacksFromUsername(username: String): LiveData<List<UserPackCrossRef>>

    @Query("SELECT * FROM user_pack_table WHERE packId = :idPack")
    fun getUsersFromPackId(idPack: Int): LiveData<List<UserPackCrossRef>>

    @Insert
    suspend fun addUserPackCrossRef(userPackCrossRef: UserPackCrossRef)

    @Query("DELETE FROM user_pack_table")
    suspend fun removeUserPackCrossRefs()
}