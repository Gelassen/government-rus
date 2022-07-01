package ru.home.government.di.modules;


import android.annotation.SuppressLint;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.home.government.di.ViewModelFactory;
import ru.home.government.screens.deputies.DeputiesViewModel;
import ru.home.government.screens.laws.BillsViewModel;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory viewModelFactory);
    //You are able to declare ViewModelProvider.Factory dependency in another module. For example in ApplicationModule.

    @SuppressLint("UnsafeOptInUsageWarning")
    @Binds
    @IntoMap
    @ViewModelKey(DeputiesViewModel.class)
    abstract ViewModel deputiesViewModel(DeputiesViewModel vm);

    @Binds
    @IntoMap
    @ViewModelKey(BillsViewModel.class)
    abstract ViewModel billsViewModel(BillsViewModel vm);

    //Others ViewModels
}

