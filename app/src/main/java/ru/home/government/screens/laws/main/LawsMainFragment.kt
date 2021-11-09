package ru.home.government.screens.laws.main

import android.os.Bundle
import android.view.*
import androidx.core.app.ComponentActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_law_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.databinding.FragmentLawMainBinding
import ru.home.government.di.ViewModelFactory
import ru.home.government.model.Law
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
        visibleProgress(true)
        fetchLaws()
    }

    override fun onItemClick(item: Law) {
        DetailsActivity.start(activity as ComponentActivity, item)
    }

    private fun fetchLaws() {
        lifecycleScope.launch {
            val flow = billsViewModel.getLawsByPage()
            flow.collectLatest {
                    it ->
                visibleProgress(false)
                (list.adapter as LawsAdapter).submitData(it)
            }
        }
    }

}