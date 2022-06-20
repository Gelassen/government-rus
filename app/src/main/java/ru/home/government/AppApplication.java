package ru.home.government;

import android.app.Application;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.CombinedLoadStates;
import androidx.paging.LoadState;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.splunk.mint.Mint;

import org.jetbrains.annotations.NotNull;

import ru.home.government.di.AppComponent;
import ru.home.government.di.modules.AppModule;
import ru.home.government.di.DaggerAppComponent;
import ru.home.government.di.modules.CustomNetworkModule;
import ru.home.government.di.modules.NetworkModule;
import ru.home.government.di.modules.RepositoryModule;

public class AppApplication extends Application {

    protected AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
            Mint.initAndStartSession(this, "93f093e1");
        }

        initializeComponent();
    }

    public void initializeComponent() {
        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getBaseContext()))
                .customNetworkModule(new CustomNetworkModule(getBaseContext()))
                .repositoryModule(new RepositoryModule(this))
                .build();
    }

    @NotNull
    public AppComponent getComponent() {
        return component;
    }

    public void setComponent(AppComponent component) {
        this.component = component;
    }
}
