package ir.sass.base_domain.model

/*we are having clean mvvm, so we have a repository which send data into viewModel
this is a wrapper class for holding data, if we are on loading stage the repository
will send Domain.Progress, after that repository will send Domain.Data, the data is
a Result class so it can be a success request or failure */

sealed class Domain<T> {
    class Progress<T>(val message : String = "Loading") : Domain<T>()
    class Data<T>(val data : kotlin.Result<T>) : Domain<T>()
}