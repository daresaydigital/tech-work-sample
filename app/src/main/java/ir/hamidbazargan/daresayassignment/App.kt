package ir.hamidbazargan.daresayassignment

import android.app.Application
import ir.hamidbazargan.daresayassignment.data.di.apiModule
import ir.hamidbazargan.daresayassignment.data.di.dataBaseModule
import ir.hamidbazargan.daresayassignment.data.di.repositoryModule
import ir.hamidbazargan.daresayassignment.domain.di.useCasesModule
import ir.hamidbazargan.daresayassignment.presentation.di.coroutineDispatcherModule
import ir.hamidbazargan.daresayassignment.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                apiModule,
                dataBaseModule,
                repositoryModule,
                useCasesModule,
                coroutineDispatcherModule,
                viewModelModule
            )
        }
    }
}