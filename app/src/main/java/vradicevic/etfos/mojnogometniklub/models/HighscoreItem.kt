package vradicevic.etfos.mojnogometniklub.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class HighscoreItem constructor(
    @PrimaryKey val fifaID:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "surname") val surname:String,
    @ColumnInfo(name = "value") val value:Int,
    @ColumnInfo(name = "image") val image:String

){
}