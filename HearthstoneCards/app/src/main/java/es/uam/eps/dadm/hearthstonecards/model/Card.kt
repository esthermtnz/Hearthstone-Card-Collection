package es.uam.eps.dadm.hearthstonecards.model

data class Card (
    val id: Int ,
    val pictureRes: Int, //Id image in res/drawable
    var rarity: Double
)