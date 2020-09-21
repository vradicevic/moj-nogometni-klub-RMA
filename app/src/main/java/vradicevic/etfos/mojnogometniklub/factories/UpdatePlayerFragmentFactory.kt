package vradicevic.etfos.mojnogometniklub.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vradicevic.etfos.mojnogometniklub.utils.CreatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.viewmodels.CreatePlayerViewModel
import vradicevic.etfos.mojnogometniklub.viewmodels.UpdatePlayerViewModel

class UpdatePlayerFactory(private var listener: CreatePlayerCallbacks): ViewModelProvider.NewInstanceFactory()  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UpdatePlayerViewModel(listener) as T
    }

}