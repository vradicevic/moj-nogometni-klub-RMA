package vradicevic.etfos.mojnogometniklub.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player
constructor(
    @PrimaryKey val fifaID:String,
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "surname") val surname:String,
    @ColumnInfo(name = "redCards") val redCards:Int,
    @ColumnInfo(name = "yellowCards") val yellowCards:Int,
    @ColumnInfo(name = "equipment") val equipment:Int,
    @ColumnInfo(name = "scores") val scores:Int,
    @ColumnInfo(name = "position") val position:String,
    @ColumnInfo(name = "image") val image: String
){

}