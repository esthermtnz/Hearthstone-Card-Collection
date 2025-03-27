package es.uam.eps.dadm.hearthstonecards.model


data class Collection(
    val id: Int,
    var name: String,
    val cards: List<Card>,
    var obtained: MutableMap<Card, Int>
)