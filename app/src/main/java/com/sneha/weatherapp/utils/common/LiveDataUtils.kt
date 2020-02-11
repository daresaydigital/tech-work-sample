package com.sneha.weatherapp.utils.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData


/**
 * This function creates a [LiveData] of a [Pair] of the two types provided. The resulting LiveData is updated whenever either input LiveData updates and both LiveData have updated at least once before.
 *
 * If the combination of A and B is C, and A and B are updated in this pattern: `AABA`, C would be updated twice (once with the second A value and first B value, and once with the third A value and first B value).
 *
 * @param a the first LiveData
 * @param b the second LiveData
 * @author Mitchell Skaggs https://gist.github.com/magneticflux-/044c9d7a3cea431aa0e4f4f4950a2898
 */
fun <A, B> combineLatest(a: LiveData<A>, b: LiveData<B>): MutableLiveData<Pair<A, B>> {
    return MediatorLiveData<Pair<A, B>>().apply {
        var lastA: A? = null
        var lastB: B? = null

        fun update() {
            val localLastA = lastA
            val localLastB = lastB
            if (localLastA != null && localLastB != null)
                this.value = Pair(localLastA, localLastB)
        }

        addSource(a) {
            lastA = it
            update()
        }
        addSource(b) {
            lastB = it
            update()
        }
    }
}

/**
 * This is merely an extension function for [combineLatest].
 *
 * @see combineLatest
 * @author Mitchell Skaggs
 */
fun <A, B> LiveData<A>.combineLatestWith(b: LiveData<B>): LiveData<Pair<A, B>> = combineLatest(this, b)