package vradicevic.etfos.mojnogometniklub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.players_fragment.*
import kotlinx.android.synthetic.main.top_players_fragment.*
import vradicevic.etfos.login.PlayersRecyclerAdapter
import vradicevic.etfos.mojnogometniklub.adapters.HighscorePlayersAdapter
import vradicevic.etfos.mojnogometniklub.viewmodels.FormationsViewModel
import vradicevic.etfos.mojnogometniklub.viewmodels.TopPlayersViewModel

class TopPlayersFragment : Fragment() {

    companion object {
        fun newInstance() = TopPlayersFragment()
    }

    private lateinit var viewModel: TopPlayersViewModel
    private lateinit var highscoreAdapter: HighscorePlayersAdapter
    private lateinit var rgSortBy:RadioGroup


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.top_players_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rgSortBy=view.findViewById(R.id.rgSortBy)
        rgSortBy.setOnCheckedChangeListener(listener)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TopPlayersViewModel::class.java)
        initRecyclerView()
        viewModel.players.observe(viewLifecycleOwner, Observer{
            highscoreAdapter.submitList(it)
            highscoreAdapter.notifyDataSetChanged()
        })
    }

    private fun initRecyclerView(){
        highscore_recycler_view.layoutManager = LinearLayoutManager(activity)
        val itemDecoration = PlayerItemDecoration(30)
        highscore_recycler_view.addItemDecoration(itemDecoration)
        highscoreAdapter = HighscorePlayersAdapter()
        highscore_recycler_view.adapter=highscoreAdapter
    }

    var listener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        val radio: RadioButton = requireActivity().findViewById(checkedId)
        viewModel.players.removeObservers(viewLifecycleOwner)
        when(radio.id){
            R.id.rbScore-> viewModel.changePlayersReference("score")
            R.id.rbRedCards-> viewModel.changePlayersReference("redcards")
            R.id.rbYellowCards-> viewModel.changePlayersReference("yellowcards")
            R.id.rbEquipment -> viewModel.changePlayersReference("equipment")
        }
        viewModel.players.observe(viewLifecycleOwner, Observer{
            highscoreAdapter.submitList(it)
            highscoreAdapter.notifyDataSetChanged()
        })

    }

}