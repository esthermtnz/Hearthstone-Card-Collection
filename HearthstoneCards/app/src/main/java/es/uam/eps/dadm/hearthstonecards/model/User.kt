package es.uam.eps.dadm.hearthstonecards.model

data class User(
    val id: Int,
    var name: String,
    var surname: String,
    var email: String,
    var tlf: String,
    var password: String,
    var username: String,
    var openToken: String
) {
    fun changePassword(newPassword: String) {
        password = newPassword
    }
}
