package ru.home.government.screens.laws.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.home.government.R
import ru.home.government.databinding.ActivityLawDeputiesBinding
import ru.home.government.model.Deputy
import ru.home.government.screens.BaseActivity
import ru.home.government.screens.deputies.DeputiesFragment

class DeputiesOnLawActivity: BaseActivity() {

    companion object {

        private const val EXTRA_DEPUTIES = "EXTRA_DEPUTIES"

        fun launch(activity: AppCompatActivity, deputies: ArrayList<Deputy>) {
            val intent = Intent(activity, DeputiesOnLawActivity::class.java)
            intent.putParcelableArrayListExtra(EXTRA_DEPUTIES, deputies)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityLawDeputiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLawDeputiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val dr = ColorDrawable(getApiSupportColor())
        supportActionBar!!.setBackgroundDrawable(dr)

        val deputies: java.util.ArrayList<Deputy> = intent.getParcelableArrayListExtra(EXTRA_DEPUTIES)!!

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_deputies, DeputiesFragment.instance(deputies))
            .commit()
    }
}