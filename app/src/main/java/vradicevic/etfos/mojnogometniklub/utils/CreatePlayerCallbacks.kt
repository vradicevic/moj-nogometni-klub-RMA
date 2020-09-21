package vradicevic.etfos.mojnogometniklub.utils

import android.net.Uri

interface CreatePlayerCallbacks {
    fun onSuccess(uri: Uri?)
    fun onError(message: String)
}