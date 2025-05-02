package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card

@Dao
interface CardDAO {
    @Query("SELECT * FROM card_table")
    fun getCards(): LiveData<List<Card>>

    @Query("SELECT * FROM card_table WHERE id = :idCard")
    fun getCard(idCard: Int): LiveData<Card?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCard(card: Card)

    @Query("DELETE FROM card_table")
    suspend fun removeCards()
}