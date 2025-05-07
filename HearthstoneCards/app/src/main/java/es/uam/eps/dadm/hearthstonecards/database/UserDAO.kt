package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.uam.eps.dadm.hearthstonecards.model.User

@Dao
interface UserDAO {
    @Query("SELECT * FROM user_table")
    fun getUsers(): List<User>

    @Query("SELECT * FROM user_table WHERE username= :username")
    suspend fun getUser(username: String): User?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    @Query("DELETE FROM user_table")
    suspend fun removeUsers()

    @Query("UPDATE user_table SET openTokens=openTokens-1 WHERE username=:username")
    suspend fun decreaseToken(username: String)

    @Update
    fun updateUser(user: User)
}