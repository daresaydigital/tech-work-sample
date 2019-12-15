package com.russellmorris.getlocation

import com.russellmorris.getlocation.ui.viewmodel.GetLocationViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule
//        useCaseModule,
//        repositoryModule,
//        dataSourceModule,
//        fragmentSourceModule
//        networkModule
    )
}

val viewModelModule: Module = module {
    viewModel { GetLocationViewModel() }
}
//
//val useCaseModule: Module = module {
//    factory { LaunchUseCase(launchRepository = get()) }
//}
//
//val repositoryModule: Module = module {
//    single { LaunchRepositoryImpl(launchDataSource = get()) as LaunchRepository }
//}
//
//val dataSourceModule: Module = module {
//    single { LaunchApiService(api = launchApi) as LaunchDataSource }
//}

//val fragmentSourceModule: Module = module {
//    single { GetLocationFragment }
//}

//val networkModule: Module = module {
//    single { launchApi }
//}
//
//private const val BASE_URL = "https://api.spacexdata.com/v3/"

//private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)
//
//private val launchApi: LaunchApi = retrofit.create(LaunchApi::class.java)