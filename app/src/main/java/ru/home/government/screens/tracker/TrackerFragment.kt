package ru.home.government.screens.tracker

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.*
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tracker.*
import kotlinx.android.synthetic.main.layout_tracked_laws_placeholder.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentTrackerBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.Law
import ru.home.government.repository.CacheRepository
import ru.home.government.repository.Response
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.details.DetailsActivity
import java.lang.IllegalStateException
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

        binding.list.layoutManager = LinearLayoutManager(context)
        binding.list.adapter = TrackerAdapter(this)

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_divider)!!)
        binding.list.addItemDecoration(dividerItemDecoration)

        val codes = CacheRepository(requireActivity()).getLawCodes()
        Log.d(App.TAG, "Tracked laws: " + codes.size);

        billsViewModel = viewModelFactory.create(BillsViewModel::class.java)

        lifecycleScope.launch {
            billsViewModel.trackedLaws
                .onStart {
                    visibleProgress(true)
                }
                .collect { it ->
                    visibleProgress(false)
                    when(it) {
                        is Response.Data -> {
                            (list.adapter as TrackerAdapter).update(it.data.laws)
                            trackedPlaceholder.visibility = if (it.data.laws.size == 0) View.VISIBLE else View.GONE
                        }
                        is Response.Error -> {
                            if (it is Response.Error.Message) {
                                showError(it.msg)
                            } else if (it is Response.Error.Exception){
                                showError(getString(R.string.unknown_error) + "\n" + it.error)
                            } else {
                                throw IllegalStateException("Failed to obtain law by number and handle error response")
                            }
                        }
                    }
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
        (binding.list.adapter as TrackerAdapter).reset()
        trackedPlaceholder.visibility = View.VISIBLE
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

}