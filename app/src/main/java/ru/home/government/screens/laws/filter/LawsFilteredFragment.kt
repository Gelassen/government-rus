package ru.home.government.screens.laws.filter

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentLawFilteredBinding
import ru.home.government.databinding.FragmentLawMainBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.OnSearchClickListener
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.details.DetailsActivity
import ru.home.government.screens.laws.main.LawsAdapter
import java.lang.Exception
import javax.inject.Inject

class LawsFilteredFragment: BaseFragment(),
    LawsAdapter.ClickListener,
    OnSearchClickListener {

    companion object {

        const val TAG = "LawsFilteredFragment"

        private const val EXTRA_KEY = "EXTRA_KEY"

        fun instance(filter: String): LawsFilteredFragment {
            val fragment =
                LawsFilteredFragment()
            val args = Bundle()
            args.putString(EXTRA_KEY, filter)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var lawsAdapter: LawsAdapter

    private lateinit var binding: FragmentLawFilteredBinding

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentLawFilteredBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        lawsAdapter = LawsAdapter(Dispatchers.Main, Dispatchers.Default)

        binding.listLawsFiltered.layoutManager = LinearLayoutManager(context)
        binding.listLawsFiltered.adapter = lawsAdapter
        (binding.listLawsFiltered.adapter as LawsAdapter).listener = this

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_divider)!!)
        binding.listLawsFiltered.addItemDecoration(dividerItemDecoration)

        val filter = requireArguments().getString(EXTRA_KEY, "")
        onSearch(filter)
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

    override fun onSearch(str: String?) {
        searchJob?.cancel()
        visibleProgress(true)
        fetchLawsWithFilter(str)
    }

    private fun fetchLawsWithFilter(str: String?) {
        searchJob = lifecycleScope.launch {
            val billsViewModel: BillsViewModel by viewModels() { viewModelFactory }
            billsViewModel.getLawByName(str!!).collectLatest { it ->
                visibleProgress(false)
                try {
                    (binding.listLawsFiltered.adapter as LawsAdapter).submitData(it)
                } catch (ex: Exception) {
                    Log.e(App.TAG, "Search job exception", ex)
                }

            }
        }
        lifecycleScope.launch {
            (binding.listLawsFiltered.adapter as LawsAdapter).loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        // no op
                    }
                    is LoadState.Error -> {
                        visibleProgress(false)
                        showNoDataView()
                        showError(requireActivity().findViewById(R.id.nav_view), (loadState.refresh as LoadState.Error).error.localizedMessage)
                    }
                    is LoadState.NotLoading -> {
                        visibleProgress(false)
                        showNoDataView()
                    }
                }
            }
        }

    }

    private fun showNoDataView() {
        if ((binding.listLawsFiltered.adapter as LawsAdapter).itemCount == 0) {
            Log.d(App.TAG, "No items for search")
            binding.lawsNoData.visibility = View.VISIBLE
            binding.listLawsFiltered.visibility = View.GONE
        } else {
            Log.d(App.TAG, "There is items for search")
            binding.lawsNoData.visibility = View.GONE
            binding.listLawsFiltered.visibility = View.VISIBLE
        }
    }

}