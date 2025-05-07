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