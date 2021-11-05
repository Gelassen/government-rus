package ru.home.government.screens.laws

import android.os.Bundle
import android.view.*
import android.widget.Toast
import com.miguelcatalan.materialsearchview.MaterialSearchView
import ru.home.government.R
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.OnSearchClickListener
import ru.home.government.screens.laws.filter.LawsFilteredFragment
import ru.home.government.screens.laws.main.LawsMainFragment
import java.lang.RuntimeException

class LawsFragment: BaseFragment() {

    private lateinit var search_view: MaterialSearchView

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
            .replace(R.id.container,
                LawsMainFragment()
            )
            .commit()

        search_view = requireActivity().findViewById<MaterialSearchView>(R.id.search_view)
        search_view.setOnQueryTextListener(object: MaterialSearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                onSearch(query)
                search_view.closeSearch()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                TODO("Not yet implemented")
                return true
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_law, menu)

        val menuItem = menu.findItem(R.id.action_search)
        search_view.setMenuItem(menuItem)
    }

    fun onSearch(str: String?) {
        if (wasFragmentDestroyed()) return
        
        val frag = requireFragmentManager().findFragmentByTag(LawsFilteredFragment.TAG)
        if (frag == null) {
            parentFragmentManager
                .beginTransaction()
                .add(R.id.container, LawsFilteredFragment.instance(str!!), LawsFilteredFragment.TAG)
                .addToBackStack(LawsFilteredFragment.TAG)
                .commit()
        } else {
            if (frag is OnSearchClickListener) {
                frag.onSearch(str)
            } else {
                throw RuntimeException("Did you miss to add search interface to fragment?")
            }
        }
    }

    fun wasFragmentDestroyed(): Boolean {
        return parentFragmentManager == null
    }

}