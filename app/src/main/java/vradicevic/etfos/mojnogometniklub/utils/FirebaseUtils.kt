package vradicevic.etfos.mojnogometniklub.utils

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import vradicevic.etfos.login.Repository

class FirebaseUtils {

    companion object{
        val storage = Firebase.storage
        val database = FirebaseDatabase.getInstance()
        var club:String? = null
        var playersReference:DatabaseReference? = null
        val auth = FirebaseAuth.getInstance()

    }

    fun startFirebaseConnection(){
        database.getReference("users").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(data: DataSnapshot) {
                var uuid = PreferenceManager().retrieveUUID()
                Log.d("UUID",uuid)
                data.children.forEach { child->

                    if(uuid==child.key.toString()){
                        club = child.child("club").value.toString()
                        PreferenceManager().saveClub(club)
                        Repository.getDataFromAPI()
                    }
                }
            }

        })
    }



}