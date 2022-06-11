package ru.home.government.screens.deputies

import android.os.Bundle
import android.util.Log
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
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (arguments != null
            && arguments!!.containsKey(EXTRA_LAUNCH_WITH_CONTENT)
            && arguments!!.getBoolean(EXTRA_LAUNCH_WITH_CONTENT)) {
            processWithExistingData()
        } else {
            decorView()
            processWithRequestForData()
        }
    }

    private fun processWithExistingData() {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        layoutParams.topMargin = resources.getDimension(R.dimen.actionBarSize).toInt()
        binding.deputiesContainer.layoutParams = layoutParams

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
                viewModel.isLoading.collect { value ->
                    visibleProgress(value)
                }
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
    private fun decorView() {
        if (arguments != null && !arguments!!.getBoolean(EXTRA_NO_BOTTOM_VIEW)) {
            val bottomNavigation: BottomNavigationView = activity!!.findViewById(R.id.nav_view)
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

    private suspend fun processDeputiesModel() {
        visibleProgress(true)
        val viewModel: DeputiesViewModel by viewModels() { viewModelFactory }
        viewModel.deputies.collect { result ->
            when (result) {
                is Response.Data -> {
                    val activeDeputies = result.data.filter { it -> it.isCurrent!! }
                    (binding.list.adapter as DeputiesAdapter).update(activeDeputies)
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