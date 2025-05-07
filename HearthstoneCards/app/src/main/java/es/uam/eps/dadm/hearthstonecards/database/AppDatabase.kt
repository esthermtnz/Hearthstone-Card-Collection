package es.uam.eps.dadm.hearthstonecards.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User
import es.uam.eps.dadm.hearthstonecards.model.UserPackCrossRef
import es.uam.eps.dadm.hearthstonecards.model.CollectionCardCrossRef
import es.uam.eps.dadm.hearthstonecards.model.ObtainedCardCrossRef


/**
 * Database implemented with Room for Hearthstone App
 *
 *
 * @property cardDao DAO for model Card
 * @property collectionDao DAO for model Collection
 * @property packDao DAO for model Pack
 * @property userDao DAO for model User
 * @property collectionCardCrossRefDao DAO for N:N relation between collections and cards
 * @property userPackCrossRefDao DAO for N:N relation between users and packs
 * @property obtainedCardCrossRefDao DAO for N:N relation between users and the obtained cards
 */
@Database(entities=[Card::class, Collection::class, Pack::class, User::class,
        CollectionCardCrossRef::class, UserPackCrossRef::class, ObtainedCardCrossRef::class],
        version = 3, exportSchema=false)
abstract class AppDatabase : RoomDatabase(){
    abstract val cardDao: CardDAO
    abstract val collectionDao: CollectionDAO
    abstract val packDao: PackDAO
    abstract val userDao: UserDAO
    abstract val collectionCardCrossRefDao: CollectionCardCrossRefDAO
    abstract val userPackCrossRefDao: UserPackCrossRefDAO
    abstract val obtainedCardCrossRefDao: ObtainedCardCrossRefDAO
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase{
            var instance = INSTANCE

            if (instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
            }
            return instance
        }
    }
}