/**
 * Class that inherits from Application() and uses Timber on it
 */
package es.uam.eps.dadm.hearthstonecards.view

import es.uam.eps.dadm.hearthstonecards.database.AppDatabase
import android.app.Application
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.CollectionCardCrossRef
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
            database.cardDao.addCard(Card(1, R.drawable.priest_1, 0.1))
            database.cardDao.addCard(Card(2, R.drawable.priest_2, 0.2))
            database.cardDao.addCard(Card(3, R.drawable.priest_3, 0.1))
            database.cardDao.addCard(Card(4, R.drawable.priest_4, 0.2))
            database.cardDao.addCard(Card(5, R.drawable.priest_5, 0.2))
            database.cardDao.addCard(Card(6, R.drawable.priest_6, 0.2))
            database.cardDao.addCard(Card(7, R.drawable.priest_7, 0.2))
            database.cardDao.addCard(Card(8, R.drawable.priest_8, 0.4))
            database.cardDao.addCard(Card(9, R.drawable.priest_9, 0.5))
            database.cardDao.addCard(Card(10, R.drawable.priest_10, 0.5))
            database.cardDao.addCard(Card(11, R.drawable.warrior_1, 0.1))
            database.cardDao.addCard(Card(12, R.drawable.warrior_2, 0.2))
            database.cardDao.addCard(Card(13, R.drawable.warrior_3, 0.1))
            database.cardDao.addCard(Card(14, R.drawable.warrior_4, 0.2))
            database.cardDao.addCard(Card(15, R.drawable.warrior_5, 0.2))
            database.cardDao.addCard(Card(16, R.drawable.warrior_6, 0.2))
            database.cardDao.addCard(Card(17, R.drawable.warrior_7, 0.2))
            database.cardDao.addCard(Card(18, R.drawable.warrior_8, 0.4))
            database.cardDao.addCard(Card(19, R.drawable.warrior_9, 0.5))
            database.cardDao.addCard(Card(20, R.drawable.warrior_10, 0.5))

            database.collectionDao.addCollection(Collection(1, "Priest"))
            database.collectionDao.addCollection(Collection(2, "Warrior"))

            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,1))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,2))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,3))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,4))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,5))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,6))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,7))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,8))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,9))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(1,10))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,11))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,12))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,13))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,14))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,15))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,16))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,17))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,18))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,19))
            database.collectionCardCrossRefDao.addCollectionCardCrossRef(CollectionCardCrossRef(2,20))





        }

        Timber.plant(Timber.DebugTree())
    }
}