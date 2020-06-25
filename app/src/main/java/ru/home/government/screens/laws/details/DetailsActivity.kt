package ru.home.government.screens.laws.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import ru.home.government.R
import ru.home.government.repository.CacheRepository

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

    lateinit var cacheRepository: CacheRepository
    lateinit var lawCode: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_law_details)

        setSupportActionBar(toolbar)

        val dr = ColorDrawable(resources.getColor(R.color.colorActionBar))
        supportActionBar!!.setBackgroundDrawable(dr)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        val extras = intent.extras
        lawCode = extras!!.getString(EXTRA_LAW_CODE, "")
        if (extras != null) { // cover corner case on back press scenario
            val adapter = LawDetailsPagerAdapter(supportFragmentManager)
            viewPager.adapter = adapter
            adapter.addFragment(
                LawOverviewFragment.instance(lawCode),
                "Законопроект"
            )
            adapter.addFragment(
                LawDetailsFragment.instance(extras.getString(EXTRA_DETAILS_URL, "")),
                "Подробнее"
            )

            val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
            tabLayout!!.setupWithViewPager(viewPager)
        }

        cacheRepository =
            CacheRepository(this)//CacheRepository(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_top_law, menu)
        if (cacheRepository.isCodeInCache(lawCode)) {
            val selected = 1;
            menu!!.findItem(R.id.itemFav).icon.level = selected
        } else {
            val unselected = 0
            menu!!.findItem(R.id.itemFav).icon.level = unselected
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemFav -> {
                val selected = 1;
                val unselected = 0
                when(item.icon.level) {
                    selected -> {
                        item.icon.level = unselected
                        cacheRepository.removeLawCode(lawCode)
                    }
                    unselected -> {
                        item.icon.level = selected
                        cacheRepository.saveLawCode(lawCode)
                    }
                    else -> {

                    }
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}