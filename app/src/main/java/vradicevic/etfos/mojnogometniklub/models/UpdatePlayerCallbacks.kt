package vradicevic.etfos.mojnogometniklub.models

import android.net.Uri

interface UpdatePlayerCallbacks {
    fun onSuccess(data: ModeledPlayer)
    fun onError(message: String)
}