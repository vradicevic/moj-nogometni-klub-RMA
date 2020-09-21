package vradicevic.etfos.mojnogometniklub.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.update_player_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.databinding.UpdatePlayerFragmentBinding
import vradicevic.etfos.mojnogometniklub.factories.CreatePlayerFragmentFactory
import vradicevic.etfos.mojnogometniklub.factories.UpdatePlayerFragmentFactory
import vradicevic.etfos.mojnogometniklub.models.ModeledPlayer
import vradicevic.etfos.mojnogometniklub.models.Player
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayer
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.utils.CreatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils
import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.utils.PreferenceManager
import vradicevic.etfos.mojnogometniklub.viewmodels.CreatePlayerViewModel
import vradicevic.etfos.mojnogometniklub.viewmodels.UpdatePlayerViewModel
import java.io.ByteArrayOutputStream
import java.io.IOException

class UpdatePlayerFragment : Fragment(), UpdatePlayerCallbacks {

    companion object {
        fun newInstance() =
            UpdatePlayerFragment()
    }

    private lateinit var viewModel: UpdatePlayerViewModel
    private lateinit var navController: NavController
    private lateinit var binder:UpdatePlayerFragmentBinding
    private lateinit var imageView:ImageView
    private val args by navArgs<UpdatePlayerFragmentArgs>()
    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binder = DataBindingUtil.inflate(inflater,R.layout.update_player_fragment, container, false )
        viewModel=ViewModelProvider(this, UpdatePlayerFragmentFactory(this, args.player1 )).get(UpdatePlayerViewModel::class.java)
        binder.viewModel=viewModel
        binder.lifecycleOwner =this
        return binder.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }



    override fun onSuccess(player: ModeledPlayer) {
        btnUpdatePlayer.text="Učitavanje..."
        btnUpdatePlayer.isEnabled= false
        CoroutineScope(IO).launch{
            var position:String= "P"
            when(rgPositionUP.checkedRadioButtonId){
                R.id.rbGoalkeeper-> position="GK"
                R.id.rbPlayer -> position="P"
            }
            val club = PreferenceManager().retrieveClub()
            val dataForUpdate = Player(
                player.getFifaID(),
                player.getName(),
                player.getSurname(),
                player.getRedCards().toInt(),
                player.getYellowCards().toInt(),
                player.getEquipment().toInt(),
                player.getScore().toInt(),
                position,
                 player.getImage()?: ""
            )
            FirebaseUtils.database.getReference("clubs/${club}/players/${etFifaIDUP.text.toString()}").setValue(dataForUpdate)


            withContext(Main){
                btnUpdatePlayer.text="Dodaj"
                btnUpdatePlayer.isEnabled= true
                navController= Navigation.findNavController(binder.root)
                navController.navigate(R.id.action_updatePlayerFragment_to_playersFragment)
            }

        }



    }

    override fun onError(message: String) {
        Toast.makeText(MyAppContext.getContext(),message,Toast.LENGTH_SHORT)
    }

}