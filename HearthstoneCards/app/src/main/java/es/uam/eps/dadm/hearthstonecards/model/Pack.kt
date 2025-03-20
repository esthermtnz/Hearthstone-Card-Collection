package es.uam.eps.dadm.hearthstonecards.model

data class Pack(
    val id: Int,
    var name: String,
    var picture: Int, //id in res/drawable
    var cards: List<Card> = mutableListOf()
)
