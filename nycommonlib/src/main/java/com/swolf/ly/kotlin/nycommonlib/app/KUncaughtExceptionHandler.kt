package com.swolf.ly.kotlin.nycommonlib.app

import android.content.Context
import android.os.Environment
import com.swolf.ly.kotlin.nycommonlib.util.KFileUtil
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*

class KUncaughtExceptionHandler(context: Context) : Thread.UncaughtExceptionHandler {
    var mHandler: Thread.UncaughtExceptionHandler

    init {
        mHandler = Thread.getDefaultUncaughtExceptionHandler()
        Environment.getExternalStorageState()
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        //可以上传错误日志
        if (!handleException(throwable) && mHandler != null) {
            mHandler.uncaughtException(thread, throwable)
        } else {
            //系统退出，kill进程
            android.os.Process.killProcess(android.os.Process.myPid())
            System.exit(1)
        }
    }

    private fun handleException(throwable: Throwable):Boolean {
        if(throwable==null){
            return false
        }
        saveErrorInfo2File(throwable)
        return true
    }
    private fun saveErrorInfo2File(throwable: Throwable){
        var sb = StringBuffer()
        var writer =StringWriter()
        var printWriter = PrintWriter(writer)
        throwable.printStackTrace(printWriter)
        var cause = throwable.cause;
        while(cause!=null){
            cause.printStackTrace(printWriter)
            cause = cause.cause;
        }
        var result = writer.toString()
        sb.append(result)
        var time = format.format(Date())
        var fileName = KFileUtil.rootDirectory.absolutePath+File.separator+"kotlin_error"+ File.separator + "error-" + time + ".txt";
        val fileUtil = KFileUtil.instance
        fileUtil.createFile(fileName)
        fileUtil.write(File(fileName),sb.toString())
        printWriter.close()
        writer.close()
    }
    var format = SimpleDateFormat("yyyy-MM-dd HH_mm_ss")

    companion object {
        private var uncaughtExceptionHandler: KUncaughtExceptionHandler? = null;
        fun getInstance(context: Context): KUncaughtExceptionHandler? {
            if (uncaughtExceptionHandler == null) {
                synchronized(KUncaughtExceptionHandler) {
                    if (uncaughtExceptionHandler == null) {
                        uncaughtExceptionHandler = KUncaughtExceptionHandler(context)
                    }
                }
            }
            return uncaughtExceptionHandler
        }
    }


}