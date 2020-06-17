package ru.home.government.screens.laws.details

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ru.home.government.R
import java.util.ArrayList

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_law_details)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        val adapter = LawDetailsPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        adapter.addFragment(LawOverviewFragment(), "Законопроект")
        adapter.addFragment(LawDetailsFragment(), "Подробнее")

        val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)
    }


}