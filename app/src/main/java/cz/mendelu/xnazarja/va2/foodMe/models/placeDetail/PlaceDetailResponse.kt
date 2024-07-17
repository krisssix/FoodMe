package cz.mendelu.xnazarja.va2.foodMe.models.placeDetail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "places")
data class PlaceDetailResponse (
    @ColumnInfo(name = "bbox") val bbox: Bbox? = null,
    @ColumnInfo(name = "image") val image: String? = null,
    @ColumnInfo(name = "info") val info: Info? = null,
    @ColumnInfo(name = "kinds") val kinds: String? = null,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "osm") val osm: String? = null,
    @ColumnInfo(name = "otm") val otm: String,
    @ColumnInfo(name = "point") val point: Point,
    @ColumnInfo(name = "rate") val rate: String,
    @ColumnInfo(name = "sources") val sources: Sources,
    @ColumnInfo(name = "wikidata") val wikidata: String? = null,
    @ColumnInfo(name = "wikipedia") val wikipedia: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "xid") val xid: String,
    @ColumnInfo(name = "preview") val preview: Preview? = null,
    @ColumnInfo(name = "wikipedia_extracts") val wikipedia_extracts: WikipediaExtracts? = null,
    @ColumnInfo(name = "voyage") val voyage: String? = null,
    @ColumnInfo(name = "url") val url: String? = null

): Serializable {
    var done: Boolean = false
    var favorte: Boolean = false
}