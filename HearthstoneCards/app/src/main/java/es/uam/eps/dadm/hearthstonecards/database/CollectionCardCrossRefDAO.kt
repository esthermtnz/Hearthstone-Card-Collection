package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.CollectionCardCrossRef

@Dao
interface CollectionCardCrossRefDAO {
    @Query("SELECT * FROM collection_card_table")
    fun getCollectionCardCrossRefs(): LiveData<List<CollectionCardCrossRef>>

    @Query("SELECT * FROM collection_card_table WHERE cardId = :idCard")
    fun getCollectionFromCardId(idCard: Int): LiveData<CollectionCardCrossRef>

    @Query("SELECT * FROM collection_card_table WHERE collectionId = :idCollection")
    fun getCardsFromCollectionId(idCollection: Int): LiveData<List<CollectionCardCrossRef>>

    @Insert
    suspend fun addCollectionCardCrossRef(collectionCardCrossRef: CollectionCardCrossRef)

    @Query("DELETE FROM collection_card_table")
    suspend fun removeCollectionCardCrossRefs()
}