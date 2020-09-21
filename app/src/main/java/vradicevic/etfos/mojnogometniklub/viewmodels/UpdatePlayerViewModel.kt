package vradicevic.etfos.mojnogometniklub.viewmodels

import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import androidx.room.Update
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.models.CreatePlayerForm
import vradicevic.etfos.mojnogometniklub.models.ModeledPlayer
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayer
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.utils.CreatePlayerCallbacks
import vradicevic.etfos.mojnogometniklub.utils.MyAppContext

class UpdatePlayerViewModel (private val listener: UpdatePlayerCallbacks?, p: UpdatePlayer): ViewModel() {
    var form:ModeledPlayer

    init {
        this.form = ModeledPlayer(
            p.getFifaID(),
            p.getName(),
            p.getSurname(),
            p.getPosition(),
            p.getEquipment().toString(),
            p.getRedCards().toString(),
            p.getYellowCards().toString(),
            p.getImage()!!,
            p.getScore().toString()
        )
    }

    val nameTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setName(e.toString())

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }
    val surnnameTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setSurname(e.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }
    val fifaIDTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setFifaID(e.toString())
                Log.d("WATCHER","wathcer watches watchers")
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }
    val equipmentTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setEquipment(e.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }
    val scoreTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setScore(e.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                form.setScore(p0.toString())
            }
        }
    val redcardsTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setRedCards(e.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }
    val yellowcardsTW: TextWatcher
        get() = object: TextWatcher {
            override fun afterTextChanged(e: Editable?) {
                form.setYellowCards(e.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        }

    fun onCheckedChange(radio: RadioGroup, id:Int){
        when(id){
            R.id.rbPlayer -> form.setPosition("P")
            R.id.rbGoalkeeper -> form.setPosition("GK")

        }
    }
    fun onImageClicked(view: View){
        var v= ContextCompat.getDrawable(MyAppContext.getContext(), R.drawable.ic_launcher_background)
        Log.d("IMAGE", form.getImage().toString())
    }

    fun onSubmitClicked(view: View){

        if(form.isDataValid){
            listener!!.onSuccess(form)
        }else listener!!.onError("Potrebno je ispuniti sva polja ispravno")
    }



}
    @BindingAdapter("profileImageUP")
    fun loadImageUP(view: ImageView, image: String?) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
        Glide.with(view.getContext())
            .load(image)
            .apply(requestOptions)
            .into(view)
    }