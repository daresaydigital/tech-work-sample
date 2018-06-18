package com.ukhanoff.rainbeforeseven.di

import com.ukhanoff.rainbeforeseven.WeatherApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            AppModule::class
        ]
)

@ForApplication
interface AppComponent : AndroidInjector<WeatherApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: WeatherApplication): Builder

        fun build(): AppComponent

    }

}
