package com.swolf.ly.kotlin.nycommonlib.util

import android.content.Context
import android.content.SharedPreferences

class KSharedPreferencesUtil {


    private var sp: SharedPreferences? = null

    private val fileName = "sharePreferences_data"

    private constructor(context: Context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }
    }
    companion object {
        private var instance: KSharedPreferencesUtil? = null
        fun getInstance(context: Context): KSharedPreferencesUtil?{
            if (instance == null) {
                synchronized(KSharedPreferencesUtil::class.java) {
                    if (instance == null) {
                        instance = KSharedPreferencesUtil(context)
                    }
                }
            }
            return instance
        }
    }



    fun remove(vararg ks: String): Boolean {
        if (ks != null) {
            val edit = sp!!.edit()
            for (i in ks.indices) {
                edit.remove(ks[i])
            }
            return edit.commit()
        }
        return false
    }

    fun clear(): Boolean {
        return sp!!.edit().clear().commit()
    }


    /**
     * @param resultClass resultClass ：Integer|Boolean|Float|Long|String class
     * @return
     */
    operator fun get(k: String, resultClass: Class<*>): Any? {
        if (resultClass == Int::class.java) {
            return sp!!.getInt(k, 0)
        } else if (resultClass == Boolean::class.java) {
            return sp!!.getBoolean(k, false)
        } else if (resultClass == Float::class.java) {
            return sp!!.getFloat(k, 0f)
        } else if (resultClass == Long::class.java) {
            return sp!!.getLong(k, 0L)
        } else if (resultClass == String::class.java) {
            return sp!!.getString(k, "")
        }
        return null
    }

    /**
     * @param o       o ：Integer|Boolean|Float|Long|String
     * @return
     */
    fun put(k: String, o: Any): Boolean {
        if (o is Int) {
            val v = Integer.parseInt(o.toString())
            return sp!!.edit().putInt(k, v).commit()
        } else if (o is Boolean) {
            val v = java.lang.Boolean.parseBoolean(o.toString())
            return sp!!.edit().putBoolean(k, v).commit()
        } else if (o is Float) {
            val v = java.lang.Float.parseFloat(o.toString())
            return sp!!.edit().putFloat(k, v).commit()
        } else if (o is Long) {
            val v = java.lang.Long.parseLong(o.toString())
            return sp!!.edit().putLong(k, v).commit()
        } else if (o is String) {
            val v = o.toString()
            return sp!!.edit().putString(k, v).commit()
        }
        return false
    }
}