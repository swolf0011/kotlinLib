package com.swolf.ly.kotlin.nycommonlib.photo.popupWindow

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import com.swolf.ly.kotlin.nycommonlib.R
import com.swolf.ly.kotlin.nycommonlib.dialogPopup.popupWindow.KPopupWindowUtil
import com.swolf.ly.kotlin.nycommonlib.photo.KSavePhotoUtil
import java.io.File

object KPickPhotoPopupWindow {


    /**
     *
     * 注册 存储照的容器
     *
     * name:属性值，固定写法
     * authorities:组件标识，按照江湖规矩,都以包名开头,避免和其它应用发生冲突。和FileProvider.getUriForFile()方法的第二个参数一致
     * exported:要求必须为false，为true则会报安全异常。
     * grantUriPermissions:true，表示授予 URI 临时访问权限。
     *
     * <provider
     * android:name="androidx.core.content.FileProvider"
     * android:authorities="{项目包名}.fileprovider"
     * android:exported="false"
     * android:grantUriPermissions="true">
     *
     * <!--指定Uri的共享路径-->
     * <meta-data
     * android:name="android.support.FILE_PROVIDER_PATHS"
     * android:resource="@xml/file_paths" />
     * </provider>
     *
     * @param authority "{项目包名}.fileprovider";
     */

    /**
     * 相册
     */
    val REQUEST_CODE_PICKPHOTOFROMGALLERY = 2000
    /**
     * 拍照
     */
    val REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO = 2001
    /**
     * 裁剪
     */
    val REQUEST_CODE_PHOTOZOOMCLIP = 2002
    /**
     * 当前的文件
     */
    var currentFile: File? = null

    fun newFileInfo(ext: String?) {
        val cameraDir =
            File(Environment.getExternalStorageDirectory().toString() + File.separator + "cameraDir" + File.separator)
        /** 使用照相机拍摄照片作为头像时会使用到这个路径  */
        if (!cameraDir.exists()) {
            cameraDir.mkdirs()
        }
        val sb = StringBuffer()
        if (ext != null && ext.length > 0) {
            sb.append(ext)
            sb.append("_")
        }
        sb.append("IMG_")
        sb.append(System.currentTimeMillis())
        sb.append(".jpg")
        val fileName = sb.toString()
        currentFile = File(cameraDir, fileName)
    }

    /**
     * 显示拍照popupWindow
     */
    fun showTakephotoPopupWindow(activity: Activity, clickParentView: View) {
        val pop = KPopupWindowUtil()
        val view = LayoutInflater.from(activity).inflate(R.layout.ny_popupwindow_takephoto, null)
        val textView2 = view.findViewById(R.id.textView_photo_2) as TextView
        textView2.setOnClickListener {
            pop.dismiss()
            newFileInfo("")
            pickPhotoFromTakePhoto(activity, currentFile, REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO)
        }
        pop.showScreenBottom(
            clickParentView,
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            0,
            null
        )
    }

    /**
     * 显示相册与拍照Cancel popupWindow
     */
    fun showTakephotoGalleryPhotoPopupWindow(activity: Activity, clickParentView: View) {
        val pop = KPopupWindowUtil()
        val view = LayoutInflater.from(activity).inflate(R.layout.ny_popupwindow_takephoto_gallery, null)
        val textView1 = view.findViewById(R.id.textView_photo_1) as TextView
        val textView2 = view.findViewById(R.id.textView_photo_2) as TextView
        textView1.setOnClickListener {
            pop.dismiss()
            pickPhotoFromGallery(activity, REQUEST_CODE_PICKPHOTOFROMGALLERY)
        }
        textView2.setOnClickListener {
            pop.dismiss()
            newFileInfo("")
            pickPhotoFromTakePhoto(activity, currentFile, REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO)
        }
        pop.showScreenBottom(
            clickParentView,
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            0,
            null
        )
    }

    /**
     * 显示相册与拍照popupWindow
     */
    fun showTakephotoGalleryCancelPopupWindow(activity: Activity, clickParentView: View) {
        val pop = KPopupWindowUtil()
        val view = LayoutInflater.from(activity).inflate(R.layout.ny_popupwindow_takephoto_gallery_cancel, null)
        val textView3 = view.findViewById(R.id.textView_photo_3) as TextView
        val textView2 = view.findViewById(R.id.textView_photo_2) as TextView
        val textView1 = view.findViewById(R.id.textView_photo_1) as TextView
        textView1.setOnClickListener {
            pop.dismiss()
            pickPhotoFromGallery(activity, REQUEST_CODE_PICKPHOTOFROMGALLERY)
        }
        textView2.setOnClickListener {
            pop.dismiss()
            newFileInfo("")
            pickPhotoFromTakePhoto(activity, currentFile, REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO)
        }
        textView3.setOnClickListener { pop.dismiss() }
        pop.showScreenBottom(
            clickParentView,
            view,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            0,
            0,
            null
        )
    }

    /**
     * outputX * outputY <= 129900
     */
    fun activityResult(
        activity: Activity,
        requestCode: Int,
        resultCode: Int,
        data: Intent,
        imageView: ImageView,
        outputX: Int,
        outputY: Int,
        handler: KIActivityResultHandler?
    ) {
        if (resultCode == 0) {
            handler?.cancel()
            return
        }
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(activity, "操作失败", Toast.LENGTH_LONG).show()
            handler?.fail()
            return
        }
        when (requestCode) {
            REQUEST_CODE_PICKPHOTOFROMGALLERY// 相册..结果
            -> result_pickPhotoFromGallery(activity, resultCode, data, imageView, outputX, outputY, handler)
            REQUEST_CODE_PICKPHOTOFROMTAKEPHOTO// 拍照..结果
            -> result_pickPhotoFromTakePhoto(activity, resultCode, imageView, outputX, outputY, handler)
            REQUEST_CODE_PHOTOZOOMCLIP// 裁剪图片
            -> result_photoZoomClip(activity, resultCode, data, imageView, handler)
            else -> {
            }
        }
    }

    /**
     * Gallery 相册
     */
    fun pickPhotoFromGallery(activity: Activity, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!KPickPhotoUtil.checkSelfPermissionGallery6(activity)) {
                return
            }
            //打开相册
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            activity.startActivityForResult(intent, requestCode)

        } else {
            val intent = Intent(Intent.ACTION_PICK, null)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            activity.startActivityForResult(intent, requestCode)
        }
    }

    /**
     * Take Photo 照相
     */
    fun pickPhotoFromTakePhoto(activity: Activity, imageFile: File?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!KPickPhotoUtil.checkSelfPermissionTakePhoto6(activity)) {
                return
            }
            var imageUri = Uri.fromFile(imageFile)
            //判断Android版本是否是Android7.0以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val authority = activity.packageName + ".generic.file.provider"
                imageUri = FileProvider.getUriForFile(activity, authority, imageFile!!)
            }
            //启动相机程序
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            activity.startActivityForResult(intent, requestCode)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile))
            activity.startActivityForResult(intent, requestCode)
        }
    }

    /**
     * Clip Zoom Photo 相册 裁剪图片 outputX * outputY <= 129900
     *
     * @param activity
     * @param file
     * @param outputX
     * @param outputY
     * @param saveFile    6.0及以上系统使用
     * @param requestCode
     */
    fun photoZoomClip(activity: Activity, file: File, outputX: Int, outputY: Int, saveFile: File?, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val intent = Intent("com.android.camera.action.CROP")
                intent.setDataAndType(
                    KPickPhotoUtil.getImageContentUri(activity, file),
                    "image/*"
                )//自己使用Content Uri替换File Uri
                intent.putExtra("crop", "true")
                //aspectX==aspectY?裁剪框可能为圆形:为矩形
                intent.putExtra("aspectX", outputX)
                intent.putExtra("aspectY", outputY)
                intent.putExtra("outputX", outputX)
                intent.putExtra("outputY", outputY)
                intent.putExtra("scale", true)
                intent.putExtra("return-data", false)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveFile))//定义输出的File Uri
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
                intent.putExtra("noFaceDetection", true)
                activity.startActivityForResult(intent, requestCode)
            } catch (e: ActivityNotFoundException) {
                val errorMessage = "Your device doesn't support the crop action!"
                Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        } else {
            val intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(Uri.fromFile(file), "image/*")
            intent.putExtra("crop", "true")
            //aspectX==aspectY?裁剪框可能为圆形:为矩形
            intent.putExtra("aspectX", outputX)
            intent.putExtra("aspectY", outputY)
            intent.putExtra("outputX", outputX)
            intent.putExtra("outputY", outputY)
            intent.putExtra("return-data", true)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    fun result_pickPhotoFromGallery(
        activity: Activity,
        resultCode: Int,
        data: Intent,
        imageView: ImageView?,
        outputX: Int,
        outputY: Int,
        handler: KIActivityResultHandler?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var imagePath = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //Android4.4及以上版本
                imagePath = KPickPhotoUtil.uriToPath6(activity, data.data!!)!!
            } else {
                //Android4.4以下版本
                imagePath = KPickPhotoUtil.getImagePath6(activity, data.data!!, null)!!
            }
            currentFile = File(imagePath)
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 8
                    val bm = BitmapFactory.decodeFile(currentFile!!.absolutePath, options)
                    imageView.setImageBitmap(bm)
                }
                handler?.pickPhotoFromGallerySuccess()
            } else {
                newFileInfo("C")
                photoZoomClip(activity, File(imagePath), outputX, outputY, currentFile, REQUEST_CODE_PHOTOZOOMCLIP)
            }
        } else {
            val uri = data.data
            val path = uri!!.path
            var imagePath = ""
            if (path != null && path.lastIndexOf(".jpg") == path.length - 4) {
                imagePath = path
            } else {
                imagePath = KPickPhotoUtil.getFilePathFromUri(activity, data.data!!)
            }
            currentFile = File(imagePath)
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 8
                    val bm = BitmapFactory.decodeFile(currentFile!!.absolutePath, options)
                    imageView.setImageBitmap(bm)
                }
                handler?.pickPhotoFromGallerySuccess()
            } else {
                photoZoomClip(activity, currentFile!!, outputX, outputY, null, REQUEST_CODE_PHOTOZOOMCLIP)
            }
        }
    }

    fun result_pickPhotoFromTakePhoto(
        activity: Activity,
        resultCode: Int,
        imageView: ImageView?,
        outputX: Int,
        outputY: Int,
        handler: KIActivityResultHandler?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 8
                    val bm = BitmapFactory.decodeFile(currentFile!!.absolutePath, options)
                    imageView.setImageBitmap(bm)
                }
                handler?.pickPhotoFromTakePhotoSuccess()
            } else {
                val file = File(currentFile!!.absolutePath)
                newFileInfo("C")
                photoZoomClip(activity, file, outputX, outputY, currentFile, REQUEST_CODE_PHOTOZOOMCLIP)
            }
        } else {
            if (outputX < 1 || outputY < 1) {
                if (imageView != null) {
                    val options = BitmapFactory.Options()
                    options.inSampleSize = 8
                    val bm = BitmapFactory.decodeFile(currentFile!!.absolutePath, options)
                    imageView.setImageBitmap(bm)
                }
                handler?.pickPhotoFromTakePhotoSuccess()
            } else {
                photoZoomClip(activity, currentFile!!, outputX, outputY, null, REQUEST_CODE_PHOTOZOOMCLIP)
            }
        }
    }

    fun result_photoZoomClip(
        activity: Activity,
        resultCode: Int,
        data: Intent,
        imageView: ImageView?,
        handler: KIActivityResultHandler?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val imagePath = currentFile!!.absolutePath
            val bitmap = BitmapFactory.decodeFile(imagePath)
            imageView?.setImageBitmap(bitmap)
            handler?.photoZoomClipSuccess()
        } else {
            val extras = data.extras
            if (extras != null) {
                val bitmap = extras.getParcelable<Bitmap>("data")
                newFileInfo("C")
                KSavePhotoUtil.saveImage2File(bitmap!!, Bitmap.CompressFormat.JPEG, currentFile!!)
                imageView?.setImageBitmap(bitmap)
                handler?.photoZoomClipSuccess()
            } else {
                Toast.makeText(activity, "裁剪图片失败,没有获取到裁剪的图片", Toast.LENGTH_LONG).show()
            }
        }
    }
}