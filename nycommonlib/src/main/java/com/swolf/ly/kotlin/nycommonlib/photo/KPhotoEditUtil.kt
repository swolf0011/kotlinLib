package com.swolf.ly.kotlin.nycommonlib.photo

import android.graphics.*
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream
import java.util.ArrayList

class KPhotoEditUtil {

    /**
     * 圆形 Bitmap
     */
    fun roundBitmap(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height
        val roundPx: Float
        val left: Float
        val top = 0f
        val right: Float
        val bottom: Float
        val dst_left = 0f
        val dst_top = 0f
        val dst_right: Float
        val dst_bottom: Float
        if (width <= height) {
            roundPx = (width / 2).toFloat()
            bottom = width.toFloat()
            left = 0f
            right = width.toFloat()
            height = width
            dst_right = width.toFloat()
            dst_bottom = width.toFloat()
        } else {
            roundPx = (height / 2).toFloat()
            val clip = ((width - height) / 2).toFloat()
            left = clip
            right = width - clip
            bottom = height.toFloat()
            width = height
            dst_right = height.toFloat()
            dst_bottom = height.toFloat()
        }

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val color = -0xbdbdbe
        val paint = Paint()
        val src = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        val dst = Rect(dst_left.toInt(), dst_top.toInt(), dst_right.toInt(), dst_bottom.toInt())
        val rectF = RectF(dst)

        paint.isAntiAlias = true

        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, src, dst, paint)
        return output
    }


    /**
     * 旋转照片
     */
    fun rotate(img: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        var mImg = Bitmap.createBitmap(img, 0, 0, img.width, img.height, matrix, true)
        return mImg
    }

    /**
     * 获得带倒影的图片方法
     *
     * @param bitmap
     * @return
     */
    fun createReflectionImageWithOrigin(bitmap: Bitmap): Bitmap {
        // 图片与倒影间隔距离
        val reflectionGap = 4
        // 图片的宽度
        val width = bitmap.width
        // 图片的高度
        val height = bitmap.height

        val matrix = Matrix()
        // 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
        matrix.preScale(1f, -1f)
        // 创建反转后的图片Bitmap对象，图片高是原图的一半。
        val reflectionImage = Bitmap.createBitmap(
            bitmap,
            0, height / 2, width, height / 2, matrix, false
        )
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。 可以理解为这张图将会在屏幕上显示 是原图和倒影的合体
        val bitmapWithReflection = Bitmap.createBitmap(width, height + height / 2, Bitmap.Config.ARGB_8888)
        // 构造函数传入Bitmap对象，为了在图片上画图
        val canvas = Canvas(bitmapWithReflection)
        // 画原始图片
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        // 画间隔矩形
        val deafalutPaint = Paint()
        canvas.drawRect(
            0f, height.toFloat(), width.toFloat(), (height + reflectionGap).toFloat(),
            deafalutPaint
        )
        // 画倒影图片
        canvas.drawBitmap(reflectionImage, 0f, (height + reflectionGap).toFloat(), null)
        // 实现倒影渐变效果
        val paint = Paint()
        val shader = LinearGradient(
            0f,
            bitmap.height.toFloat(),
            0f,
            (bitmapWithReflection.height + reflectionGap).toFloat(),
            0x70ffffff,
            0x00ffffff,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader

        // Set the Transfer mode to be porter duff and destination in
        // 覆盖效果
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(
            0f,
            height.toFloat(),
            width.toFloat(),
            (bitmapWithReflection.height + reflectionGap).toFloat(),
            paint
        )

        return bitmapWithReflection
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    fun zoomBitmap(bitmap: Bitmap, w: Int, h: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        val matrix = Matrix()
        val scaleWidht = w.toFloat() / width
        val scaleHeight = h.toFloat() / height
        matrix.postScale(scaleWidht, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }


    /**
     * 合成图片
     *
     * @param src              原图
     * @param watermark        水印图
     * @param xWatermarkOffset x轴水印图偏移
     * @param yWatermarkOffset y轴水印图偏移
     * @return
     */
    fun synthesisBitmap(src: Bitmap?, watermark: Bitmap, xWatermarkOffset: Int, yWatermarkOffset: Int): Bitmap? {
        var xWatermarkOffset = xWatermarkOffset
        var yWatermarkOffset = yWatermarkOffset
        if (src == null) {
            return null
        }
        val w = src.width
        val h = src.height
        val ww = watermark.width
        val wh = watermark.height

        if (xWatermarkOffset < 0) {
            xWatermarkOffset = 0
        }
        if (yWatermarkOffset < 0) {
            yWatermarkOffset = 0
        }

        //create the new blank bitmap
        val newb = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444)//创建一个新的和SRC长度宽度一样的位图
        val cv = Canvas(newb)
        //draw src into
        cv.drawBitmap(src, 0f, 0f, null)//在 0，0坐标开始画入src
        //draw watermark into
        cv.drawBitmap(watermark, xWatermarkOffset.toFloat(), yWatermarkOffset.toFloat(), null)//在src的右下角画入水印
        //save all clip
        cv.save()//保存
        //store
        cv.restore()//存储
        return newb
    }

    /**
     * 图片透明度处理
     *
     * @param sourceImg 原始图片
     * @param number    透明度
     * @return
     */
    fun setAlpha(sourceImg: Bitmap, number: Int): Bitmap {
        var sourceImg = sourceImg
        var number = number
        val argb = IntArray(sourceImg.width * sourceImg.height)
        sourceImg.getPixels(argb, 0, sourceImg.width, 0, 0, sourceImg.width, sourceImg.height)// 获得图片的ARGB值
        number = number * 255 / 100
        for (i in argb.indices) {
            argb[i] = number shl 24 or (argb[i] and 0x00FFFFFF)// 修改最高2位的值
        }
        sourceImg = Bitmap.createBitmap(argb, sourceImg.width, sourceImg.height, Bitmap.Config.ARGB_8888)
        return sourceImg
    }

    /**
     * 图标加灰色过滤；
     *
     * @param mDrawable
     * @return
     */
    fun setGray(mDrawable: Drawable): Drawable {
        mDrawable.mutate()
        val cm = ColorMatrix()
        cm.setSaturation(0f)
        val cf = ColorMatrixColorFilter(cm)
        mDrawable.colorFilter = cf
        return mDrawable
    }

    /***
     * 图片平均分割方法，将大图平均分割为N行N列，方便用户使用
     *
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param imgBit
     * ：图片
     * @param x
     * ：X轴起点坐标
     * @param y
     * ：Y轴起点坐标
     * @param w
     * ：单一图片的宽度
     * @param h
     * ：单一图片的高度
     * @param line
     * ：第几列
     * @param row
     * ：第几行
     */
    fun cuteImage(
        g: Canvas, paint: Paint, imgBit: Bitmap, x: Int,
        y: Int, w: Int, h: Int, line: Int, row: Int
    ) {
        g.clipRect(x, y, x + w, h + y)
        g.drawBitmap(imgBit, (x - line * w).toFloat(), (y - row * h).toFloat(), paint)
        g.restore()
    }

    /***
     * 绘制带有边框的文字
     *
     * @param strMsg
     * ：绘制内容
     * @param g
     * ：画布
     * @param paint
     * ：画笔
     * @param setx
     * ：：X轴起始坐标
     * @param sety
     * ：Y轴的起始坐标
     * @param fg
     * ：前景色
     * @param bg
     * ：背景色
     */
    fun drawText(
        strMsg: String, g: Canvas, paint: Paint, setx: Int,
        sety: Int, fg: Int, bg: Int
    ) {
        paint.color = bg
        g.drawText(strMsg, (setx + 1).toFloat(), sety.toFloat(), paint)
        g.drawText(strMsg, setx.toFloat(), (sety - 1).toFloat(), paint)
        g.drawText(strMsg, setx.toFloat(), (sety + 1).toFloat(), paint)
        g.drawText(strMsg, (setx - 1).toFloat(), sety.toFloat(), paint)
        paint.color = fg
        g.drawText(strMsg, setx.toFloat(), sety.toFloat(), paint)
        g.restore()
    }

    /**
     * 将彩色图转换为灰度图
     *
     * @param img 位图
     * @return 返回转换好的位图
     */
    fun convertGreyImg(img: Bitmap): Bitmap {
        val width = img.width         //获取位图的宽
        val height = img.height       //获取位图的高

        val pixels = IntArray(width * height) //通过位图的大小创建像素点数组

        img.getPixels(pixels, 0, width, 0, 0, width, height)
        val alpha = 0xFF shl 24
        for (i in 0 until height) {
            for (j in 0 until width) {
                var grey = pixels[width * i + j]

                val red = grey and 0x00FF0000 shr 16
                val green = grey and 0x0000FF00 shr 8
                val blue = grey and 0x000000FF

                grey = (red.toFloat() * 0.3 + green.toFloat() * 0.59 + blue.toFloat() * 0.11).toInt()
                grey = alpha or (grey shl 16) or (grey shl 8) or grey
                pixels[width * i + j] = grey
            }
        }
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        result.setPixels(pixels, 0, width, 0, 0, width, height)
        return result
    }

    /**
     * 将一个图片切割成多个图片
     *
     * @param bitmap
     * @param xPiece
     * @param yPiece
     * @return
     */
    fun split(bitmap: Bitmap, xPiece: Int, yPiece: Int): List<NYImagePiece> {
        val pieces = ArrayList<NYImagePiece>(xPiece * yPiece)
        val width = bitmap.width
        val height = bitmap.height
        val pieceWidth = width / 3
        val pieceHeight = height / 3
        for (i in 0 until yPiece) {
            for (j in 0 until xPiece) {
                val piece = NYImagePiece()
                piece.index = j + i * xPiece
                val xValue = j * pieceWidth
                val yValue = i * pieceHeight
                piece.bitmap = Bitmap.createBitmap(
                    bitmap, xValue, yValue,
                    pieceWidth, pieceHeight
                )
                pieces.add(piece)
            }
        }
        return pieces
    }

    /**
     * 重新编码Bitmap
     *
     * @param src     需要重新编码的Bitmap
     * @param format  编码后的格式（目前只支持png和jpeg这两种格式）
     * @param quality 重新生成后的bitmap的质量
     * @return 返回重新生成后的bitmap
     */
    fun codec(
        src: Bitmap, format: Bitmap.CompressFormat,
        quality: Int
    ): Bitmap {
        val os = ByteArrayOutputStream()
        src.compress(format, quality, os)
        val array = os.toByteArray()
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }


    class NYImagePiece {
        var index = 0
        var bitmap: Bitmap? = null
    }
}
