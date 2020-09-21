package vradicevic.etfos.mojnogometniklub.room

import androidx.room.Database
import androidx.room.RoomDatabase
import vradicevic.etfos.mojnogometniklub.models.Formation
import vradicevic.etfos.mojnogometniklub.models.Player
import vradicevic.etfos.mojnogometniklub.models.User

@Database(entities = arrayOf(Player::class,Formation::class), version = 1)
abstract class PlayersDatabase:RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun formationDao(): FormationDao

}