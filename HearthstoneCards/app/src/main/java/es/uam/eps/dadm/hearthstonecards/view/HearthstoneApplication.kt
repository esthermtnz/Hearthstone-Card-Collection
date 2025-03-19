package es.uam.eps.dadm.hearthstonecards.view

import android.app.Application
import timber.log.Timber

class HearthstoneApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}