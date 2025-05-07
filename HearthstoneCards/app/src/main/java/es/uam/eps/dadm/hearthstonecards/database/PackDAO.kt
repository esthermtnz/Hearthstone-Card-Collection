package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Pack

@Dao
interface PackDAO {
    @Query("SELECT * FROM pack_table")
    suspend fun getPacks(): List<Pack>

    @Query("SELECT * FROM pack_table WHERE id= :idPack")
    fun getPack(idPack: Int): Pack?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPack(pack: Pack)

    @Query("DELETE FROM pack_table")
    suspend fun removePacks()
}