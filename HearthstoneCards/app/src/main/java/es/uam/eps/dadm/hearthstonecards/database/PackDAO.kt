package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.Pack

@Dao
interface PackDAO {
    @Query("SELECT * FROM pack_table")
    fun getPacks(): LiveData<List<Pack>>

    @Query("SELECT * FROM pack_table WHERE id= :idPack")
    fun getPack(idPack: Int): LiveData<Pack?>

    @Insert
    suspend fun addPack(pack: Pack)

    @Query("DELETE FROM pack_table")
    suspend fun removePacks()
}