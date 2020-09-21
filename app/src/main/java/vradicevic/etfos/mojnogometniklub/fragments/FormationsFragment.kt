package vradicevic.etfos.mojnogometniklub.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.formations_fragment.*
import vradicevic.etfos.mojnogometniklub.R
import vradicevic.etfos.mojnogometniklub.utils.MyAppContext
import vradicevic.etfos.mojnogometniklub.viewmodels.FormationsViewModel

class FormationsFragment : Fragment() {

    companion object {
        fun newInstance() =
            FormationsFragment()
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

        viewModel.formation.observe(viewLifecycleOwner, Observer{

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(MyAppContext.getContext())
                .applyDefaultRequestOptions(requestOptions)
                .load(it[0].formationUrl)
                .into(ivFormation)
        })
    }

}