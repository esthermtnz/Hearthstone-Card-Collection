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
            packs.remove(packToRemove)
        } else {
            println("Pack con id $packId no encontrado.")
        }
    }

}
