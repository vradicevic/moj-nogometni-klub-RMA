package vradicevic.etfos.mojnogometniklub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CreatePlayerFragment : Fragment() {

    companion object {
        fun newInstance() = CreatePlayerFragment()
    }

    private lateinit var viewModel: CreatePlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreatePlayerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}