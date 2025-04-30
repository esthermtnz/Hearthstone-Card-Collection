/**
 * Class that inherits from Application() and uses Timber on it
 */
package es.uam.eps.dadm.hearthstonecards.view

import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import android.app.Application
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.model.Card
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber

class HearthstoneApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy {AppDatabase.getInstance(applicationContext)}


    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            database.cardDao.removeCards()
            database.cardDao.addCard(Card(1, R.drawable.priest_1, 0.1))
            database.cardDao.addCard(Card(2, R.drawable.priest_2, 0.2))
            database.cardDao.addCard(Card(3, R.drawable.priest_3, 0.1))
            database.cardDao.addCard(Card(4, R.drawable.priest_4, 0.2))

        }

        Timber.plant(Timber.DebugTree())
    }
}