package ru.home.government.screens.laws.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import ru.home.government.R
import ru.home.government.databinding.ActivityLawDeputiesBinding
import ru.home.government.model.dto.Deputy
import ru.home.government.screens.BaseActivity
import ru.home.government.screens.deputies.DeputiesFragment

/**
 * Показывает список депутатов, связанных с законопроектом.
 * Использует FragmentContainerView + commit вместо устаревшего
 * supportFragmentManager.beginTransaction().replace().
 */
class DeputiesOnLawActivity : BaseActivity() {

    companion object {
        private const val EXTRA_DEPUTIES = "EXTRA_DEPUTIES"

        fun launch(activity: AppCompatActivity, deputies: ArrayList<Deputy>) {
            val intent = Intent(activity, DeputiesOnLawActivity::class.java)
            intent.putParcelableArrayListExtra(EXTRA_DEPUTIES, deputies)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityLawDeputiesBinding

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLawDeputiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(getApiSupportColor()))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val deputies: ArrayList<Deputy> =
                intent.getParcelableArrayListExtra(EXTRA_DEPUTIES) ?: arrayListOf()

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.container_deputies, DeputiesFragment.instance(deputies))
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
