/**
 * Class made to handle the images that need to be shown in the app
 */
package es.uam.eps.dadm.hearthstonecards.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.viewmodel.MainViewModel

/**
 * Adapter class for displaying a list of pack images in a RecyclerView
 *
 * @param packs List of Pack objects to be displayed
 * @param viewModel The MainViewModel
 * @param onPackOpen Function triggered when a pack is opened
 */
class ImageAdapter(
    private var packs: List<Pack>,
    private val viewModel: MainViewModel,
    private val onPackOpen: (Int) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    /**
     * ViewHolder that holds the image view for a single pack
     *
     * @property imageView The ImageView that displays the pack image
     * @property lastClickTime Timestamp of the last click
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        var lastClickTime: Long = 0
    }

    /**
     * Inflates the layout for a pack image
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    /**
     * Binds a Pack object to the view
     *
     * @param holder The ViewHolder to bind
     * @param position The position of the item in the list
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pack = packs[position]
        holder.imageView.setImageResource(pack.picture)

        holder.imageView.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - holder.lastClickTime < 300) {
                /*viewModel.openUserPack(pack.id)
                val counter = viewModel.packs.value?.size ?: 0*/
                if(viewModel.openTokens.value != 0){
                onPackOpen(pack.id)
                Toast.makeText(
                    holder.imageView.context,
                    "¡Sobre abierto! Sobres disponibles: ${viewModel.openTokens.value?.minus(1)} ",//$counter",
                    Toast.LENGTH_SHORT
                ).show()
                }
                else{
                    Toast.makeText(holder.imageView.context, "No tienes sobres disponibles!", Toast.LENGTH_SHORT).show()
                }
            }
            holder.lastClickTime = currentTime
        }
    }

    /**
     * Gets the number of packs in the list
     *
     * @return The size of the packs list
     */
    override fun getItemCount(): Int = packs.size
}
