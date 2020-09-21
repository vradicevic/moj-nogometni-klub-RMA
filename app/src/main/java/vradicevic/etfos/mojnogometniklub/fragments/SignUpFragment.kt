package vradicevic.etfos.mojnogometniklub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.create_player_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.*
import vradicevic.etfos.login.Repository
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils
import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.utils.PreferenceManager

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var spinner:Spinner
    private var itemsClubs:MutableList<String> = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        spinner= requireActivity().findViewById(R.id.listClubs)
        addListeners()
        FirebaseUtils.database.getReference("reggedclubs").addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(data: DataSnapshot) {


                data.children.forEach { child->
                    itemsClubs.add(child.key.toString())


                }
                val clubs = itemsClubs.toTypedArray()
                if (spinner != null) {
                    val adapter = ArrayAdapter(MyAppContext.getContext(),
                        android.R.layout.simple_spinner_item,clubs)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object :
                        AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>,
                                                    view: View, position: Int, id: Long) {
                            Log.d("Spinner",spinner.selectedItem.toString())

                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            // write code to perform some action
                        }
                    }
                }
            }

        })
    }

    private fun addListeners() {
        btnSignUp.setOnClickListener {
            FirebaseUtils.auth.createUserWithEmailAndPassword(this.etEmail.text.toString(),this.etPassword.text.toString())
                .addOnCompleteListener { task->
                    if (task.isSuccessful) {
                        val club = this.spinner.selectedItem.toString()
                        val uuid = FirebaseUtils.auth.currentUser!!.uid
                        FirebaseUtils.database.getReference("users/${uuid}/club").setValue(club)
                        FirebaseUtils.auth.signOut()
                        findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                    } else {
                        // If sign in fails, display a message to the user.

                    }
                }
        }



    }

}