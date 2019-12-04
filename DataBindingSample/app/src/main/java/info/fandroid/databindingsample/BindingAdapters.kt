package info.fandroid.databindingsample

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import info.fandroid.databindingsample.data.Popularity

@BindingAdapter("app:hideIfZero")
fun hideIfZero(view: View, number: Int) {
    view.visibility = if (number == 0) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["app:progressScaled", "android:max"], requireAll = true)
fun setProgress(progressBar: ProgressBar, likes: Int, max: Int) {
    progressBar.progress = (likes * max / 5).coerceAtMost(max)
}

@BindingAdapter(value = ["app:setImage"], requireAll = true)
fun setImage(imageView: ImageView, popularity: Popularity) {
    when(popularity) {
        Popularity.NORMAL -> imageView.setImageResource(R.drawable.ic_person_black_96dp)
        Popularity.POPULAR -> imageView.setImageResource(R.drawable.ic_whatshot_black_96dp)
        Popularity.STAR -> imageView.setImageResource(R.drawable.ic_whatshot_black_96dp)
    }
}

@BindingAdapter(value = ["app:setColor"], requireAll = true)
fun setColor(imageView: ImageView, popularity: Popularity) {
    val color = when(popularity) {
        Popularity.NORMAL -> imageView.context.theme.obtainStyledAttributes(
            intArrayOf(android.R.attr.colorForeground)
        ).getColor(0, 0x000000)
        Popularity.POPULAR -> ContextCompat.getColor(imageView.context, R.color.popular)
        Popularity.STAR -> ContextCompat.getColor(imageView.context, R.color.star)
    }

    ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(color))
}