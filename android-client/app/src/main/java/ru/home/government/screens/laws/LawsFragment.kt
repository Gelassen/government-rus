package ru.home.government.screens.laws

import android.os.Bundle
import android.view.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ferfalk.simplesearchview.SimpleSearchView
import ru.home.government.R
import ru.home.government.screens.BaseFragment
import ru.home.government.screens.OnSearchClickListener
import ru.home.government.screens.laws.filter.LawsFilteredFragment
import ru.home.government.screens.laws.main.LawsMainFragment

/**
 * Контейнер вкладки «Законопроекты».
 * Внутренняя навигация (список ↔ поиск) идёт через Navigation Component,
 * а не через FragmentManager.replace.
 */
class LawsFragment : BaseFragment() {

    private lateinit var searchView: SimpleSearchView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_laws, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Вложенный NavHost для списка / поиска
        if (childFragmentManager.findFragmentById(R.id.container_laws) == null) {
            val navHost = NavHostFragment.create(R.navigation.laws_nav_graph)
            childFragmentManager.beginTransaction()
                .replace(R.id.container_laws, navHost)
                .setPrimaryNavigationFragment(navHost)
                .commitNow()
        }
    }

    @Deprecated("Use onViewCreated + viewLifecycleOwner")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_law, menu)
        val menuItem = menu.findItem(R.id.action_search)
        if (::searchView.isInitialized) {
            searchView.setMenuItem(menuItem)
        }
    }

    /**
     * Вызывается из SimpleSearchView при submit.
     * Переходим на экран поиска через Navigation Component.
     */
    fun onSearch(query: String?) {
        if (query.isNullOrBlank()) return

        val navHost = childFragmentManager.findFragmentById(R.id.container_laws) as? NavHostFragment
            ?: return
        val navController = navHost.navController

        // Если уже на экране поиска — просто обновляем запрос
        val current = navController.currentDestination?.id
        if (current == R.id.laws_filtered) {
            val frag = navHost.childFragmentManager.primaryNavigationFragment
            if (frag is OnSearchClickListener) {
                frag.onSearch(query)
            }
        } else {
            val args = Bundle().apply {
                putString("query", query)
            }
            navController.navigate(R.id.action_laws_main_to_filtered, args)
        }
    }

    private fun initSearchView() {
        searchView = requireActivity().findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                onSearch(query)
                searchView.closeSearch()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean = true

            override fun onQueryTextCleared(): Boolean = true
        })
    }
}
