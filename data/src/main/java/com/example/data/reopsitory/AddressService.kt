package com.example.data.reopsitory

import android.app.Application
import android.location.Address
import android.location.Geocoder
import android.location.Location
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import java.io.IOException
import java.util.*


class AddressService(){

 fun getAddress(location: Location, application: Application): Observable<List<Address>> {

     val mGeocoder = Geocoder(application, Locale.getDefault())

     return Observable.create(object : ObservableOnSubscribe<List<Address>> {
        @Throws(Exception::class)
        override fun subscribe(e: ObservableEmitter<List<Address>>) {

            var addresses: List<Address> = emptyList()

            try {
                addresses = mGeocoder.getFromLocation(
                        location.latitude,
                        location.longitude,
                        // In this sample, we get just a single address.
                        1)
                e.onNext(addresses)
            } catch (ioException: IOException) {
                // Catch network or other I/O problems.
                e.onError(ioException)
            } catch (illegalArgumentException: IllegalArgumentException) {
                // Catch invalid latitude or longitude values.
                e.onError(illegalArgumentException)
            }

        }
    })

    }
}