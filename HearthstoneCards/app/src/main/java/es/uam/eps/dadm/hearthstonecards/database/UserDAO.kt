package es.uam.eps.dadm.hearthstonecards.database
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user_table")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE id= :idUser")
    fun getUser(idUser: Int): LiveData<User?>

    @Insert
    suspend fun addUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun removeUsers()
}