package vradicevic.etfos.mojnogometniklub.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import vradicevic.etfos.login.Repository
import vradicevic.etfos.mojnogometniklub.models.Player

class PlayersViewModel : ViewModel() {
    var players: LiveData<MutableList<Player>> = Repository.getPlayersFromRoom()
}