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
    private val _openTokens = MutableLiveData<Int>()
    val openTokens: LiveData<Int> get() = _openTokens

/*
    fun openUserPack(packId: Int) {
        user.openPack(packId)
        _packs.value = user.packs
    }*/

    init {
        Timber.i("MainViewModel created")
    }

    fun setUsername(username: String?){
        this.username = username
    }

    fun setUser(user: User?){
        this.user = user
        _openTokens.value = user?.openTokens ?:0
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

    /*fun getOpenTokens(): Int{
        return this.user?.openTokens ?:0
    }*/


    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}