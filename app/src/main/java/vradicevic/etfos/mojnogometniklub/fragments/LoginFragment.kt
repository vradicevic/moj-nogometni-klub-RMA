package vradicevic.etfos.mojnogometniklub.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.login_fragment.*
import vradicevic.etfos.mojnogometniklub.viewmodels.LoginViewModel
import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils
import vradicevic.etfos.mojnogometniklub.utils.PreferenceManager

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() =
            LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    lateinit var navController: NavController
    lateinit var bottomNav:BottomNavigationView
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)

    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController= Navigation.findNavController(view)
        bottomNav= requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        auth = FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            Log.d("LOGOUT", "mozda sam ovjde")
            Log.d("USER",auth.currentUser.toString())
            navController.navigate(R.id.action_loginFragment_to_playersFragment)
        }else bottomNav.visibility= View.GONE
        addListeners(auth)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun addListeners(auth:FirebaseAuth) {
        btnLogin.setOnClickListener {
            it.isEnabled=false
            auth.signInWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString())
                .addOnCompleteListener { task ->
                    it.isEnabled=true
                    if (task.isSuccessful) {

                        Toast.makeText(
                            MyAppContext.getContext(), "Prijavljeni ste kao: ${auth.currentUser!!.email}",
                            Toast.LENGTH_SHORT).show()
                        bottomNav.visibility=View.VISIBLE
                        PreferenceManager().saveUUID(auth.currentUser!!.uid)
                        FirebaseUtils().startFirebaseConnection()
                        navController.navigate(R.id.action_loginFragment_to_playersFragment)


                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MyAppContext.getContext(), "${task.toString()}",
                            Toast.LENGTH_SHORT).show()

                        // ...
                    }  }



        }
        btnGoToSignUp.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

}