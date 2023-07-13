package ru.home.government.screens.tracker

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentTrackerBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.dto.Law
import ru.home.government.repository.CacheRepository
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.details.DetailsActivity
import javax.inject.Inject

class TrackerFragment: BaseFragment(), TrackerAdapter.ClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var binding: FragmentTrackerBinding

    lateinit var billsViewModel: BillsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        progressView = view.findViewById<View>(R.id.progressView)

        binding.list.setLayoutManager(LinearLayoutManager(context))
        binding.list.setAdapter(TrackerAdapter(this))

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_divider)!!)
        binding.list.getRecyclerView().addItemDecoration(dividerItemDecoration)

        val codes = CacheRepository(requireActivity()).lawCodes
        Log.d(App.TAG, "Tracked laws: " + codes.size)

        billsViewModel = viewModelFactory.create(BillsViewModel::class.java)

        lifecycleScope.launch {
            billsViewModel.uiState.collectLatest {
                (binding.list.getRecyclerView().adapter as TrackerAdapter).update(it.billsWhichTracked.laws)
                visibleProgress(it.isLoading)
                val showPlaceholder = it.billsWhichTracked.laws.size == 0 && !it.isLoading
                binding.trackedPlaceholderContainer.trackedPlaceholder.visibility =
                    if (showPlaceholder) View.VISIBLE else View.GONE
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                billsViewModel.fetchedTrackedLaws()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (binding.list.getRecyclerView().adapter as TrackerAdapter).reset()
        binding.trackedPlaceholderContainer.trackedPlaceholder.visibility = View.VISIBLE
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

    override fun visibleProgress(show: Boolean) {
        if (show) {
            binding.list.veil()
        } else {
            binding.list.unVeil()
        }
    }
}