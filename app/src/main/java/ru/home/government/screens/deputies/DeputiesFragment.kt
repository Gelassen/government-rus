package ru.home.government.screens.deputies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_deputies.*
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.screens.BaseFragment
import ru.home.government.util.newObserveBy


class DeputiesFragment: BaseFragment() {

    companion object {

        private const val EXTRA_LAUNCH_WITH_CONTENT = "EXTRA_LAUNCH_WITH_CONTENT"

        private const val EXTRA_DEPUTIES = "EXTRA_DEPUTIES"

        fun instance() : DeputiesFragment {
            val fragment = DeputiesFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_LAUNCH_WITH_CONTENT, false)
            fragment.arguments = args
            return fragment
        }

        fun instance(deputies: ArrayList<Deputy>) : DeputiesFragment {
            val fragment = DeputiesFragment()
            val args = Bundle()
            args.putBoolean(EXTRA_LAUNCH_WITH_CONTENT, true)
            args.putParcelableArrayList(EXTRA_DEPUTIES, deputies)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_deputies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressView = view.findViewById<View>(R.id.progressView)

        val list = view.findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        list.adapter = DeputiesAdapter()

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_divider)!!)
        list.addItemDecoration(dividerItemDecoration)

        if (arguments != null
            && arguments!!.containsKey(EXTRA_LAUNCH_WITH_CONTENT)
            && arguments!!.getBoolean(EXTRA_LAUNCH_WITH_CONTENT)) {
            var layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams.topMargin = resources.getDimension(R.dimen.actionBarSize).toInt()
            deputiesContainer.layoutParams = layoutParams

            val activeDeputies: ArrayList<Deputy> = arguments!!.getParcelableArrayList(EXTRA_DEPUTIES)!!
            (list.adapter as DeputiesAdapter).update(activeDeputies)
        } else {
            val viewModel = ViewModelProviders.of(this).get(DeputiesViewModel::class.java)
            viewModel.init(activity!!.application as AppApplication)
            viewModel.subscribeOnDeputies()
                .newObserveBy(
                    this,
                    onNext = {
                            it ->
                        val activeDeputies = it.filter { it -> it.isCurrent!! }
                        visibleProgress(false)
                        (list.adapter as DeputiesAdapter).update(activeDeputies)
                    },
                    onLoading = ::visibleProgress,
                    onError = ::showError
                )
            visibleProgress(true)
            viewModel.fetchDeputies()
        }

    }

}