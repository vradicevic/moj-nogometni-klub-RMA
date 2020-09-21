package vradicevic.etfos.mojnogometniklub.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayer
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.utils.CreatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.viewmodels.CreatePlayerViewModel
import vradicevic.etfos.mojnogometniklub.viewmodels.UpdatePlayerViewModel

class UpdatePlayerFragmentFactory(private var listener: UpdatePlayerCallbacks, private var updatePlayer: UpdatePlayer): ViewModelProvider.NewInstanceFactory()  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdatePlayerViewModel(listener, updatePlayer) as T
    }

}