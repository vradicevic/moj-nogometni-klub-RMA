package vradicevic.etfos.mojnogometniklub.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import vradicevic.etfos.mojnogometniklub.models.Formation
import vradicevic.etfos.mojnogometniklub.models.Player

@Dao
interface FormationDao {
    @Query("SELECT*FROM formation")
    fun getAll(): Flow<MutableList<Formation>>
    @Query("DELETE FROM formation")
    fun deleteAll()
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg formation: Formation)
}