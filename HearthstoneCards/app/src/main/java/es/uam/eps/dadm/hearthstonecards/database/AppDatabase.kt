package es.uam.eps.dadm.hearthstonecards.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User


import es.uam.eps.dadm.hearthstonecards.database.CardDAO
import es.uam.eps.dadm.hearthstonecards.database.CollectionDAO
import es.uam.eps.dadm.hearthstonecards.database.PackDAO
import es.uam.eps.dadm.hearthstonecards.database.UserDAO


@Database(entities=[Card::class, Collection::class, Pack::class, User::class], version = 1, exportSchema=false)
abstract class AppDatabase : RoomDatabase(){
    abstract val cardDao: CardDAO
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