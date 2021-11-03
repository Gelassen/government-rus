package ru.home.government.screens.tracker

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_tracker.*
import kotlinx.android.synthetic.main.layout_tracked_laws_placeholder.*
import ru.home.government.App
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentTrackerBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.Law
import ru.home.government.repository.CacheRepository
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.screens.laws.details.DetailsActivity
import ru.home.government.util.newObserveBy
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
        billsViewModel.getTrackedLaws()
            .newObserveBy(
                this,
                onNext = {
                        it ->
                    Log.d(App.TAG, "Data arrived: " + it.laws.size)
                    visibleProgress(false)
                    (list.adapter as TrackerAdapter).update(it.laws)
                    trackedPlaceholder.visibility = if (it.laws.size == 0) View.VISIBLE else View.GONE
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )

    }

    override fun onResume() {
        super.onResume()
        (binding.list.adapter as TrackerAdapter).reset()
        trackedPlaceholder.visibility = View.VISIBLE
        billsViewModel.fetchTrackedLaws()
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

}