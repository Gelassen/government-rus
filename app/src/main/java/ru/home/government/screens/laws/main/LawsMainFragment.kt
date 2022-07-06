package ru.home.government.screens.laws.main

import android.os.Bundle
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentLawMainBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.dto.Law
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.details.DetailsActivity
import javax.inject.Inject

class LawsMainFragment: BaseFragment(), LawsAdapter.ClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var billsViewModel: BillsViewModel

    private lateinit var lawsAdapter: LawsAdapter

    private lateinit var binding: FragmentLawMainBinding

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

        billsViewModel = viewModelFactory.create(BillsViewModel::class.java)
        fetchLaws()
        listenUpdates()
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

    private fun fetchLaws() {
        lifecycleScope.launch {
            billsViewModel.getLawsByPage()
        }
    }

    private fun listenUpdates() {
        lifecycleScope.launch {
            billsViewModel.uiState.collectLatest {
                if (it.errors.isNotEmpty()) {
                    showError(
                        view = requireActivity().findViewById(R.id.nav_view),
                        text = it.errors.first(),
                        onDismiss = { billsViewModel.removeShownError() }
                    )
                }
                visibleProgress(it.isLoading)
                showNoDataView()
                (binding.list.adapter as LawsAdapter).submitData(it.billsByPage)
            }
        }
        lifecycleScope.launch {
            (binding.list.adapter as LawsAdapter).loadStateFlow.collectLatest { loadState ->
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        // no op
                    }
                    is LoadState.Error -> {
                        billsViewModel.addError((loadState.refresh as LoadState.Error).error.localizedMessage!!)
                        visibleProgress(false)
                        showNoDataView()
                        (loadState.refresh as LoadState.Error).error.localizedMessage?.let { it ->
                            showError(requireActivity().findViewById(R.id.nav_view), it)
                        }
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
        if ((binding.list.adapter as LawsAdapter).itemCount == 0) {
            binding.lawsNoData.visibility = View.VISIBLE
        } else {
            binding.lawsNoData.visibility = View.GONE
        }
    }

}