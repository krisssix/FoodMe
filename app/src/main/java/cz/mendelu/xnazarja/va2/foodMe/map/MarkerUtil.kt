package cz.mendelu.xnazarja.va2.foodMe.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import cz.mendelu.xnazarja.va2.foodMe.R
import cz.mendelu.xnazarja.va2.foodMe.models.places.Feature

class MarkerUtil {

    companion object {

        fun createMarkerIconFromResource(context: Context, resource: Int): Bitmap {
            val drawable = ContextCompat.getDrawable(context, resource)
            drawable!!.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
            val bm = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )

            val canvas = Canvas(bm)
            drawable.draw(canvas)
            return bm
        }

        val categories = listOf("restaurants", "cafes", "fast_food", "food_courts", "pubs", "bars", "biergartens", "picnic_site")

        fun createBitmapBasedOnType(place: Feature, context: Context): Bitmap {
            return when {
                place.properties.kinds.contains("restaurants") -> createMarkerIconFromResource(context,R.drawable.marker_cultural)
                place.properties.kinds.contains("cafes") -> createMarkerIconFromResource(context,R.drawable.marker_architecture)
                place.properties.kinds.contains("fast_food") -> createMarkerIconFromResource(context,R.drawable.marker_historic)
                place.properties.kinds.contains("food_courts") -> createMarkerIconFromResource(context,R.drawable.marker_religion)
                place.properties.kinds.contains("pubs") -> createMarkerIconFromResource(context,R.drawable.marker_natural)
                place.properties.kinds.contains("bars") -> createMarkerIconFromResource(context,R.drawable.marker_natural)
                place.properties.kinds.contains("biergartens") -> createMarkerIconFromResource(context,R.drawable.marker_natural)
                place.properties.kinds.contains("picnic_site") -> createMarkerIconFromResource(context,R.drawable.marker_natural)
                /*
                place.properties.kinds.contains("cultural") -> createMarkerIconFromResource(context,R.drawable.marker_cultural)
                place.properties.kinds.contains("architecture") -> createMarkerIconFromResource(context,R.drawable.marker_architecture)
                place.properties.kinds.contains("historic") -> createMarkerIconFromResource(context,R.drawable.marker_historic)
                place.properties.kinds.contains("religion") -> createMarkerIconFromResource(context,R.drawable.marker_religion)
                place.properties.kinds.contains("natural") -> createMarkerIconFromResource(context,R.drawable.marker_natural)
                 */
                else -> createMarkerIconFromResource(context,R.drawable.ic_pin_24)

            }
        }

    }
}