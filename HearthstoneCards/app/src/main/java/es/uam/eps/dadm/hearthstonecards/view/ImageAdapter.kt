package es.uam.eps.dadm.hearthstonecards.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import es.uam.eps.dadm.hearthstonecards.R
import es.uam.eps.dadm.hearthstonecards.model.Pack
import es.uam.eps.dadm.hearthstonecards.model.User

class ImageAdapter(
    private val packs: List<Pack>,
    private val user: User
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        var lastClickTime: Long = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pack = packs[position]
        holder.imageView.setImageResource(pack.picture)

        holder.imageView.setOnClickListener {
            val currentTime = System.currentTimeMillis()
            if (currentTime - holder.lastClickTime < 300) {
                // Doble clic detectado
                user.openPack(pack.id)
                Toast.makeText(
                    holder.imageView.context,
                    "¡Sobre abierto!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            holder.lastClickTime = currentTime
        }
    }

    override fun getItemCount(): Int = packs.size
}