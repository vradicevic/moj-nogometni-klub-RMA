package vradicevic.etfos.mojnogometniklub

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.View.inflate
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import vradicevic.etfos.login.Repository
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils

import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.utils.PreferenceManager
import vradicevic.etfos.mojnogometniklub.viewmodels.FormationsViewModel
import vradicevic.etfos.mojnogometniklub.viewmodels.PlayersViewModel


class MainActivity : AppCompatActivity(),FirebaseAuth.AuthStateListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var menu:Menu
    private lateinit var bottomNavigationView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MyAppContext.setContext(applicationContext)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)
    }






    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        val inflater = menuInflater
        inflater.inflate(R.menu.account,menu)
        this.menu=menu!!
        FirebaseUtils.auth.addAuthStateListener(this)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.itemSignOut-> signOut()
        }

        return super.onOptionsItemSelected(item)
    }
    fun signOut(){
        FirebaseUtils.auth.signOut()
        PreferenceManager().apply {
            saveClub("")
            saveUUID("")
        }
        navController= findNavController(R.id.fragment)
        navController.apply {
            popBackStack(R.id.playersFragment, true);
            popBackStack(R.id.updatePlayerFragment, true);
            popBackStack(R.id.createPlayerFragment, true);
            popBackStack(R.id.topPlayersFragment, true);
            popBackStack(R.id.formationsFragment, true);
        }
        when(navController.currentDestination!!.id){
            R.id.playersFragment-> navController.navigate(R.id.action_playersFragment_to_loginFragment)
            R.id.updatePlayerFragment-> navController.navigate(R.id.action_updatePlayerFragment_to_loginFragment)
            R.id.createPlayerFragment-> navController.navigate(R.id.action_createPlayerFragment_to_loginFragment)
            R.id.topPlayersFragment-> navController.navigate(R.id.action_topPlayersFragment_to_loginFragment)
            R.id.formationsFragment-> navController.navigate(R.id.action_formationsFragment_to_loginFragment)
        }

    }
    override fun onAuthStateChanged(auth: FirebaseAuth) {
        bottomNavigationView= findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        Log.d("TAG_IO",auth.currentUser.toString())
        if(auth.currentUser!=null){
            bottomNavigationView.visibility= View.VISIBLE
            menu.findItem(R.id.itemSignOut).isVisible=true
        }else{
            bottomNavigationView.visibility= View.GONE
            menu.findItem(R.id.itemSignOut).isVisible=false

        }
    }


}