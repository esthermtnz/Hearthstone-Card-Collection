package es.uam.eps.dadm.hearthstonecards.viewmodel

import androidx.lifecycle.ViewModel
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.model.Card
import es.uam.eps.dadm.hearthstonecards.model.Collection
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User
import timber.log.Timber

class MainViewModel : ViewModel() {

    var user = User(1,
        "Juan",
        "García",
        "juangarcia@gmail.com",
        "956700478",
        "password",
        "juanillo01",
        "123456",
        mutableListOf<Pack>(Pack(1,
            "Warrior Pack",
            R.drawable.warrior_pack,
            cards = listOf(
                Card(1, R.drawable.warrior_1, 0.2),
                Card(2, R.drawable.warrior_2, 0.2),
                Card(3, R.drawable.warrior_3, 0.7),
                Card(4, R.drawable.warrior_4, 0.7),
                Card(8, R.drawable.warrior_8, 0.05),
            )),
            Pack(2,
                "Priest Pack",
                R.drawable.priest_pack,
                cards = listOf(
                    Card(11, R.drawable.priest_1, 0.2),
                    Card(12, R.drawable.priest_2, 0.7),
                    Card(13, R.drawable.priest_3, 0.7),
                    Card(15, R.drawable.priest_5, 0.2),
                    Card(16, R.drawable.priest_6, 0.2),
                ))
            ),
        mutableListOf<Collection>(
            Collection(1,
                "Warrior Collection",
                cards = listOf(
                    Card(1, R.drawable.warrior_1, 0.2),
                    Card(2, R.drawable.warrior_2, 0.2),
                    Card(3, R.drawable.warrior_3, 0.7),
                    Card(4, R.drawable.warrior_4, 0.7),
                    Card(5, R.drawable.warrior_5, 0.05),
                    Card(6, R.drawable.warrior_6,0.2),
                    Card(7, R.drawable.warrior_7, 0.7),
                    Card(8, R.drawable.warrior_8, 0.05),
                    Card(9, R.drawable.warrior_9, 0.01),
                    Card(10, R.drawable.warrior_10, 0.01),),
                obtained = mutableMapOf()
            ),
            Collection(2,
                "Priest Collection",
                cards = listOf(
                    Card(11, R.drawable.priest_1, 0.2),
                    Card(12, R.drawable.priest_2, 0.7),
                    Card(13, R.drawable.priest_3, 0.7),
                    Card(14, R.drawable.priest_4, 0.7),
                    Card(15, R.drawable.priest_5, 0.2),
                    Card(16, R.drawable.priest_6, 0.2),
                    Card(17, R.drawable.priest_7, 0.05),
                    Card(18, R.drawable.priest_8, 0.05),
                    Card(19, R.drawable.priest_9, 0.01),
                    Card(20, R.drawable.priest_10, 0.01)
                ),
                obtained =  mutableMapOf()
            ),
        )
    )

    init {
        Timber.i("MainViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("MainViewModel destroyed")
    }
}