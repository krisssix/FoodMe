package cz.mendelu.xnazarja.va2.foodMe.database

import androidx.room.TypeConverter
import cz.mendelu.xnazarja.va2.foodMe.models.placeDetail.*

class Converters {

    @TypeConverter
    fun fromPoint(point: Point): String {
            return point.lat.toString() + "|" + point.lon.toString()
    }

    @TypeConverter
    fun toPoint(attributes: String): Point {
            val values = attributes.split("|").toTypedArray()
            return Point(lat = values[0].toDouble(), lon = values[1].toDouble())
    }

    @TypeConverter
    fun fromWikipediaExtracts(wikipediaExtracts: WikipediaExtracts?): String? {
        if(wikipediaExtracts != null) {
            return wikipediaExtracts.title + "|" + wikipediaExtracts.text + "|" + wikipediaExtracts.html
        } else {
            return null
        }
    }

    @TypeConverter
    fun toWikipediaExtracts(attributes: String?): WikipediaExtracts? {
        if(attributes != null) {
            val values = attributes.split("|").toTypedArray()
            return WikipediaExtracts(title = values[0], text = values[1], html = values[2])
        } else {
            return null
        }
    }

    @TypeConverter
    fun fromSources(sources: Sources): String {
            var attrs: String = ""
            sources.attributes.forEach { attr ->
                attrs += "$attr,"
            }
            attrs = attrs.dropLast(1)

            return attrs + "|" + sources.geometry
    }

    @TypeConverter
    fun toSources(attributes: String): Sources {
            val values = attributes.split("|").toTypedArray()
            val attrs = values[0].split(",").toList()

            return Sources(attributes = attrs, geometry = values[1])
    }

    @TypeConverter
    fun fromInfo(info: Info?): String? {
        if(info != null) {
            return info.descr + "|" + info.image + "|" + info.img_height + "|" + info.img_width + "|" + info.src + "|" + info.src_id
        } else {
            return null
        }
    }

    @TypeConverter
    fun toInfo(attributes: String?): Info? {
        if(attributes != null) {
            val values = attributes.split("|").toTypedArray()
            return Info(
                descr = values[0],
                image = values[1],
                img_height = values[2].toInt(),
                img_width = values[3].toInt(),
                src = values[4],
                src_id = values[5].toInt()
            )
        } else {
            return null
        }
    }

    @TypeConverter
    fun fromPreview(preview: Preview?): String? {
        if(preview != null) {
            return preview.source + "|" + preview.width + "|" + preview.height
        } else {
            return null
        }
    }

    @TypeConverter
    fun toPreview(attributes: String?): Preview? {
        if(attributes != null) {
            val values = attributes.split("|").toTypedArray()
            return Preview(
                source = values[0],
                width = values[1].toInt(),
                height = values[2].toInt()
            )
        } else {
            return null
        }
    }

    @TypeConverter
    fun fromBbox(bbox: Bbox?): String? {
        if(bbox != null) {
            return bbox.lat_max.toString() + "|" + bbox.lat_min + "|" + bbox.lon_max + "|" + bbox.lon_min
        } else {
            return null
        }
    }

    @TypeConverter
    fun toBbox(attributes: String?): Bbox? {
        if(attributes != null) {
            val values = attributes.split("|").toTypedArray()
            return Bbox(
                values[0].toDouble(),
                values[1].toDouble(),
                values[2].toDouble(),
                values[3].toDouble()
            )
        } else {
            return null
        }
    }

}