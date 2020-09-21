package vradicevic.etfos.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vradicevic.etfos.mojnogometniklub.models.Formation

import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.models.HighscoreItem
import vradicevic.etfos.mojnogometniklub.models.Player
import vradicevic.etfos.mojnogometniklub.room.PlayersDatabase
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils.Companion.club
import vradicevic.etfos.mojnogometniklub.utils.NotifManager
import vradicevic.etfos.mojnogometniklub.utils.PreferenceManager

object Repository {
    private lateinit var dbRoom: PlayersDatabase
    init {
        dbRoom = Room.databaseBuilder(
            MyAppContext.getContext(),
            PlayersDatabase::class.java,
            "database-name"
        ).build()
        FirebaseUtils().startFirebaseConnection()
    }
    private lateinit var db:DatabaseReference

    var players = mutableListOf<Player>()
    var formation:Formation = Formation("")

    fun getPlayersFromRoom():LiveData<MutableList<Player>>{
        return object:LiveData<MutableList<Player>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(IO).launch {
                    dbRoom.playerDao().getAll().collect{it->
                        Log.d("FLOW", it.toString())
                        withContext(Main){
                            value=it
                        }
                    }
                }
            }
        }
    }
    fun getFormationFromRoom():LiveData<MutableList<Formation>>{
        return object:LiveData<MutableList<Formation>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(IO).launch {
                    dbRoom.formationDao().getAll().collect{it->
                        withContext(Main){
                            value=it
                        }
                    }
                }
            }
        }
    }



    fun getPlayerByID(id:String):Player{
        val player = dbRoom.playerDao().getPlayer(id)
        return player
    }

    fun getScoreHighscore():LiveData<MutableList<HighscoreItem>>{
        return object:LiveData<MutableList<HighscoreItem>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(IO).launch {
                    dbRoom.playerDao().getPlayersByScore().collect{it->
                        Log.d("FLOW", it.toString())
                        withContext(Main){
                            value=it
                        }
                    }
                }
            }
        }
    }
    fun getYellowCardsHighscore():LiveData<MutableList<HighscoreItem>>{
        return object:LiveData<MutableList<HighscoreItem>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(IO).launch {
                    dbRoom.playerDao().getPlayersByYellowCards().collect{it->
                        Log.d("FLOW", it.toString())
                        withContext(Main){
                            value=it
                        }
                    }
                }
            }
        }
    }
    fun getRedCardsHighscore():LiveData<MutableList<HighscoreItem>>{
        return object:LiveData<MutableList<HighscoreItem>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(IO).launch {
                    dbRoom.playerDao().getPlayersByRedCards().collect{it->
                        Log.d("FLOW", it.toString())
                        withContext(Main){
                            value=it
                        }
                    }
                }
            }
        }
    }
    fun getEquipmentHighscore():LiveData<MutableList<HighscoreItem>>{
        return object:LiveData<MutableList<HighscoreItem>>(){
            override fun onActive() {
                super.onActive()
                CoroutineScope(IO).launch {
                    dbRoom.playerDao().getPlayersByEquipmentCards().collect{it->
                        Log.d("FLOW", it.toString())
                        withContext(Main){
                            value=it
                        }
                    }
                }
            }
        }
    }

    fun getDataFromAPI(){
        val club=PreferenceManager().retrieveClub()
        db=FirebaseUtils.database.getReference("clubs/${club}/players")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                NotifManager.sendNotification()
                players.clear()
                dataSnapshot.children.forEach { child ->

                    players.add(
                        Player(
                            child.key.toString(),
                            child.child("name").value.toString(),
                            child.child("surname").value.toString(),
                            child.child("redcards").value.toString().toInt(),
                            child.child("yellowcards").value.toString().toInt(),
                            child.child("equipment").value.toString().toFloat().toInt(),
                            child.child("scores").value.toString().toInt(),
                            child.child("position").value.toString(),
                            child.child("image").value.toString()
                        )
                    )
                }


                CoroutineScope(IO).launch{
                    dbRoom.playerDao().deleteAll()
                    players.forEach { player ->
                        dbRoom.playerDao().insertAll(player)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("AppDebug",databaseError.message)
            }
        })
        FirebaseUtils.database.getReference("clubs/${club}/formation")
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                        Log.d("FORM_DEBUG", dataSnapshot.value.toString())
                        formation.formationUrl=dataSnapshot.value.toString()


                    CoroutineScope(IO).launch{
                        dbRoom.formationDao().deleteAll()
                        dbRoom.formationDao().insertAll(formation)

                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.d("AppDebug",databaseError.message)
                }
            })



    }



}