package vradicevic.etfos.mojnogometniklub.models

import android.os.Parcelable
import android.text.TextUtils
import androidx.databinding.BaseObservable

class ModeledPlayer (
    private var fifaID:String,
    private var name:String,
    private var surname: String,
    private var position:String,
    private var equipment:String,
    private var redCards:String,
    private var yellowCards:String,
    private var image: String?,
    private var score: String
): BaseObservable() {
    val isDataValid: Boolean
        get() = (!TextUtils.isEmpty(getName()))
                && (!TextUtils.isEmpty(getFifaID()))
                && (!TextUtils.isEmpty(getSurname()))
                && (!TextUtils.isEmpty(getPosition()))
                && (!TextUtils.isEmpty(getRedCards()))
                && (!TextUtils.isEmpty(getYellowCards()))
                && (!TextUtils.isEmpty(getEquipment()))
                && (getImage() != null)


    fun getName(): String {
        return name
    }

    fun getFifaID(): String {
        return fifaID
    }

    fun getSurname(): String {
        return surname
    }

    fun getPosition(): String {
        return position
    }

    fun getRedCards(): String {
        return redCards
    }

    fun getYellowCards(): String {
        return yellowCards
    }

    fun getImage(): String? {
        return image
    }

    fun getEquipment(): String {
        return equipment
    }

    fun getScore(): String {
        return score
    }

    fun setFifaID(fifaID: String) {
        this.fifaID = fifaID
    }

    fun setName(name: String) {
        this.name = name
    }

    fun setSurname(surname: String) {
        this.surname = surname
    }

    fun setPosition(position: String) {
        this.position = position
    }

    fun setRedCards(redCards: String) {
        this.redCards = redCards
    }

    fun setYellowCards(yellowCards: String) {
        this.yellowCards = yellowCards
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun setEquipment(equipment: String) {
        this.equipment = equipment
    }

    fun setScore(score: String) {
        this.score = score
    }

}