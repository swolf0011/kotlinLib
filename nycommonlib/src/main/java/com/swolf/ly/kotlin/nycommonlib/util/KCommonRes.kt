package com.swolf.ly.kotlin.nycommonlib.util

import android.R

object KCommonRes {

    private var anim:       R.anim?     = null
    private var attr:       R.attr?     = null
    private var bool:       R.bool?     = null
    private var color:      R.color?    = null
    private var dimen:      R.dimen?    = null
    private var drawable:   R.drawable? = null
    private var id:         R.id?       = null
    private var integer:    R.integer?  = null
    private var layout:     R.layout?   = null
    private var mipmap:     R.mipmap?   = null
    private var string:     R.string?   = null
    private var style:      R.style?    = null

    fun getAnim(): R.anim? {
        if (anim != null) {
            try {
                val obj = R.anim::class.java.newInstance()
                anim = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return anim
    }

    fun getAttr(): R.attr? {
        if (attr != null) {
            try {
                val obj = R.attr::class.java.newInstance()
                attr = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return attr
    }

    fun getBool(): R.bool? {
        if (bool != null) {
            try {
                val obj = R.bool::class.java.newInstance()
                bool = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return bool
    }

    fun getColor(): R.color? {
        if (color != null) {
            try {
                val obj = R.color::class.java.newInstance()
                color = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return color
    }

    fun getDimen(): R.dimen? {
        if (dimen != null) {
            try {
                val obj = R.dimen::class.java.newInstance()
                dimen = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return dimen
    }

    fun getDrawable(): R.drawable? {
        if (drawable != null) {
            try {
                val obj = R.drawable::class.java.newInstance()
                drawable = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return drawable
    }

    fun getId(): R.id? {
        if (id != null) {
            try {
                val obj = R.id::class.java.newInstance()
                id = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return id
    }

    fun getInteger(): R.integer? {
        if (integer != null) {
            try {
                val obj = R.integer::class.java.newInstance()
                integer = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return integer
    }

    fun getLayout(): R.layout? {
        if (layout != null) {
            try {
                val obj = R.layout::class.java.newInstance()
                layout = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return layout
    }

    fun getMipmap(): R.mipmap? {
        if (mipmap != null) {
            try {
                val obj = R.mipmap::class.java.newInstance()
                mipmap = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return mipmap
    }

    fun getString(): R.string? {
        if (string != null) {
            try {
                val obj = R.string::class.java.newInstance()
                string = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return string
    }

    fun getStyle(): R.style? {
        if (style != null) {
            try {
                val obj = R.style::class.java.newInstance()
                style = obj
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return style
    }
}