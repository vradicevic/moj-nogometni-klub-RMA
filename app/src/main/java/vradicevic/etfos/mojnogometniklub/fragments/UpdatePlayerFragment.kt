package vradicevic.etfos.mojnogometniklub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class UpdatePlayerFragment : Fragment() {

    companion object {
        fun newInstance() = UpdatePlayerFragment()
    }

    private lateinit var viewModel: UpdatePlayerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_player_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UpdatePlayerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}