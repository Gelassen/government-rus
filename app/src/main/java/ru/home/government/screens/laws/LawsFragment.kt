package ru.home.government.screens.laws

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.ferfalk.simplesearchview.SimpleSearchView
import ru.home.government.R
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.MainActivity
import ru.home.government.screens.OnSearchClickListener
import ru.home.government.screens.laws.filter.LawsFilteredFragment
import ru.home.government.screens.laws.filter.LawsFilteredFragment.Companion.EXTRA_KEY
import ru.home.government.screens.laws.main.LawsMainFragment
import java.lang.RuntimeException

class LawsFragment: BaseFragment() {

//    private lateinit var search_view: SimpleSearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return layoutInflater.inflate(R.layout.fragment_laws, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        parentFragmentManager
            .beginTransaction()
            .replace(R.id.container_laws,
                LawsMainFragment()
            )
            .commit()
    }

    @Deprecated(message = "It should be urgently replaced with valid implementation")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActivityDependentViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_law, menu)

        // TODO complete me ref. #7
/*        val menuItem = menu.findItem(R.id.action_search)
        search_view.setMenuItem(menuItem)*/
    }

    fun onSearch(str: String?) {
//        val frag = childFragmentManager.findFragmentByTag(LawsFilteredFragment.TAG)
        val navController = findNavController()
//        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar) TODO complete me ref. #7
//        (requireActivity() as MainActivity).appBarCon
//        NavigationUI.setupWithNavController(toolbar = toolbar, navController = navController/*, configuration = null*/)
        navController.navigate(R.id.navigationLawsFilteredFragment, bundleOf(EXTRA_KEY to str))
/*        if (frag == null) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.container_laws, LawsFilteredFragment.instance(str!!), LawsFilteredFragment.TAG)
                .addToBackStack(LawsFilteredFragment.TAG)
                .commit()
        } else {
            if (frag is OnSearchClickListener) {
                frag.onSearch(str)
            } else {
                throw RuntimeException("Did you miss to add search interface to fragment?")
            }
        }*/
    }

    private fun initActivityDependentViews() {
        // TODO complete me ref. #7
/*        search_view = requireActivity().findViewById<SimpleSearchView>(R.id.searchView)
        search_view.isSearchOpen
        search_view.setOnQueryTextListener(object: SimpleSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                onSearch(query)
                search_view.closeSearch()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // no op
                return true
            }

            override fun onQueryTextCleared(): Boolean {
                // no op
                return true
            }
        })*/
    }

}