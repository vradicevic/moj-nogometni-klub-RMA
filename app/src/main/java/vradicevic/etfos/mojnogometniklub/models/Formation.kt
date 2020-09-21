package vradicevic.etfos.mojnogometniklub.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formation")
data class Formation
constructor(
    @PrimaryKey var formationUrl:String
){

}