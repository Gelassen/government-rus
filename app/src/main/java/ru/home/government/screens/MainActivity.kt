package ru.home.government.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import ru.home.government.AppApplication
import ru.home.government.R
import ru.home.government.screens.laws.BillsViewModel
import ru.home.government.util.observeBy

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val viewModel = ViewModelProviders.of(this).get(DeputiesViewModel::class.java)
//        viewModel.init(application as AppApplication)
//        viewModel.deputiesBoundResource
//            .asLiveData()
//            .observeBy(
//                this,
//                onNext = {
//                    // TODO complete me
//                        it -> Log.d("TAG", "Data arrived: " + it)
//                },
//                onLoading = ::visibleProgress,
//                onError = ::showError
//            )
//        viewModel.fetchDeputies()

        val billsViewModel = ViewModelProviders.of(this).get(BillsViewModel::class.java)
        billsViewModel.init(application as AppApplication)
        billsViewModel.boundResource
            .asLiveData()
            .observeBy(
                this,
                onNext = {
                    // TODO complete me
                        it -> Log.d("TAG", "Data arrived: " + it)
                },
                onLoading = ::visibleProgress,
                onError = ::showError
            )
        billsViewModel.fetch()
    }

    private fun visibleProgress(show: Boolean) {
//        refreshLayout.isRefreshing = show
    }

    private fun showError(errorText: String?) {
        errorText?.let {
            Toast.makeText(this, errorText, Toast.LENGTH_LONG).show()
        }
    }
}


