package com.ukhanoff.rainbeforeseven.di

import com.ukhanoff.rainbeforeseven.WeatherApplication
import com.ukhanoff.rainbeforeseven.di.data.DataModule
import com.ukhanoff.rainbeforeseven.di.ui.ActivityBuilderModule
import com.ukhanoff.rainbeforeseven.di.ui.ViewModelModule
import com.ukhanoff.rainbeforeseven.di.util.ForApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    DataModule::class,
    ViewModelModule::class,
    ActivityBuilderModule::class])
@ForApplication
interface AppComponent : AndroidInjector<WeatherApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: WeatherApplication): Builder

        fun build(): AppComponent

    }

}
