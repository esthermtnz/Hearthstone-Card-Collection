package es.uam.eps.dadm.hearthstonecards.model


data class Collection(
    val id: Int,
    var name: String,
    val cards: List<Card>,
    var obtained: MutableMap<Card, Int>
)

{
    fun shuffleCards(): List<Card> {
        val selectedCards = mutableListOf<Card>()
        val random = java.util.Random()

        val availableCards = cards.toMutableList()

        repeat(5) {
            if (availableCards.isEmpty()) return@repeat

            val totalRarity = availableCards.sumOf { it.rarity }

            val randValue = random.nextDouble() * totalRarity

            var cumulative = 0.0
            var selected: Card? = null
            for (card in availableCards) {
                cumulative += card.rarity
                if (randValue <= cumulative) {
                    selected = card
                    break
                }
            }

            selected?.let {
                selectedCards.add(it)
                availableCards.remove(it)
            }
        }

        return selectedCards
    }
}