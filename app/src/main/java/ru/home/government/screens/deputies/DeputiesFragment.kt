package ru.home.government.screens.deputies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dropbox.android.external.store4.FetcherResult
import kotlinx.android.synthetic.main.fragment_deputies.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentDeputiesBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.Deputy
import ru.home.government.repository.Response
import ru.home.government.screens.BaseFragment
import java.lang.StringBuilder
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
class DeputiesFragment: BaseFragment() {

    companion object {

        private const val EXTRA_LAUNCH_WITH_CONTENT = "EXTRA_LAUNCH_WITH_CONTENT"

        private const val EXTRA_DEPUTIES = "EXTRA_DEPUTIES"

        fun instance() : DeputiesFragment {
            val fragment = DeputiesFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_LAUNCH_WITH_CONTENT, false)
            fragment.arguments = args
            return fragment
        }

        fun instance(deputies: ArrayList<Deputy>) : DeputiesFragment {
            val fragment = DeputiesFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_LAUNCH_WITH_CONTENT, true)
            args.putParcelableArrayList(EXTRA_DEPUTIES, deputies)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: FragmentDeputiesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeputiesBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity!!.application as AppApplication).component.inject(this)

        progressView = view.findViewById<View>(R.id.progressView)

        binding.list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = DeputiesAdapter()

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_divider)!!)
        binding.list.addItemDecoration(dividerItemDecoration)

        if (arguments != null
            && arguments!!.containsKey(EXTRA_LAUNCH_WITH_CONTENT)
            && arguments!!.getBoolean(EXTRA_LAUNCH_WITH_CONTENT)) {
            processWithExistingData()
        } else {
            processWithRequestForData()
        }
    }

    private fun processWithExistingData() {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.topMargin = resources.getDimension(R.dimen.actionBarSize).toInt()
        deputiesContainer.layoutParams = layoutParams

        val activeDeputies: ArrayList<Deputy> = arguments!!.getParcelableArrayList(EXTRA_DEPUTIES)!!
        (binding.list.adapter as DeputiesAdapter).update(activeDeputies)
    }

    @FlowPreview
    private fun processWithRequestForData() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                processDeputiesModel()
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                val viewModel: DeputiesViewModel by viewModels() { viewModelFactory }
                viewModel.init(activity!!.application as AppApplication)
                viewModel.isLoading.collect { value ->
                    visibleProgress(value)
                }
            }
        }
    }

    private suspend fun processDeputiesModel() {
        visibleProgress(true)
        val viewModel: DeputiesViewModel by viewModels() { viewModelFactory }
        viewModel.init(activity!!.application as AppApplication)
        viewModel.deputies.collect { result ->
            when (result) {
                is Response.Data -> {
                    val activeDeputies = result.data.filter { it -> it.isCurrent!! }
                    (list.adapter as DeputiesAdapter).update(activeDeputies)
                }
                is Response.Error.Message -> {
                    val error = StringBuilder()
                        .append(getString(R.string.unknown_error) + ". " + result.msg)
                        .append(". ")
                        .append(result.msg)
                        .toString()
                    showError(error)
                }
                is Response.Error.Exception -> {
                    Log.e(App.TAG, getString(R.string.unknown_error), result.error)
                    showError(getString(R.string.unknown_error))
                }
            }
        }
    }

}