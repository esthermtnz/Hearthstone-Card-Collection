/**
 * Data class created to define the functioning of the card collections.
 */

package es.uam.eps.dadm.hearthstonecards.model

/**
 * Definition of the data class collection
 *
 * @param id ID of the collection
 * @param name Name of the collection
 * @param cards All cards included in the collection
 * @param obtained Cards that a determined user has collected from a specific collection
 */
data class Collection(
    val id: Int,
    var name: String,
    val cards: List<Card>,
    var obtained: MutableMap<Card, Int>
)

{
    /**
     * Selects 5 random cards from the available cards in the collection
     *
     * @return selectedCards the 5 random cards picked by the algorithm
     */
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