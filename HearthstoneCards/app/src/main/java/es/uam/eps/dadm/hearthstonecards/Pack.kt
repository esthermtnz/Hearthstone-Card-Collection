package es.uam.eps.dadm.hearthstonecards

data class Pack(
    val id: Int,
    var name: String,
    var picture: String,
    var cards: MutableList<Card> = mutableListOf()
)
