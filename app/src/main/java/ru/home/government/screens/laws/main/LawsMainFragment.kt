package ru.home.government.screens.laws.main

import android.os.Bundle
import android.util.Log
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
import org.jetbrains.annotations.TestOnly
import ru.home.government.App
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

    object Const {
        // used for integration tests
        val TAG_LIST = LawsMainFragment::class.java.simpleName.plus("TAG_LIST")
        val TAG_SHIMMER = LawsMainFragment::class.java.simpleName.plus("TAG_SHIMMER")
    }

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
        lawsAdapter.addOnPagesUpdatedListener { billsViewModel.onPageUpdate(lawsAdapter.itemCount) }

        binding.list.getRecyclerView().tag = Const.TAG_LIST
        binding.list.getVeiledRecyclerView().tag = Const.TAG_SHIMMER
        binding.list.setLayoutManager(LinearLayoutManager(context))
        binding.list.setAdapter(lawsAdapter)
        (binding.list.getRecyclerView().adapter as LawsAdapter).listener = this

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_divider)!!)
        binding.list.getRecyclerView().addItemDecoration(dividerItemDecoration)

        billsViewModel = viewModelFactory.create(BillsViewModel::class.java)
        fetchLaws()
        listenUpdates()
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

    override fun visibleProgress(show: Boolean) {
        Log.d(App.TAG, "${LawsMainFragment::class.java.simpleName} Toggle visibility for main screen with laws ${show}")
        if (show) {
            binding.list.veil()
        } else {
            binding.list.unVeil()
        }
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
                (binding.list.getRecyclerView().adapter as LawsAdapter).submitData(it.billsByPage)
            }
        }
        lifecycleScope.launch {
            (binding.list.getRecyclerView().adapter as LawsAdapter).loadStateFlow.collectLatest { loadState ->
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
        if ((binding.list.getRecyclerView().adapter as LawsAdapter).itemCount == 0) {
            binding.lawsNoData.visibility = View.VISIBLE
        } else {
            binding.lawsNoData.visibility = View.GONE
        }
    }

}