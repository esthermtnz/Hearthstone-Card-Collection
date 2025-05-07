/**
 * Class that holds the functioning of the main view
 */
package es.uam.eps.dadm.hearthstonecards.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User
import timber.log.Timber

/**
 * ViewModel class that stores user and UI shared in the activities
 */
class MainViewModel() : ViewModel() {

    private var packs: List<Pack> = emptyList()

    private var username: String? = null
    private var user: User? = null
    //Main
    private val _openTokens = MutableLiveData<Int>()
    val openTokens: LiveData<Int> get() = _openTokens
    //Profile
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> get() = _userName
    private val _userSurname = MutableLiveData<String>()
    val userSurname: LiveData<String> get() = _userSurname
    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail
    private val _userPhone = MutableLiveData<String>()
    val userPhone: LiveData<String> get() = _userPhone
    private val _userIcon = MutableLiveData<String>()
    val userIcon: LiveData<String> get() = _userIcon
    private lateinit var database: AppDatabase

    init {
        Timber.i("MainViewModel created")
    }

    /**
     * Sets the current username
     * @param username The username string
     */
    fun setUsername(username: String?){
        this.username = username
    }

    /**
     * Updates the user LiveData and stores user information
     * @param user The user instance retrieved
     */
    fun setUser(user: User?){
        this.user = user
        _openTokens.value = user?.openTokens ?:0
        _userName.value = user?.name
        _userSurname.value = user?.surname
        _userEmail.value = user?.email
        _userPhone.value = user?.tlf
        _userIcon.value = user?.icon
    }

    /**
     * Gets the current user
     */
    fun getUser(): User?{
        return this.user
    }

    /**
     * GEts the current username
     */
    fun getUsername(): String?{
        return this.username
    }

    /**
     * Sets the list of card packs
     * @param packs List of Pack objects
     */
    fun setPacks(packs: List<Pack>){
        this.packs = packs
    }

    /**
     * Gets the list of card packs
     */
    fun getPacks(): List<Pack>{
        return this.packs
    }

    /**
     * Updates the user's profile icon in the ViewModel and the database
     *
     * @param context The context used to access the database
     * @param iconName The name of the icon
     */
    fun updateUserIcon(context: Context, iconName: String) {
        _userIcon.value = iconName
        user?.let {
            it.icon = iconName
            val dao = AppDatabase.getInstance(context).userDao
            Thread {
                dao.updateUser(it)
            }.start()
        }
    }

    /**
     * Called when the ViewModel is destroyed
     */
    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}