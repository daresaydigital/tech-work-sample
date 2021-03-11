package com.midnight.weatherforecast.controller

import android.database.sqlite.SQLiteDatabase
import com.midnight.weatherforecast.core.Loader
import com.midnight.weatherforecast.models.modelsDb.DaoMaster
import com.midnight.weatherforecast.models.modelsDb.DaoSession
import com.midnight.weatherforecast.utils.AppConstant
import org.greenrobot.greendao.query.QueryBuilder

/**
 *
 */
class ControllerDB {
    private var daoSession: DaoSession? = null
    private var helper: DaoMaster.DevOpenHelper? = null
    private var db: SQLiteDatabase? = null
    private var daoMaster: DaoMaster? = null

    private object Holder { val INSTANCE=ControllerDB()}
    companion object {
        val instance: ControllerDB by lazy { Holder.INSTANCE }
    }

    init {
        helper = DaoMaster.DevOpenHelper(Loader.getInstanse().applicationContext, AppConstant.DB_NAME, null)
        db = helper!!.writableDatabase
        daoMaster = DaoMaster(db)
        daoSession = daoMaster!!.newSession()
        QueryBuilder.LOG_SQL = true
        QueryBuilder.LOG_VALUES = true
    }

    /**
     *
     */
    fun getDaoSession(): DaoSession? {
        return daoSession
    }

    /**
     *
     */
    fun getMaster(): DaoMaster{
        return this.daoMaster!!
    }

    /**
     *
     */
    fun getDb():SQLiteDatabase{
        return db!!
    }
}