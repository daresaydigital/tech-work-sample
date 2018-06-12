import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

class FragmentUtils(private val fragmentManager: android.support.v4.app.FragmentManager) {
    private lateinit var fragmentTransaction: FragmentTransaction

    fun beginTransaction(): FragmentUtils {
        fragmentTransaction = fragmentManager.beginTransaction()
        return this
    }

    /**
     * Add a given fragment to the given container.
     * @param containerId of fragment
     * *
     * @param fragment we are interested.
     * *
     * @return this class
     */
    fun add(containerId: Int, fragment: Fragment): FragmentUtils {
        fragmentTransaction.add(containerId, fragment)
        return this
    }

    /**
     * Add a given fragment to the given container.
     * @param containerId of fragment
     * *
     * @param fragment we are interested.
     * *
     * @return this class
     */
    fun replace(containerId: Int, fragment: Fragment): FragmentUtils {
        fragmentTransaction.replace(containerId, fragment)
        return this
    }

    /**
     *
     * Same as replace method but has a replace with tag option.
     */
    fun replaceWithTag(containerId: Int, fragment: Fragment, tag: String): FragmentUtils {
        fragmentTransaction.replace(containerId, fragment, tag)
        return this
    }


    /**
     * Commit a fragment transaction.
     * @return
     */
    fun commit(): Int {
        return this.fragmentTransaction.commit()
    }

    /**
     * Set custom transition for fragment.
     * @param start transition resource id.
     * *
     * @param end transition resource id.
     */
    fun setTransition(start: Int, end: Int): FragmentUtils {
        fragmentTransaction.setCustomAnimations(start, end)
        return this
    }

    /**

     * @param add
     */
    fun addToBackStack(add: Boolean, tag: String = "TAG"): FragmentUtils {
        if (add) fragmentTransaction.addToBackStack(tag)
        return this

    }
}
