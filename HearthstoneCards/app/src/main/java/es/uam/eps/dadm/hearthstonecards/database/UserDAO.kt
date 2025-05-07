package es.uam.eps.dadm.hearthstonecards.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.uam.eps.dadm.hearthstonecards.model.User

/**
 * DAO for the User model
 *
 * Provides functions to get, insert, update, and deleting users and manage the tokens
 */
@Dao
interface UserDAO {

    /**
     * Gets all users from the database
     *
     * @return List of User objects in the database
     */
    @Query("SELECT * FROM user_table")
    fun getUsers(): List<User>

    /**
     * Gets a specific user by their username
     *
     * @param username The unique username of the user
     * @return The User object, or null
     */
    @Query("SELECT * FROM user_table WHERE username= :username")
    suspend fun getUser(username: String): User?

    /**
     * Inserts a new user into the database
     *
     * @param user The User object to insert
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: User)

    /**
     * Deletes all users from the database
     */
    @Query("DELETE FROM user_table")
    suspend fun removeUsers()

    /**
     * Decreases the openTokens value by 1 for a user
     *
     * @param username The username of the user
     */
    @Query("UPDATE user_table SET openTokens=openTokens-1 WHERE username=:username")
    suspend fun decreaseToken(username: String)

    /**
     * Updates an existing user
     *
     * @param user The User object with updated fields
     */
    @Update
    fun updateUser(user: User)
}