package ru.home.government.screens.laws.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.app.ComponentActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import ru.home.government.R
import ru.home.government.databinding.ActivityLawDetailsBinding
import ru.home.government.model.dto.Law
import ru.home.government.repository.CacheRepository
import ru.home.government.screens.BaseActivity

class DetailsActivity : BaseActivity() {

    companion object {

        const val EXTRA_LAW_CODE = "EXTRA_LAW_CODE"
        const val EXTRA_DISCUSSIONS_URL = "EXTRA_DISCUSSIONS_URL"
        const val EXTRA_DETAILS_URL = "EXTRA_DETAILS_URL"

        fun start(context: ComponentActivity, item: Law) {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_LAW_CODE, item.number)
            intent.putExtra(EXTRA_DETAILS_URL, item.url)
            intent.putExtra(EXTRA_DISCUSSIONS_URL, item.transcriptUrl)
            context.startActivity(intent)
        }
    }

    lateinit var cacheRepository: CacheRepository
    lateinit var lawCode: String
    var extras: Bundle? = null

    private lateinit var binding: ActivityLawDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLawDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dr = ColorDrawable(getApiSupportColor())
        supportActionBar!!.setBackgroundDrawable(dr)

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager

        extras = intent.extras
        lawCode = extras!!.getString(EXTRA_LAW_CODE, "")
        if (extras != null) { // cover corner case on back press scenario
            val adapter = LawDetailsPagerAdapter(supportFragmentManager)
            viewPager.adapter = adapter
            adapter.addFragment(
                LawOverviewFragment.instance(lawCode),
                getString(R.string.title_law_tab_law)
            )
            adapter.addFragment(
                LawDetailsFragment.instance(extras!!.getString(EXTRA_DETAILS_URL, "")),
                getString(R.string.title_law_tab_details)
            )
            adapter.addFragment(
                LawVotesFragment.instance(lawCode),
                getString(R.string.title_law_tab_votes)
            )

            val tabLayout = findViewById<View>(R.id.tabs) as TabLayout
            tabLayout.setupWithViewPager(viewPager)
        }

        cacheRepository = CacheRepository(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_top_law, menu)
        if (cacheRepository.isCodeInCache(lawCode)) {
            val selected = 1
            menu.findItem(R.id.itemFav).icon!!.level = selected
        } else {
            val unselected = 0
            menu.findItem(R.id.itemFav).icon!!.level = unselected
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.itemFav -> {
                val selected = 1
                val unselected = 0
                when(item.icon!!.level) {
                    selected -> {
                        item.icon!!.level = unselected
                        cacheRepository.removeLawCode(lawCode)
                    }
                    unselected -> {
                        item.icon!!.level = selected
                        cacheRepository.saveLawCode(lawCode)
                    }
                    else -> {

                    }
                }
                return true
            }
            R.id.itemTranscription -> {
                if (extras != null && extras!!.getString(EXTRA_DISCUSSIONS_URL) != null) {
                    val url = extras!!.getString(EXTRA_DISCUSSIONS_URL)
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, getString(R.string.msg_no_discussions), Toast.LENGTH_SHORT).show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}