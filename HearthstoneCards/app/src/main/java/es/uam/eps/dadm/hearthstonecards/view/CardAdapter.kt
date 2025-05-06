package es.uam.eps.dadm.hearthstonecards.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.databinding.ItemCardBinding
import es.uam.eps.dadm.hearthstonecards.model.Card

// Adapter to display the list of cards from a user
class CardAdapter(private val cards: List<Card>) :
    RecyclerView.Adapter<CardAdapter.CardViewHolder>() {

    class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Binds the card data to the UI
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

    // inflates the layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    // Gets the total number of cards in the list
    override fun getItemCount(): Int = cards.size

}
