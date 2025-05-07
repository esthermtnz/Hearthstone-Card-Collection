package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Collection

@Dao
interface CollectionDAO {
    @Query("SELECT * FROM collection_table")
    fun getCollections(): List<Collection>

    @Query("SELECT * FROM collection_table WHERE id = :idCollection")
    fun getCollection(idCollection: Int): Collection?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollection(collection: Collection)

    @Query("DELETE FROM collection_table")
    suspend fun removeCollections()
}

