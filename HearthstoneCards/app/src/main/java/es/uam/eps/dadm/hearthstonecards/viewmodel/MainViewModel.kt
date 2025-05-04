/**
 * Class that holds the functioning of the main view
 */
package es.uam.eps.dadm.hearthstonecards.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User
import timber.log.Timber

/**
 * Definition of the class
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


    init {
        Timber.i("MainViewModel created")
    }

    fun setUsername(username: String?){
        this.username = username
    }

    fun setUser(user: User?){
        this.user = user
        _openTokens.value = user?.openTokens ?:0
        _userName.value = user?.name
        _userSurname.value = user?.surname
        _userEmail.value = user?.email
        _userPhone.value = user?.tlf
        _userIcon.value = user?.icon
    }

    fun getUser(): User?{
        return this.user
    }

    fun getUsername(): String?{
        return this.username
    }

    fun setPacks(packs: List<Pack>){
        this.packs = packs
    }

    fun getPacks(): List<Pack>{
        return this.packs
    }

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




    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}