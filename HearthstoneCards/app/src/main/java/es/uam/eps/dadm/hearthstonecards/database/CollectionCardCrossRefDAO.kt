package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.CollectionCardCrossRef

@Dao
interface CollectionCardCrossRefDAO {
    @Query("SELECT * FROM collection_card_table")
    fun getCollectionCardCrossRefs(): List<CollectionCardCrossRef>

    @Query("SELECT * FROM collection_card_table WHERE cardId = :idCard")
    fun getCollectionFromCardId(idCard: Int): CollectionCardCrossRef?

    @Query("SELECT * FROM collection_card_table WHERE collectionId = :idCollection")
    fun getCardsFromCollectionId(idCollection: Int): List<CollectionCardCrossRef>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollectionCardCrossRef(collectionCardCrossRef: CollectionCardCrossRef)

    @Query("DELETE FROM collection_card_table")
    suspend fun removeCollectionCardCrossRefs()
}