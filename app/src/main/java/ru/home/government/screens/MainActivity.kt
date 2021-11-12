package ru.home.government.screens

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import ru.home.government.App
import ru.home.government.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val dr = ColorDrawable(resources.getColor(R.color.colorActionBar))
        supportActionBar!!.setBackgroundDrawable(dr)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
//
        navController.navigate(R.id.navigation_dashboard)
        Log.d(App.LF, "MainActivity::onCreate()")
    }

    override fun onResume() {
        super.onResume()
        Log.d(App.LF, "MainActivity::onResume()")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.d(App.LF, "MainActivity::onPostResume()")
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        Log.d(App.LF, "MainActivity::onResumeFragments()")
    }

    override fun onPause() {
        super.onPause()
        Log.d(App.LF, "MainActivity::onPause()")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
}


