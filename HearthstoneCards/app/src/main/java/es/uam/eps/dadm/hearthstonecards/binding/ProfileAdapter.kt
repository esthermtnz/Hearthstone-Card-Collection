package es.uam.eps.dadm.hearthstonecards.binding
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import es.uam.eps.dadm.hearthstonecards.R

/**
 * BindingAdapter that assigns a resource drawable to ImageView from the name of an icon
 *
 * If the icon name is null we set it to ic_profile which is the default image
 *
 * @param iconName The name of the drawable resource
 */
@BindingAdapter("iconName")
fun ImageView.setProfileIcon(iconName: String?) {
    val name = iconName ?: "ic_profile"  // load default image

    val resId = context.resources.getIdentifier(name, "drawable", context.packageName)
    if (resId != 0) {
        setImageResource(resId)
    } else {
        setImageResource(R.drawable.ic_profile)
    }
}
