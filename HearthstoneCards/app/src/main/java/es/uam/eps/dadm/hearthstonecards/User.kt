package es.uam.eps.dadm.hearthstonecards

data class User(
    val id: Int,
    var name: String,
    var surname: String,
    var email: String,
    var tlf: String,
    private var password: String,
    var username: String,
    var openToken: String
) {
    fun changePassword(newPassword: String) {
        password = newPassword
    }
}
