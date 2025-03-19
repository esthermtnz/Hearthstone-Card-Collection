package es.uam.eps.dadm.hearthstonecards.model

data class Collection(
    val id: Int,
    var obtained: MutableMap<Card, Int>
)