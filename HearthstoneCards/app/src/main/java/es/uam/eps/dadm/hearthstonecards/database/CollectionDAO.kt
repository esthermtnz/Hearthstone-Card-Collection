package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection

@Dao
interface CollectionDAO {
    @Query("SELECT * FROM collection_table")
    fun getCollections(): LiveData<List<Collection>>

    @Query("SELECT * FROM collection_table WHERE id = :idCollection")
    fun getCollection(idCollection: Int): LiveData<Collection?>

    @Insert
    suspend fun addCollection(collection: Collection)

    @Query("DELETE FROM collection_table")
    suspend fun removeCollections()
}

