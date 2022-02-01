package ru.home.government.screens.laws.filter

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_law_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
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

    private lateinit var binding: FragmentLawMainBinding

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = FragmentLawMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        lawsAdapter = LawsAdapter(Dispatchers.Main, Dispatchers.Default)

        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = lawsAdapter
        (binding.list.adapter as LawsAdapter).listener = this

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_divider)!!)
        binding.list.addItemDecoration(dividerItemDecoration)

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
                    (binding.list.adapter as LawsAdapter).submitData(it)
                    if ((binding.list.adapter as LawsAdapter).itemCount == 0) {
                        lawsNoData.visibility = View.VISIBLE
                        list.visibility = View.GONE
                    } else {
                        lawsNoData.visibility = View.GONE
                        list.visibility = View.VISIBLE
                    }
                } catch (ex: Exception) {
                    Log.e(App.TAG, "Search job exception", ex)
                }

            }
        }

    }

}