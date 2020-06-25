package ru.home.government.screens.laws.details

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginBottom
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_deputies.*
import ru.home.government.R
import ru.home.government.model.Deputy
import ru.home.government.screens.deputies.DeputiesFragment

class DeputiesOnLawActivity: AppCompatActivity() {

    companion object {

        private const val EXTRA_DEPUTIES = "EXTRA_DEPUTIES"

        fun launch(activity: AppCompatActivity, deputies: ArrayList<Deputy>) {
            val intent = Intent(activity, DeputiesOnLawActivity::class.java)
            intent.putParcelableArrayListExtra(EXTRA_DEPUTIES, deputies)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_law_deputies)

        setSupportActionBar(toolbar)

        val dr = ColorDrawable(resources.getColor(R.color.colorActionBar))
        supportActionBar!!.setBackgroundDrawable(dr)

        val deputies: ArrayList<Deputy> = intent.getParcelableArrayListExtra(EXTRA_DEPUTIES)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DeputiesFragment.instance(deputies))
            .commit()
    }
}