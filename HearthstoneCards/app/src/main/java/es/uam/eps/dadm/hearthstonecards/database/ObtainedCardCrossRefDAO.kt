package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.ObtainedCardCrossRef

@Dao
interface ObtainedCardCrossRefDAO {
    @Query("SELECT * FROM obtained_card_table")
    fun getObtainedCardCrossRefs(): LiveData<List<ObtainedCardCrossRef>>

    @Query("SELECT * FROM obtained_card_table WHERE userId = :idUser")
    fun getObtainedCardsFromUserId(idUser: Int): LiveData<List<ObtainedCardCrossRef>>

    @Query("SELECT * FROM obtained_card_table WHERE userId = :idUser AND collectionId = :idCollection")
    fun getObtainedCardsFromUserIdAndCollectionId(idUser: Int, idCollection:Int): LiveData<List<ObtainedCardCrossRef>>

    @Query("SELECT quantity FROM obtained_card_table WHERE userId = :idUser AND collectionId = :idCollection AND cardId = :idCard")
    fun getQuantity(idUser: Int, idCollection: Int, idCard: Int): Int

    @Insert
    suspend fun addObtainedCard(obtainedCardCrossRef: ObtainedCardCrossRef)

    @Query("UPDATE obtained_card_table SET quantity=:newQuantity WHERE userId = :idUser AND collectionId = :idCollection AND cardId = :idCard")
    suspend fun updateQuantity(idUser: Int, idCollection: Int, idCard: Int, newQuantity: Int)

    @Query("DELETE FROM obtained_card_table")
    suspend fun removeObtainedCardCrossRef()
}