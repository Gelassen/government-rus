package ru.home.government.screens.deputies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentDeputiesBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.dto.Deputy
import ru.home.government.screens.BaseFragment
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
class DeputiesFragment: BaseFragment() {

    companion object {

        private const val EXTRA_LAUNCH_WITH_CONTENT = "EXTRA_LAUNCH_WITH_CONTENT"

        private const val EXTRA_DEPUTIES = "EXTRA_DEPUTIES"

        private const val EXTRA_NO_BOTTOM_VIEW = "EXTRA_NO_BOTTOM_VIEW"

        fun instance() : DeputiesFragment {
            val fragment = DeputiesFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_LAUNCH_WITH_CONTENT, false)
            args.putBoolean(EXTRA_NO_BOTTOM_VIEW, false)
            fragment.arguments = args
            return fragment
        }

        fun instance(deputies: ArrayList<Deputy>) : DeputiesFragment {
            val fragment = DeputiesFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_LAUNCH_WITH_CONTENT, true)
            args.putBoolean(EXTRA_NO_BOTTOM_VIEW, true)
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity().application as AppApplication).component.inject(this)

        progressView = view.findViewById<View>(R.id.progressView)

        binding.list.setLayoutManager(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false))
        binding.list.setAdapter(DeputiesAdapter())

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_divider)!!)
        binding.list.getRecyclerView().addItemDecoration(dividerItemDecoration)
        binding.list.addVeiledItems(15)
    }

    @Deprecated(message = "It should be urgently replaced with valid implementation")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: DeputiesViewModel by viewModels { viewModelFactory }
        lifecycleScope.launch {
            viewModel.uiState.collectLatest { it ->
                if (it.errors.isNotEmpty()) {
                    showError(
                        view = requireActivity().findViewById(R.id.nav_view),
                        text = it.errors.first(),
                        onDismiss = { viewModel.removeShownError() }
                    )
                }
                (binding.list.getRecyclerView().adapter as DeputiesAdapter).update(it.deputies)
                visibleProgress(it.isLoading)
                showList(!it.isLoading)
            }
        }
        if (arguments != null
            && requireArguments().containsKey(EXTRA_LAUNCH_WITH_CONTENT)
            && requireArguments().getBoolean(EXTRA_LAUNCH_WITH_CONTENT)) {
            decorViewForLayoutWithoutBottomNavigation()
            processWithExistingData()
        } else {
            decorViewForLayoutWithBottomNavigation()
            processWithRequestForData()
        }
    }

    private fun decorViewForLayoutWithoutBottomNavigation() {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.topMargin = resources.getDimension(R.dimen.actionBarSize).toInt()
        binding.deputiesContainer.layoutParams = layoutParams
    }

    private fun processWithExistingData() {
        val activeDeputies: ArrayList<Deputy> = requireArguments().getParcelableArrayList(EXTRA_DEPUTIES)!!
        val viewModel: DeputiesViewModel by viewModels { viewModelFactory }
        viewModel.manuallyUpdateDeputies(activeDeputies)
    }

    @FlowPreview
    private fun processWithRequestForData() {
        val viewModel: DeputiesViewModel by viewModels { viewModelFactory }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.fetchDeputies()
            }
        }
    }

    /**
     * This method should be called only when parent activity has bottom navigation view.
     *
     * findViewById(R.id.nav_view) throws an exception if nav_view is not presented in the
     * layout, I didn't find an option to check it existence in other way, so I added extra
     * flag to explicitly tell API users to consider it due using this fragment.
     * */
    private fun decorViewForLayoutWithBottomNavigation() {
        if (arguments != null
            && requireArguments().containsKey(EXTRA_NO_BOTTOM_VIEW)
            && !requireArguments().getBoolean(EXTRA_NO_BOTTOM_VIEW)) {
            val bottomNavigation: BottomNavigationView = requireActivity().findViewById(R.id.nav_view)
            val viewTreeObserver = bottomNavigation.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        val viewHeight = bottomNavigation.height
                        if (viewHeight != 0) {
                            bottomNavigation.viewTreeObserver.removeOnGlobalLayoutListener(this)
                            (binding.deputiesContainer.layoutParams as FrameLayout.LayoutParams).bottomMargin = viewHeight
                        }
                    }
                })
            }
        }
    }

    private fun showList(show: Boolean) {
        if (show) {
            if (binding.list.getRecyclerView().adapter?.itemCount == 0) {
                binding.deputiesNoData.visibility = View.VISIBLE
//                binding.list.visibility = View.GONE
                binding.list.veil()
            } else {
                binding.deputiesNoData.visibility = View.GONE
//                binding.list.visibility = View.VISIBLE
                binding.list.unVeil()
            }
        } else {
            binding.deputiesNoData.visibility = View.GONE
//            binding.list.visibility = View.GONE
            binding.list.veil()
        }
    }
}