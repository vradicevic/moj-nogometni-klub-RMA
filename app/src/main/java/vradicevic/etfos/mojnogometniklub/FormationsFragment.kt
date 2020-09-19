package vradicevic.etfos.mojnogometniklub

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.delay
import vradicevic.etfos.mojnogometniklub.viewmodels.FormationsViewModel
import vradicevic.etfos.mojnogometniklub.viewmodels.PlayersViewModel

class FormationsFragment : Fragment() {

    companion object {
        fun newInstance() = FormationsFragment()
    }

    private lateinit var viewModel: FormationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.formations_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FormationsViewModel::class.java)

        viewModel.players.observe(viewLifecycleOwner, Observer{

        })
    }

}