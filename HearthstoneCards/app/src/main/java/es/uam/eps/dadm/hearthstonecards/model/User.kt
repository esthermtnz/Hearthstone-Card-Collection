package es.uam.eps.dadm.hearthstonecards.model


data class User(
    val id: Int,
    var name: String,
    var surname: String,
    var email: String,
    var tlf: String,
    var password: String,
    var username: String,
    var openToken: String,
    var packs: MutableList<Pack>,
    var collections: MutableList<Collection>
) {
    fun changePassword(newPassword: String) {
        password = newPassword
    }

    fun openPack(packId: Int) {
        val packToRemove = packs.find { it.id == packId }
        if (packToRemove != null) {
            packToRemove.openPack()

            //Add card to obtained and update number of repetitions
            for (carta in packToRemove.cards) {
                val cantidadActual = packToRemove.collection.obtained[carta] ?: 0
                packToRemove.collection.obtained[carta] = cantidadActual + 1
            }

            println("Cartas obtenidas al abrir el sobre:")
            for (carta in packToRemove.cards) {
                println("- ${carta.id} (rareza: ${carta.rarity})")
            }

            //Delete pack
            packs.remove(packToRemove)
        } else {
            println("Pack con id $packId no encontrado.")
        }
    }

}
