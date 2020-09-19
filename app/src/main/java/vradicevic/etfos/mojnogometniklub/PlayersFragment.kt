package vradicevic.etfos.mojnogometniklub


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.players_fragment.*
import vradicevic.etfos.login.PlayersRecyclerAdapter
import vradicevic.etfos.login.Repository
import vradicevic.etfos.mojnogometniklub.viewmodels.PlayersViewModel

class PlayersFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersFragment()
    }

    private lateinit var viewModel:PlayersViewModel
    private lateinit var playerAdapter: PlayersRecyclerAdapter

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

    }

    private fun initRecyclerView(){
        recycler_view.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = PlayerItemDecoration(30)
        recycler_view.addItemDecoration(itemDecoration)
        playerAdapter = PlayersRecyclerAdapter()
        recycler_view.adapter=playerAdapter
    }

}