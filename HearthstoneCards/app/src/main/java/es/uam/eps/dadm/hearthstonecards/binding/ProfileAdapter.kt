package es.uam.eps.dadm.hearthstonecards.binding
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import es.uam.eps.dadm.hearthstonecards.R

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
