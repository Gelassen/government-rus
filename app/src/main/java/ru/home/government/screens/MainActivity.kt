package ru.home.government.screens

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.home.government.R
import ru.home.government.databinding.ActivityMainBinding
import java.lang.IllegalStateException

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val dr = ColorDrawable(getApiSupportColor())
        supportActionBar!!.setBackgroundDrawable(dr)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
            ?: throw IllegalStateException("There is no NavHostFragment. Did you lose it during refactoring?")

        val navController = navHostFragment.findNavController()
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        MenuInflater(this).inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.openPrivacyPolicy -> {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(getString(R.string.privacyPolicy))
                startActivity(intent)
                true
            }
            R.id.openDisclaimer -> {
                intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(getString(R.string.disclaimer))
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        val searchView = findViewById<SimpleSearchView>(R.id.searchView)
        if (searchView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }
}


