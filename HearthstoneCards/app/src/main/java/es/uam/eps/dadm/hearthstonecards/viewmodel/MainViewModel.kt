package es.uam.eps.dadm.hearthstonecards.viewmodel

import androidx.lifecycle.ViewModel
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Pack
import timber.log.Timber

class MainViewModel : ViewModel() {

    var warrior_pack = Pack(1,
        "Warrior",
        R.drawable.warrior_pack,
        cards = listOf(
            Card(1, R.drawable.warrior_1, 0.5),
            Card(2, R.drawable.warrior_2, 0.5),
            Card(3, R.drawable.warrior_3, 0.5),
            Card(4, R.drawable.warrior_4, 0.5),
            Card(5, R.drawable.warrior_5, 0.5),
            Card(6,R.drawable.warrior_6,0.5),
            Card(7, R.drawable.warrior_7, 0.5),
            Card(8, R.drawable.warrior_8, 0.5),
            Card(9, R.drawable.warrior_9, 0.5),
            Card(10, R.drawable.warrior_10, 0.5),
        ))

    var priest_pack = Pack(2,
        "Priest",
        R.drawable.priest_pack,
        cards = listOf())

    var packs = listOf(warrior_pack, priest_pack)

    init {
        Timber.i("MainViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}