package es.uam.eps.dadm.hearthstonecards.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.databinding.ItemCardBinding
import es.uam.eps.dadm.hearthstonecards.model.Card

/**
 * Adapter class for displaying a list of Card items in a RecyclerView
 *
 * @param cards List of Card objects to display
 */
class CardAdapter(private val cards: List<Card>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    /**
     * ViewHolder class for displaying a single card item using ViewBinding
     *
     * @param binding The binding object for the item layout
     */
    class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a Card to the layout elements
         *
         * @param card The Card object to bind to the UI
         */
        fun bind(card: Card) {
            val context = binding.root.context

            // Determine the rarity text depending on the cards'r arity value
            val rarityText = when {
                card.rarity <= 0.1 -> context.getString(R.string.rarity_common)
                card.rarity <= 0.3 -> context.getString(R.string.rarity_rare)
                card.rarity <= 0.5 -> context.getString(R.string.rarity_epic)
                else -> context.getString(R.string.rarity_legendary)
            }

            // Set the name, rarity and image of the card
            binding.cardName.text = context.getString(R.string.card_number, card.id)
            binding.cardRarity.text = context.getString(R.string.rarity_prefix, rarityText)
            binding.cardImage.setImageResource(card.pictureRes)
        }

    }

    /**
     * Inflates the item layout
     *
     * @param parent The parent ViewGroup
     * @param viewType The view type of the new View
     * @return A new CardViewHolder instance
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    /**
     * Binds a card from the list to a ViewHolder
     *
     * @param holder The ViewHolder to bind
     * @param position The position of the item in the list
     */
    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    /**
     * Gets the total number of card items
     *
     * @return Size of the cards list
     */
    override fun getItemCount(): Int = cards.size

}
