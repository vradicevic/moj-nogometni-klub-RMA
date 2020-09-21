package vradicevic.etfos.mojnogometniklub.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.players_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vradicevic.etfos.login.PlayersRecyclerAdapter
import vradicevic.etfos.login.Repository
import vradicevic.etfos.mojnogometniklub.PlayerItemDecoration
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.models.UpdatePlayer
import vradicevic.etfos.mojnogometniklub.utils.FirebaseUtils
import vradicevic.etfos.mojnogometniklub.viewmodels.PlayersViewModel

class PlayersFragment : Fragment(),PlayersRecyclerAdapter.OnPlayerClickedInterface {

    companion object {
        fun newInstance() =
            PlayersFragment()
    }
    private lateinit var viewModel:PlayersViewModel
    private lateinit var playerAdapter: PlayersRecyclerAdapter
    private lateinit var btnAddPlayer:FloatingActionButton
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlayersViewModel::class.java)
        initRecyclerView()
        viewModel.players.observe(viewLifecycleOwner, Observer{
            playerAdapter.submitList(it)
            playerAdapter.notifyDataSetChanged()

        })
        setListeners()

    }

    private fun setListeners() {
        btnAddPlayer = requireActivity().findViewById(R.id.btnAddPlayer)
        btnAddPlayer.setOnClickListener { view ->
            navController= Navigation.findNavController(view)
            navController.navigate(R.id.action_playersFragment_to_createPlayerFragment)
        }
    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val itemDecoration =
            PlayerItemDecoration(30)
        recycler_view.addItemDecoration(itemDecoration)
        playerAdapter = PlayersRecyclerAdapter(this)
        recycler_view.adapter=playerAdapter
    }

    override fun onUpdateClicked(id: String,it:View) {
        var data:UpdatePlayer
        CoroutineScope(IO).launch {
            val player=Repository.getPlayerByID(id)
            data = UpdatePlayer(
                player.fifaID,
                player.name,
                player.surname,
                player.position,
                player.equipment,
                player.redcards,
                player.yellowcards,
                player.image,
                player.scores
            )
            withContext(Main){
                val action =  PlayersFragmentDirections.actionPlayersFragmentToUpdatePlayerFragment(data)
                navController= Navigation.findNavController(it)
                navController.navigate(action)
            }
        }


    }

    override fun onDeleteClicked(id: String) {
        try{
            FirebaseUtils.database.getReference("clubs/${FirebaseUtils.club}/players/${id}").removeValue()
        }catch(e:Exception){
            Toast.makeText(requireContext(),e.toString(),Toast.LENGTH_SHORT).show()

        }
    }

}