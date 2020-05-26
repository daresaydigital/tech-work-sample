package se.bynumbers.daresayweather.util

// Convenience class that you only want to write once...
// Use with, for example:
// class HttpHelper private constructor(context: Context) {
//    init {
//        // Init using context argument
//    }
//
//    companion object : SingletonHolder<Manager, Context>(::HttpHelper)
//}
// And then
// HttpHelper.getInstance(context).doStuff()


open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A) -> T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            val i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }
}