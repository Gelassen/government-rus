package ru.home.government.screens.laws.filter

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_law_main.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.OnSearchClickListener
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.main.LawsAdapter
import ru.home.government.screens.laws.details.DetailsActivity
import java.lang.Exception

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

    private lateinit var billsViewModel: BillsViewModel

    private lateinit var filteredLawsAdapter: FilteredLawsAdapter

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return layoutInflater.inflate(R.layout.fragment_law_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        filteredLawsAdapter = FilteredLawsAdapter()

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = filteredLawsAdapter
        (list.adapter as FilteredLawsAdapter).listener = this

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_divider)!!)
        list.addItemDecoration(dividerItemDecoration)

        billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(activity!!.application as AppApplication)

        val filter = arguments!!.getString(EXTRA_KEY, "")
        onSearch(filter)
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

    override fun onSearch(str: String?) {
        searchJob?.cancel()
        fetchLawsWithFilter(str)
    }

    private fun fetchLawsWithFilter(str: String?) {
        searchJob = lifecycleScope.launch {
            billsViewModel.getLawsByName(str!!).collectLatest { it ->
                try {
                    (list.adapter as FilteredLawsAdapter).submitData(it)
                    if ((list.adapter as FilteredLawsAdapter).itemCount == 0) {
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