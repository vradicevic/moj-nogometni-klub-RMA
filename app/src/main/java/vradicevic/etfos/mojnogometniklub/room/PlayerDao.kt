package vradicevic.etfos.mojnogometniklub.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vradicevic.etfos.mojnogometniklub.models.HighscoreItem
import vradicevic.etfos.mojnogometniklub.models.Player
import vradicevic.etfos.mojnogometniklub.models.User

@Dao
interface PlayerDao {
    @Query("SELECT * FROM player")
    fun getAll(): Flow<MutableList<Player>>

    @Query("SELECT fifaid, name, surname, scores AS value, image FROM player ORDER BY scores DESC")
    fun getPlayersByScore():Flow<MutableList<HighscoreItem>>
    @Query("SELECT fifaid, name, surname, redcards AS value, image FROM player ORDER BY redCards DESC")
    fun getPlayersByRedCards():Flow<MutableList<HighscoreItem>>
    @Query("SELECT fifaid, name, surname, yellowcards AS value, image FROM player ORDER BY yellowcards DESC")
    fun getPlayersByYellowCards():Flow<MutableList<HighscoreItem>>
    @Query("SELECT fifaid, name, surname, equipment AS value, image FROM player ORDER BY equipment DESC")
    fun getPlayersByEquipmentCards():Flow<MutableList<HighscoreItem>>
    @Query("SELECT * FROM player WHERE fifaID LIKE :fifaID")
    fun getPlayer(fifaID:String):Player
    @Query("DELETE FROM player")
    fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg player:Player)
}