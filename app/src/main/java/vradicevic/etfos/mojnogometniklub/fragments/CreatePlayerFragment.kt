package vradicevic.etfos.mojnogometniklub.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.create_player_fragment.*
import vradicevic.etfos.mojnogometniklub.utils.CreatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.viewmodels.CreatePlayerViewModel
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.databinding.CreatePlayerFragmentBinding
import vradicevic.etfos.mojnogometniklub.factories.CreatePlayerFragmentFactory
import vradicevic.etfos.mojnogometniklub.models.Player
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils
import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.utils.PreferenceManager
import java.io.ByteArrayOutputStream
import java.io.IOException

class CreatePlayerFragment : Fragment(),
    CreatePlayerCallbacks {

    companion object {
        fun newInstance() =
            CreatePlayerFragment()
    }


    private lateinit var viewModel: CreatePlayerViewModel
    private lateinit var navController: NavController
    private lateinit var fragBinding: CreatePlayerFragmentBinding
    private lateinit var imageView:ImageView
    val requestOptions = RequestOptions()
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_launcher_background)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragBinding = DataBindingUtil.inflate(inflater,R.layout.create_player_fragment, container, false )
        viewModel=ViewModelProvider(this, CreatePlayerFragmentFactory(this)).get(CreatePlayerViewModel::class.java)
        fragBinding.viewModel=viewModel
        fragBinding.lifecycleOwner =this

        return fragBinding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        imageView=requireActivity().findViewById<ImageView>(R.id.ivPlayerCP)

        requireActivity().findViewById<ImageView>(R.id.ivPlayerCP).setOnClickListener {
            var intentGallery= Intent( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intentGallery, 1)
        }
        viewModel=ViewModelProvider(this, CreatePlayerFragmentFactory(this)).get(CreatePlayerViewModel::class.java)
        fragBinding.viewModel=viewModel
    }

    override fun onSuccess(uri: Uri?) {



        btnCreatePlayer.text="Učitavanje..."
        btnCreatePlayer.isEnabled= false

        val bitmap = MediaStore.Images.Media.getBitmap(MyAppContext.getContext().contentResolver, uri)
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        val club = PreferenceManager().retrieveClub()
        val fifaID = etFifaIDCP.text
        var uploadTask = FirebaseUtils.storage.reference.child("clubs/${club}/players/${fifaID}.jpg").putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            FirebaseUtils.storage.reference
                .child("clubs/${club}/players/${fifaID}.jpg")
                .downloadUrl.addOnSuccessListener { it->
                    var position:String= "P"
                    when(rgPosition.checkedRadioButtonId){
                        R.id.rbGoalkeeper-> position="GK"
                        R.id.rbPlayer -> position="P"
                    }

                    val player = Player(
                        etFifaIDCP.text.toString(),
                        etNameCP.text.toString(),
                        etSurnameCP.text.toString(),
                        etRedCardsCP.text.toString().toInt(),
                        etYellowCardsCP.text.toString().toInt(),
                        etEquipmentCP.text.toString().toInt(),
                        etScoreCP.text.toString().toInt(),
                        position,
                        it.toString()
                    )
                    FirebaseUtils.database.getReference("clubs/${club}/players/${etFifaIDCP.text.toString()}").setValue(player)
                    btnCreatePlayer.text="Dodaj"
                    btnCreatePlayer.isEnabled= true
                    navController= Navigation.findNavController(fragBinding.root)
                    navController.navigate(R.id.action_createPlayerFragment_to_playersFragment)
                }

        }
    }

    override fun onError(message: String) {
        Toast.makeText(MyAppContext.getContext(),message,Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (data != null)
        {
            val contentURI:Uri? = data!!.data


            try
            {

                Glide.with(requireActivity())
                    .applyDefaultRequestOptions(requestOptions)
                    .load(contentURI)
                    .into(imageView)

                this.viewModel.form.setImage(contentURI!!)

            }
            catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(requireActivity(), "Failed!", Toast.LENGTH_SHORT).show()
            }

        }
    }


}