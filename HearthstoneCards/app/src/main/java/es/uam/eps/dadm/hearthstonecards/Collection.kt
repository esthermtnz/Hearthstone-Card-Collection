package es.uam.eps.dadm.hearthstonecards

data class Collection(
    val id: Int,
    var obtained: MutableMap<Card, Int>
)