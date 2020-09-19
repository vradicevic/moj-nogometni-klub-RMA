package vradicevic.etfos.mojnogometniklub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
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
            navController.navigate(R.id.action_loginFragment_to_playersFragment)
        }else bottomNav.visibility= View.GONE
        addListeners(auth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    private fun addListeners(auth:FirebaseAuth) {
        btnLogin.setOnClickListener {

            auth.signInWithEmailAndPassword(etEmail.text.toString(),etPassword.text.toString())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(MyAppContext.getContext(), "Prijavljeni ste kao: ${auth.currentUser}",
                            Toast.LENGTH_SHORT).show()
                        bottomNav.visibility=View.VISIBLE
                        navController.navigate(R.id.action_loginFragment_to_playersFragment)


                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(getContext(), "${task.toString()}",
                            Toast.LENGTH_SHORT).show()

                        // ...
                    }  }



        }
    }

}