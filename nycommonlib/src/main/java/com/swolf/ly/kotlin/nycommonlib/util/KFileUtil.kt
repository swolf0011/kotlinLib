package com.swolf.ly.kotlin.nycommonlib.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.*

class KFileUtil private constructor() {
    companion object {
        var dataDirectory = Environment.getDataDirectory()
        var downloadCacheDirectory = Environment.getDownloadCacheDirectory()
        var rootDirectory = Environment.getRootDirectory()

        private object KSubHolder {
            val util = KFileUtil()
        }

        val instance: KFileUtil
            get() = KSubHolder.util
    }

    fun getFile(path: String): File {
        return File(path)
    }

    fun delFile(path: String): Boolean {
        var file = File(path)
        if (file.isDirectory) {
            var list = file.listFiles()
            if (list.size > 0) {
                for (item in list) {
                    delFile(item.absolutePath)
                }
            }
        }
        return file.delete()
    }

    fun createFile(path: String): Boolean {
        var file = File(path)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (file.exists()) {
            return false
        }
        return file.createNewFile()
    }

    fun readStr(file: File): String {
        var inputStream: FileInputStream = FileInputStream(file)
        return readStr(inputStream)
    }


    fun readStr(inputStream: InputStream): String {
        var sb = StringBuffer()
        if (inputStream != null) {
            var bytes = ByteArray(4 * 1024)
            var len = -1
            try {
                while ({ len = inputStream.read(bytes);len }() != -1) {
                    sb.append(String(bytes, 0, len))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                closeInputStream(inputStream)
            }
        }
        return sb.toString()
    }


    fun closeInputStream(input: InputStream): Unit = try {
        if (input == null) {
        } else {
            input.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    fun closeOutputStream(output: OutputStream): Unit = try {
        if (output == null) {
        } else {
            output.close()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    /**
     * @param file
     * @param inSampleSize inSampleSize = 4;//Bitmap is 1/4 size
     * @return
     */
    fun readBitmap(file: File, inSampleSize: Int = 1): Bitmap? {
        var bitmap: Bitmap? = null
        if (file.exists()) {
            var options = BitmapFactory.Options()
            if (inSampleSize > 0) {
                options.inSampleSize = inSampleSize
                bitmap = BitmapFactory.decodeFile(file.absolutePath, options)
            } else {
                bitmap = BitmapFactory.decodeFile(file.absolutePath)
            }
        }
        return bitmap
    }

    fun readBytes(file: File): ByteArray {
        var len = file.length().toInt()
        var bytes = ByteArray(len)
        var inputStream: FileInputStream = FileInputStream(file)
        if (inputStream != null) {
            inputStream.read(bytes)
            closeInputStream(inputStream)
        }
        return bytes
    }

    fun readBytes(file: File, start: Long = 0, len: Int = 0): ByteArray {
        var bytes = ByteArray(len)
        var inputStream: RandomAccessFile = RandomAccessFile(file, "rw")
        if (inputStream != null) {
            inputStream.seek(start)
            inputStream.read(bytes)
            inputStream.close()
        }
        return bytes
    }

    fun write(file: File, bytes: ByteArray, isSuperaddition: Boolean = false): Boolean {
        var b = false
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file.exists() && bytes != null && bytes.size > 0) {
            var outputStream = FileOutputStream(file, isSuperaddition)
            outputStream.write(bytes)
            outputStream.flush()
            closeOutputStream(outputStream)
            b = true
        }
        return b
    }

    fun write(file: File, str: String, isSuperaddition: Boolean = false): Boolean {
        return write(file, str.toByteArray(), isSuperaddition);
    }

    fun write(file: File, inputStream: InputStream, isSuperaddition: Boolean = false): Boolean {
        var b = false
        if (!file.exists()) {
            file.createNewFile();
        }
        if (file.exists() && inputStream != null) {
            var outputStream = FileOutputStream(file, isSuperaddition)

            var bytes = ByteArray(4 * 1024)
            var len = -1
            try {
                while ({ len = inputStream.read(bytes);len }() != -1) {
                    outputStream.write(bytes, 0, len)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                closeInputStream(inputStream)
            }
            outputStream.flush()
            closeOutputStream(outputStream)
            b = true
        }
        return b
    }


}