package com.swolf.ly.kotlin.nycommonlib.util

import java.util.*

class KRandomNumberUtil private constructor() {

    private val allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
    private val intactChar = "0123456789abcdefghijklmnopqrstuvwxyz"
    private val letterChar = "abcdefghijklmnopqrstuvwxyz"
    private val numberChar = "0123456789"
    private val hexChar = "0123456789ABCDEF"


    companion object {
        private object KSubHolder {
            val util = KRandomNumberUtil()
        }

        val instance: KRandomNumberUtil
            get() = KSubHolder.util
    }

    /**
     * i~j random int
     */
    fun randomInt(i: Int, j: Int): Int {
        return (Math.random() * (1 + j - i)).toInt() + i
    }

    /**
     * 0~i random int
     */
    fun randomInt(i: Int): Int {
        return (Math.random() * (i + 1)).toInt()
    }

    /**
     * 0~1 random double
     */
    fun randomDouble(): Double {
        return Math.random()
    }

    /**
     * UUID random String :5d981b3c-82df-4d81-b799-e3fcee051424
     */
    fun randomUUID2String(): String {
        return UUID.randomUUID().toString()
    }

    /**
     * UUID random long
     */
    fun randomUUID2Long(): Long {
        return UUID.randomUUID().timestamp()
    }

    /**
     * 随机生成字符串, 大小写数字混合
     *
     * @param length 生成的长度
     */
    fun randomAllChar(length: Int): String {
        val strbuf = StringBuffer()
        val random = Random()
        for (i in 0 until length) {
            strbuf.append(allChar[random.nextInt(allChar.length)])
        }
        return strbuf.toString()
    }

    /**
     * 随机生成字符串, 小写数字混合
     *
     * @param length 生成的长度
     */
    fun randomIntactChar(length: Int): String {
        val strbuf = StringBuffer()
        val random = Random()
        for (i in 0 until length) {
            strbuf.append(intactChar[random.nextInt(intactChar.length)])
        }
        return strbuf.toString()
    }

    /**
     * 随机生成字符串, 小写
     *
     * @param length 生成的长度
     */
    fun randomLetterChar(length: Int): String {
        val strbuf = StringBuffer()
        val random = Random()
        for (i in 0 until length) {
            strbuf.append(letterChar[random.nextInt(letterChar.length)])
        }
        return strbuf.toString()
    }

    /**
     * 随机生成字符串, 数字
     *
     * @param length 生成的长度
     */
    fun randomNumberChar(length: Int): String {
        val strbuf = StringBuffer()
        val random = Random()
        for (i in 0 until length) {
            strbuf.append(numberChar[random.nextInt(numberChar.length)])
        }
        return strbuf.toString()
    }

    /**
     * 随机生成字符串, HEX, 十六进制数字和字母
     *
     * @param length 生成的长度
     */
    fun randomHexChar(length: Int): String {
        val strbuf = StringBuffer()
        val random = Random()
        for (i in 0 until length) {
            strbuf.append(hexChar[random.nextInt(hexChar.length)])
        }
        return strbuf.toString()
    }


}