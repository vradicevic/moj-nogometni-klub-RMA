package vradicevic.etfos.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import vradicevic.etfos.mojnogometniklub.MyAppContext
import vradicevic.etfos.mojnogometniklub.models.Cards
import vradicevic.etfos.mojnogometniklub.models.HighscoreItem
import vradicevic.etfos.mojnogometniklub.models.Player
import vradicevic.etfos.mojnogometniklub.room.PlayerDao
import vradicevic.etfos.mojnogometniklub.room.PlayersDatabase
import java.security.AccessController.getContext

object Repository {
    private lateinit var dbRoom: PlayersDatabase
    init {

        dbRoom = Room.databaseBuilder(
            MyAppContext.getContext(),
            PlayersDatabase::class.java,
            "database-name"
        ).build()
        getDataFromAPI()
    }
    private lateinit var db:DatabaseReference

    var players = mutableListOf<Player>()
    fun getPlayers():LiveData<MutableList<Player>>{

        return object : LiveData<MutableList<Player>>(){
            override fun onActive() {
                super.onActive()
                        db=FirebaseDatabase.getInstance().getReference("nksava/players")
                        db.addValueEventListener(object: ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                players.clear()
                                dataSnapshot.children.forEach { child ->
                                    players.add(
                                        Player(
                                            child.key.toString(),
                                            child.child("name").value.toString(),
                                            child.child("surname").value.toString(),

                                            child.child("cards/red/total").value.toString().toInt(),
                                            child.child("cards/yellow/total").value.toString().toInt(),
                                            child.child("equipment").value.toString().toInt(),
                                            child.child("scores").value.toString().toInt(),
                                            child.child("position").value.toString(),
                                            child.child("image").value.toString()
                                        )
                                    )
                                }
                                value=players;

                                CoroutineScope(IO).launch{
                                    players.forEach { player ->
                                    dbRoom.playerDao().insertAll(player)
                                    }
                                    val result= dbRoom.playerDao().getAll()
                                    withContext(Main){
                                        Log.d("DEBUG", result.toString())
                                    }
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Log.d("AppDebug",databaseError.message)
                            }
                        })
            }
        }
    }


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
        db=FirebaseDatabase.getInstance().getReference("nksava/players")
        db.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                players.clear()
                dataSnapshot.children.forEach { child ->
                    players.add(
                        Player(
                            child.key.toString(),
                            child.child("name").value.toString(),
                            child.child("surname").value.toString(),

                            child.child("cards/red/total").value.toString().toInt(),
                            child.child("cards/yellow/total").value.toString().toInt(),
                            child.child("equipment").value.toString().toFloat().toInt(),
                            child.child("scores").value.toString().toInt(),
                            child.child("position").value.toString(),
                            child.child("image").value.toString()
                        )
                    )
                }


                CoroutineScope(IO).launch{
                    players.forEach { player ->
                        dbRoom.playerDao().insertAll(player)
                    }
                    val result= dbRoom.playerDao().getAll()
                    withContext(Main){
                        Log.d("DEBUG", result.toString())
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("AppDebug",databaseError.message)
            }
        })
    }



}