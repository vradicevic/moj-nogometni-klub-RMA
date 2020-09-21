package vradicevic.etfos.mojnogometniklub.models

import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.widget.ImageView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class CreatePlayerForm(
    private var fifaID:String,
    private var name:String,
    private var surname: String,
    private var position:String,
    private var equipment:Int,
    private var redCards:Int,
    private var yellowCards:Int,
    private var image: Uri?,
    private var score: Int
): BaseObservable() {
    val isDataValid:Boolean
        get() = (!TextUtils.isEmpty(getName()))
                && (!TextUtils.isEmpty(getFifaID()))
                && (!TextUtils.isEmpty(getSurname()))
                && (!TextUtils.isEmpty(getPosition()))
                && (getRedCards()>=0)
                && (getYellowCards()>=0)
                && (getEquipment()>=0)
                && (getImage()!=null)

    fun getName(): String {
        return name
    }
    fun getFifaID(): String {
        return  fifaID
    }
    fun getSurname(): String {
        return surname
    }
    fun getPosition(): String {
        return position
    }
    fun getRedCards(): Int {
        return redCards
    }
    fun getYellowCards(): Int {
        return yellowCards
    }
    fun getImage(): Uri? {
        return image
    }
    fun getEquipment(): Int {
        return equipment
    }
    fun getScore(): Int {
        return score
    }

    fun setFifaID(fifaID: String){
        this.fifaID = fifaID
    }
    fun setName(name: String){
        this.name = name
    }
    fun setSurname(surname: String){
        this.surname = surname
    }
    fun setPosition(position: String){
        this.position = position
    }
    fun setRedCards(redCards: Int){
        this.redCards =redCards
    }
    fun setYellowCards(yellowCards: Int){
        this.yellowCards =yellowCards
    }
    fun setImage(image: Uri?){
        this.image =image
    }
    fun setEquipment(equipment: Int){
        this.equipment = equipment
    }
    fun setScore(score: Int){
        this.score = score
    }



}