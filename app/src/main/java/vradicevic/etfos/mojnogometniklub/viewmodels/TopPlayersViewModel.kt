package vradicevic.etfos.mojnogometniklub.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import vradicevic.etfos.login.Repository
import vradicevic.etfos.mojnogometniklub.models.HighscoreItem
import vradicevic.etfos.mojnogometniklub.models.Player

class TopPlayersViewModel : ViewModel() {
    var players: LiveData<MutableList<HighscoreItem>> = Repository.getScoreHighscore()
    fun changePlayersReference(type:String){
        Log.d("RADIO",type)
        when(type){
            "score"->players=Repository.getScoreHighscore()
            "redcards"-> players=Repository.getRedCardsHighscore()
            "yellowcards"-> players=Repository.getYellowCardsHighscore()
            "equipment"-> players=Repository.getEquipmentHighscore()
        }
        Log.d("RADIO",players.toString())
    }
}
