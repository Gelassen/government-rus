package ru.home.government;

import android.app.Application;

import com.splunk.mint.Mint;

import org.jetbrains.annotations.NotNull;
import ru.home.government.di.AppComponent;
import ru.home.government.di.AppModule;
import ru.home.government.di.DaggerAppComponent;

public class AppApplication extends Application {

    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG) {
            Mint.setApplicationEnvironment(Mint.appEnvironmentStaging);
            Mint.initAndStartSession(this, "93f093e1");
        }

        component = DaggerAppComponent
                .builder()
                .appModule(new AppModule(getBaseContext()))
                .build();
    }

    @NotNull
    public AppComponent getComponent() {
        return component;
    }
}
