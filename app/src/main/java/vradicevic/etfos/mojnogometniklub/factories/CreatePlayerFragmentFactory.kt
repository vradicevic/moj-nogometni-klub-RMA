package vradicevic.etfos.mojnogometniklub.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vradicevic.etfos.mojnogometniklub.utils.CreatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.viewmodels.CreatePlayerViewModel

class CreatePlayerFragmentFactory(private var listener: CreatePlayerCallbacks): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreatePlayerViewModel(listener) as T
    }
}