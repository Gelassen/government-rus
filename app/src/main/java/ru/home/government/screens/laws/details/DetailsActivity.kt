package ru.home.government.screens.laws.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ru.home.government.R

class DetailsActivity : AppCompatActivity() {

    companion object {

        private const val EXTRA_LAW_CODE = "EXTRA_LAW_CODE"
        private const val EXTRA_DETAILS_URL = "EXTRA_DETAILS_URL"

        fun start(context: ComponentActivity, code: String, url: String) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_LAW_CODE, code)
            intent.putExtra(EXTRA_DETAILS_URL, url)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_law_details)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        val extras = intent.extras
        if (extras != null) { // cover corner case on back press scenario
            val adapter = LawDetailsPagerAdapter(supportFragmentManager)
            viewPager.adapter = adapter
            adapter.addFragment(
                LawOverviewFragment.instance(extras.getString(EXTRA_LAW_CODE, "")),
                "Законопроект"
            )
            adapter.addFragment(
                LawDetailsFragment.instance(extras.getString(EXTRA_DETAILS_URL, "")),
                "Подробнее"
            )

            val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
            tabLayout!!.setupWithViewPager(viewPager)
        }

    }

}